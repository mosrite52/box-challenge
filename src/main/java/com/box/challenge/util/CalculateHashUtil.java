package com.box.challenge.util;

import com.box.challenge.constants.HashAlgorithms;
import com.google.common.hash.Hashing;

public class CalculateHashUtil {
    public static String calculateHash(byte[] fileBytes, String algorithm) {
        return switch (algorithm) {
            case HashAlgorithms.SHA_256 -> Hashing.sha256().hashBytes(fileBytes).toString();
            case HashAlgorithms.SHA_512 -> Hashing.sha512().hashBytes(fileBytes).toString();
            default -> throw new IllegalArgumentException("Invalid hash algorithm");
        };
    }
}
