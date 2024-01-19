package com.example.testtask.document.controller;

import com.example.testtask.document.controller.dto.AnswerOnCheckDocumentDto;
import com.example.testtask.document.controller.dto.CheckTagsInDocumentDto;
import com.example.testtask.document.controller.dto.DocumentDto;
import com.example.testtask.document.service.DocumentService;
import com.example.testtask.document.store.DocumentStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    @Autowired
    private DocumentService service;

    @PostMapping(path = "/checkDocument",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public AnswerOnCheckDocumentDto checkDocument(
            @RequestBody CheckTagsInDocumentDto checkTagsInDocumentDto
    ) {
        return service.checkDocument(checkTagsInDocumentDto);
    }
}
