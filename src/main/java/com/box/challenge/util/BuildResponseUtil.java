package com.box.challenge.util;

import com.box.challenge.model.response.DocumentResponse;

import java.util.List;
import java.util.Map;

public class BuildResponseUtil {
    public static Object filesResponse(String algorithm, List<DocumentResponse> documentResponses) {
        return Map.of(
                "algorithm", algorithm,
                "documents", documentResponses
        );
    }
}
