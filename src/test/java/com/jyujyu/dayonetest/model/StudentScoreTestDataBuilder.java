package com.jyujyu.dayonetest.model;

public class StudentScoreTestDataBuilder {
    public static StudentScore.StudentScoreBuilder passed() {
        return StudentScore
                .builder()
                .korScore(80)
                .engScore(100)
                .mathScore(90)
                .studentName("defaultName")
                .exam("defaultExam");
    }

    public static StudentScore.StudentScoreBuilder failed() {
        return StudentScore
                .builder()
                .korScore(50)
                .engScore(40)
                .mathScore(30)
                .studentName("defaultName")
                .exam("defaultExam");
    }
}
