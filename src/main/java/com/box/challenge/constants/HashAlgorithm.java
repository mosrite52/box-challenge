package com.box.challenge.constants;

import com.box.challenge.util.MessageSourceUtil;
import lombok.Getter;

import java.util.Locale;

public enum HashAlgorithm {

    SHA_256("SHA-256"),
    SHA_512("SHA-512");

    private final String algorithm;

    HashAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public static HashAlgorithm fromString(String value) {
        for (HashAlgorithm hashAlgorithm : values()) {
            if (hashAlgorithm.algorithm.equals(value)) {
                return hashAlgorithm;
            }
        }
        String message = MessageSourceUtil.getMessage("error.invalid.hash", null, Locale.getDefault());
        throw new IllegalArgumentException(message + value);
    }
}