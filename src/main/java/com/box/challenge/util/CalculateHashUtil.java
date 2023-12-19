package com.box.challenge.util;

import com.box.challenge.constants.HashAlgorithm;
import com.google.common.hash.Hashing;

import static com.box.challenge.constants.HashAlgorithm.SHA_256;
import static com.box.challenge.constants.HashAlgorithm.SHA_512;

public class CalculateHashUtil {
    public static String calculateHash(byte[] fileBytes, String algorithm) {

        return switch (HashAlgorithm.fromString(algorithm)) {
            case SHA_256 -> Hashing.sha256().hashBytes(fileBytes).toString();
            case SHA_512 -> Hashing.sha512().hashBytes(fileBytes).toString();
            default -> throw new IllegalArgumentException("Invalid hash algorithm");
        };
    }
}
