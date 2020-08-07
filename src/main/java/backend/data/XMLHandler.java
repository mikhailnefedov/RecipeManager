package backend.data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Handler for getting xml files.
 */
public final class XMLHandler {

    private XMLHandler() {
    }

    /**
     * Gets the requested XML document from path.
     *
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

    /**
     * Writes the new data formatted into xml.
     *
     * @param doc        document in which the data will be written
     * @param docElement DocumentElement of doc
     * @param path       path of XML file
     */
    static void writeToXML(Document doc, Element docElement, String path) {
        doc.replaceChild(docElement, docElement);
        Transformer tFormer;
        try {
            tFormer = TransformerFactory.newInstance().newTransformer();
            tFormer.setOutputProperty(OutputKeys.INDENT, "yes");
            String indentAmount = "{http://xml.apache.org/xslt}indent-amount";
            tFormer.setOutputProperty(indentAmount, "2");
            Source source = new DOMSource(doc);
            File xmlFile = new File(path);
            Result result = new StreamResult(xmlFile);
            tFormer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes White Space Nodes from Document for formatting purposes.
     *
     * @param doc document itself
     */
    static void removeWhiteSpaceNodes(Document doc) {

        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPathExpression xpathExp;
        try {
            xpathExp = xpathFactory.newXPath().compile(
                    "//text()[normalize-space(.) = '']");
            NodeList emptyTextNodes = (NodeList)
                    xpathExp.evaluate(doc, XPathConstants.NODESET);
            // Remove each empty text node from document.
            for (int i = 0; i < emptyTextNodes.getLength(); i++) {
                Node emptyTextNode = emptyTextNodes.item(i);
                emptyTextNode.getParentNode().removeChild(emptyTextNode);
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }
}
