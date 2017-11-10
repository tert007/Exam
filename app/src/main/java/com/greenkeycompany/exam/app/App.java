package com.greenkeycompany.exam.app;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;

import com.greenkeycompany.exam.PremiumUtil;
import com.greenkeycompany.exam.R;
import com.greenkeycompany.exam.repository.model.Chapter;
import com.greenkeycompany.exam.repository.model.Rule;
import com.greenkeycompany.exam.repository.model.RulePoint;
import com.greenkeycompany.exam.repository.model.WordCard;
import com.greenkeycompany.exam.repository.model.WordCardSet;

import org.solovyev.android.checkout.Billing;

import java.io.IOException;

import javax.annotation.Nonnull;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by tert0 on 11.10.2017.
 */

public class App extends Application {

    private PremiumUtil premiumUtil;
    public PremiumUtil getPremiumUtil() {
        return premiumUtil;
    }

    @Nonnull
    private final Billing billing = new Billing(this, new Billing.DefaultConfiguration() {
        @Nonnull
        @Override
        public String getPublicKey() {
            return "AAAAAAAAAAAAAAAAAAAAAAA";
        }
    });

    @Nonnull
    public Billing getBilling() {
        return billing;
    }

    private static App instance;
    public static App get() {
        return instance;
    }

    /*
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
                case 2: return resources.getStringArray(R.array.chapter_2_rule_titles);

                default: return null;
            }
        }

        String[] getRulePointTitles(int ruleId) {
            switch (ruleId) {
                case 1: return resources.getStringArray(R.array.rule_1_rule_point_titles);
                case 2: return resources.getStringArray(R.array.rule_2_rule_point_titles);
                case 3: return resources.getStringArray(R.array.rule_3_rule_point_titles);
                case 4: return resources.getStringArray(R.array.rule_4_rule_point_titles);
                case 5: return resources.getStringArray(R.array.rule_5_rule_point_titles);

                default: return null;
            }
        }
        String[] getRulePointDescriptions(int ruleId) {
            switch (ruleId) {
                case 1: return resources.getStringArray(R.array.rule_1_rule_point_descriptions);
                case 2: return resources.getStringArray(R.array.rule_2_rule_point_descriptions);
                case 3: return resources.getStringArray(R.array.rule_3_rule_point_descriptions);
                case 4: return resources.getStringArray(R.array.rule_4_rule_point_descriptions);
                case 5: return resources.getStringArray(R.array.rule_5_rule_point_descriptions);

                default: return null;
            }
        }

        int[] getWordCardTrainingCounts(int ruleId) {
            switch (ruleId) {
                case 1: return resources.getIntArray(R.array.rule_1_rule_point_word_card_training_count);
                case 2: return resources.getIntArray(R.array.rule_2_rule_point_word_card_training_count);
                case 3: return resources.getIntArray(R.array.rule_3_rule_point_word_card_training_count);
                case 4: return resources.getIntArray(R.array.rule_4_rule_point_word_card_training_count);
                case 5: return resources.getIntArray(R.array.rule_5_rule_point_word_card_training_count);

                default: return null;
            }
        }

        String[] getWordCardCorrectWords(int rulePointId) {
            switch (rulePointId) {
                case 1: return resources.getStringArray(R.array.rule_point_1_word_card_correct_words);
                case 2: return resources.getStringArray(R.array.rule_point_2_word_card_correct_words);
                case 3: return resources.getStringArray(R.array.rule_point_3_word_card_correct_words);
                case 4: return resources.getStringArray(R.array.rule_point_4_1_word_card_correct_words);
                case 5: return resources.getStringArray(R.array.rule_point_4_2_word_card_correct_words);
                case 6: return resources.getStringArray(R.array.rule_point_5_word_card_correct_words);
                case 7: return resources.getStringArray(R.array.rule_point_6_word_card_correct_words);
                case 8: return resources.getStringArray(R.array.rule_point_7_word_card_correct_words);
                case 9: return resources.getStringArray(R.array.rule_point_8_word_card_correct_words);
                case 10: return resources.getStringArray(R.array.rule_point_9_word_card_correct_words);
                case 11: return resources.getStringArray(R.array.rule_point_10_word_card_correct_words);
                case 12: return resources.getStringArray(R.array.rule_point_11_word_card_correct_words);
                case 13: return resources.getStringArray(R.array.rule_point_12_word_card_correct_words);

                default: return null;
            }
        }
        String[] getWordCardIncorrectWords(int rulePointId) {
            switch (rulePointId) {
                case 1: return resources.getStringArray(R.array.rule_point_1_word_card_incorrect_words);
                case 2: return resources.getStringArray(R.array.rule_point_2_word_card_incorrect_words);
                case 3: return resources.getStringArray(R.array.rule_point_3_word_card_incorrect_words);
                case 4: return resources.getStringArray(R.array.rule_point_4_1_word_card_incorrect_words);
                case 5: return resources.getStringArray(R.array.rule_point_4_2_word_card_incorrect_words);
                case 6: return resources.getStringArray(R.array.rule_point_5_word_card_incorrect_words);
                case 7: return resources.getStringArray(R.array.rule_point_6_word_card_incorrect_words);
                case 8: return resources.getStringArray(R.array.rule_point_7_word_card_incorrect_words);
                case 9: return resources.getStringArray(R.array.rule_point_8_word_card_incorrect_words);
                case 10: return resources.getStringArray(R.array.rule_point_9_word_card_incorrect_words);
                case 11: return resources.getStringArray(R.array.rule_point_10_word_card_incorrect_words);
                case 12: return resources.getStringArray(R.array.rule_point_11_word_card_incorrect_words);
                case 13: return resources.getStringArray(R.array.rule_point_12_word_card_incorrect_words);

                default: return null;
            }
        }
    }
    */

    @Override
    public void onCreate() {
        super.onCreate();
        App.instance = this;
        Realm.init(this);

        /*
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

                        int[] wordCardTrainingCounts = realmHelper.getWordCardTrainingCounts(ruleId);
                        String[] rulePointTitles = realmHelper.getRulePointTitles(ruleId);
                        String[] rulePointDescriptions = realmHelper.getRulePointDescriptions(ruleId);

                        for (int rulePointIndex = 0; rulePointIndex < rulePointTitles.length; rulePointIndex++) {
                            RulePoint rulePoint = realm.createObject(RulePoint.class, rulePointId);
                            rulePoint.setRule(rule);
                            rulePoint.setTitle(rulePointTitles[rulePointIndex]);
                            rulePoint.setDescription(rulePointDescriptions[rulePointIndex]);
                            rulePoint.setWordCardTrainingCount(wordCardTrainingCounts[rulePointIndex]);

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
        */

        RealmConfiguration config = new RealmConfiguration.Builder().initialData(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    AssetManager assetManager = getResources().getAssets();

                    realm.createAllFromJson(Chapter.class, assetManager.open("chapters.json"));
                    realm.createAllFromJson(Rule.class, assetManager.open("rules.json"));
                    realm.createAllFromJson(RulePoint.class, assetManager.open("rule_points.json"));
                    realm.createAllFromJson(WordCardSet.class, assetManager.open("word_card_sets.json"));
                    realm.createAllFromJson(WordCard.class, assetManager.open("word_cards.json"));
                } catch (IOException e) {
                    Log.d("RealmConfiguration", e.getMessage());
                }
            }
        }).deleteRealmIfMigrationNeeded().build();

        Realm.setDefaultConfiguration(config);
    }
}
