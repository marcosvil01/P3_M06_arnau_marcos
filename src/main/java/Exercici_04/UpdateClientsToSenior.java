package Exercici_04;

import Functions.Functions;
import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XQueryService;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.Resource;

public class UpdateClientsToSenior {
    public void execute(Collection col) throws Exception {
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
                        <localitat>{data($client/localitat)}</localitat>
                        <telèfon>{data($client/telèfon)}</telèfon>
                        <correu>{data($client/correu)}</correu>
                        <dataAlta>{data($client/dataAlta)}</dataAlta>
                        <categoria>{data($client/categoria)}</categoria>
                   </client>
        """;

        // Ejecutar la consulta y recoger los resultados
        ResourceSet result = service.query(selectQuery);

        // Crear contenido del log
        StringBuilder logContent = new StringBuilder();
        logContent.append("Clients actualitzats a 'Senior':\n\n");

        // Iterar sobre los clientes seleccionados y registrar la información
        boolean clientsFound = false;
        for (int i = 0; i < result.getSize(); i++) {
            Resource res = result.getResource(i);
            logContent.append(res.getContent().toString()).append("\n\n");
            clientsFound = true;
        }

        // Si no se encuentran clientes, añadir un mensaje al log
        if (!clientsFound) {
            logContent.append("Cap client compleix els criteris per ser actualitzat a 'Senior'.\n");
        }

        // Consulta para actualizar la categoría a "Senior"
        String updateQuery = """
            let $currentDate := current-date()
            for $client in /clients/client
            where year-from-date($currentDate) - year-from-date(xs:date($client/dataNaixement)) > 50
            return update replace $client/categoria with <categoria>Senior</categoria>
        """;

        // Ejecutar la consulta de actualización
        service.query(updateQuery);

        // Escribir el log
        Functions.writeLog("ex4_h.log", logContent.toString(), false); // Sobrescribir el archivo

        System.out.println("\u001B[32mExercici 4.h fet!✅ Categoria actualitzada a 'Senior' per clients majors de 50 anys\u001B[0m");
    }
}
