package Exercici_01;

import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XQueryService;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.Resource;

import static Exercici_01.Resources.writeToFile.writeToFile;

/// A partir de l'XML original, genera un nou document XML que només contingui les
/// comandes de clients de Seattle.

public class GenerateSeattleOrders {
    public void execute(Collection col) throws Exception {
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
        writeToFile(xmlOutput.toString(), "SeattleOrders.xml");
    }
}
