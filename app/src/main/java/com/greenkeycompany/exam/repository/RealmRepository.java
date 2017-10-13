package com.greenkeycompany.exam.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.greenkeycompany.exam.repository.model.Chapter;
import com.greenkeycompany.exam.repository.model.Rule;
import com.greenkeycompany.exam.repository.model.RulePoint;
import com.greenkeycompany.exam.repository.model.RulePointTrainingResult;
import com.greenkeycompany.exam.repository.model.RuleTrainingResult;
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
    public void addRuleTrainingResult(final int ruleId, final float score, final long unixTime) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Rule rule = getRule(ruleId);

                int nextId = getNextId(RuleTrainingResult.class);

                RuleTrainingResult result = realm.createObject(RuleTrainingResult.class, nextId);
                result.setRule(rule);
                result.setScore(score);
                result.setUnixTime(unixTime);
            }
        });
    }

    @Override
    public void addRulePointResult(final int rulePointId, final float score, final long unixTime) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RulePoint rulePoint = getRulePoint(rulePointId);

                int nextId = getNextId(RulePointTrainingResult.class);

                RulePointTrainingResult result = realm.createObject(RulePointTrainingResult.class, nextId);
                result.setRulePoint(rulePoint);
                result.setScore(score);
                result.setUnixTime(unixTime);
            }
        });
    }

    @Nullable
    @Override
    public RuleTrainingResult getLastRuleTrainingResult(int ruleId) {
        return realm.where(RuleTrainingResult.class).
                equalTo(RuleTrainingResult.FIELD_RULE_ID, ruleId).
                findAllSorted(IdentityRealmObject.FILED_ID, Sort.DESCENDING).
                first();
    }

    @Nullable
    @Override
    public RuleTrainingResult getBestRuleTrainingResult(int ruleId) {
        return realm.where(RuleTrainingResult.class).
                equalTo(RuleTrainingResult.FIELD_RULE_ID, ruleId).
                findAllSorted(ResultRealmObject.FILED_SCORE, Sort.DESCENDING).
                first();
    }

    @Nullable
    @Override
    public RulePointTrainingResult getLastRulePointTrainingResult(int rulePointId) {
        return realm.where(RulePointTrainingResult.class).
                equalTo(RulePointTrainingResult.FIELD_RULE_POINT_ID, rulePointId).
                findAllSorted(IdentityRealmObject.FILED_ID, Sort.DESCENDING).
                first();
    }

    @Nullable
    @Override
    public RulePointTrainingResult getBestRulePointTrainingResult(int rulePointId) {
        return realm.where(RulePointTrainingResult.class).
                equalTo(RulePointTrainingResult.FIELD_RULE_POINT_ID, rulePointId).
                findAllSorted(ResultRealmObject.FILED_SCORE, Sort.DESCENDING).
                first();
    }
}
