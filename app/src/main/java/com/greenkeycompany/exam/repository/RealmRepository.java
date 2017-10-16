package com.greenkeycompany.exam.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.greenkeycompany.exam.repository.model.Chapter;
import com.greenkeycompany.exam.repository.model.Rule;
import com.greenkeycompany.exam.repository.model.RuleResult;
import com.greenkeycompany.exam.repository.model.RulePoint;
import com.greenkeycompany.exam.repository.model.WordCard;
import com.greenkeycompany.exam.repository.realm.IdentityRealmObject;
import com.greenkeycompany.exam.repository.realm.ResultRealmObject;

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
                equalTo(Chapter.FILED_ID, id).
                findFirst();
    }

    @NonNull
    @Override
    public List<Rule> getRuleList(int chapterId) {
        return realm.where(Rule.class).
                findAll();
    }

    @Nullable
    @Override
    public RulePoint getRulePoint(int id) {
        return realm.where(RulePoint.class).
                equalTo(Chapter.FILED_ID, id).
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
                equalTo(Chapter.FILED_ID, id).
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
    public int getWordCardCountByRule(int ruleId) {
        long count = realm.where(WordCard.class).
                equalTo(WordCard.FILED_RULE_ID, ruleId).
                count();

        return (int) count;
    }

    @Override
    public int getWordCardCountByRulePoint(int rulePointId) {
        long count = realm.where(WordCard.class).
                equalTo(WordCard.FILED_RULE_POINT_ID, rulePointId).
                count();

        return (int) count;
    }

    @Override
    public void addRuleResult(final int ruleId, final float score, final long unixTime) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Rule rule = getRule(ruleId);

                int nextId = getNextId(RuleResult.class);

                RuleResult result = realm.createObject(RuleResult.class, nextId);
                result.setRule(rule);
                result.setScore(score);
                result.setUnixTime(unixTime);
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
    public void updateRulePoint(final int rulePointId, final int wordCardCompletedCount, final boolean trainingCompleted) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RulePoint rulePoint = getRulePoint(rulePointId);
                rulePoint.setCompleted(trainingCompleted);
                rulePoint.setWordCardCompletedCount(wordCardCompletedCount);
            }
        });
    }

    @Nullable
    @Override
    public RuleResult getBestRuleExamResult(int ruleId) {
        return realm.where(RuleResult.class).
                equalTo(RuleResult.FIELD_RULE_ID, ruleId).
                findAllSorted(ResultRealmObject.FILED_SCORE, Sort.DESCENDING).
                first(null);
    }
}
