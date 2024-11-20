package Exercici_02;

import Functions.Functions;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XQueryService;

//Modificar la quantity del primer Venta, el primer ítem a 33:

public class ModifyFirstOrderQuantity {
    public void execute(Collection col) throws Exception {
        System.out.println("\u001B[90m🚀 Començant exercici 2.f...\u001B[0m");
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = """
            update replace /PurchaseOrders/PurchaseOrder[1]/Items/Item[1]/Quantity
            with <Quantity>33</Quantity>
        """;
        ResourceSet result = service.query(xquery);

        Functions.printResult(result);
        System.out.println("\u001B[32mExercici 2.f fet!✅\u001B[0m");
    }
}
