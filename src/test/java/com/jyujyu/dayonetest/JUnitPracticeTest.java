package com.jyujyu.dayonetest;

import org.junit.jupiter.api.*;

import java.util.List;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class JUnitPracticeTest  {
    @Test
    @DisplayName("Assert Equals 메소드 테스트") // DisplayNameGeneration 보다 우선 적용
    public void assert_equals_test() {
        String expect = "something";
        String actual = "something";

        Assertions.assertEquals(expect, actual);
    }

    @Test
    @DisplayName("Assert Not Equals 메소드 테스트")
    public void assertNotEqualsTest() {
        String expect = "something";
        String actual = "something different";

        Assertions.assertNotEquals(expect, actual);
    }

    @Test
    @DisplayName("Assert True 메소드 테스트")
    public void assertTrueTest() {
        Integer a = 10;
        Integer b = 10;

        Assertions.assertTrue(a.equals(b));
    }

    @Test
    @DisplayName("Assert False 메소드 테스트")
    public void assertFalseTest() {
        Integer a = 10;
        Integer b = 20;

        Assertions.assertFalse(a.equals(b));
    }

    @Test
    @DisplayName("Assert Throws 메소드 테스트")
    public void assertThrowsTest() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException("임의로 발생시킨 에러");
        });

    }

    @Test
    @DisplayName("Assert Not Null 메소드 테스트")
    public void assertNotNullTest() {
        String value = null;

        Assertions.assertNull(value);
    }


    @Test
    @DisplayName("Assert Iterable 메소드 테스트")
    public void assertIterableEquals() {
        List<Integer> list1 = List.of(1, 2);
        List<Integer> list2 = List.of(1, 2);

        Assertions.assertIterableEquals(list1, list2);
    }

    @Test
    @DisplayName("Assert All 메소드 테스트")
    public void assertAllTest() {
        String expect = "something";
        String actual = "something";



        List<Integer> list1 = List.of(1, 2);
        List<Integer> list2 = List.of(1, 2);

        Assertions.assertAll("Assert All", List.of( // list of executable
                () -> {
                    Assertions.assertEquals(expect, actual);
                },
                () -> {
                    Assertions.assertIterableEquals(list1, list2);
                }
        ));

    }

}
