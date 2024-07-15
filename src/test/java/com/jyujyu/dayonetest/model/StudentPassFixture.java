package com.jyujyu.dayonetest.model;

import com.jyujyu.dayonetest.MyCalculator;

public class StudentPassFixture {
    public static StudentPass create(StudentScore studentScore) {
        MyCalculator calculator = new MyCalculator(0.0);
        return StudentPass
                .builder()
                .exam(studentScore.getExam())
                .studentName(studentScore.getStudentName())
                .avgScore(calculator
                        .add(studentScore.getKorScore().doubleValue())
                        .add(studentScore.getEngScore().doubleValue())
                        .add(studentScore.getMathScore().doubleValue())
                        .divide(3.0)
                        .getResult()
                )
                .build();
    }

    public static StudentPass create(String studentName, String exam) {
        return StudentPass
                .builder()
                .studentName(studentName)
                .exam(exam)
                .avgScore(80.0)
                .build();
    }
}
