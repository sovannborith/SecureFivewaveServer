package com.securefivewave.util;

import org.springframework.data.util.Pair;

public final class ErrorCode {

    public static final Pair<Integer, String> ERROR_INVALID_PASSWORD = Pair.of(1032, "The current password is invalid");

    public static final Pair<Integer, String> ERROR_PASSWORD_NOT_STRONG = Pair.of(1006,
            "Password strength is not strong enough");

    public static final Pair<Integer, String> ERROR_INVALID_PASSWORD_LENGTH = Pair.of(1050,
            "Password length doesn’t match the minimum length");

    public static final Pair<Integer, String> ERROR_PASSWORD_HAS_REPEATED_CHARACTER = Pair.of(1051,
            "Password has repeated character");

    public static final Pair<Integer, String> ERROR_PASSWORD_HAS_SEQUENCE_CHARACTER = Pair.of(1052,
            "Password has sequence character");

    public static final Pair<Integer, String> ERROR_PASSWORD_HAS_BREACHED = Pair.of(1053, "Password has breached");

    private ErrorCode() {
        // nothing
    }

}