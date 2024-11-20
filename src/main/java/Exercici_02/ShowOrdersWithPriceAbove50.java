package Exercici_02;

import Functions.Functions;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XQueryService;

//Mostrar tots els purchaseOrder amb USPrice superior a 50

public class ShowOrdersWithPriceAbove50 {
    public void execute(Collection col) throws Exception {
        System.out.println("\u001B[90m🚀 Començant exercici 2.c...\u001B[0m");
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = """
            for $order in /PurchaseOrders/PurchaseOrder
            where some $item in $order/Items/Item satisfies $item/USPrice > 50
            return $order
        """;
        ResourceSet result = service.query(xquery);
        Functions.printResult(result);
        System.out.println("\u001B[32mExercici 2.c fet!✅\u001B[0m");
    }
}
