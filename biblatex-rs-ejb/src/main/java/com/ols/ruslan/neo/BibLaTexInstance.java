package com.ols.ruslan.neo;

import java.util.Map;

/**
 * Данный класс хранит в себе поля и дает к ним доступ
 */
public class BibLaTexInstance {
    private Map<String, String> fields;
    private String oldType;

    public BibLaTexInstance(Map<String, String> fields) {
        this.fields = fields;
        oldType = getRecordType();
        if (!"".equals(getJournal())) {
            fields.put("journal", getJournal());
        }
        fields.remove("journal_description");
    }

    public String getOldType() {
        return oldType;
    }

    public void setOldType(String oldType) {
        this.oldType = oldType;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }

    public String getRecordType() {
        return fields.get("recordType") != null ? fields.get("recordType") : "";
    }

    public void setRecordType(String recordType) {
        this.fields.put("recordType", recordType);
    }

    public String getTechreport() {
        return fields.get("techreport");
    }

    public void setTechreport(String techreport) {
        this.fields.put("techreport", techreport);
    }

    public String getAuthor() {
        return fields.get("author") != null ? fields.get("author") : "";
    }

    public void setAuthor(String author) {
        this.fields.put("author", author);
    }

    public String getYear() {
        return fields.get("year") != null ? fields.get("year") : "";
    }

    public void setYear(String year) {
        this.fields.put("year", year);
    }

    public String getPublisher() {
        return fields.get("publisher") != null ? fields.get("publisher") : "";
    }

    public void setPublisher(String publisher) {
        this.fields.put("publisher", publisher);
    }

    public String getTitle() {
        return fields.get("title") != null ? fields.get("title") : "";
    }

    public void setTitle(String title) {
        this.fields.put("title", title);
    }

    public String getLanguage() {
        return fields.get("language") != null ? fields.get("language") : "";
    }

    public void setLanguage(String language) {
        this.fields.put("language", language);
    }

    public String getSchool() {
        return fields.get("school") != null ? fields.get("school") : "";
    }

    public void setSchool(String school) {
        this.fields.put("school", school);
    }

    public String getUrl() {
        return fields.get("url") != null ? fields.get("url") : "";
    }

    public void setUrl(String url) {
        this.fields.put("url", url);
    }

    public String getAddress() {
        return fields.get("address") != null ? fields.get("address") : "";
    }

    public void setAddress(String address) {
        this.fields.put("address", address);
    }

    public String getEdition() {
        return fields.get("edition") != null ? fields.get("edition") : "";
    }

    public void setEdition(String edition) {
        this.fields.put("edition", edition);
    }

    public String getJournal() {
        StringBuilder journal = new StringBuilder();
        if (fields.get("journal") != null) journal.append(fields.get("journal"));
        if (fields.get("journal_description") != null && PatternFactory.journalPattern.matcher(fields.get("journal_description").toLowerCase()).find()) {
            journal.append(", ").append(fields.get("journal_description"));
            setRecordType("article");
        }
        return journal.toString();
    }

    public void setJournal(String journal) {
        this.fields.put("journal", journal);
    }

    public String getNumber() {
        return fields.get("number") != null ? fields.get("number") : "";
    }

    public void setNumber(String number) {
        this.fields.put("number", number);
    }

    public String getPages() {
        return fields.get("pages") != null ? fields.get("pages") : "";
    }

    public void setPages(String pages) {
        this.fields.put("pages", pages);
    }

    public String getVolume() {
        return fields.get("volume") != null ? fields.get("volume") : "";
    }

    public void setVolume(String volume) {
        this.fields.put("volume", volume);
    }

    public void deleteYear() {
        this.fields.remove("year");
    }

    public void deletePublisher() {
        this.fields.remove("publisher");
    }

    public void deleteTitle() {
        this.fields.remove("title");
    }

    public void deleteLanguage() {
        this.fields.remove("language");
    }

    public void deleteSchool() {
        this.fields.remove("school");
    }

    public void deleteUrl() {
        this.fields.remove("url");
    }

    public void deleteAddress() {
        this.fields.remove("address");
    }

    public void deleteEdition() {
        this.fields.remove("edition");
    }

    public void deleteJournal() {
        this.fields.remove("journal");
    }

    public void deleteNumber() {
        this.fields.remove("number");
    }

    public void deletePages() {
        this.fields.remove("pages");
    }

    public void deleteVolume() {
        this.fields.remove("volume");
    }

    public void deleteAuthor() {
        this.fields.remove("author");
    }

    public void deleteRecordType() {
        this.fields.remove("recordType");
    }

    public void deleteTechreport() {
        this.fields.remove("techreport");
    }

    public String getTitleChapter() {
        return fields.get("title_chapter") != null ? fields.get("title_chapter") : "";
    }

    public void setTitleChapter(String title_chapter) {
        this.fields.put("title_chapter", title_chapter);
    }


}

