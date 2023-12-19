package com.box.challenge.service;

import com.box.challenge.constants.HashAlgorithm;
import com.box.challenge.entity.Document;
import com.box.challenge.model.response.DocumentResponse;
import com.box.challenge.repository.DocumentRepository;
import com.box.challenge.util.CalculateHashUtil;
import com.box.challenge.util.MessageSourceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<DocumentResponse> saveDocuments(List<MultipartFile> files, String algorithm) throws IOException {

        validateFiles(files);

        return files.stream()
                .map(file -> processFile(file, algorithm))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public List<DocumentResponse> getAllDocuments() {
        List<Document> documents = documentRepository.findAll();
        return documents.stream()
                .map(this::mapToDocumentResponse)
                .collect(Collectors.toList());
    }

    private Optional<DocumentResponse> processFile(MultipartFile file, String algorithm) {
        String filename = file.getOriginalFilename();
        Optional<Document> existingDocument = documentRepository.findByFilename(filename);

        Document document = existingDocument.orElse(new Document());
        document.setFilename(filename);

        try {
            byte[] fileBytes = file.getBytes();
            String hash = CalculateHashUtil.calculateHash(fileBytes, algorithm);
            HashAlgorithm hashAlgorithm = HashAlgorithm.fromString(algorithm);

            // Setea los campos de hash según el algoritmo
            switch (hashAlgorithm) {
                case SHA_256 -> {
                    document.setHashSha256(hash);
                }
                case SHA_512 -> {
                    document.setHashSha512(hash);
                }
                default -> throw new IllegalArgumentException("Invalid hash algorithm");
            }

            // Establece la fecha de la última carga
            if(existingDocument.isPresent())
                document.setLastUpload(LocalDateTime.now());

            documentRepository.save(document);

            DocumentResponse response = new DocumentResponse();
            response.setFileName(filename);
            response.setHash(hash);
            response.setLastUpload(document.getLastUpload());

            return Optional.of(response);
        } catch (IOException e) {
            //TODO Better exceptions
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public DocumentResponse findDocumentByHash(String hashType, String hash) {

        Optional<Document> document = documentRepository.findByHash(hashType, hash);
        return document.map(doc -> mapToDocumentResponse(doc, hashType, hash)).orElse(null);
    }

    private DocumentResponse mapToDocumentResponse(Document document) {
        DocumentResponse response = new DocumentResponse();
        response.setFileName(document.getFilename());
        response.setHashSha256(document.getHashSha256());
        response.setHashSha512(document.getHashSha512());
        response.setLastUpload(document.getLastUpload());
        return response;
    }

    private DocumentResponse mapToDocumentResponse(Document document, String hashType, String hash) {
        DocumentResponse response = new DocumentResponse();
        response.setFileName(document.getFilename());
        switch (HashAlgorithm.fromString(hashType)) {
            case SHA_256 -> {
                response.setHash(document.getHashSha256());
            }
            case SHA_512 -> {
                response.setHash(document.getHashSha512());
            }
        }
        response.setLastUpload(document.getLastUpload());
        return response;
    }

    private void validateFiles(List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException(MessageSourceUtil.getMessage("error.no.files"));
        }
        if (files.stream().anyMatch(file -> file.isEmpty() || !StringUtils.hasText(file.getOriginalFilename()))) {
            throw new IllegalArgumentException(MessageSourceUtil.getMessage("error.no.files"));
        }
    }


}
