package com.ols.ruslan.neo;


import org.w3c.dom.Document;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.logging.Logger;


@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionManagement(TransactionManagementType.CONTAINER)
@Singleton(name = "XmlToBiblatexTransformer")
@Startup
@Remote(MediaTypeTransformerFacade.class)
@EJB(name = "java:global/ruslan/mediaType/application/xml/application/x-biblatex", beanInterface = MediaTypeTransformerFacade.class)
public class XmlToBiblatexTransformer implements MediaTypeTransformerFacade {
    private static final Logger log = Logger.getLogger(XmlToBiblatexTransformer.class
            .getName());
    private static final TransformerFactory transformerFactory = TransformerFactory.newInstance();
    private static Templates templates;


    @PostConstruct
    void startup() {
        log.info("Startup");
        try {
            templates = transformerFactory.newTemplates(new StreamSource(
                    XmlToBiblatexTransformer.class.getClassLoader().getResourceAsStream(
                            "RUSMARC2BibLaTex.xsl")));

        } catch (TransformerConfigurationException e) {
            log.severe("Unable to initialise templates: " + e.getMessage());
            e.printStackTrace();
        }
    }



    @Override
    public byte[] transform(byte[] content, String encoding) throws Exception {
        // Создаем трансформер для преобразования одного xml в другой
        Transformer transformer = templates.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        DOMResult result = new DOMResult();

        // Создаем источник для преобразования из поступившего массива байт
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new ByteArrayInputStream(content));

        //Трансформация,парсинг и создание нового формата
        transformer.transform(new DOMSource(document), result);
        Map<String, String> fields = XmlParser.parse((Document) result.getNode());
        BibLaTexBuilder bibLaTexBuilder = new BibLaTexBuilder(fields);

        return bibLaTexBuilder.buildBiblatex().getBytes(encoding);
    }

}
