package com.box.challenge.constants;

import lombok.Getter;

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
        throw new IllegalArgumentException("Invalid hash algorithm: " + value);
    }
}