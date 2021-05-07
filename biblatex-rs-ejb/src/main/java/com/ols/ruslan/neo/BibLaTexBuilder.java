package com.ols.ruslan.neo;


import org.jsoup.helper.StringUtil;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class BibLaTexBuilder {
    private String recordType;
    BibLaTexInstance instance;

    public BibLaTexBuilder(final Map<String, String> fields) {
        instance = new BibLaTexInstance(fields);
        TypeDefiner typeDefiner = new TypeDefiner(instance);
        this.recordType = typeDefiner.getRecordType();
        refactorFields();
    }
    // Имя библиографичксой записи формата Bibtex
    // (указывается до перечисления полей, записываем в форме AuthorYear)
    private String getBibtexKey() {
        if (!instance.getFields().isEmpty())
            return instance.getAuthor().split(" ")[0] + instance.getYear();
        else return "EmptyRecord";
    }

    // Метод для выделения цифр из поля
    public String getDigits(String field) {
        return field.replaceAll("[^0-9-]", "");
    }
    // Изменение полей
    private void refactorFields(){
        if (!instance.getAddress().equals("")) {
            instance.setAddress(instance.getAddress() + ":");
        }

        if (!instance.getYear().equals("")) {
            instance.setYear(instance.getYear() + ". - ");
        }

        if (PatternFactory.volumesPattern.matcher(instance.getRecordType()).find()) {
            instance.setTitle(instance.getTitle() +". " + PatternFactory.volumesPattern.matcher(instance.getRecordType()).group());
        }



        this.recordType = this.recordType != null ? recordType : "misc";
        // Удаляем поля
        // RecordType записывается отдельно в самом начале
        // Techreport: если есть это поле- однозначно определяется тип, если его нет- удаляется
        instance.deleteRecordType();
        instance.deleteTechreport();

        // Удаление "and" в конце поля "author"
        String author = instance.getAuthor();
        if (!StringUtil.isBlank(author)) instance.setAuthor(author.substring(0, author.length() - 2));

        // Заменяем "rus" на "russian" (по правилам данного формата)
        if (instance.getLanguage().equals("rus"))
            instance.setLanguage("russian");
        // Удаляем поле том, если оно не удовлетворяет паттерну
        if (!PatternFactory.volumePattern.matcher(instance.getVolume().toLowerCase()).find()) instance.deleteVolume();
        // Если не статья, то удаляем номер
        if (!"article".equals(recordType)) instance.deleteNumber();
        // Если тип записи статья,  но номер журнала не подходит под паттерн-
        // удаляем его. В противном случае удаляем номер тома
        if ("article".equals(recordType)) {
            if (!PatternFactory.numberPattern.matcher(instance.getNumber().toLowerCase()).find()) instance.deleteNumber();
            else instance.deleteVolume();
        }
        String pages = instance.getPages();
        if (!"book".equals(recordType) & PatternFactory.pagePattern.matcher(pages).find()) instance.deletePages();

        if (!"".equals(instance.getAddress())) instance.setAddress("-" + instance.getAddress() + ":");
        if (!"".equals(instance.getEdition())) instance.setEdition("-" + instance.getEdition());
        if (!"".equals(instance.getPages())) instance.setPages("-" + getDigits(instance.getPages()) + " с.");
        if (!"".equals(instance.getPublisher())) instance.setPublisher(instance.getPublisher() + ", ");
        if (!"".equals(instance.getTitleChapter())) instance.setTitleChapter("// " + instance.getTitleChapter());


        instance.getFields().entrySet().forEach(entry -> {
            if (!PatternFactory.specialSymbolsPattern.matcher(entry.getValue()).find()) {
                entry.setValue(entry.getValue() + ". ");
            }
        });
    }

    public String buildBiblatex() {
        instance.setFields(
                instance.getFields()
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() != null && !entry.getValue().equals(""))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue , (a, b) -> a, LinkedHashMap::new)));
        StringBuilder builder = new StringBuilder();
        if (!"".equals(instance.getAuthor()) && !"proceedings".equals(recordType)) {
            builder.append(instance.getAuthor())
                    .append(instance.getTitle());
        } else {
            builder.append(instance.getTitle());
        }
        if ("article".equals(recordType)) {
            if (!"".equals(instance.getPages())) instance.setPages("-" + getDigits("C" + instance.getPages()));
            //getEditor /before  //after
            builder.append(instance.getJournal());
            builder.append(instance.getAddress());
            builder.append(instance.getPublisher());
            builder.append(instance.getYear());
            if (!instance.getVolume().equals("")) builder.append(" - ").append(instance.getVolume());
            builder.append(instance.getPages());
        } else if ("book".equals(recordType)) {
            builder.append(instance.getVolume());
            builder.append(instance.getEdition());
            //getEditor /before
            builder.append(instance.getAddress());
            builder.append(instance.getPublisher());
            builder.append(instance.getYear());
            builder.append(instance.getPages());
        } else if ("mvbook".equals(recordType)) {
            //getEditor /before
            builder.append(instance.getEdition());
            builder.append(instance.getAddress());
            builder.append(instance.getPublisher());
            builder.append(instance.getYear());
            builder.append(instance.getPages());
        } else if ("inbook".equals(recordType)) {
            builder.append(instance.getVolume());
            builder.append(instance.getEdition());
            //getEditor /before
            if (!instance.getPublisher().equals("")) instance.setPublisher("В: " + instance.getPublisher() + "(изд.)");
            if (!"".equals(instance.getPages())) instance.setPages("-" + getDigits("C" + instance.getPages()));
            builder.append(instance.getTitleChapter());
            builder.append(instance.getAddress());
            builder.append(instance.getPublisher());
            builder.append(instance.getYear());
            builder.append(instance.getPages());
        } else if ("thesis".equals(recordType)) {
            if (!instance.getOldType().equals("")) builder.append(": ").append(instance.getOldType());
            // : speciality code
            // editor /before
            builder.append(instance.getAddress());
            builder.append(instance.getPublisher());
            builder.append(instance.getYear());
            builder.append(instance.getPages());
        } else if ("proceedings".equals(recordType)) {
            //getEditor /before
            builder.append(instance.getAddress());
            builder.append(instance.getPublisher());
            builder.append(instance.getYear());
            builder.append(instance.getPages());
            // -(series; number).
        } else if ("inproceedings".equals(recordType)) {
            if (!"".equals(instance.getPages())) instance.setPages("-" + getDigits("C" + instance.getPages()));
            //getEditor; /before
            builder.append(instance.getTitleChapter());
            builder.append(instance.getAddress());
            builder.append(instance.getPublisher());
            builder.append(instance.getYear());
            builder.append(instance.getPages());
            //  -(series; number).
        } else {
            builder = new StringBuilder();
            instance.getFields().values().forEach(builder::append);
        }
        builder.trimToSize();
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString().replace("..", ".");
    }

}
