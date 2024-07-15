package com.jyujyu.dayonetest.service;

import com.jyujyu.dayonetest.controller.response.ExamFailStudentResponse;
import com.jyujyu.dayonetest.controller.response.ExamPassStudentResponse;
import com.jyujyu.dayonetest.model.StudentFail;
import com.jyujyu.dayonetest.model.StudentPass;
import com.jyujyu.dayonetest.repository.StudentFailRepository;
import com.jyujyu.dayonetest.repository.StudentPassRepository;
import com.jyujyu.dayonetest.repository.StudentScoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

public class StudentScoreServiceMockTest {

    @Test
    public void firstSaveScoreMockTest() {
        // given
        StudentScoreService studentScoreService = new StudentScoreService(
                Mockito.mock(StudentScoreRepository.class),
                Mockito.mock(StudentFailRepository.class),
                Mockito.mock(StudentPassRepository.class)
        );
        String givenStudentName = "testName";
        String givenExam = "testExam";
        Integer givenKorScore = 80;
        Integer givenEnglishScore = 100;
        Integer givenMathScore = 60;

        // when
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishScore,
                givenMathScore
        );

    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60점 이상인 경우")
    public void saveScoreMockTest1() {
        // given
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentFailRepository,
                studentPassRepository
        );

        String givenStudentName = "testName";
        String givenExam = "testExam";
        Integer givenKorScore = 100;
        Integer givenEnglishScore = 100;
        Integer givenMathScore = 60;

        // when
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishScore,
                givenMathScore
        );

        // then
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentPassRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentFailRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60점 이하인 경우")
    public void saveScoreMockTest2() {
        // given
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentFailRepository,
                studentPassRepository
        );

        String givenStudentName = "testName";
        String givenExam = "testExam";
        Integer givenKorScore = 50;
        Integer givenEnglishScore = 50;
        Integer givenMathScore = 50;

        // when
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishScore,
                givenMathScore
        );

        // then
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());
        Mockito.verify(studentFailRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("합격자 명단 가져오기")
    public void getPassStudentListTest() {
        // given
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentPass expectStudent1 = StudentPass.builder().id(1L).studentName("sewon").exam("test exam").avgScore(80.0).build();
        StudentPass expectStudent2 = StudentPass.builder().id(2L).studentName("test").exam("test exam").avgScore(80.0).build();
        StudentPass notExpectStudent3 = StudentPass.builder().id(3L).studentName("iamnot").exam("not test exam").avgScore(80.0).build();

        Mockito.when(studentPassRepository.findAll()).thenReturn(List.of( //의미: findAll이 호출되었을 때는 이걸 리턴해라
                expectStudent1,
                expectStudent2,
                notExpectStudent3
        ));

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentFailRepository,
                studentPassRepository
        );

        String givenTestExam = "test exam";

        // when
        List<ExamPassStudentResponse> expectedResponse = List.of(expectStudent1, expectStudent2).stream()
                .map(studentPass -> new ExamPassStudentResponse(studentPass.getStudentName(), studentPass.getAvgScore()))
                .toList();
        List<ExamPassStudentResponse> responses = studentScoreService.getPassStudentList(givenTestExam);

        // then
        Assertions.assertIterableEquals(expectedResponse, responses);
    }

    @Test
    @DisplayName("불합격자 명단 가져오기")
    public void getFailStudentListTest() {
        // given
        StudentScoreRepository studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        StudentPassRepository studentPassRepository = Mockito.mock(StudentPassRepository.class);
        StudentFailRepository studentFailRepository = Mockito.mock(StudentFailRepository.class);

        StudentScoreService studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentFailRepository,
                studentPassRepository
        );

        StudentFail expectStudent1 = StudentFail.builder().id(1L).studentName("sewon").exam("test exam").avgScore(59.0).build();
        StudentFail expectStudent2 = StudentFail.builder().id(2L).studentName("test").exam("test exam").avgScore(12.0).build();
        StudentFail notExpectStudent3 = StudentFail.builder().id(3L).studentName("iamnot").exam("not test exam").avgScore(52.0).build();

        Mockito.when(studentFailRepository.findAll()).thenReturn(List.of( //의미: findAll이 호출되었을 때는 이걸 리턴해라
                expectStudent1,
                expectStudent2,
                notExpectStudent3
        ));

        String givenTestExam = "test exam";

        // when
        List<ExamFailStudentResponse> responses = studentScoreService.getFailStudentList(givenTestExam);
        List<ExamFailStudentResponse> expectedResponse = List.of(expectStudent1, expectStudent2).stream()
                .map(student -> new ExamFailStudentResponse(student.getStudentName(), student.getAvgScore()))
                .toList();

        // then
        Assertions.assertIterableEquals(expectedResponse, responses);
    }

}
