package Exercici_01;

import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XQueryService;

/// Afegeix una nova comanda amb id="4", OrderDate="2024-01-20", amb
/// informació d’enviament per a un client anomenat "Michael Brown" que viu a
/// "Chicago, IL 60601, USA" i un únic article: ProductName="Monitor",
/// Quantity="1", USPrice="200.00".

public class AddNewOrder {
    public void execute(Collection col) throws Exception {
        System.out.println("\u001B[90m🚀 Començant exercici 1.a...\u001B[0m");
        XQueryService service = (XQueryService) col.getService("XQueryService", "1.0");
        String xquery = """
            update insert
            <PurchaseOrder id="4">
                <OrderDate>2024-01-20</OrderDate>
                <ShipTo>
                    <Name>Michael Brown</Name>
                    <Address>Chicago, IL 60601, USA</Address>
                    <City>Chicago</City>
                    <State>IL</State>
                    <Zip>60601</Zip>
                    <Country>USA</Country>
                </ShipTo>
                <Items>
                    <Item partNumber="999-XY">
                        <ProductName>Monitor</ProductName>
                        <Quantity>1</Quantity>
                        <USPrice>200.00</USPrice>
                    </Item>
                </Items>
            </PurchaseOrder>
            into /PurchaseOrders
        """;
        service.query(xquery);
        System.out.println("\u001B[32mExercici fet!✅\u001B[0m");
    }
}