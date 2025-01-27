package ru.otus.test_examples;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

@SuppressWarnings({"java:S112", "java:S1186"})
public class ExceptionInAfterTest {
    @Before
    public void before() {}

    @Test
    public void test1() {}

    @After
    public void after1() {}

    @After
    public void after2() {
        throw new RuntimeException("some exception in after method");
    }

    @After
    public void after3() {
        throw new AssertionError("some error in after method");
    }

    @After
    public void after4() {}
}
