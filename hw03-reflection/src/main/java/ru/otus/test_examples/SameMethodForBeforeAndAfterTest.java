package ru.otus.test_examples;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

@SuppressWarnings({"java:S112", "java:S1186"})
public class SameMethodForBeforeAndAfterTest {
    @Before
    @After
    public void beforeAndAfter() {
        throw new RuntimeException("some exception in method annotated both @Before and @After");
    }

    @Before
    public void before() {}

    @Test
    public void test1() {
        throw new RuntimeException("this exception in test should not be reached");
    }

    @Test
    public void test2() {}

    @After
    public void after1() {}

    @After
    public void after() {}
}
