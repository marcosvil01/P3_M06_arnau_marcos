package Exercici_04;

import Functions.Functions;
import org.w3c.dom.*;
import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ListClientsBornBeforeDate {
    public void execute(Collection col, String outputFilePath) throws Exception {
        // Normalizar las fechas al formato YYYY-MM-DD
        normalizeDates(col);

        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");

        // Fecha de ejemplo para filtrar (puedes cambiarla dinámicamente)
        String date = "2000-01-01";

        // Consulta XQuery para listar clientes nacidos antes de una fecha específica
        String selectQuery = String.format("""
            for $client in /clients/client
            where fn:matches($client/dataNaixement, '\\d{4}-\\d{2}-\\d{2}')
                  and xs:date($client/dataNaixement) < xs:date('%s')
            return $client
        """, date);

        // Ejecutar la consulta y recoger los resultados
        var result = service.query(selectQuery);

        // Crear un nuevo documento XML con los clientes filtrados
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document newDoc = docBuilder.newDocument();

        // Crear el nodo raíz
        Element rootElement = newDoc.createElement("clients");
        newDoc.appendChild(rootElement);

        // Verificar si hay resultados y añadir los clientes al nuevo documento
        boolean clientsFound = false;
        for (long i = 0; i < result.getSize(); i++) {
            XMLResource resource = (XMLResource) result.getResource(i);
            String clientXML = (String) resource.getContent();
            Node clientNode = docBuilder.parse(new java.io.ByteArrayInputStream(clientXML.getBytes())).getDocumentElement();
            Node importedClient = newDoc.importNode(clientNode, true);
            rootElement.appendChild(importedClient);
            clientsFound = true;
        }

        // Si no se encuentran clientes, registrar en el log y finalizar
        if (!clientsFound) {
            String logMessage = "Cap client nascut abans de la data especificada.\n";
            // Functions.writeLog("ex4_g.log", logMessage, false); // Logging
            System.out.println("\u001B[31mNo s'han trobat clients nascuts abans de la data especificada.\u001B[0m");
            return;
        }

        // Guardar el nuevo archivo XML localmente
        Functions.saveDocument(newDoc, outputFilePath);

        // Subir el archivo a eXistDB
        uploadToExistDB(col, newDoc, "clientsBeforeDate.xml");

        // Logging
        // String logMessage = "Clients nascuts abans de la data especificada i guardats al fitxer: " + outputFilePath + "\n";
        // Functions.writeLog("ex4_g.log", logMessage, false);

        System.out.println("\u001B[32mExercici 4.g fet!✅ Clients filtrats i arxiu pujat a eXistDB: clientsBeforeDate.xml\u001B[0m");
    }

    private void normalizeDates(Collection col) throws Exception {
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = """
            for $client in /clients/client
            let $oldDate := $client/dataNaixement
            where fn:matches($oldDate, '\\d{2}/\\d{2}/\\d{4}')
            let $newDate := fn:replace($oldDate, '(\\d{2})/(\\d{2})/(\\d{4})', '$3-$1-$2')
            return if ($newDate != $oldDate) then replace value of node $client/dataNaixement with $newDate else ()
        """;
        service.query(xquery);
        System.out.println("\u001B[34mFechas normalizadas al formato YYYY-MM-DD.\u001B[0m");
    }

    private void uploadToExistDB(Collection collection, Document doc, String resourceName) throws Exception {
        // Crear un recurso nuevo en eXistDB
        XMLResource resource = (XMLResource) collection.createResource(resourceName, "XMLResource");
        resource.setContentAsDOM(doc);
        collection.storeResource(resource);

        System.out.println("\u001B[34mNou arxiu pujat a eXistDB: " + resourceName + "\u001B[0m");
    }
}
