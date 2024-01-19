package com.example.testtask.document.service;

import com.example.testtask.document.controller.dto.*;
import com.example.testtask.document.store.DocumentStore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    /**
     * Метод для проверки в документе позиций с необходимыми метками
     *
     * @param checkTagsInDocumentDto метки для проверки
     * @return ответ на проверенный документ
     */
    @Override
    public AnswerOnCheckDocumentDto checkDocument(CheckTagsInDocumentDto checkTagsInDocumentDto) {
        var answer = new AnswerOnCheckDocumentDto();
        answer.setAnswerTable(new ArrayList<>());

        var permutationsTags = generatePermutationsTagsList(checkTagsInDocumentDto.getTags());

        for (var tags : permutationsTags) {
            var answerTable = fillAnswerTable(tags.toArray(new OneTagForCheckDto[0]));
            if (answerTable != null) {
                answer.setAnswerTable(new ArrayList<>(answerTable));
                break;
            }
        }
        if (answer.getAnswerTable().isEmpty()) {
            answer.setAnswer("Набора нет в документе");
        } else {
            answer.setAnswer("Набор содержится в документе.");
        }
        return answer;
    }

    /**
     * Метод для заполнения с таблицей ответов на проверку позиций в документе
     *
     * @param checkTags метки для поиска в документе
     * @return таблица с позициями и количеством товаров с меткой
     */
    private List<OnePositionInAnswerDto> fillAnswerTable(OneTagForCheckDto[] checkTags) {
        var products =
                DocumentStore.getInstance().getDocument().getProducts().toArray(new ProductDto[0]);
        products = deapCopy(products);
        checkTags = deapCopy(checkTags);
        var answerTable = new ArrayList<OnePositionInAnswerDto>();
        for (var checkTag : checkTags) {
            var positions = checkTagInProductsList(products, checkTag.getTag(), checkTag.getCount());
            if (positions == null) {
                return null;
            }
            answerTable.addAll(positions);
        }
        return answerTable;
    }

    /**
     * Поиск тега в документе с товарами
     *
     * @param products массив товаров
     * @param tag      метка которую ищем
     * @param count    количество метки которую ищем
     * @return список найденных позиций
     */
    private List<OnePositionInAnswerDto> checkTagInProductsList(ProductDto[] products,
                                                                String tag,
                                                                Long count) {
        var positions = new ArrayList<OnePositionInAnswerDto>();
        for (int i = 0; i < products.length; i++) {
            boolean checkTag =
                    products[i].getTags().stream().anyMatch(t -> t.equals(tag));
            if (checkTag) {
                if (products[i].getCount() > 0) {
                    if (products[i].getCount() >= count) {
                        positions.add(new OnePositionInAnswerDto(products[i].getName(), count));
                        products[i].setCount(products[i].getCount() - count);
                        return positions;
                    } else {
                        positions.add(new OnePositionInAnswerDto(products[i].getName(), products[i].getCount()));
                        count -= products[i].getCount();
                        products[i].setCount(0L);
                    }
                }
            }
        }
        if (count != 0) {
            return null;
        }
        return positions;
    }

    /**
     * Генерация различных перестановок тегов для проверки наличия в различных комбинациях
     *
     * @param elements елементы для генерации перестановок
     * @return возвращает список со списком тего в различных комбинациях
     */
    private List<List<OneTagForCheckDto>> generatePermutationsTagsList(List<OneTagForCheckDto> elements) {
        List<OneTagForCheckDto> currentTags = new ArrayList<>();
        List<List<OneTagForCheckDto>> permutationsTags = new ArrayList<>();
        boolean[] used = new boolean[elements.size()];

        generatePermutations(elements,
                currentTags,
                permutationsTags,
                used
        );

        return permutationsTags;
    }

    /**
     * Рекурсивная генерация перестановок элементов списка без повторений
     *
     * @param elements         елементы из которого состоит набор
     * @param current          текущая комбинация
     * @param permutationsTags список для добавления перестановок
     * @param used             массив использованных элементов
     * @param <T>              тип списка
     */
    private <T> void generatePermutations(List<T> elements,
                                          List<T> current,
                                          List<List<T>> permutationsTags,
                                          boolean[] used) {
        if (current.size() == elements.size()) {
            permutationsTags.add(new ArrayList<>(current));
            return;
        }

        for (int i = 0; i < elements.size(); i++) {
            if (!used[i]) {
                used[i] = true;
                current.add(elements.get(i));
                generatePermutations(elements, current, permutationsTags, used);
                current.remove(current.size() - 1);
                used[i] = false;
            }
        }
    }

    /**
     * Глубокое копирование массива через сериализация в строку JSON
     *
     * @param arrFoCopy массив для копирования
     * @param <T>       тип массива
     * @return копию массива
     */
    private <T> T[] deapCopy(T[] arrFoCopy) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(arrFoCopy);
            arrFoCopy = (T[]) objectMapper.readValue(json, arrFoCopy.getClass());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return arrFoCopy;
    }
}
