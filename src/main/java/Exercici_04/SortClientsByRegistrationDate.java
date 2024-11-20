package Exercici_04;

import Functions.Functions;
import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XQueryService;
import org.xmldb.api.base.ResourceSet;

public class SortClientsByRegistrationDate {
    public void execute(Collection col, String order) throws Exception {
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = String.format("""
            for $client in /clients/client
            order by xs:dateTime($client/dataAlta) %s
            return $client
        """, order.equalsIgnoreCase("asc") ? "ascending" : "descending");

        ResourceSet result = service.query(xquery);

        // Mostrar resultados utilizando Utils.printResult
        Functions.printResult(result);

        System.out.println("\u001B[32mExercici 4.f fet!✅ Clients ordenats per data d'alta\u001B[0m");
    }
}
