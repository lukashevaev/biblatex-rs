package com.ols.ruslan.neo;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Получаем все поля из схемы
 */
public abstract class XmlParser {

    public static Map<String, String> parse(final Document document) {
        if (document == null || document.getDocumentElement() == null) return Collections.emptyMap();
        else {
            Node book = document.getDocumentElement().getFirstChild();
            NodeList bookProps = book.getChildNodes();
            return IntStream.range(0, bookProps.getLength())
                    .mapToObj(bookProps::item)
                    .filter(bookProp -> bookProp.hasChildNodes() &&
                            !bookProp.getTextContent().isEmpty())
                    .collect(Collectors.toMap(Node::getNodeName, Node::getTextContent, (a, b) -> a, LinkedHashMap::new));
        }
    }
}
