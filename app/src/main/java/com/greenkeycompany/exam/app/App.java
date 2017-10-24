package com.greenkeycompany.exam.app;

import android.app.Application;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import com.greenkeycompany.exam.R;
import com.greenkeycompany.exam.repository.model.Chapter;
import com.greenkeycompany.exam.repository.model.Rule;
import com.greenkeycompany.exam.repository.model.RulePoint;
import com.greenkeycompany.exam.repository.model.WordCard;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by tert0 on 11.10.2017.
 */

public class App extends Application {

    private static class RealmHelper {

        private Resources resources;
        RealmHelper(@NonNull Resources resources) {
            this.resources = resources;
        }

        String[] getChapterTitles() {
            return resources.getStringArray(R.array.chapter_titles);
        }

        String[] getRuleTitles(int chapterId) {
            switch (chapterId) {
                case 1: return resources.getStringArray(R.array.chapter_1_rule_titles);

                default: return null;
            }
        }

        String[] getRulePointTitles(int ruleId) {
            switch (ruleId) {
                case 1: return resources.getStringArray(R.array.rule_1_rule_point_titles);

                default: return null;
            }
        }
        String[] getRulePointDescriptions(int ruleId) {
            switch (ruleId) {
                case 1: return resources.getStringArray(R.array.rule_1_rule_point_descriptions);

                default: return null;
            }
        }

        String[] getWordCardCorrectWords(int rulePointId) {
            switch (rulePointId) {
                case 1: return resources.getStringArray(R.array.rule_point_1_word_card_correct_words);
                case 2: return resources.getStringArray(R.array.rule_point_2_word_card_correct_words);
                case 3: return resources.getStringArray(R.array.rule_point_3_word_card_correct_words);
                case 4: return resources.getStringArray(R.array.rule_point_4_word_card_correct_words);

                default: return null;
            }
        }
        String[] getWordCardIncorrectWords(int rulePointId) {
            switch (rulePointId) {
                case 1: return resources.getStringArray(R.array.rule_point_1_word_card_incorrect_words);
                case 2: return resources.getStringArray(R.array.rule_point_2_word_card_incorrect_words);
                case 3: return resources.getStringArray(R.array.rule_point_3_word_card_incorrect_words);
                case 4: return resources.getStringArray(R.array.rule_point_4_word_card_incorrect_words);

                default: return null;
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.Builder().initialData(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                int chapterId = 1;
                int ruleId = 1;
                int rulePointId = 1;
                int wordCardId = 1;

                RealmHelper realmHelper = new RealmHelper(getResources());

                for (String chapterTitle : realmHelper.getChapterTitles()) {
                    Chapter chapter = realm.createObject(Chapter.class, chapterId);
                    chapter.setTitle(chapterTitle);

                    for (String ruleTitle : realmHelper.getRuleTitles(chapterId)) {
                        Rule rule = realm.createObject(Rule.class, ruleId);
                        rule.setTitle(ruleTitle);
                        rule.setChapter(chapter);

                        String[] rulePointTitles = realmHelper.getRulePointTitles(ruleId);
                        String[] rulePointDescriptions = realmHelper.getRulePointDescriptions(ruleId);

                        for (int rulePointIndex = 0; rulePointIndex < rulePointTitles.length; rulePointIndex++) {
                            RulePoint rulePoint = realm.createObject(RulePoint.class, rulePointId);
                            rulePoint.setRule(rule);
                            rulePoint.setTitle(rulePointTitles[rulePointIndex]);
                            rulePoint.setDescription(rulePointDescriptions[rulePointIndex]);

                            String[] wordCardCorrectWords = realmHelper.getWordCardCorrectWords(rulePointId);
                            String[] wordCardIncorrectWords = realmHelper.getWordCardIncorrectWords(rulePointId);

                            for (int wordCardIndex = 0; wordCardIndex < wordCardCorrectWords.length; wordCardIndex++) {
                                WordCard wordCard = realm.createObject(WordCard.class, wordCardId++);
                                wordCard.setRulePoint(rulePoint);
                                wordCard.setCorrectWord(wordCardCorrectWords[wordCardIndex]);
                                wordCard.setIncorrectWord(wordCardIncorrectWords[wordCardIndex]);
                            }
                            rulePointId++;
                        }
                        ruleId++;
                    }
                    chapterId++;
                }
            }
        }).deleteRealmIfMigrationNeeded().build();

        Realm.setDefaultConfiguration(config);
    }

}
