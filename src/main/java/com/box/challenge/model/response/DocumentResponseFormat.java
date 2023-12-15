package com.box.challenge.model.response;

import lombok.Data;

import java.util.List;
@Data
public class DocumentResponseFormat {
    private String algorithm;
    private List<DocumentResponse> documents;

    public DocumentResponseFormat(String algorithm, List<DocumentResponse> documents) {
        this.algorithm = algorithm;
        this.documents = documents;
    }
}
