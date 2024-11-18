package Exercici_03;

import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XPathQueryService;

public class SumCalories {
    public void execute(Collection collection) throws XMLDBException {
        String xpathQuery = "sum(/recetas/receta/calorias)";
        XPathQueryService xpathService = (XPathQueryService) collection.getService("XPathQueryService", "1.0");
        String result = xpathService.query(xpathQuery).getIterator().nextResource().getContent().toString();
        System.out.println("La suma de calories de totes les receptes és: " + result);
    }
}
