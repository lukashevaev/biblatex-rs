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
        setTitle(getTitle());
        setJournal(getJournal());
        fields.remove("journal_description");
    }

    public String getOldType() {
        return oldType;
    }

    public void setOldType(String oldType) {
        if (oldType != null && !"".equals(oldType)) return;
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
        if (fields.get("recordType") == null) return;
        this.fields.put("recordType", recordType);
    }

    public String getTechreport() {
        return fields.get("techreport");
    }

    public void setTechreport(String techreport) {
        if (fields.get("techreport") == null) return;
        this.fields.put("techreport", techreport);
    }

    public String getConference() {
        return fields.get("conference");
    }

    public void setConference(String conference) {
        if (fields.get("conference") == null) return;
        this.fields.put("conference", conference);
    }

    public String getData() {
        return fields.get("data");
    }

    public void setData(String data) {
        if (fields.get("data") == null) return;
        this.fields.put("data", data);
    }

    public String getUniversity() {
        return fields.get("university");
    }

    public void setUniversity(String university) {
        if (fields.get("university") == null) return;
        this.fields.put("university", university);
    }

    public String getAuthor() {
        return fields.get("author") != null ? fields.get("author") : "";
    }

    public void setAuthor(String author) {
        if (fields.get("author") == null) return;
        this.fields.put("author", author);
    }

    public String getYear() {
        return fields.get("year") != null ? fields.get("year") : "";
    }

    public void setYear(String year) {
        if (fields.get("year") == null) return;
        this.fields.put("year", year);
    }

    public String getPublisher() {
        return fields.get("publisher") != null ? fields.get("publisher") : "";
    }

    public void setPublisher(String publisher) {
        if (fields.get("publisher") == null) return;
        this.fields.put("publisher", publisher);
    }

    public String getTitle() {
        StringBuilder builder = new StringBuilder();
        String recordType = fields.get("recordType");
        if (fields.get("title") != null) builder.append(fields.get("title"));
        if (recordType != null && PatternFactory.notEmptyFieldPattern.matcher(recordType).find()) builder.append(": ").append(recordType);
        return builder.toString();
    }

    public void setTitle(String title) {
        if (fields.get("title") == null) return;
        this.fields.put("title", title);
    }

    public String getLanguage() {
        return fields.get("language") != null ? fields.get("language") : "";
    }

    public void setLanguage(String language) {
        if (fields.get("language") == null) return;
        this.fields.put("language", language);
    }

    public String getSchool() {
        return fields.get("school") != null ? fields.get("school") : "";
    }

    public void setSchool(String school) {
        if (fields.get("school") == null) return;
        this.fields.put("school", school);
    }

    public String getUrl() {
        return fields.get("url") != null ? fields.get("url") : "";
    }

    public void setUrl(String url) {
        if (fields.get("url") == null) return;
        this.fields.put("url", url);
    }

    public String getAddress() {
        return fields.get("address") != null ? fields.get("address") : "";
    }

    public void setAddress(String address) {
        if (fields.get("address") == null) return;
        this.fields.put("address", address);
    }

    public String getEdition() {
        return fields.get("edition") != null ? fields.get("edition") : "";
    }

    public void setEdition(String edition) {
        if (fields.get("edition") == null) return;
        this.fields.put("edition", edition);
    }

    public String getEditor() { return fields.get("editor") != null ? fields.get("editor") : ""; }

    public void setEditor(String editor) {
        if (fields.get("editor") == null) return;
        this.fields.put("editor", editor);
    }

    public String getJournal() {
        StringBuilder journal = new StringBuilder();
        if (fields.get("journal") != null && !fields.get("journal").equals("")) journal.append(fields.get("journal"));
        if (fields.get("journal_description") != null && PatternFactory.journalPattern.matcher(fields.get("journal_description").toLowerCase()).find()) {
            journal.append(", ").append(fields.get("journal_description"));
            setRecordType("article");
        }
        return journal.toString();
    }

    public void setJournal(String journal) {
        if (fields.get("journal") == null) return;
        this.fields.put("journal", journal);
    }

    public String getNumber() {
        return fields.get("number") != null ? fields.get("number") : "";
    }

    public void setNumber(String number) {
        if (fields.get("number") == null) return;
        this.fields.put("number", number);
    }

    public String getPages() {
        return fields.get("pages") != null ? fields.get("pages") : "";
    }

    public void setPages(String pages) {
        if (fields.get("pages") == null) return;
        this.fields.put("pages", pages);
    }

    public String getVolume() {
        return fields.get("volume") != null ? fields.get("volume") : "";
    }

    public void setVolume(String volume) {
        if (fields.get("volume") == null) return;
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

