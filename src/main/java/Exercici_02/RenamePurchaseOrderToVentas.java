package Exercici_02;

import Functions.Functions;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XQueryService;

//Renombrar el node PurchaseOrder a Ventas:

public class RenamePurchaseOrderToVentas {
    public void execute(Collection col) throws Exception {
        System.out.println("\u001B[90m🚀 Començant exercici 2.e...\u001B[0m");
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = """
            update rename /PurchaseOrders/PurchaseOrder as "Ventas"
        """;
        ResourceSet result = service.query(xquery);

        Functions.printResult(result);
        System.out.println("\u001B[32mExercici 2.e fet!✅\u001B[0m");
    }
}
