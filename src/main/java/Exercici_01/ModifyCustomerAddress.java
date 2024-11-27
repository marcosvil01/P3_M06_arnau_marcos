package Exercici_01;

import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XQueryService;

/// Modifica l’adreça del client "Emily Johnson" per "San Francisco, CA 94103, USA".

public class ModifyCustomerAddress {
    public void execute(Collection col) throws Exception {
        System.out.println("\u001B[90m🚀 Començant exercici 1.d...\u001B[0m");
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = """
            update replace /PurchaseOrders/PurchaseOrder/ShipTo[Name='Emily Johnson']/Address
            with <Address>San Francisco, CA 94103, USA</Address>
        """;
        service.query(xquery);
        System.out.println("\u001B[32mExercici fet!✅\u001B[0m");
    }
}
