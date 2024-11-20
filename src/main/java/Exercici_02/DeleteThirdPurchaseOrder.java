package Exercici_02;

import Functions.Functions;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XQueryService;


//Eliminar el tercer PurchaseOrder:

public class DeleteThirdPurchaseOrder {
    public void execute(Collection col) throws Exception {
        System.out.println("\u001B[90m🚀 Començant exercici 2.g...\u001B[0m");
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = """
            update delete /PurchaseOrders/PurchaseOrder[3]
        """;
        ResourceSet result = service.query(xquery);

        Functions.printResult(result);
        System.out.println("\u001B[32mExercici 2.g fet!✅\u001B[0m");
    }
}
