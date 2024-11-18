package Exercici_03;

import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XUpdateQueryService;

public class DeleteRecipe {
    public void execute(Collection collection) throws XMLDBException {
        String xUpdateQuery =
                "<xu:modifications version=\"1.0\" xmlns:xu=\"http://www.xmldb.org/xupdate\">" +
                        "  <xu:remove select=\"/recetas/receta[nombre='Tarta de chocolate']\"/>" +
                        "</xu:modifications>";

        XUpdateQueryService xUpdateService = (XUpdateQueryService) collection.getService("XUpdateQueryService", "1.0");
        xUpdateService.update(xUpdateQuery);
        System.out.println("Recepta 'Tarta de chocolate' esborrada correctament.");
    }
}
