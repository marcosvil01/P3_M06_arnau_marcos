package Exercici_01;

import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XQueryService;

/// Modifica l’adreça del client "Emily Johnson" per "San Francisco, CA 94103, USA".

public class ModifyCustomerAddress {
    public void execute(Collection col) throws Exception {
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = """
            update replace /PurchaseOrders/PurchaseOrder/ShipTo[Name='Emily Johnson']/Address
            with <Address>San Francisco, CA 94103, USA</Address>
        """;
        service.query(xquery);
    }
}
