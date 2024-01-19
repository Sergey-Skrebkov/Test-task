package com.example.testtask.document.store;

import com.example.testtask.document.controller.dto.DocumentDto;
import com.example.testtask.document.controller.dto.ProductDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DocumentStore {
    private static DocumentStore INSTANCE;
    private static DocumentDto document;

    private DocumentStore() {

        List<ProductDto> productDtos = new ArrayList<>();

        List<String> tags = new ArrayList<>();
        tags.add("C");

        productDtos.add(new ProductDto("Товар1", 5L, new ArrayList<>(tags)));
        productDtos.add(new ProductDto("Товар5", 1L, new ArrayList<>(tags)));
        productDtos.add(new ProductDto("Товар3", 1L, new ArrayList<>()));

        tags.clear();
        tags.add("A");

        productDtos.add(new ProductDto("Товар4", 2L, new ArrayList<>(tags)));

        tags.add("B");

        productDtos.add(new ProductDto("Товар2", 2L, new ArrayList<>(tags)));

        productDtos.sort(
                Comparator.comparing(ProductDto::getName)
        );

        document = new DocumentDto(productDtos);
    }

    public static DocumentStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DocumentStore();
        }
        return INSTANCE;
    }

    public DocumentDto getDocument() {
        return document;
    }
}
