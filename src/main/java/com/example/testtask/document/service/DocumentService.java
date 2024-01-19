package com.example.testtask.document.service;

import com.example.testtask.document.controller.dto.AnswerOnCheckDocumentDto;
import com.example.testtask.document.controller.dto.CheckTagsInDocumentDto;

public interface DocumentService {
    public AnswerOnCheckDocumentDto checkDocument(CheckTagsInDocumentDto checkTagsInDocumentDto);
}
