package Exercici_01;

import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XQueryService;

public class AddPriorityAttribute {
    public void execute(Collection col) throws Exception {
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = """
            update insert attribute Priority { 'High' }
            into /PurchaseOrders/PurchaseOrder[@id='3']
        """;
        service.query(xquery);
    }
}
