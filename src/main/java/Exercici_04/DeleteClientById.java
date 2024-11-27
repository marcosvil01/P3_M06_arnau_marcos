package Exercici_04;

import Functions.Functions;
import org.w3c.dom.*;
import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XMLResource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Scanner;

public class DeleteClientById {
    public void execute(String xmlFilePath, Collection collection) throws Exception {
        // Cargar el archivo XML
        File xmlFile = new File(xmlFilePath);
        if (!xmlFile.exists()) {
            throw new Exception("El archivo XML no existe: " + xmlFilePath);
        }

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(xmlFile);

        // Obtener el nodo raíz
        Element root = doc.getDocumentElement();

        Scanner scanner = new Scanner(System.in);
        String clientId;
        boolean clientFound = false;
        StringBuilder logMessage = new StringBuilder();

        // Pedir al usuario el ID del cliente a eliminar y verificar si existe
        while (!clientFound) {
            System.out.println("Introdueix el ID del client a eliminar (exemple: C0001):");
            clientId = scanner.nextLine();

            NodeList clients = doc.getElementsByTagName("client");
            for (int i = 0; i < clients.getLength(); i++) {
                Element client = (Element) clients.item(i);
                if (client.getAttribute("id").equals(clientId)) {
                    clientFound = true;

                    // Registrar datos del cliente eliminado en el log
                    logMessage.append("Client eliminat:\n");
                    logMessage.append("ID: ").append(client.getAttribute("id")).append("\n");
                    logMessage.append("Nom: ").append(client.getElementsByTagName("nom").item(0).getTextContent()).append("\n");
                    logMessage.append("Data de Naixement: ").append(client.getElementsByTagName("dataNaixement").item(0).getTextContent()).append("\n");
                    logMessage.append("Adreça: ").append(client.getElementsByTagName("adreça").item(0).getTextContent()).append("\n");
                    logMessage.append("Telèfon: ").append(client.getElementsByTagName("telèfon").item(0).getTextContent()).append("\n");
                    logMessage.append("Correu: ").append(client.getElementsByTagName("correu").item(0).getTextContent()).append("\n");
                    logMessage.append("Data d'Alta: ").append(client.getElementsByTagName("dataAlta").item(0).getTextContent()).append("\n");
                    logMessage.append("Categoria: ").append(client.getElementsByTagName("categoria").item(0).getTextContent()).append("\n");

                    // Eliminar el nodo del cliente
                    root.removeChild(client);
                    break;
                }
            }

            if (!clientFound) {
                System.out.println("\u001B[31mError: No s'ha trobat cap client amb l'ID especificat. Torna-ho a intentar.\u001B[0m");
            }
        }

        // Guardar el archivo XML actualizado localmente
        Functions.saveDocument(doc, xmlFilePath);

        // Actualizar el archivo XML en eXistDB
        updateExistDB(collection, doc, xmlFilePath);

        // Registrar la operación en el log
        // Functions.writeLog("ex4_c.log", logMessage.toString(), true); // Logging

        System.out.println("\u001B[32mExercici 4.c fet!✅ Client eliminat.\u001B[0m");
    }

    private void updateExistDB(Collection collection, Document doc, String resourceName) throws Exception {
        // Obtener el recurso de eXistDB
        XMLResource resource = (XMLResource) collection.createResource(resourceName, "XMLResource");

        // Actualizar el contenido del recurso
        resource.setContentAsDOM(doc);
        collection.storeResource(resource);

        System.out.println("\u001B[34mArchivo actualizado en eXistDB: " + resourceName + "\u001B[0m");
    }
}
