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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

public class StudentScoreServiceMockTest {
    private StudentScoreService studentScoreService;
    private StudentScoreRepository studentScoreRepository;
    private StudentPassRepository studentPassRepository;
    private StudentFailRepository studentFailRepository;

    @BeforeEach
    public void beforeEach() {
        // repository initialize
        studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
        studentPassRepository = Mockito.mock(StudentPassRepository.class);
        studentFailRepository = Mockito.mock(StudentFailRepository.class);

        // service initialize
        studentScoreService = new StudentScoreService(
                studentScoreRepository,
                studentFailRepository,
                studentPassRepository
        );
    }

    @Test
    @DisplayName("첫번째 Mock 테스트")
    public void firstSaveScoreMockTest() {
        // given
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
        String givenStudentName = "testName";
        String givenExam = "testExam";
        Integer givenKorScore = 100;
        Integer givenEnglishScore = 100;
        Integer givenMathScore = 60;

        StudentScore expectedStudentScore = StudentScore
                .builder()
                .studentName(givenStudentName)
                .exam(givenExam)
                .korScore(givenKorScore)
                .engScore(givenEnglishScore)
                .mathScore(givenMathScore)
                .build();

        StudentPass expectedStudentPass = StudentPass
                .builder()
                .studentName(givenStudentName)
                .exam(givenExam)
                .avgScore(
                        (new MyCalculator(0.0))
                                .add(givenKorScore.doubleValue())
                                .add(givenEnglishScore.doubleValue())
                                .add(givenMathScore.doubleValue())
                                .divide(3.0)
                                .getResult()
                )
                .build();

        // when
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishScore,
                givenMathScore
        );

        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentPass> studentPassArgumentCaptor = ArgumentCaptor.forClass(StudentPass.class);

        // then
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(studentScoreArgumentCaptor.capture());
        StudentScore capturedStudentScore = studentScoreArgumentCaptor.getValue(); // 테스트하는 메서드에 전달하는 인자 확인
        Assertions.assertEquals(expectedStudentScore.getStudentName(), capturedStudentScore.getStudentName());
        Assertions.assertEquals(expectedStudentScore.getExam(), capturedStudentScore.getExam());
        Assertions.assertEquals(expectedStudentScore.getEngScore(), capturedStudentScore.getEngScore());
        Assertions.assertEquals(expectedStudentScore.getKorScore(), capturedStudentScore.getKorScore());
        Assertions.assertEquals(expectedStudentScore.getMathScore(), capturedStudentScore.getMathScore());

        Mockito.verify(studentPassRepository, Mockito.times(1)).save(studentPassArgumentCaptor.capture());
        StudentPass capturedStudentPass = studentPassArgumentCaptor.getValue();
        Assertions.assertEquals(expectedStudentPass.getStudentName(), capturedStudentPass.getStudentName());
        Assertions.assertEquals(expectedStudentPass.getAvgScore(), capturedStudentPass.getAvgScore());
        Assertions.assertEquals(expectedStudentPass.getExam(), capturedStudentPass.getExam());

        Mockito.verify(studentFailRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    @DisplayName("성적 저장 로직 검증 / 60점 이하인 경우")
    public void saveScoreMockTest2() {
        // given
        String givenStudentName = "testName";
        String givenExam = "testExam";
        Integer givenKorScore = 10;
        Integer givenEnglishScore = 59;
        Integer givenMathScore = 50;

        StudentScore expectedStudentScore = StudentScore
                .builder()
                .studentName(givenStudentName)
                .exam(givenExam)
                .korScore(givenKorScore)
                .engScore(givenEnglishScore)
                .mathScore(givenMathScore)
                .build();

        StudentPass expectedStudentFail = StudentPass
                .builder()
                .studentName(givenStudentName)
                .exam(givenExam)
                .avgScore(
                        (new MyCalculator(0.0))
                                .add(givenKorScore.doubleValue())
                                .add(givenEnglishScore.doubleValue())
                                .add(givenMathScore.doubleValue())
                                .divide(3.0)
                                .getResult()
                )
                .build();

        ArgumentCaptor<StudentScore> studentScoreArgumentCaptor = ArgumentCaptor.forClass(StudentScore.class);
        ArgumentCaptor<StudentFail> studentFailArgumentCaptor = ArgumentCaptor.forClass(StudentFail.class);

        // when
        studentScoreService.saveScore(
                givenStudentName,
                givenExam,
                givenKorScore,
                givenEnglishScore,
                givenMathScore
        );

        // then
        Mockito.verify(studentScoreRepository, Mockito.times(1)).save(studentScoreArgumentCaptor.capture());
        StudentScore capturedStudentScore = studentScoreArgumentCaptor.getValue();
        Assertions.assertEquals(expectedStudentScore.getStudentName(), capturedStudentScore.getStudentName());
        Assertions.assertEquals(expectedStudentScore.getExam(), capturedStudentScore.getExam());
        Assertions.assertEquals(expectedStudentScore.getEngScore(), capturedStudentScore.getEngScore());
        Assertions.assertEquals(expectedStudentScore.getKorScore(), capturedStudentScore.getKorScore());
        Assertions.assertEquals(expectedStudentScore.getMathScore(), capturedStudentScore.getMathScore());


        Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());

        Mockito.verify(studentFailRepository, Mockito.times(1)).save(studentFailArgumentCaptor.capture());
        StudentFail capturedStudentFail = studentFailArgumentCaptor.getValue();
        Assertions.assertEquals(expectedStudentFail.getStudentName(), capturedStudentFail.getStudentName());
        Assertions.assertEquals(expectedStudentFail .getAvgScore(), capturedStudentFail.getAvgScore());
        Assertions.assertEquals(expectedStudentFail.getExam(), capturedStudentFail.getExam());


    }

    @Test
    @DisplayName("합격자 명단 가져오기")
    public void getPassStudentListTest() {
        // given
        StudentPass expectStudent1 = StudentPass.builder().id(1L).studentName("sewon").exam("test exam").avgScore(80.0).build();
        StudentPass expectStudent2 = StudentPass.builder().id(2L).studentName("test").exam("test exam").avgScore(80.0).build();
        StudentPass notExpectStudent3 = StudentPass.builder().id(3L).studentName("iamnot").exam("not test exam").avgScore(80.0).build();

        Mockito.when(studentPassRepository.findAll()).thenReturn(List.of( //의미: findAll이 호출되었을 때는 이걸 리턴해라
                expectStudent1,
                expectStudent2,
                notExpectStudent3
        ));

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
