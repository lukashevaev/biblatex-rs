package com.ols.ruslan.neo;

/**
 * Перечисление типов
 */
public enum RecordType {
    book,
    mvbook,
    patent,
    inbook,
    proceedings,
    article,
    thesis,
    techreport,
    misc;

    public static RecordType getType(String type) {
        return RecordType.valueOf(type);
    }
}
