package com.greenkeycompany.exam;

import android.support.annotation.NonNull;

/**
 * Created by tert0 on 04.10.2017.
 */

public interface FragmentListener {
    void requestToSetMainMenuFragment();
    void requestToSetChapterDetailFragment(int chapterId);

    void requestToSetTrainingMenuFragment(int ruleId);
    void requestToSetRuleDescriptionFragment(int ruleId);
    void requestToSetRuleDetailFragment(int ruleId);

    void requestToSetWordCardTrainingFragment(@NonNull TrainingType trainingType, int id);
    void requestToSetWordCardResultFragment(@NonNull TrainingType trainingType, int resultId, int[] wrongAnswerWordCardIds);
}