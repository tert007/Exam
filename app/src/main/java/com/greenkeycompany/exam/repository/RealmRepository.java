package com.greenkeycompany.exam.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.greenkeycompany.exam.repository.model.Chapter;
import com.greenkeycompany.exam.repository.model.ChapterResult;
import com.greenkeycompany.exam.repository.model.Rule;
import com.greenkeycompany.exam.repository.model.RulePointResult;
import com.greenkeycompany.exam.repository.model.RuleResult;
import com.greenkeycompany.exam.repository.model.RulePoint;
import com.greenkeycompany.exam.repository.model.WordCard;
import com.greenkeycompany.exam.repository.realm.IdentityRealmObject;

import java.util.List;

import io.realm.Realm;
import io.realm.Sort;

/**
 * Created by tert0 on 11.10.2017.
 */

public class RealmRepository implements IRepository {

    private Realm realm;
    public RealmRepository(@NonNull Realm realm) {
        this.realm = realm;
    }

    public void close() {
        realm.close();
    }

    private int getNextId(Class objectClass) {
        Number id = realm.where(objectClass).
                max(IdentityRealmObject.FILED_ID);

        return (id == null) ? 1 : id.intValue() + 1;
    }

    @Nullable
    @Override
    public Chapter getChapter(int id) {
        return realm.where(Chapter.class).
                equalTo(Chapter.FILED_ID, id).
                findFirst();
    }

    @NonNull
    @Override
    public List<Chapter> getChapterList() {
        return realm.where(Chapter.class).
                findAll();
    }

    @Nullable
    @Override
    public Rule getRule(int id) {
        return realm.where(Rule.class).
                equalTo(Rule.FILED_ID, id).
                findFirst();
    }

    @NonNull
    @Override
    public List<Rule> getRuleList(int chapterId) {
        return realm.where(Rule.class).
                equalTo(Rule.FIELD_CHAPTER_ID, chapterId).
                findAll();
    }

    @Nullable
    @Override
    public RulePoint getRulePoint(int id) {
        return realm.where(RulePoint.class).
                equalTo(RulePoint.FILED_ID, id).
                findFirst();
    }

    @NonNull
    @Override
    public List<RulePoint> getRulePointList(int ruleId) {
        return realm.where(RulePoint.class).
                equalTo(RulePoint.FIELD_RULE_ID, ruleId).
                findAll();
    }

    @Nullable
    @Override
    public WordCard getWordCard(int id) {
        return realm.where(WordCard.class).
                equalTo(WordCard.FILED_ID, id).
                findFirst();
    }

    @NonNull
    @Override
    public List<WordCard> getWordCardList() {
        return realm.where(WordCard.class).
                findAll();
    }

    @NonNull
    @Override
    public List<WordCard> getWordCardListByChapter(int chapterId) {
        return realm.where(WordCard.class).
                equalTo(WordCard.FILED_CHAPTER_ID, chapterId).
                findAll();
    }

    @NonNull
    @Override
    public List<WordCard> getWordCardListByRule(int ruleId) {
        return realm.where(WordCard.class).
                equalTo(WordCard.FILED_RULE_ID, ruleId).
                findAll();
    }

    @NonNull
    @Override
    public List<WordCard> getWordCardListByRulePoint(int rulePointId) {
        return realm.where(WordCard.class).
                equalTo(WordCard.FILED_RULE_POINT_ID, rulePointId).
                findAll();
    }

    @Override
    public void addRulePointResult(final int rulePointId, final int wordCardCompletedCount, final long unixTime,
                                   final Listener listener) {

        final int nextId = getNextId(RulePointResult.class);

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RulePoint rulePoint = realm.where(RulePoint.class).
                        equalTo(RulePoint.FILED_ID, rulePointId).
                        findFirst();

                RulePointResult result = realm.createObject(RulePointResult.class, nextId);
                result.setRulePoint(rulePoint);
                result.setWordCardCompletedCount(wordCardCompletedCount);
                result.setUnixTime(unixTime);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (listener != null)
                    listener.onAdded(nextId);
            }
        });
    }

    @Override
    public void addRuleResult(final int ruleId, final float score, final long unixTime,
                              final Listener listener) {

        final int nextId = getNextId(RuleResult.class);

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Rule rule = realm.where(Rule.class).
                        equalTo(Rule.FILED_ID, ruleId).
                        findFirst();

                RuleResult result = realm.createObject(RuleResult.class, nextId);
                result.setRule(rule);
                result.setScore(score);
                result.setUnixTime(unixTime);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (listener != null)
                    listener.onAdded(nextId);
            }
        });
    }

    @Override
    public void addChapterResult(final int chapterId, final float score, final long unixTime,
                                 final Listener listener) {

        final int nextId = getNextId(ChapterResult.class);

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Chapter chapter = realm.where(Chapter.class).
                        equalTo(Chapter.FILED_ID, chapterId).
                        findFirst();

                ChapterResult result = realm.createObject(ChapterResult.class, nextId);
                result.setChapter(chapter);
                result.setScore(score);
                result.setUnixTime(unixTime);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (listener != null)
                    listener.onAdded(nextId);
            }
        });
    }

    @Override
    public void updateRule(final int ruleId, final boolean descriptionCompleted) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Rule rule = getRule(ruleId);
                rule.setDescriptionCompleted(descriptionCompleted);
            }
        });
    }

    @Override
    public void updateRulePoint(final int rulePointId, final boolean trainingCompleted) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RulePoint rulePoint = getRulePoint(rulePointId);
                rulePoint.setTrainingCompleted(trainingCompleted);
            }
        });
    }

    @Nullable
    @Override
    public RulePointResult getRulePointResult(int id) {
        return realm.where(RulePointResult.class).
                equalTo(RulePointResult.FILED_ID, id).
                findFirst();
    }

    @Nullable
    @Override
    public RulePointResult getBestRulePointResult(int rulePointId) {
        return realm.where(RulePointResult.class).
                equalTo(RulePointResult.FIELD_RULE_POINT_ID, rulePointId).
                findAllSorted(RulePointResult.FIELD_WORD_CARD_COMPLETED_COUNT_, Sort.DESCENDING).
                first(null);
    }

    @Nullable
    @Override
    public RuleResult getRuleResult(int id) {
        return realm.where(RuleResult.class).
                equalTo(RuleResult.FILED_ID, id).
                findFirst();
    }

    @Nullable
    @Override
    public RuleResult getBestRuleResult(int ruleId) {
        return realm.where(RuleResult.class).
                equalTo(RuleResult.FIELD_RULE_ID, ruleId).
                findAllSorted(RuleResult.FILED_SCORE, Sort.DESCENDING).
                first(null);
    }

    @Nullable
    @Override
    public ChapterResult getChapterResult(int id) {
        return realm.where(ChapterResult.class).
                equalTo(ChapterResult.FILED_ID, id).
                findFirst();
    }

    @Nullable
    @Override
    public ChapterResult getBestChapterResult(int chapterId) {
        return realm.where(ChapterResult.class).
                equalTo(ChapterResult.FIELD_CHAPTER_ID, chapterId).
                findAllSorted(ChapterResult.FILED_SCORE, Sort.DESCENDING).
                first(null);
    }
}
