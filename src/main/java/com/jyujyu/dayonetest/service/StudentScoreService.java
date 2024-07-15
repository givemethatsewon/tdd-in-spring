package com.jyujyu.dayonetest.service;

import com.jyujyu.dayonetest.MyCalculator;
import com.jyujyu.dayonetest.controller.response.ExamFailStudentResponse;
import com.jyujyu.dayonetest.controller.response.ExamPassStudentResponse;
import com.jyujyu.dayonetest.model.StudentFail;
import com.jyujyu.dayonetest.model.StudentPass;
import com.jyujyu.dayonetest.model.StudentScore;
import com.jyujyu.dayonetest.repository.StudentFailRepository;
import com.jyujyu.dayonetest.repository.StudentPassRepository;
import com.jyujyu.dayonetest.repository.StudentScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentScoreService {
    private final StudentScoreRepository studentScoreRepository;
    private final StudentFailRepository studentFailRepository;
    private final StudentPassRepository studentPassRepository;

    public void saveScore(String studentName, String exam, Integer korScore, Integer engScore, Integer mathScore) {
        StudentScore studentScore = StudentScore.builder()
                .exam(exam)
                .studentName(studentName)
                .korScore(korScore)
                .engScore(engScore)
                .mathScore(mathScore)
                .build();

        studentScoreRepository.save(studentScore);

        Double avgScore = (new MyCalculator(0.0))
                .add(korScore.doubleValue())
                .add(engScore.doubleValue())
                .add(mathScore.doubleValue())
                .divide(3.0)
                .getResult();

        if (avgScore >= 60) {
            StudentPass studentPass = StudentPass.builder()
                    .exam(exam)
                    .studentName(studentName)
                    .avgScore(avgScore)
                    .build();
            studentPassRepository.save(studentPass);
        } else {
            StudentFail studentFail = StudentFail.builder()
                    .exam(exam)
                    .studentName(studentName)
                    .avgScore(avgScore)
                    .build();
            studentFailRepository.save(studentFail);
        }
    }

    public List<ExamPassStudentResponse> getPassStudentList(String exam) {
        List<StudentPass> studentPasses = studentPassRepository.findAll();

        return studentPasses.stream()
                .filter((pass) -> pass.getExam().equals(exam))
                .map((pass) -> new ExamPassStudentResponse(pass.getStudentName(), pass.getAvgScore()))
                .toList();
    }

    public List<ExamFailStudentResponse> getFailStudentList(String exam) {
        List<StudentFail> studentFails = studentFailRepository.findAll();

        return studentFails.stream()
                .filter((fail) -> fail.getExam().equals(exam))
                .map((fail) -> new ExamFailStudentResponse(fail.getStudentName(), fail.getAvgScore()))
                .toList();
    }

}
