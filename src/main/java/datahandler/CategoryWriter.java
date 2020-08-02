package datahandler;

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
 * Writer for categories.xml.
 */
public class CategoryWriter {

    /** Path of the categories xml. */
    private static String catXMLPath = "./src/main/resources/categories.xml";

    /**
     * Writes a new category into xml.
     * @param categoryName name of the new category
     * @throws FileNotFoundException If categories.xml not found
     */
    public static void writeCategory(String categoryName) throws FileNotFoundException {

        Document doc = XMLHandler.getDocument(catXMLPath);
        Element docElement = doc.getDocumentElement();
        removeWhiteSpaceNodes(doc);

        Element nodeElement = doc.createElement("category");
        nodeElement.setAttribute("name", categoryName);
        docElement.appendChild(nodeElement);
        writeToXML(doc, docElement);
    }

    /**
     * Writes new item into the xml.
     * @param categoryName name of category to which the item will be inserted
     * @param itemName name of the new item
     * @throws FileNotFoundException If categories.xml not found
     */
    public static void writeItem(String categoryName, String itemName) throws FileNotFoundException {

        Document doc = XMLHandler.getDocument(catXMLPath);
        Element docElement = doc.getDocumentElement();
        removeWhiteSpaceNodes(doc);

        NodeList nList = doc.getDocumentElement().getElementsByTagName("category");
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (element.getAttribute("name").equals(categoryName)) {
                    handleInsertion(doc, element, itemName);
                }
            }
        }
        writeToXML(doc, docElement);
    }

    /**
     * Appends new item node to category node.
     * @param doc document in which the Appending happens
     * @param node category node to which the item will be appended
     * @param itemName name of the new item
     */
    private static void handleInsertion(Document doc, Element node, String itemName) {

        Element element = doc.createElement("item");
        element.setTextContent(itemName);
        node.appendChild(element);
    }

    /**
     * Writes the new data formatted into xml.
     * @param doc document in which the data will be written
     * @param docElement DocumentElement of doc
     */
    private static void writeToXML(Document doc, Element docElement) {
        doc.replaceChild(docElement, docElement);
        Transformer tFormer;
        try {
            tFormer = TransformerFactory.newInstance().newTransformer();
            //tFormer.setOutputProperty(OutputKeys.METHOD, "xml");
            tFormer.setOutputProperty(OutputKeys.INDENT, "yes");
            tFormer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            Source source = new DOMSource(doc);
            File xmlFile = new File(catXMLPath);
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
     * @param doc document itself
     */
    private static void removeWhiteSpaceNodes(Document doc) {

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
