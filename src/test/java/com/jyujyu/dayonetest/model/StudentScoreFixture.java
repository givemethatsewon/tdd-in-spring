package com.jyujyu.dayonetest.model;

public class StudentScoreFixture {
    public static StudentScore passed() {
        return StudentScore
                .builder()
                .exam("defaultExam")
                .studentName("defaultName")
                .korScore(90)
                .engScore(80)
                .mathScore(100)
                .build();
    }

    public static StudentScore failed() {
        return StudentScore
                .builder()
                .exam("defaultExam")
                .studentName("defaultName")
                .korScore(40)
                .engScore(60)
                .mathScore(50)
                .build();
    }
}
