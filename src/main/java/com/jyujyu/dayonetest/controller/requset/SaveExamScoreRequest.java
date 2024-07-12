package com.jyujyu.dayonetest.controller.requset;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveExamScoreRequest {
    private final String studentName;
    private final Integer koScore;
    private final Integer englishScore;
    private final Integer mathScore;
}