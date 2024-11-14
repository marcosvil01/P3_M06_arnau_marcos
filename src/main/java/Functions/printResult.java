package Functions;

import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceSet;

public class printResult {
    public static void printResult(ResourceSet result) throws Exception {
        for (int i = 0; i < result.getSize(); i++) { // Iterem sobre tots els recursos al conjunt de resultats
            Resource res = result.getResource(i);
            System.out.println(res.getContent());
        }
    }
}
