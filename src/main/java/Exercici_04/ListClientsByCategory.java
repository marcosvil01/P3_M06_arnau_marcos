package Exercici_04;

import Functions.Functions;
import org.w3c.dom.*;
import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XMLResource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Scanner;

public class ListClientsByCategory {
    public void execute(String xmlFilePath, String outputFilePath, Collection collection) throws Exception {
        // Cargar el archivo XML original
        File xmlFile = new File(xmlFilePath);
        if (!xmlFile.exists()) {
            throw new Exception("El archivo XML no existe: " + xmlFilePath);
        }

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document originalDoc = docBuilder.parse(xmlFile);

        // Normalizar fechas en el documento original
        normalizeDates(originalDoc.getDocumentElement());

        // Pedir al usuario la categoría deseada
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introdueix la categoria (A, B, C, D, E):");
        String category = scanner.nextLine().toUpperCase();

        // Crear un nuevo documento XML
        Document newDoc = docBuilder.newDocument();
        Element rootElement = newDoc.createElement("clients");
        newDoc.appendChild(rootElement);

        // Filtrar los clientes por categoría
        NodeList clients = originalDoc.getElementsByTagName("client");
        for (int i = 0; i < clients.getLength(); i++) {
            Element client = (Element) clients.item(i);
            String clientCategory = client.getElementsByTagName("categoria").item(0).getTextContent();
            if (clientCategory.equalsIgnoreCase(category)) {
                Node importedClient = newDoc.importNode(client, true);
                rootElement.appendChild(importedClient);
            }
        }

        // Guardar el nuevo documento XML localmente
        Functions.saveDocument(newDoc, outputFilePath);

        // Subir el archivo filtrado a eXistDB
        String resourceName = "clientsByCategory.xml"; // Nombre del recurso que se creará en eXistDB
        uploadToExistDB(collection, newDoc, resourceName);

        System.out.println("\u001B[32mExercici 4.e fet!✅ Arxiu creat i pujat a eXistDB: " + resourceName + "\u001B[0m");
    }

    private void uploadToExistDB(Collection collection, Document doc, String resourceName) throws Exception {
        // Crear un recurso nuevo en eXistDB
        XMLResource resource = (XMLResource) collection.createResource(resourceName, "XMLResource");
        resource.setContentAsDOM(doc);
        collection.storeResource(resource);

        System.out.println("\u001B[34mNou arxiu pujat a eXistDB: " + resourceName + "\u001B[0m");
    }

    private void normalizeDates(Element rootElement) {
        NodeList clients = rootElement.getElementsByTagName("client");
        for (int i = 0; i < clients.getLength(); i++) {
            Element client = (Element) clients.item(i);
            NodeList dateNodes = client.getElementsByTagName("dataAlta");
            for (int j = 0; j < dateNodes.getLength(); j++) {
                Node dateNode = dateNodes.item(j);
                String originalDate = dateNode.getTextContent();
                if (originalDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    // Convertir la fecha de MM/dd/yyyy a yyyy-MM-dd
                    String[] parts = originalDate.split("/");
                    String normalizedDate = parts[2] + "-" + parts[0] + "-" + parts[1];
                    dateNode.setTextContent(normalizedDate);
                }
            }
        }
    }
}
