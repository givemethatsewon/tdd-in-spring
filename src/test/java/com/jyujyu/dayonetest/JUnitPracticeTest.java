package com.jyujyu.dayonetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class JUnitPracticeTest {
    @Test
    public void assertEqualsTest() {
        String expect = "something";
        String actual = "something";

        Assertions.assertEquals(expect, actual);
    }

    @Test
    public void assertNotEqualsTest() {
        String expect = "something";
        String actual = "something different";

        Assertions.assertNotEquals(expect, actual);
    }

    @Test
    public void assertTrueTest() {
        Integer a = 10;
        Integer b = 10;

        Assertions.assertTrue(a.equals(b));
    }

    @Test
    public void assertFalseTest() {
        Integer a = 10;
        Integer b = 20;

        Assertions.assertFalse(a.equals(b));
    }

    @Test
    public void assertThrowsTest() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException("임의로 발생시킨 에러");
        });

    }

    @Test
    public void assertNotNullTest() {
        String value = null;

        Assertions.assertNull(value);
    }


    @Test
    public void assertIterableEquals() {
        List<Integer> list1 = List.of(1, 2);
        List<Integer> list2 = List.of(1, 2);

        Assertions.assertIterableEquals(list1, list2);
    }

    @Test
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
