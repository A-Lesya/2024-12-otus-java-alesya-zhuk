package ru.otus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;
import ru.otus.exceptions.TestRunningException;
import ru.otus.test_results.TestResult;
import ru.otus.test_results.TestsRunResult;

public class TestsRunner {
    private static final Logger log = LoggerFactory.getLogger(TestsRunner.class);
    private static final String LOG_DETERMINER = "--------------------------------------------------------------------";

    private TestsRunner() {}

    public static TestsRunResult run(Class<?> clazz) {
        log.info("\n{}\nRunning tests in class {}\n", LOG_DETERMINER, clazz.getName());

        List<Method> beforeMethods = new LinkedList<>();
        List<Method> afterMethods = new LinkedList<>();
        List<Method> testMethods = new LinkedList<>();

        findMethods(clazz, beforeMethods, testMethods, afterMethods);

        var result = runTests(clazz, beforeMethods, testMethods, afterMethods);

        log.atDebug()
                .setMessage("\n{}\nTests run result:\n{}\n{}\n{}")
                .addArgument(LOG_DETERMINER)
                .addArgument(LOG_DETERMINER)
                .addArgument(result::toString)
                .addArgument(LOG_DETERMINER)
                .log();

        return result;
    }

    private static TestsRunResult runTests(
            Class<?> clazz, List<Method> beforeMethods, List<Method> testMethods, List<Method> afterMethods) {
        var testsRunResult = new TestsRunResult();

        testMethods.forEach(testMethod -> {
            var testResult = runTest(clazz, testMethod, beforeMethods, afterMethods);
            testsRunResult.registerTestRun(testResult);
        });

        return testsRunResult;
    }

    private static TestResult runTest(
            Class<?> clazz, Method testMethod, List<Method> beforeMethods, List<Method> afterMethods) {
        var testName = "%s.%s".formatted(clazz.getName(), testMethod.getName());

        log.debug("Test started: {}", testName);

        Object instance = ReflectionHelper.instantiate(clazz);
        List<Throwable> throwableList = new LinkedList<>();

        callAndCatchTestRunningException(
                () -> {
                    runBeforeMethods(instance, beforeMethods);
                    runTestMethod(instance, testMethod);
                },
                throwableList);

        runAfterMethods(instance, afterMethods, throwableList);

        var result = new TestResult(testName, throwableList);

        log.atDebug()
                .setMessage("Test finished: {}\nResult: {}\n")
                .addArgument(testName)
                .addArgument(result::shortResult)
                .log();

        return result;
    }

    private static void runTestMethod(Object object, Method testMethod) {
        ReflectionHelper.callMethod(object, testMethod);
    }

    private static void runBeforeMethods(Object object, List<Method> beforeMethods) {
        for (Method method : beforeMethods) {
            ReflectionHelper.callMethod(object, method);
        }
    }

    private static void runAfterMethods(Object object, List<Method> afterMethods, List<Throwable> throwableList) {
        afterMethods.forEach(method ->
                callAndCatchTestRunningException(() -> ReflectionHelper.callMethod(object, method), throwableList));
    }

    private static void callAndCatchTestRunningException(Runnable runnable, List<Throwable> throwableList) {
        try {
            runnable.run();
        } catch (TestRunningException e) {
            if (e.getCause() instanceof InvocationTargetException invocationTargetException
                    && invocationTargetException.getCause() != null) {
                throwableList.add(invocationTargetException.getCause());
            } else {
                throwableList.add(e);
            }
        }
    }

    private static void findMethods(
            Class<?> clazz, List<Method> beforeMethods, List<Method> testMethods, List<Method> afterMethods) {
        Arrays.stream(clazz.getDeclaredMethods()).forEach(method -> {
            if (method.isAnnotationPresent(Before.class)) {
                beforeMethods.add(method);
            }

            if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            }

            if (method.isAnnotationPresent(After.class)) {
                afterMethods.add(method);
            }
        });
    }
}
