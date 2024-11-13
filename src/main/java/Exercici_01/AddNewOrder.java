package Exercici_01;

import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XQueryService;

public class AddNewOrder {
    public void execute(Collection col) throws Exception {
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
    }
}