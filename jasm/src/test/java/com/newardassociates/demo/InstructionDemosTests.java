package com.newardassociates.demo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InstructionDemosTests {
    @Test void testSimpleIntReturn() { assertThat(InstructionDemos.simpleIntReturn()).isEqualTo(5); }

    @Test void testConstantReturn() { assertThat(InstructionDemos.simpleConstantReturn()).isEqualTo(432); }

    /* Just don't crash! */
    @Test void testStackFun() { InstructionDemos.stackFun(); }

    @Test void testTheTestClass() {
        assertThat(TestClass.getTestValue()).isEqualTo("Test Value");
    }
}
