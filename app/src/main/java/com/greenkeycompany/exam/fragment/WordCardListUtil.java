package com.greenkeycompany.exam.fragment;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.repository.model.WordCard;

import java.util.Collections;
import java.util.List;

/**
 * Created by tert0 on 20.10.2017.
 */

public class WordCardListUtil {

    private static final int CHAPTER_WORD_CARD_COUNT = 50;
    private static final int RULE_WORD_CARD_COUNT = 20;

    public static List<WordCard> getRuleShuffleSubList(@NonNull List<WordCard> wordCardList) {
        return getShuffleSubList(wordCardList, RULE_WORD_CARD_COUNT);
    }

    public static List<WordCard> getChapterShuffleSubList(@NonNull List<WordCard> wordCardList) {
        return getShuffleSubList(wordCardList, CHAPTER_WORD_CARD_COUNT);
    }

    private static List<WordCard> getShuffleSubList(@NonNull List<WordCard> wordCardList, int count) {
        Collections.shuffle(wordCardList);
        return wordCardList.subList(0, count);
    }

}
