package com.ols.ruslan.neo;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Данный класс используется для того, чтобы определить тип записи на основании паттернов
 * и на основании обязательных полей
 */
public class TypeDefiner {
    private String recordType;
    private final Map<RecordType, Pattern> patternsForType;
    private final Map<RecordType, Set<String>> requiredFields = new HashMap<>();
    private final Map<RecordType, Set<String>> rejectedFields = new HashMap<>();
    private Set<String> recordTypes = new HashSet<>();
    private final BibLaTexInstance instance;

    public TypeDefiner(BibLaTexInstance instance){
        PatternFactory patternFactory = new PatternFactory();
        patternsForType = patternFactory.getPatternsForType();
        this.instance = instance;
        if (!instance.getFields().isEmpty()) {
            recordType = instance.getRecordType().toLowerCase();
            fillRequiredFields();
            fillRejectedFields();
            defineType();
        }
    }
    // Метод, который определяет тип
    private void defineType(){
        boolean isChanged = false;
        String currentFoundRecordType;
        //Поиск типа по паттернам
        for (Map.Entry<RecordType,Pattern> entry : patternsForType.entrySet()) {
                if (entry.getValue().matcher(recordType).find() ||
                        entry.getValue().matcher(instance.getTitle().toLowerCase()).find()) {
                    currentFoundRecordType = entry.getKey().toString();
                    recordType = currentFoundRecordType;
                    return;
                }
        }

        //Проверка на наличие у записи всех обязательных полей для какого-либо типа и проверка на отсутсвие запрещенных полей этого типа
        //При удачной проверке тип запишется в recordTypes
        requiredFields.forEach((key, value) -> {
            if (instance.getFields().keySet().containsAll(value)
                    && instance.getFields().keySet().stream().noneMatch(field -> rejectedFields.get(key) != null && rejectedFields.get(key).contains(field))) {
                recordTypes.add(key.toString());
            }
        });
        // Если тип не нашелся по паттернам, то поиск по обязательным полям и отсутствию запрещенных
        // Если по обязательным полям найдется один тип, то он выведется. А если больше одного, то поиск продолжается по особым случаям записи
        if (recordTypes.size() == 1){
            recordType = recordTypes.iterator().next();
            return;
        } else {
            //searchForSpecialCases

            // Проверка @book: есть общее количество страниц, не 12-25
            String pages = instance.getPages();
            if (PatternFactory.pagePattern.matcher(pages).find()
                    && !PatternFactory.pagesPattern.matcher(pages).find()) {
                recordType = "book";
                return;
            }
            // Если удовлетворяет паттерну "digits-digits" и подходит под @book, то это @inbook
            if (recordType.equals("book") && PatternFactory.pagesPattern.matcher(pages).find()) {
                recordType = "inbook";
                return;
            }

            //Если удовлетворяет паттерну "digits-digits" и подходит под @proceedings, то это @inproceedings
            if (recordType.equals("proceedings")
                && PatternFactory.pagesPattern.matcher(instance.getPages()).find()) {
                recordType = "inproceedings";
                return;
            }

        }
        if (!isChanged) recordType = "misc";
    }

    //Обязательные поля для каждого типа
    private void fillRequiredFields() {
        requiredFields.put(RecordType.book, new HashSet<>(Arrays.asList(
                "author",
                "title",
                "year",
                "publisher"
        )));
        requiredFields.put(RecordType.article, new HashSet<>(Arrays.asList(
                "author",
                "title",
                "journal",
                "year"
        )));
    }

    //Запрещенные поля для каждого типа
    private void fillRejectedFields() {
        rejectedFields.put(RecordType.proceedings, new HashSet<>(Arrays.asList(
                "author"
        )));
        rejectedFields.put(RecordType.book, new HashSet<>(Arrays.asList(
                "journal"
        )));
    }

    public String getRecordType(){
        return recordType;
    }
}
