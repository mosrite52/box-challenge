package com.box.challenge.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentResponse {
    private String fileName;
    private String hash;
    private String  hashSha256;
    private String  hashSha512;
    private LocalDateTime lastUpload;
}
