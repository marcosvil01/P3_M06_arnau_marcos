package Exercici_04;

import Functions.Functions;
import org.w3c.dom.*;
import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XQueryService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class UpdateClientsToSenior {
    public void execute(Collection col, String outputFilePath) throws Exception {
        // Normalizar las fechas al formato YYYY-MM-DD
        normalizeDates(col);

        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");

        // Consulta para seleccionar clientes mayores de 50 años
        String selectQuery = """
            let $currentDate := current-date()
            for $client in /clients/client
            where year-from-date($currentDate) - year-from-date(xs:date($client/dataNaixement)) > 50
            return <client>
                        <id>{data($client/@id)}</id>
                        <nom>{data($client/nom)}</nom>
                        <dataNaixement>{data($client/dataNaixement)}</dataNaixement>
                        <adreça>{data($client/adreça)}</adreça>
                        <telèfon>{data($client/telèfon)}</telèfon>
                        <correu>{data($client/correu)}</correu>
                        <dataAlta>{data($client/dataAlta)}</dataAlta>
                        <categoria>Senior</categoria>
                   </client>
        """;

        // Ejecutar la consulta para obtener clientes mayores de 50 años
        var result = service.query(selectQuery);

        // Crear un nuevo documento XML con los clientes actualizados
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
            String clientXML = (String) resource.getContent(); // Obtener como texto
            Node clientNode = docBuilder.parse(new java.io.ByteArrayInputStream(clientXML.getBytes())).getDocumentElement();
            Node importedClient = newDoc.importNode(clientNode, true);
            rootElement.appendChild(importedClient);
            clientsFound = true;
        }

        // Si no se encuentran clientes, registrar en el log y finalizar
        if (!clientsFound) {
            String logMessage = "Cap client compleix els criteris per ser actualitzat a 'Senior'.\n";
            // Functions.writeLog("ex4_h.log", logMessage, false); // Logging
            System.out.println("\u001B[31mNo s'han trobat clients majors de 50 anys.\u001B[0m");
            return;
        }

        // Guardar el nuevo archivo XML localmente
        Functions.saveDocument(newDoc, outputFilePath);

        // Subir el archivo a eXistDB
        uploadToExistDB(col, newDoc, "clientsSenior.xml");

        // Log de la operación
        String logMessage = "Clients actualitzats a 'Senior' i guardats al fitxer: " + outputFilePath + "\n";
        // Functions.writeLog("ex4_h.log", logMessage, false); // Logging

        System.out.println("\u001B[32mExercici 4.h fet!✅ Clients actualitzats a 'Senior' i arxiu pujat a eXistDB: clientsSenior.xml\u001B[0m");
    }

    private void normalizeDates(Collection col) throws Exception {
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = """
            for $client in /clients/client
            let $oldDate := $client/dataNaixement
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
