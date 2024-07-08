package com.jyujyu.dayonetest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LombokTestDataTest {

    @Test
    public void testDataTest() {
        TestData testData = new TestData();
        testData.setName("sewon");

        Assertions.assertEquals("sewon", testData.getName());
    }
}
