package Exercici_03;

import org.xmldb.api.base.Collection;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XPathQueryService;

public class GenerateRecipeIndex {
    public void execute(Collection collection) throws XMLDBException {
        String xpathQuery =
                "<IndiceRecetas>{ " +
                        "for $receta in /recetas/receta " +
                        "let $tipo := $receta/elaboracion " +
                        "return <Elaboracion tipo=\"{data($tipo)}\">" +
                        "  { for $r in /recetas/receta[elaboracion = $tipo] " +
                        "    return <Receta nombre=\"{data($r/nombre)}\"/> }" +
                        "</Elaboracion> } </IndiceRecetas>";

        XPathQueryService xpathService = (XPathQueryService) collection.getService("XPathQueryService", "1.0");
        String result = xpathService.query(xpathQuery).getIterator().nextResource().getContent().toString();
        System.out.println("Índex generat:");
        System.out.println(result);
    }
}
