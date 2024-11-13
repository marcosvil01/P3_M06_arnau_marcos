package Exercici_01;

import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XQueryService;

public class DeleteOrderById {
    public void execute(Collection col) throws Exception {
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = """
            update delete /PurchaseOrders/PurchaseOrder[@id='2']
        """;
        service.query(xquery);
    }
}
