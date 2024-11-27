package Functions;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceSet;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Path;

public class Functions {

    /**
     * Escriu un log en un fitxer dins del directori 'output'.
     *
     * @param fileName   Nom del fitxer.
     * @param logContent Contingut del log.
     * @param append     Indica si s'ha d'afegir al fitxer existent o sobreescriure'l.
     */
    public static void writeLog(String fileName, String logContent, boolean append) {
        /// Eliminamos los logs, pero dejamos la funcion sin usar

        FileWriter writer = null;
        try {
            // Asegurar que el directorio exista
            File outputDir = new File("output");
            ensureDirectoryExists(outputDir);

            // Crear el archivo log en el directorio
            File logFile = new File(outputDir, fileName);

            // Usar FileWriter para escribir en el archivo
            writer = new FileWriter(logFile, append);
            writer.write(logContent);
            writer.flush(); // Asegurar que los datos se escriben
            System.out.println("\u001B[32mLog escrit correctament: " + logFile.getAbsolutePath() + "\u001B[0m");
        } catch (IOException e) {
            System.err.println("\u001B[31mError escrivint el log: " + e.getMessage() + "\u001B[0m");
        } finally {
            // Cerrar el FileWriter en el bloque finally
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("\u001B[31mError tancant el FileWriter: " + e.getMessage() + "\u001B[0m");
                }
            }
        }
    }

    /**
     * Imprimeix el contingut d'un ResourceSet a la consola.
     *
     * @param result ResourceSet a imprimir.
     * @throws Exception Si es produeix un error durant l'accés als recursos.
     */
    public static void printResult(ResourceSet result) throws Exception {
        for (int i = 0; i < result.getSize(); i++) {
            Resource res = result.getResource(i);
            System.out.println(res.getContent());
        }
    }

    /**
     * Crea un element XML amb un nom de tag i un valor especificats.
     *
     * @param doc     Document XML on es crearà l'element.
     * @param tagName Nom del tag de l'element.
     * @param value   Valor del contingut de l'element (pot ser null).
     * @return L'element XML creat amb el valor especificat.
     * @throws IllegalArgumentException Si el document o el tagName són null.
     */
    public static Element createElement(Document doc, String tagName, String value) {
        if (doc == null) {
            throw new IllegalArgumentException("El document no pot ser null.");
        }
        if (tagName == null || tagName.trim().isEmpty()) {
            throw new IllegalArgumentException("El tagName no pot ser null o buit.");
        }

        Element element = doc.createElement(tagName);
        if (value != null) {
            element.appendChild(doc.createTextNode(value));
        }
        return element;
    }



    /**
     * Desa un document XML a una ruta especificada.
     *
     * @param doc      Document XML a desar.
     * @param filePath Ruta del fitxer on es desarà el document.
     * @return
     * @throws Exception Si es produeix un error durant el desament.
     */


    public static File saveDocument(Document doc, String filePath) throws Exception {
        // Convertir la ruta a un objeto Path seguro
        Path safePath = Paths.get(filePath).toAbsolutePath().normalize();
        File file = safePath.toFile();

        // Verificar o crear el directorio
        ensureDirectoryExists(file.getParentFile());

        // Mostrar la ruta final para depuración
        System.out.println("\u001B[34mDesant fitxer a: " + safePath.toString() + "\u001B[0m");

        try {
            // Configurar el Transformer para guardar el XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            // Usar StreamResult con un FileOutputStream para evitar problemas de codificación
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                StreamResult result = new StreamResult(outputStream);
                transformer.transform(source, result);
            }

            System.out.println("\u001B[32mFitxer desat correctament: " + safePath.toString() + "\u001B[0m");
            return file;
        } catch (FileNotFoundException e) {
            System.err.println("\u001B[31mError: No se encontró la ruta del archivo. Verifica permisos y caracteres especiales en la ruta.\u001B[0m");
            throw e;
        } catch (Exception e) {
            System.err.println("\u001B[31mError al desar el fitxer: " + e.getMessage() + "\u001B[0m");
            throw e;
        }
    }




    /**
     * Escriu contingut en un fitxer dins del directori 'output'.
     *
     * @param content  Contingut a escriure.
     * @param fileName Nom del fitxer.
     */
    public static void writeToFile(String content, String fileName) {
        FileWriter writer = null;
        try {
            // Asegurar que el directorio 'output' exista
            File outputDir = new File("output");
            ensureDirectoryExists(outputDir);

            // Crear el archivo en el directorio
            File file = new File(outputDir, fileName);

            // Escribir el contenido en el archivo
            writer = new FileWriter(file);
            writer.write(content);
            writer.flush(); // Asegurar que los datos se escriban en disco
            System.out.println("\u001B[32mEl document XML s'ha creat correctament: " + file.getAbsolutePath() + "\u001B[0m");
        } catch (IOException e) {
            System.err.println("\u001B[31mError escrivint el document XML: " + e.getMessage() + "\u001B[0m");
        } finally {
            // Cerrar el FileWriter en el bloque finally
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println("\u001B[31mError tancant el FileWriter: " + e.getMessage() + "\u001B[0m");
                }
            }
        }
    }

    /**
     * Verifica que un directori existeixi, i el crea si no és així.
     *
     * @throws IOException Si no es pot crear el directori.
     */
    private static void ensureDirectoryExists(File dir) throws IOException {
        if (dir != null && !dir.exists()) {
            System.out.println("\u001B[33mCreant directori: " + dir.getCanonicalPath() + "\u001B[0m");
            if (!dir.mkdirs()) {
                throw new IOException("No s'ha pogut crear el directori: " + dir.getCanonicalPath());
            }
        }
    }
}
