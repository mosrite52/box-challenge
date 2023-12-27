package com.box.challenge.repository;
import com.box.challenge.entity.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class DocumentRepositoryTest {

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private TestEntityManager entityManager;

    private Document document;

    @BeforeEach
    public void setup(){
        document = Document.builder().filename("file.pdf").hashSha256("111111111").build();
    }

    @Test
    public void testSaveDocument() {

        // When
        Document persistedDocument = documentRepository.save(document);

        // Then
        assertEquals(document.getFilename(), persistedDocument.getFilename());

    }

    @Test
    public void testGetAllDocuments() {
        // Given
        Document document = Document.builder().filename("file.pdf").hashSha256("mockHash").build();
        documentRepository.save(document);

        // When
        List<Document> documents = documentRepository.findAll();

        // Then
        assertEquals(1, documents.size());
        assertEquals("file.pdf", documents.get(0).getFilename());
    }

    @Test
    public void testFindByHash() {
        // Given
        String hashType = "SHA-256";
        String hash = "mockHash";

        Document document = Document.builder()
                .id(1L)
                .filename("test.txt")
                .hashSha256("mockHash")
                .build();

        documentRepository.save(document);

        // When
        Optional<Document> result = documentRepository.findByHash(hashType, hash);

        // Then
        assertTrue(result.isPresent());
        assertEquals(document.getId(), result.get().getId());
        assertEquals(document.getFilename(), result.get().getFilename());
        assertEquals(document.getHashSha256(), result.get().getHashSha256());
    }
}
