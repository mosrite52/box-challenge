package com.box.challenge.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("hash-sha-256")
    private String  hashSha256;
    @JsonProperty("hash-sha-512")
    private String  hashSha512;
    private LocalDateTime lastUpload;
}
