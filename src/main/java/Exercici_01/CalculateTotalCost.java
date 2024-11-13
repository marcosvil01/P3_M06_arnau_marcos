package Exercici_01;

import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XQueryService;
import org.xmldb.api.base.ResourceSet;

import static Exercici_01.Resources.printResult.printResult;

public class CalculateTotalCost {
    public void execute(Collection col) throws Exception {
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = """
            for $order in /PurchaseOrders/PurchaseOrder
            let $total := sum($order/Items/Item/USPrice * $order/Items/Item/Quantity)
            return <Order id="{$order/@id}" TotalCost="{$total}"/>
        """;
        ResourceSet result = service.query(xquery);
        printResult(result);
    }
}
