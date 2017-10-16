package com.greenkeycompany.exam.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.greenkeycompany.exam.repository.model.Chapter;
import com.greenkeycompany.exam.repository.model.Rule;
import com.greenkeycompany.exam.repository.model.RuleResult;
import com.greenkeycompany.exam.repository.model.RulePoint;
import com.greenkeycompany.exam.repository.model.WordCard;

import java.util.List;

/**
 * Created by tert0 on 07.10.2017.
 */

public interface IRepository {

    @Nullable Chapter getChapter(int id);
    @NonNull List<Chapter> getChapterList();

    @Nullable Rule getRule(int id);
    @NonNull List<Rule> getRuleList(int chapterId);

    @Nullable RulePoint getRulePoint(int id);
    @NonNull List<RulePoint> getRulePointList(int ruleId);

    @Nullable WordCard getWordCard(int id);
    @NonNull List<WordCard> getWordCardList();
    @NonNull List<WordCard> getWordCardListByRule(int ruleId);
    @NonNull List<WordCard> getWordCardListByRulePoint(int rulePointId);

    int getWordCardCountByRule(int ruleId);
    int getWordCardCountByRulePoint(int rulePointId);

    void updateRule(int ruleId, boolean descriptionCompleted);
    void updateRulePoint(int rulePointId, int wordCardCompletedCount, boolean trainingCompleted);


    void addRuleResult(int ruleId, float score, long unixTime);
    @Nullable
    RuleResult getBestRuleExamResult(int ruleId);
}
