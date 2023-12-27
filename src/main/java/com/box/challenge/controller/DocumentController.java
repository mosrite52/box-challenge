package com.box.challenge.controller;

import com.box.challenge.exception.DocumentException;
import com.box.challenge.model.response.DocumentResponse;
import com.box.challenge.model.response.DocumentSearchResponse;
import com.box.challenge.service.DocumentService;
import com.box.challenge.service.IDocumentService;
import com.box.challenge.util.BuildResponseUtil;
import com.box.challenge.util.MessageSourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    private final IDocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/hash")
    public ResponseEntity<Object> uploadDocuments(
            @RequestParam("algorithm") String algorithm,
            @RequestPart("files") List<MultipartFile> files) {
        try {List<DocumentResponse> documentResponses = documentService.saveDocuments(files, algorithm);
            return ResponseEntity.status(HttpStatus.CREATED).body(BuildResponseUtil.filesResponse(algorithm, documentResponses));
        } catch (IOException | IllegalArgumentException e) {
            throw new DocumentException(e.getMessage());
        }
    }

    @GetMapping
    public List<DocumentResponse> getAllDocuments() {

        List<DocumentResponse> documents = documentService.getAllDocuments();
        if (documents.isEmpty()) {
            throw new DocumentException(MessageSourceUtil.getMessage("no.files.in.db"));
        }

        return documents;
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