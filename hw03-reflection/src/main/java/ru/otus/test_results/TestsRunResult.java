package ru.otus.test_results;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TestsRunResult {
    private final List<TestResult> testResults = new LinkedList<>();

    public long failed() {
        return testResults.stream().filter(testResult -> !testResult.isPassed()).count();
    }

    public long passed() {
        return testResults.stream().filter(TestResult::isPassed).count();
    }

    public long totalTestsRun() {
        return testResults.size();
    }

    public void registerTestRun(TestResult testResult) {
        testResults.add(testResult);
    }

    @Override
    public String toString() {
        return testResults.stream().map(TestResult::toString).collect(Collectors.joining("\n"));
    }

    public long throwableCount() {
        return testResults.stream().map(TestResult::throwableCount).reduce(0L, Long::sum);
    }

    public String getStatistics() {
        return "Tests run: %d, passed: %d, failed: %d".formatted(totalTestsRun(), passed(), failed());
    }
}
