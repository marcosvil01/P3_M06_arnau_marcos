package Exercici_01;

import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XQueryService;

/// Afegeix un atribut Priority="High" a la comanda amb id="3".

public class AddPriorityAttribute {
    public void execute(Collection col) throws Exception {
        System.out.println("\u001B[90m🚀 Començant exercici 1.e...\u001B[0m");
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = """
            update insert attribute Priority { 'High' }
            into /PurchaseOrders/PurchaseOrder[@id='3']
        """;
        service.query(xquery);
        System.out.println("\u001B[32mExercici fet!✅\u001B[0m");
    }
}
