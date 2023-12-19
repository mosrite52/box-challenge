package com.box.challenge.controller;

import com.box.challenge.model.response.DocumentResponse;
import com.box.challenge.model.response.DocumentSearchResponse;
import com.box.challenge.service.DocumentService;
import com.box.challenge.util.BuildResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        try {List<DocumentResponse> documentResponses = documentService.saveDocuments(files, algorithm);
            return ResponseEntity.status(HttpStatus.CREATED).body(BuildResponseUtil.filesResponse(algorithm, documentResponses));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error uploading documents.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<DocumentResponse> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    @GetMapping(params = {"hashType", "hash"})
    public ResponseEntity<DocumentResponse> findDocumentByHash(@RequestParam String hashType,
                                                               @RequestParam String hash) {
        DocumentSearchResponse searchRequest = new DocumentSearchResponse();

        DocumentResponse documentResponse = documentService.findDocumentByHash(hashType, hash );

        if (documentResponse != null) {
            return new ResponseEntity<>(documentResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}