package Exercici_03;

import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XUpdateQueryService;

public class UpdateRecipeStep {
    public void execute(Collection collection) throws XMLDBException {
        String xUpdateQuery =
                "<xu:modifications version=\"1.0\" xmlns:xu=\"http://www.xmldb.org/xupdate\">" +
                        "  <xu:update select=\"/recetas/receta[nombre='Tarta de chocolate']/pasos/paso[@orden='4']\">" +
                        "    Afegir les clares muntades a la barreja sense perdre volum." +
                        "  </xu:update>" +
                        "</xu:modifications>";

        XUpdateQueryService xUpdateService = (XUpdateQueryService) collection.getService("XUpdateQueryService", "1.0");
        xUpdateService.update(xUpdateQuery);
        System.out.println("Pas número 4 de 'Tarta de chocolate' actualitzat correctament.");
    }
}
