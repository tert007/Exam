package com.greenkeycompany.exam.main.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.greenkeycompany.exam.main.repository.model.Rule;
import com.greenkeycompany.exam.main.repository.model.RulePoint;
import com.greenkeycompany.exam.main.repository.model.WordCard;
import com.greenkeycompany.exam.main.repository.model.WordCardSet;
import com.greenkeycompany.exam.main.repository.model.result.ChapterExamResult;
import com.greenkeycompany.exam.main.repository.model.result.FinalExamResult;
import com.greenkeycompany.exam.main.repository.model.result.RuleExamResult;
import com.greenkeycompany.exam.main.repository.model.result.WordCardSetResult;
import com.greenkeycompany.exam.main.repository.model.status.RuleStatus;
import com.greenkeycompany.exam.main.repository.model.status.WordCardSetStatus;
import com.greenkeycompany.exam.main.repository.model.Chapter;

import java.util.List;

/**
 * Created by tert0 on 07.10.2017.
 */

public interface IRepository {

    @Nullable Chapter getChapter(int id);
    @NonNull List<Chapter> getChapterList();

    @Nullable
    Rule getRule(int id);
    @NonNull List<Rule> getRuleList(int chapterId);

    @Nullable
    RulePoint getRulePoint(int id);
    @NonNull List<RulePoint> getRulePointList(int ruleId);

    @Nullable
    WordCardSet getWordCardSet(int id);
    @NonNull List<WordCardSet> getWordCardSetList(int ruleId);

    @Nullable
    WordCard getWordCard(int id);
    @NonNull List<WordCard> getWordCardList();
    @NonNull List<WordCard> getWordCardListByChapter(int chapterId);
    @NonNull List<WordCard> getWordCardListByRule(int ruleId);
    @NonNull List<WordCard> getWordCardListByWordCardSet(int wordCardSetId);

    interface Listener {
        void onAdded(int id);
    }

    void addOrUpdateRuleStatus(int ruleId, boolean learned);
    @Nullable
    RuleStatus getRuleStatus(int ruleId);

    void addOrUpdateWordCardSetStatus(int wordCardSetId, boolean completed);
    @Nullable
    WordCardSetStatus getWordCardSetStatus(int wordCardSetId);

    void addWordCardSetResult(int wordCardSetId, int wordCardCompletedCount, long unixTime, Listener listener);
    @Nullable
    WordCardSetResult getWordCardSetResult(int id);
    @Nullable WordCardSetResult getBestWordCardSetResult(int wordCardSetId);

    void addRuleExamResult(int ruleId, float score, long unixTime, Listener listener);
    @Nullable
    RuleExamResult getRuleExamResult(int id);
    @Nullable RuleExamResult getBestRuleExamResult(int ruleId);

    void addChapterExamResult(int chapterId, float score, long unixTime, Listener listener);
    @Nullable
    ChapterExamResult getChapterExamResult(int id);
    @Nullable ChapterExamResult getBestChapterExamResult(int chapterId);

    void addFinalExamResult(float score, long unixTime, Listener listener);
    @Nullable
    FinalExamResult getFinalExamResult(int id);
    @Nullable FinalExamResult getBestFinalExamResult();
}
