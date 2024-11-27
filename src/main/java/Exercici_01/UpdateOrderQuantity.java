package Exercici_01;

import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XQueryService;

/// A la comanda amb id="1", canvia la Quantity de l’article amb
/// ProductName="Mouse" a 5.

public class UpdateOrderQuantity {
    public void execute(Collection col) throws Exception {
        System.out.println("\u001B[90m🚀 Començant exercici 1.c...\u001B[0m");
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = """
            update replace /PurchaseOrders/PurchaseOrder[@id='1']/Items/Item[ProductName='Mouse']/Quantity
            with <Quantity>5</Quantity>
        """;
        service.query(xquery);
        System.out.println("\u001B[32mExercici fet!✅\u001B[0m");
    }
}
