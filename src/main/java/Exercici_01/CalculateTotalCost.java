package Exercici_01;

import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XQueryService;

/// Crea una consulta per calcular el cost total de cada comanda (sumant USPrice *
/// Quantity per a cada article) i mostrar el resultat.

public class CalculateTotalCost {
    public void execute(Collection col) throws Exception {
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = """
            for $order in doc("PurchaseOrders.xml")/PurchaseOrders/PurchaseOrder
            let $total := sum(
                for $item in $order/Items/Item
                return xs:decimal($item/USPrice) * xs:decimal($item/Quantity)
            )
            return <Order id="{$order/@id}" TotalCost="{$total}"/>
        """;
        service.query(xquery);
    }
}
