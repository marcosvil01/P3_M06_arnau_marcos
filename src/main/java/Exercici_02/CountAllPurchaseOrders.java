package Exercici_02;

import Functions.Functions;
import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XQueryService;
import org.xmldb.api.base.ResourceSet;

//Comptar tots els PurchaseOrder:

public class CountAllPurchaseOrders {
    public void execute(Collection col) throws Exception {
        System.out.println("\u001B[90m🚀 Començant exercici 2.h...\u001B[0m");
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = """
            let $count := count(/PurchaseOrders/PurchaseOrder)
            return <TotalPurchaseOrders>{$count}</TotalPurchaseOrders>
        """;
        ResourceSet result = service.query(xquery);
        Functions.printResult(result);

        System.out.println("\u001B[32mExercici 2.h fet!✅\u001B[0m");
    }
}
