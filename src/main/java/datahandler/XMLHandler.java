package datahandler;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Handler for getting xml files.
 */
public class XMLHandler {

    /**
     * Gets the requested XML document from path.
     * @param filePath path to xml document
     * @return document of xml
     * @throws FileNotFoundException If file doesn't exist
     */
    public static Document getDocument(String filePath) throws FileNotFoundException {

        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileNotFoundException(filePath + "is missing");
        }
    }
}
