package Exercici_01;

import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XQueryService;
import org.xmldb.api.base.ResourceSet;

import static Functions.printResult.printResult;

/// Crea una consulta per llistar els articles amb un USPrice superior a 500.00.

public class ListExpensiveItems {
    public void execute(Collection col) throws Exception {
        System.out.println("\u001B[90m🚀 Començant exercici ...\u001B[0m");
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = """
            for $item in /PurchaseOrders/PurchaseOrder/Items/Item[USPrice > 500.00]
            return $item
        """;
        ResourceSet result = service.query(xquery);
        System.out.println(" ################################################### ");
        System.out.println(" ### Articles amb un preu superior de 500.00 USD ### ");
        System.out.println(" ################################################### ");
        printResult(result);
        System.out.println("\u001B[32mExercici fet!✅\u001B[0m");
    }
}
