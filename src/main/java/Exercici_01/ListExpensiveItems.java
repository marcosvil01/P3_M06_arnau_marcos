package Exercici_01;

import Functions.Functions;
import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XQueryService;
import org.xmldb.api.base.ResourceSet;


/// Crea una consulta per llistar els articles amb un USPrice superior a 500.00.

public class ListExpensiveItems {
    public void execute(Collection col) throws Exception {
        System.out.println("\u001B[90m🚀 Començant exercici 1.g...\u001B[0m");
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = """
            for $item in /PurchaseOrders/Ventas/Items/Item[USPrice > 500.00]
            return $item
        """;
        ResourceSet result = service.query(xquery);
        System.out.println(" ################################################### ");
        System.out.println(" ### Articles amb un preu superior de 500.00 USD ### ");
        System.out.println(" ################################################### ");
        Functions.printResult(result);
        System.out.println("\u001B[32mExercici fet!✅\u001B[0m");
    }
}
