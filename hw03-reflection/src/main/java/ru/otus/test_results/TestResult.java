package ru.otus.test_results;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TestResult {
    private final String testName;

    private final List<Throwable> throwableList;

    public TestResult(String testName, List<Throwable> throwableList) {
        this.testName = testName;
        this.throwableList = throwableList == null ? new LinkedList<>() : throwableList;
    }

    public boolean isPassed() {
        return throwableList.isEmpty();
    }

    public long throwableCount() {
        return throwableList.size();
    }

    public String shortResult() {
        if (isPassed()) return "Passed";
        return "Failed with %d error(s):%n%s%n"
                .formatted(
                        throwableList.size(),
                        throwableList.stream().map(Throwable::toString).collect(Collectors.joining("\n")));
    }

    @Override
    public String toString() {
        return "%s %s".formatted(testName, shortResult());
    }
}
