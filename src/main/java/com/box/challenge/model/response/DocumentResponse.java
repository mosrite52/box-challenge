package com.box.challenge.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class DocumentResponse {
    private String fileName;
    private String hash;
    private LocalDateTime lastUpload;

    public DocumentResponse(String fileName, String hash, LocalDateTime lastUpload) {
        this.fileName = fileName;
        this.hash = hash;
        this.lastUpload = lastUpload;
    }
}
