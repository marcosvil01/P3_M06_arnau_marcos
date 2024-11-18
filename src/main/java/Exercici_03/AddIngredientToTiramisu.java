package Exercici_03;

import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XUpdateQueryService;

public class AddIngredientToTiramisu {
    public void execute(Collection collection) throws XMLDBException {
        String xUpdateQuery =
                "<xu:modifications version=\"1.0\" xmlns:xu=\"http://www.xmldb.org/xupdate\">" +
                        "  <xu:append select=\"/recetas/receta[nombre='Tiramisú']/ingredientes\">" +
                        "    <ingrediente nombre=\"chocolate rallado\" cantidad=\"50 grams\"/>" +
                        "  </xu:append>" +
                        "</xu:modifications>";

        XUpdateQueryService xUpdateService = (XUpdateQueryService) collection.getService("XUpdateQueryService", "1.0");
        xUpdateService.update(xUpdateQuery);
        System.out.println("Ingredient 'chocolate rallado' afegit a 'Tiramisú'.");
    }
}
