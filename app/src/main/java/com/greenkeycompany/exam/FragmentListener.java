package com.greenkeycompany.exam;

/**
 * Created by tert0 on 04.10.2017.
 */

public interface FragmentListener {
    void requestToSetMainMenuFragment();

    void requestToSetChapterFragment(int chapterId);

    void requestToSetTrainingMenuFragment(int ruleId);

    void requestToSetRuleFragment(int ruleId);
    void requestToSetRuleDescriptionFragment(int ruleId);

    void requestToSetWordCardTrainingFragment();
    void requestToSetWordCardRuleTrainingFragment(int ruleId);
    void requestToSetWordCardRulePointTrainingFragment(int rulePointId);
}