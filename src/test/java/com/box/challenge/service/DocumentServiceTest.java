package com.box.challenge.service;

import com.box.challenge.entity.Document;
import com.box.challenge.model.response.DocumentResponse;
import com.box.challenge.repository.DocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
@DisplayName("JUnit test for saveDocuments")
class DocumentServiceTest {

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentService documentService;
    private MultipartFile file1;
    private MultipartFile file2;


    @BeforeEach
    public void setup(){
        file1 = new MockMultipartFile("file1", "file1.pdf",
                "text/plain", "ile1 content1".getBytes());
        file2 = new MockMultipartFile("file2", "file2.pdf",
                "text/plain", "file2 content2".getBytes());
    }

    @Test
    public void saveDocuments_ValidFiles_ShouldSaveDocuments() throws IOException {

        // Given
        List<MultipartFile> files = Arrays.asList(file1, file2);
        String algorithm = "SHA-256";

        // When
        List<DocumentResponse> documentResponses = documentService.saveDocuments(files, algorithm);

        // Then
        assertEquals(2, documentResponses.size());
        assertEquals(file1.getOriginalFilename(), documentResponses.get(0).getFileName());
        assertEquals(file2.getOriginalFilename(), documentResponses.get(1).getFileName());
    }

    @Test
    void getAllDocuments() {
        // Given
        Document document = new Document();
        document.setFilename("test.txt");
        List<Document> mockDocuments = Collections.singletonList(document);

        // Mock repository behavior
        when(documentRepository.findAll()).thenReturn(mockDocuments);

        // When
        List<DocumentResponse> documentResponses = documentService.getAllDocuments();

        // Then
        assertFalse(documentResponses.isEmpty());
        assertEquals("test.txt", documentResponses.get(0).getFileName());
    }

    @Test
    void findDocumentByHash() {
        // Given
        String hashType = "SHA-256";
        String hash = "mockHash";
        Document mockDocument = new Document();
        mockDocument.setFilename("test.txt");

        // Mock repository behavior
        when(documentRepository.findByHash(hashType, hash)).thenReturn(Optional.of(mockDocument));

        // When
        DocumentResponse documentResponse = documentService.findDocumentByHash(hashType, hash);

        // Then
        assertNotNull(documentResponse);
        assertEquals("test.txt", documentResponse.getFileName());
    }
}