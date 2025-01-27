package ru.otus;

import java.lang.reflect.Method;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.exceptions.TestRunningException;

@SuppressWarnings({"java:S3011"})
public class ReflectionHelper {
    private ReflectionHelper() {}

    private static final Logger log = LoggerFactory.getLogger(ReflectionHelper.class);

    public static Object callMethod(Object object, Method method, Object... args) {
        log.debug("calling method {}.{}", object.getClass().getName(), method.getName());

        try {
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (Exception e) {
            throw new TestRunningException(e);
        }
    }

    public static <T> T instantiate(Class<T> type, Object... args) {
        log.debug("creating instance of {}", type.getName());

        try {
            if (args.length == 0) {
                return type.getDeclaredConstructor().newInstance();
            } else {
                Class<?>[] classes = toClasses(args);
                return type.getDeclaredConstructor(classes).newInstance(args);
            }
        } catch (Exception e) {
            throw new TestRunningException(e);
        }
    }

    public static Class<?>[] toClasses(Object[] args) {
        return Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
    }
}
