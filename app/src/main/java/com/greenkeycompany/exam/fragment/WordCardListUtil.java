package com.greenkeycompany.exam.fragment;

import android.support.annotation.NonNull;

import com.greenkeycompany.exam.repository.model.WordCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by tert0 on 20.10.2017.
 */

public class WordCardListUtil {

    private static final int FINAL_EXAM_WORD_CARD_COUNT = 100;
    private static final int CHAPTER_EXAM_WORD_CARD_COUNT = 50;
    private static final int RULE_EXAM_WORD_CARD_COUNT = 30;

    public static List<WordCard> getWordCardSetList(@NonNull List<WordCard> wordCardList, int wordCardCount) {
        return getShuffleSubList(wordCardList, wordCardCount);
    }

    public static List<WordCard> getRuleExamList(@NonNull List<WordCard> wordCardList) {
        return getShuffleSubList(wordCardList, RULE_EXAM_WORD_CARD_COUNT);
    }

    public static List<WordCard> getChapterExamList(@NonNull List<WordCard> wordCardList) {
        return getShuffleSubList(wordCardList, CHAPTER_EXAM_WORD_CARD_COUNT);
    }

    public static List<WordCard> getFinalExamList(@NonNull List<WordCard> wordCardList) {
        return getShuffleSubList(wordCardList, FINAL_EXAM_WORD_CARD_COUNT);
    }

    private static List<WordCard> getShuffleSubList(@NonNull List<WordCard> wordCardList, int wordCardCount) {
        List<Integer> indexesList = getUniqueIndexes(wordCardList.size());
        indexesList = indexesList.subList(0, wordCardCount);

        List<WordCard> resultList = new ArrayList<>(wordCardCount);
        for (int index : indexesList) {
            resultList.add(wordCardList.get(index));
        }

        return resultList;
    }

    private static List<Integer> getUniqueIndexes(int size) {
        List<Integer> indices = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);

        return indices;
    }
}
