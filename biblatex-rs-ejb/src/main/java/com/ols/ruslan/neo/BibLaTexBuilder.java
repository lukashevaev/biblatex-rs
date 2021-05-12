package com.ols.ruslan.neo;


import java.util.*;
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

        // Заменяем "rus" на "russian" (по правилам данного формата)
        if (instance.getLanguage().equals("rus")) instance.setLanguage("russian");
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

        instance.setAddress(" - " + instance.getAddress() + ":");
        instance.setEdition(" - " + instance.getEdition());
        instance.setPages("- " + getDigits(instance.getPages()) + " с.");
        instance.setPublisher(instance.getPublisher() + ", ");
        instance.setTitleChapter("// " + instance.getTitleChapter());
        instance.setEditor("/ " + instance.getEditor());


        instance.getFields().entrySet().forEach(entry -> {
            String value = entry.getValue();
            if (value != null
                    && value.length() > 1
                    && !PatternFactory.specialSymbolsPattern.matcher(String.valueOf(value.charAt(value.length() - 1))).find()
                    && PatternFactory.notEmptyFieldPattern.matcher(entry.getValue()).find()) {
                entry.setValue(entry.getValue() + ". ");
            }
        });

        //Удаляем пустые поля
        instance.setFields(
                instance.getFields()
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getValue() != null && !entry.getValue().equals("") && PatternFactory.notEmptyFieldPattern.matcher(entry.getValue()).find())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue , (a, b) -> a, LinkedHashMap::new)));
    }

    public String buildBiblatex() {

        StringBuilder builder = new StringBuilder();
        if (!"".equals(instance.getAuthor()) && !"proceedings".equals(recordType)) {
            builder.append(instance.getAuthor())
                    .append(instance.getTitle());
        } else {
            builder.append(instance.getTitle());
        }
        if ("article".equals(recordType)) {
            instance.setPages(" - " + getDigits("C" + instance.getPages()));
            builder.append(instance.getEditor());
            builder.append(" // ").append(instance.getJournal());
            builder.append(" - ").append(instance.getVolume());
            builder//.append(instance.getEditor())
                    .append(instance.getAddress())
                    .append(instance.getPublisher())
                    .append(instance.getYear())
                    .append(instance.getPages());
        } else if ("book".equals(recordType)) {
            builder.append(instance.getVolume())
                    .append(instance.getEdition())
                    .append(instance.getEditor())
                    .append(instance.getAddress())
                    .append(instance.getPublisher())
                    .append(instance.getYear())
                    .append(instance.getPages());
        } else if ("mvbook".equals(recordType)) {
            builder.append(instance.getEditor())
                    .append(instance.getEdition())
                    .append(instance.getAddress())
                    .append(instance.getPublisher())
                    .append(instance.getYear())
                    .append(instance.getPages());
        } else if ("inbook".equals(recordType)) {
            instance.setPublisher("В: " + instance.getPublisher() + "(изд.)");
            instance.setPages(" - " + getDigits("C" + instance.getPages()));
            builder.append(instance.getVolume())
                    .append(instance.getEdition())
                    .append(instance.getEditor())
                    .append(instance.getTitleChapter())
                    .append(instance.getAddress())
                    .append(instance.getPublisher())
                    .append(instance.getYear())
                    .append(instance.getPages());
        } else if ("thesis".equals(recordType)) {
            builder.append(instance.getOldType())
                    // : speciality code
                    .append(instance.getEditor())
                    .append(instance.getAddress())
                    .append(instance.getPublisher())
                    .append(instance.getYear())
                    .append(instance.getPages());
        } else if ("proceedings".equals(recordType)) {
            builder.append(instance.getEditor())
                    .append(instance.getAddress())
                    .append(instance.getPublisher())
                    .append(instance.getYear())
                    .append(instance.getPages());
            // -(series; number).
        } else if ("inproceedings".equals(recordType)) {
            if (!"".equals(instance.getPages())) instance.setPages("-" + getDigits("C" + instance.getPages()));
            builder.append(instance.getEditor())
                    .append(instance.getTitleChapter())
                    .append(instance.getAddress())
                    .append(instance.getPublisher())
                    .append(instance.getYear())
                    .append(instance.getPages());
            //  -(series; number).
        } else {
            builder = new StringBuilder();
            instance.getFields().values().forEach(builder::append);
        }
        builder.trimToSize();
        String[] words = builder.toString().split(" ");
        String field = null;
        for (int i = words.length - 1; i >= 0; i--) {
            field = words[i];
            if (PatternFactory.notEmptyFieldPattern.matcher(field).find() && field.length() > 1) {
                break;
            }
        }
        String result = builder.toString();
        if (field != null) return builder
                .substring(0, result.lastIndexOf(field) + field.length())
                .replaceAll("\\.\\s*[a-zA-Zа-яА-Я]?\\s*\\.", ".")
                .replaceAll(",\\s*[,.]", ",")
                .replaceAll(":\\s*[,.:]", ":")
                .replaceAll("-\\s*-", "-");
        return result;
    }
}
