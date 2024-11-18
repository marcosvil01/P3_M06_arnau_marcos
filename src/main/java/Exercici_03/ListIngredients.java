package Exercici_03;

import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XPathQueryService;

public class ListIngredients {
    public void execute(Collection collection) throws XMLDBException {
        String xpathQuery = "/recetas/receta[nombre='Tiramisú']/ingredientes/ingrediente";
        XPathQueryService xpathService = (XPathQueryService) collection.getService("XPathQueryService", "1.0");
        String result = xpathService.query(xpathQuery).getIterator().nextResource().getContent().toString();
        System.out.println("Ingredients de 'Tiramisú':");
        System.out.println(result);
    }
}
