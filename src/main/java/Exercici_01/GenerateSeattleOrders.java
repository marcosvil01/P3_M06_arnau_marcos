package Exercici_01;

import Functions.Functions;
import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XQueryService;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.Resource;


/// A partir de l'XML original, genera un nou document XML que només contingui les
/// comandes de clients de Seattle.

public class GenerateSeattleOrders {
    public void execute(Collection col) throws Exception {
        System.out.println("\u001B[90m🚀 Començant exercici 1.h...\u001B[0m");
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = """
            let $seattleOrders := /PurchaseOrders/PurchaseOrder[ShipTo/City='Seattle']
            return <SeattleOrders>{$seattleOrders}</SeattleOrders>
        """;
        ResourceSet result = service.query(xquery);

        // Crea el nou document XML
        StringBuilder xmlOutput = new StringBuilder();
        long size = result.getSize(); // Obtenim la mida del conjunt de resultats
        for (int i = 0; i < size; i++) {
            Resource res = result.getResource(i);
            xmlOutput.append(res.getContent().toString());
        }

        // Escriu el contingut a un nou fitxer XML
        Functions.writeToFile(xmlOutput.toString(), "SeattleOrders.xml");
        System.out.println("\u001B[32mExercici fet!✅\u001B[0m");
    }
}
