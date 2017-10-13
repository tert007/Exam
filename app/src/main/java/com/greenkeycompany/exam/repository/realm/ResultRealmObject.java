package com.greenkeycompany.exam.repository.realm;

/**
 * Created by tert0 on 11.10.2017.
 */

public interface ResultRealmObject {

    float MIN_SCORE = 0.0f;
    float MAX_SCORE = 5.0f;

    String FILED_SCORE = "score";

    float getScore();
    void setScore(float score);
}
