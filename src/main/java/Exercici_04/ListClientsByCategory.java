package Exercici_04;

import Functions.Functions;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ListClientsByCategory {
    public void execute(String xmlFilePath, String outputFilePath) throws Exception {
        // Cargar el archivo XML original
        File xmlFile = new File(xmlFilePath);
        if (!xmlFile.exists()) {
            throw new Exception("El archivo XML no existe: " + xmlFilePath);
        }

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document originalDoc = docBuilder.parse(xmlFile);

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
                // Importar el nodo al nuevo documento
                Node importedClient = newDoc.importNode(client, true);
                rootElement.appendChild(importedClient);
            }
        }

        // Guardar el nuevo documento XML
        Functions.saveDocument(newDoc, outputFilePath);

        System.out.println("\u001B[32mExercici 4.e fet!✅ Arxiu creat: " + outputFilePath + "\u001B[0m");
    }
}
