package com.example.testtask.document.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerOnCheckDocumentDto {

    private String answer;

    private List<OnePositionInAnswerDto> answerTable;
}
