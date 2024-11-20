package Exercici_04;

import Functions.Functions;
import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XQueryService;
import org.xmldb.api.base.ResourceSet;

public class ListClientsBornBeforeDate {
    public void execute(Collection col, String date) throws Exception {
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = String.format("""
            for $client in /clients/client
            where xs:date($client/dataNaixement) < xs:date('%s')
            return $client
        """, date);

        ResourceSet result = service.query(xquery);

        // Mostrar resultados utilizando Utils.printResult
        Functions.printResult(result);

        System.out.println("\u001B[32mExercici 4.g fet!✅ Clients nascuts abans de la data especificada\u001B[0m");
    }
}
