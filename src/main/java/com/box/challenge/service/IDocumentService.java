package com.box.challenge.service;

import com.box.challenge.entity.Document;
import com.box.challenge.model.response.DocumentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IDocumentService {
    List<DocumentResponse> saveDocuments(List<MultipartFile> files, String algorithm) throws IOException;

    List<DocumentResponse> getAllDocuments();

    DocumentResponse findDocumentByHash(String hashType, String hash);
}
