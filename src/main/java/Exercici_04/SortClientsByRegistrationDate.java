package Exercici_04;

import Functions.Functions;
import org.w3c.dom.*;
import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;
import org.xmldb.api.base.ResourceSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Scanner;

public class SortClientsByRegistrationDate {
    public void execute(Collection col, String outputFilePath) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String order;

        // Validar la opción de clasificación (asc o desc)
        while (true) {
            System.out.println("Introdueix l'ordre de classificació (asc o desc):");
            order = scanner.nextLine().trim().toLowerCase();
            if (order.equals("asc") || order.equals("desc")) {
                break;
            } else {
                System.out.println("\u001B[31mError: Opció no vàlida. Escriu 'asc' per ascendent o 'desc' per descendent.\u001B[0m");
            }
        }

        // Normalizar fechas al formato YYYY-MM-DD
        normalizeDates(col);

        // Ejecutar la consulta XQuery para ordenar los clientes
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = String.format("""
            let $clients := for $client in /clients/client
                            order by xs:date($client/dataAlta) %s
                            return $client
            return <clients>{ $clients }</clients>
        """, order.equals("asc") ? "ascending" : "descending");

        ResourceSet result;
        try {
            result = service.query(xquery);
        } catch (Exception e) {
            System.err.println("\u001B[31mError al executar la consulta XQuery: " + e.getMessage() + "\u001B[0m");
            throw e;
        }

        // Crear un nuevo documento XML con los resultados ordenados
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document newDoc = docBuilder.newDocument();

        // Raíz del nuevo documento
        Element rootElement = newDoc.createElement("clients");
        newDoc.appendChild(rootElement);

        // Iterar sobre los resultados y agregar los nodos al nuevo documento
        for (int i = 0; i < result.getSize(); i++) {
            XMLResource resource = (XMLResource) result.getResource(i);
            Node clientNode = resource.getContentAsDOM();
            Node importedClient = newDoc.importNode(clientNode, true);
            rootElement.appendChild(importedClient);
        }

        // Guardar el archivo XML localmente
        Functions.saveDocument(newDoc, outputFilePath);

        // Subir el archivo a eXistDB
        uploadToExistDB(col, newDoc, "sortedClients.xml");

        System.out.println("\u001B[32mExercici 4.f fet!✅ Clients ordenats per data d'alta i arxiu pujat a eXistDB: sortedClients.xml\u001B[0m");
    }

    private void normalizeDates(Collection col) throws Exception {
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = """
            for $client in /clients/client
            let $oldDate := $client/dataAlta
            let $newDate := fn:replace($oldDate, '(\\d{2})/(\\d{2})/(\\d{4})', '$3-$1-$2')
            where $newDate != $oldDate
            return update value $client/dataAlta with $newDate
        """;

        try {
            service.query(xquery);
            System.out.println("\u001B[34mFechas normalizadas al formato YYYY-MM-DD.\u001B[0m");
        } catch (Exception e) {
            System.err.println("\u001B[31mError al normalizar las fechas: " + e.getMessage() + "\u001B[0m");
            throw e;
        }
    }

    private void uploadToExistDB(Collection collection, Document doc, String resourceName) throws Exception {
        // Crear un recurso nuevo en eXistDB
        XMLResource resource = (XMLResource) collection.createResource(resourceName, "XMLResource");
        resource.setContentAsDOM(doc);
        try {
            collection.storeResource(resource);
            System.out.println("\u001B[34mNou arxiu pujat a eXistDB: " + resourceName + "\u001B[0m");
        } catch (Exception e) {
            System.err.println("\u001B[31mError al pujar el fitxer a eXistDB: " + e.getMessage() + "\u001B[0m");
            throw e;
        }
    }
}
