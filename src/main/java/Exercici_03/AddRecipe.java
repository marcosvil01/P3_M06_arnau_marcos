package Exercici_03;

import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XUpdateQueryService;

public class AddRecipe {
    public void execute(Collection collection) throws XMLDBException {
        String xUpdateQuery =
                "<xu:modifications version=\"1.0\" xmlns:xu=\"http://www.xmldb.org/xupdate\">" +
                        "  <xu:append select=\"/recetas\">" +
                        "    <receta>" +
                        "      <tipo definicion=\"bebida\"/>" +
                        "      <dificultad>Fácil</dificultad>" +
                        "      <nombre>Batido de Fresas</nombre>" +
                        "      <ingredientes>" +
                        "        <ingrediente nombre=\"fresas\" cantidad=\"300 gramos\"/>" +
                        "        <ingrediente nombre=\"leche\" cantidad=\"200 ml\"/>" +
                        "        <ingrediente nombre=\"azúcar\" cantidad=\"1 culleradeta\"/>" +
                        "      </ingredientes>" +
                        "      <calorias>200</calorias>" +
                        "      <pasos>" +
                        "        <paso orden=\"1\">Rentant les freses</paso>" +
                        "        <paso orden=\"2\">Afegir tots els ingredients a la licuadora</paso>" +
                        "        <paso orden=\"3\">Licuar fins que estigui suau</paso>" +
                        "      </pasos>" +
                        "      <tiempo>10 minutos</tiempo>" +
                        "      <elaboracion>Licuadora</elaboracion>" +
                        "    </receta>" +
                        "  </xu:append>" +
                        "</xu:modifications>";

        XUpdateQueryService xUpdateService = (XUpdateQueryService) collection.getService("XUpdateQueryService", "1.0");
        xUpdateService.update(xUpdateQuery);
        System.out.println("Recepta 'Batido de Fresas' afegida correctament.");
    }
}