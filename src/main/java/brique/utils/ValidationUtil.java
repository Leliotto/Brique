package brique.utils;

/**
 * Helper methods that don't fit elsewhere (e.g., preconditions, invariants).
 */
public final class ValidationUtil {
    private ValidationUtil() {}

    public static void require(boolean condition, String message) {
        if (!condition) throw new IllegalArgumentException(message);
    }
}
