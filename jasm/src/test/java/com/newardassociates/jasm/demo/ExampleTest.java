package com.newardassociates.jasm.demo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ExampleTest {
    @Test
    void testTheTestClass() {
        assertThat(TestClass.getTestValue()).isEqualTo("Test Value");
    }
}