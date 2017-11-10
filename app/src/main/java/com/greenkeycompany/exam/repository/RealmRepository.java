package com.greenkeycompany.exam.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.greenkeycompany.exam.repository.model.Chapter;
import com.greenkeycompany.exam.repository.model.WordCardSet;
import com.greenkeycompany.exam.repository.model.result.ChapterExamResult;
import com.greenkeycompany.exam.repository.model.Rule;
import com.greenkeycompany.exam.repository.model.result.FinalExamResult;
import com.greenkeycompany.exam.repository.model.result.WordCardSetResult;
import com.greenkeycompany.exam.repository.model.result.RuleExamResult;
import com.greenkeycompany.exam.repository.model.RulePoint;
import com.greenkeycompany.exam.repository.model.WordCard;
import com.greenkeycompany.exam.repository.model.status.RuleStatus;
import com.greenkeycompany.exam.repository.model.status.WordCardSetStatus;
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
    public WordCardSet getWordCardSet(int id) {
        return realm.where(WordCardSet.class).
                equalTo(WordCardSet.FILED_ID, id).
                findFirst();
    }

    @NonNull
    @Override
    public List<WordCardSet> getWordCardSetList(int ruleId) {
        return realm.where(WordCardSet.class).
                equalTo(WordCardSet.FILED_RULE_ID, ruleId).
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
    public List<WordCard> getWordCardListByWordCardSet(int wordCardSetId) {
        return realm.where(WordCard.class).
                equalTo(WordCard.FILED_WORD_CARD_SET_ID, wordCardSetId).
                findAll();
    }


    @Override
    public void addOrUpdateRuleStatus(final int ruleId, final boolean learned) {
        RuleStatus ruleStatus = getRuleStatus(ruleId);
        if (ruleStatus != null) {
            ruleStatus.setLearned(learned);
        } else {
            final int nextId = getNextId(RuleStatus.class);

            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RuleStatus ruleStatus = realm.createObject(RuleStatus.class, nextId);
                    ruleStatus.setRuleId(ruleId);
                    ruleStatus.setLearned(learned);
                }
            });
        }
    }

    @Nullable
    @Override
    public RuleStatus getRuleStatus(int ruleId) {
        return realm.where(RuleStatus.class).
                equalTo(RuleStatus.FIELD_RULE_ID, ruleId).
                findFirst();
    }

    @Override
    public void addOrUpdateWordCardSetStatus(final int wordCardSetId, final boolean completed) {
        WordCardSetStatus wordCardSetStatus = getWordCardSetStatus(wordCardSetId);
        if (wordCardSetStatus != null) {
            wordCardSetStatus.setCompleted(completed);
        } else {
            final int nextId = getNextId(WordCardSetStatus.class);

            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    WordCardSetStatus wordCardSetStatus = realm.createObject(WordCardSetStatus.class, nextId);
                    wordCardSetStatus.setWordCardSetId(wordCardSetId);
                    wordCardSetStatus.setCompleted(completed);
                }
            });
        }
    }

    @Nullable
    @Override
    public WordCardSetStatus getWordCardSetStatus(int wordCardSetId) {
        return realm.where(WordCardSetStatus.class).
                equalTo(WordCardSetStatus.FIELD_WORD_CARD_SET_ID, wordCardSetId).
                findFirst();
    }

    @Override
    public void addWordCardSetResult(final int wordCardSetId, final int wordCardCompletedCount, final long unixTime,
                                     final Listener listener) {

        final int nextId = getNextId(WordCardSetResult.class);

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                WordCardSetResult result = realm.createObject(WordCardSetResult.class, nextId);
                result.setWordCardSetId(wordCardSetId);
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
    public void addRuleExamResult(final int ruleId, final float score, final long unixTime,
                                  final Listener listener) {

        final int nextId = getNextId(RuleExamResult.class);

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RuleExamResult result = realm.createObject(RuleExamResult.class, nextId);
                result.setRuleId(ruleId);
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
    public void addChapterExamResult(final int chapterId, final float score, final long unixTime,
                                     final Listener listener) {

        final int nextId = getNextId(ChapterExamResult.class);

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ChapterExamResult result = realm.createObject(ChapterExamResult.class, nextId);
                result.setChapterId(chapterId);
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
    public void addFinalExamResult(final float score, final long unixTime,
                                   final Listener listener) {

        final int nextId = getNextId(FinalExamResult.class);

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                FinalExamResult result = realm.createObject(FinalExamResult.class, nextId);
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

    @Nullable
    @Override
    public WordCardSetResult getWordCardSetResult(int id) {
        return realm.where(WordCardSetResult.class).
                equalTo(WordCardSetResult.FILED_ID, id).
                findFirst();
    }

    @Nullable
    @Override
    public WordCardSetResult getBestWordCardSetResult(int wordCardSetId) {
        return realm.where(WordCardSetResult.class).
                equalTo(WordCardSetResult.FIELD_WORD_CARD_SET_ID, wordCardSetId).
                findAllSorted(WordCardSetResult.FIELD_WORD_CARD_COMPLETED_COUNT_, Sort.DESCENDING).
                first(null);
    }

    @Nullable
    @Override
    public RuleExamResult getRuleExamResult(int id) {
        return realm.where(RuleExamResult.class).
                equalTo(RuleExamResult.FILED_ID, id).
                findFirst();
    }

    @Nullable
    @Override
    public RuleExamResult getBestRuleExamResult(int ruleId) {
        return realm.where(RuleExamResult.class).
                equalTo(RuleExamResult.FIELD_RULE_ID, ruleId).
                findAllSorted(RuleExamResult.FILED_SCORE, Sort.DESCENDING).
                first(null);
    }

    @Nullable
    @Override
    public ChapterExamResult getChapterExamResult(int id) {
        return realm.where(ChapterExamResult.class).
                equalTo(ChapterExamResult.FILED_ID, id).
                findFirst();
    }

    @Nullable
    @Override
    public ChapterExamResult getBestChapterExamResult(int chapterId) {
        return realm.where(ChapterExamResult.class).
                equalTo(ChapterExamResult.FIELD_CHAPTER_ID, chapterId).
                findAllSorted(ChapterExamResult.FILED_SCORE, Sort.DESCENDING).
                first(null);
    }

    @Nullable
    @Override
    public FinalExamResult getFinalExamResult(int id) {
        return realm.where(FinalExamResult.class).
                equalTo(FinalExamResult.FILED_ID, id).
                findAllSorted(ChapterExamResult.FILED_SCORE, Sort.DESCENDING).
                first(null);
    }

    @Nullable
    @Override
    public FinalExamResult getBestFinalExamResult() {
        return realm.where(FinalExamResult.class).
                findAllSorted(FinalExamResult.FILED_SCORE, Sort.DESCENDING).
                first(null);
    }
}
