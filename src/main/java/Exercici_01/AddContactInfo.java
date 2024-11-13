package Exercici_01;

import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XQueryService;

public class AddContactInfo {
    public void execute(Collection col) throws Exception {
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = """
            for $shipTo in /PurchaseOrders/PurchaseOrder/ShipTo
            let $contact := <Contact><Email>example@email.com</Email><Phone>123-456-7890</Phone></Contact>
            return update insert $contact into $shipTo
        """;
        service.query(xquery);
    }
}