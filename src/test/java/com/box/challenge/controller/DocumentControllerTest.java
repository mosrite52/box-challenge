package com.box.challenge.controller;

import com.box.challenge.model.response.DocumentResponse;
import com.box.challenge.service.DocumentService;
import com.box.challenge.util.BuildResponseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;



@WebMvcTest(controllers = DocumentController.class)
public class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DocumentService documentService;

    @InjectMocks
    private DocumentController documentController;

    DocumentResponse documentResponse;

    @BeforeEach
    public void setup(){
        documentController = new DocumentController(documentService);
        documentResponse = DocumentResponse.builder().fileName("test.txt").hashSha256("111111111").build();

    }

    @Test
    public void testUploadDocuments_Success() throws IOException {
        // Given
        List<DocumentResponse> documentResponses = Collections.singletonList(documentResponse);
        when(documentService.saveDocuments(Collections.emptyList(), "SHA-256")).thenReturn(documentResponses);

        // When
        ResponseEntity<Object> actualResponse = documentController.uploadDocuments("SHA-256", Collections.emptyList());

        // Then
        assertEquals(ResponseEntity.status(HttpStatus.CREATED).body(BuildResponseUtil.filesResponse("SHA-256", documentResponses)), actualResponse);

    }


    @Test
    public void testGetAllDocuments() throws Exception {
        // given
        List<DocumentResponse> documentResponses = Collections.singletonList(documentResponse);
        when(documentService.getAllDocuments()).thenReturn(documentResponses);

        // when
        List<DocumentResponse> actualDocuments = documentController.getAllDocuments();

        // then
        assertEquals(documentResponses, actualDocuments);
    }

    @Test
    public void testFindDocumentByHash() throws Exception {
        // given
        String hashType = "SHA-256";
        String hash = "mockHash";

        when(documentService.findDocumentByHash(hashType, hash)).thenReturn(documentResponse);

        // when
        ResponseEntity<DocumentResponse> responseEntity = documentController.findDocumentByHash(hashType, hash);

        // then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(documentResponse, responseEntity.getBody());
    }
}