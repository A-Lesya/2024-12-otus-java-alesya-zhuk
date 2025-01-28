package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.test_examples.*;
import ru.otus.test_results.TestsRunResult;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final String LOG_FORMAT = "{}\n\n";

    public static void main(String[] args) {
        checkSimpleTest();
        checkExceptionInAfterTest();
        checkExceptionInBeforeTest();
        checkExceptionInTestAndAfterTest();
        checkSameMethodForBeforeAndAfterTest();
    }

    private static void checkSimpleTest() {
        var result = TestsRunner.run(SimpleTest.class);
        log.info(LOG_FORMAT, result.getStatistics());

        assertTestsRunResult(result, 2, 2, 2);
    }

    private static void checkExceptionInAfterTest() {
        var result = TestsRunner.run(ExceptionInAfterTest.class);
        log.info(LOG_FORMAT, result.getStatistics());

        assertTestsRunResult(result, 1, 0, 2);
    }

    private static void checkExceptionInBeforeTest() {
        var result = TestsRunner.run(ExceptionInBeforeTest.class);
        log.info(LOG_FORMAT, result.getStatistics());

        assertTestsRunResult(result, 1, 0, 1);
    }

    private static void checkExceptionInTestAndAfterTest() {
        var result = TestsRunner.run(ExceptionInTestAndAfterTest.class);
        log.info(LOG_FORMAT, result.getStatistics());

        assertTestsRunResult(result, 2, 0, 3);
    }

    private static void checkSameMethodForBeforeAndAfterTest() {
        var result = TestsRunner.run(SameMethodForBeforeAndAfterTest.class);
        log.info(LOG_FORMAT, result.getStatistics());

        assertTestsRunResult(result, 2, 0, 4);
    }

    private static void assertTestsRunResult(TestsRunResult result, long failed, long passed, long throwableCount) {
        assert result.failed() == failed;
        assert result.passed() == passed;
        assert result.throwableCount() == throwableCount;
    }
}
