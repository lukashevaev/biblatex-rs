package com.ols.ruslan.neo;

//import org.bouncycastle.util.test.Test;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


public class Tester {
    XmlToBiblatexTransformer transformer;
    DocumentBuilderFactory builderFactory;
    DocumentBuilder docBuilder;
    Document document = null;

    public Tester() throws ParserConfigurationException {
        transformer = new XmlToBiblatexTransformer();
        transformer.startup();

        builderFactory = DocumentBuilderFactory.newInstance();
        docBuilder = builderFactory.newDocumentBuilder();
    }


    public static void main(String[] args) throws Exception {
        /*XmlToBibtexTransformer transformer = new XmlToBibtexTransformer();
        transformer.startup();
        InputStream inputStream = Tester.class.getClassLoader().getResourceAsStream("file.xml");
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
        Document document = null;
        if (inputStream != null) document = docBuilder.parse(inputStream);
        byte[] bytes = getBytes(document);
        System.out.println(Arrays.toString(transformer.transform(bytes, "UTF-8")));*/
    }

    public static byte[] getBytes(Document document) throws Exception {
        Source source = new DOMSource( document );
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Result result = new StreamResult(out);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.transform(source, result);
        return out.toByteArray();
    }

    public byte[] getSourceByteArray(String fileName) {

        InputStream inputStream = Tester.class.getClassLoader().getResourceAsStream(fileName);

        if (inputStream != null) {
            try {
                document = docBuilder.parse(inputStream);

                return getBytes(document);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String transform(byte[] bytes) {
        try {
            return transformer.transformTest(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }





}
