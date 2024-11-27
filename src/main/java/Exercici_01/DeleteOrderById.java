package Exercici_01;

import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XQueryService;

/// Esborra la comanda amb id="2".

public class DeleteOrderById {
    public void execute(Collection col) throws Exception {
        System.out.println("\u001B[90m🚀 Començant exercici 1.b\u001B[0m");
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = """
            update delete /PurchaseOrders/PurchaseOrder[@id='2']
        """;
        service.query(xquery);
        System.out.println("\u001B[32mExercici fet!✅\u001B[0m");
    }
}
