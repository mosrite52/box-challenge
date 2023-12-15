package com.box.challenge.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DocumentSearchResponse {
    private String hashType;
    private String hash;
}
