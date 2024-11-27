package Exercici_04;

import Functions.Functions;
import org.w3c.dom.*;
import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XMLResource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.Scanner;

public class UpdateClientData {
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

        // Pedir al usuario el ID del cliente a actualizar y validar que exista
        Scanner scanner = new Scanner(System.in);
        Element clientToUpdate = null;
        String clientId = null;

        while (clientToUpdate == null) {
            System.out.println("Introdueix el ID del client a actualitzar (exemple: C0001):");
            clientId = scanner.nextLine();

            // Buscar el cliente por ID
            NodeList clients = doc.getElementsByTagName("client");
            for (int i = 0; i < clients.getLength(); i++) {
                Element client = (Element) clients.item(i);
                if (client.getAttribute("id").equals(clientId)) {
                    clientToUpdate = client;
                    break;
                }
            }

            if (clientToUpdate == null) {
                System.out.println("\u001B[31mError: No s'ha trobat cap client amb l'ID especificat. Torna-ho a intentar.\u001B[0m");
            }
        }

        // Mostrar opciones de campos editables
        String[] fields = {"nom", "dataNaixement", "adreça", "telèfon", "correu", "dataAlta", "categoria"};
        System.out.println("Quin camp vols actualitzar? Tria una opció:");
        for (int i = 0; i < fields.length; i++) {
            System.out.println((i + 1) + ". " + fields[i]);
        }

        int fieldIndex = -1;
        while (fieldIndex < 1 || fieldIndex > fields.length) {
            System.out.println("Introdueix el número del camp a actualitzar:");
            fieldIndex = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
        }

        String fieldToUpdate = fields[fieldIndex - 1];

        // Obtener el valor antiguo
        String oldValue = null;
        NodeList fieldNodes = clientToUpdate.getElementsByTagName(fieldToUpdate);
        if (fieldNodes.getLength() > 0) {
            oldValue = fieldNodes.item(0).getTextContent();
        } else {
            System.out.println("\u001B[31mError: El camp especificat no existeix.\u001B[0m");
            return;
        }

        // Pedir el nuevo valor
        System.out.println("Introdueix el nou valor per al camp '" + fieldToUpdate + "':");
        String newValue = scanner.nextLine();

        // Actualizar el valor en el documento XML
        fieldNodes.item(0).setTextContent(newValue);

        // Guardar el archivo XML actualizado localmente
        Functions.saveDocument(doc, xmlFilePath);

        // Actualizar el archivo XML en eXistDB
        updateExistDB(collection, doc, xmlFilePath);

        // Registrar la operación en el log
        String logMessage = String.format("""
                Client actualitzat:
                ID: %s
                Camp: %s
                Valor Antic: %s
                Nou Valor: %s
                """, clientId, fieldToUpdate, oldValue, newValue);

        // Functions.writeLog("ex4_d.log", logMessage, true); // Logging

        System.out.println("\u001B[32mExercici 4.d fet!✅ Camp actualitzat: " + fieldToUpdate + ", Valor Antic: " + oldValue + ", Nou Valor: " + newValue + "\u001B[0m");
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
