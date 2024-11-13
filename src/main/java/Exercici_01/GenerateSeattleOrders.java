package Exercici_01;

import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XQueryService;
import org.xmldb.api.base.ResourceSet;

import static Exercici_01.Resources.printResult.printResult;

public class GenerateSeattleOrders {
    public void execute(Collection col) throws Exception {
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = """
            let $seattleOrders := /PurchaseOrders/PurchaseOrder[ShipTo/City='Seattle']
            return <SeattleOrders>{$seattleOrders}</SeattleOrders>
        """;
        ResourceSet result = service.query(xquery);
        printResult(result);
    }
}
