package com.box.challenge.controller;

import com.box.challenge.model.response.DocumentResponse;
import com.box.challenge.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/hash")
    public ResponseEntity<Object> uploadDocuments(
            @RequestParam("algorithm") String algorithm,
            @RequestPart("files") List<MultipartFile> files) {
        try {
            List<DocumentResponse> documentResponses = documentService.saveDocuments(files, algorithm);
            return ResponseEntity.status(HttpStatus.CREATED).body(buildResponse(algorithm, documentResponses));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error uploading documents.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private Object buildResponse(String algorithm, List<DocumentResponse> documentResponses) {
        return Map.of(
                "algorithm", algorithm,
                "documents", documentResponses
        );
    }
}