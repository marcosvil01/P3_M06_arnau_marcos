package Exercici_04;

import Functions.*;
import org.w3c.dom.*;
import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XMLResource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AddNewClient {
    public void execute(String xmlFilePath, Collection collection) throws Exception {
        File xmlFile = new File(xmlFilePath);
        File outputDir = xmlFile.getParentFile();

        // Verificar si el directorio de salida existe, si no, crearlo
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            throw new IOException("No se pudo crear el directorio: " + outputDir.getAbsolutePath());
        }

        // Verificar si el archivo XML existe, si no, lanzar excepción
        if (!xmlFile.exists()) {
            throw new Exception("El archivo XML no existe en la ruta: " + xmlFilePath + ". Por favor, genera el archivo en el Exercici 4.a.");
        }

        // Cargar el archivo XML existente
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(xmlFile);

        // Obtener el nodo raíz
        Element root = doc.getDocumentElement();

        Scanner scanner = new Scanner(System.in);

        // Preguntar si se desean ingresar datos manualmente o generar aleatorios
        System.out.println("Vols introduir les dades manualment? (S):");
        String respuesta = scanner.nextLine().trim().toUpperCase();

        String clientId, nom, dataNaixement, adreça, telèfon, correu, dataAlta, categoria, localitat;
        if (respuesta.equals("S")) {
            // Ingresar los datos manualmente
            while (true) {
                clientId = askForInput(scanner, "Introdueix el ID del client (exemple: C0001):", "C\\d{4}");
                if (idExists(doc, clientId)) {
                    System.out.println("\u001B[31mError: El ID ja existeix. Prova amb un altre ID.\u001B[0m");
                } else {
                    break;
                }
            }
            nom = askForInput(scanner, "Introdueix el nom complet del client:", ".+");
            dataNaixement = askForInput(scanner, "Introdueix la data de naixement (format: MM/dd/yyyy):", "\\d{2}/\\d{2}/\\d{4}");
            adreça = askForInput(scanner, "Introdueix la adreça completa del client:", ".+");
            telèfon = askForInput(scanner, "Introdueix el telèfon (exemple: (123) 456-7890):", "\\(\\d{3}\\) \\d{3}-\\d{4}");
            correu = askForInput(scanner, "Introdueix el correu electrònic (exemple: example@domain.com):", "[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}");
            dataAlta = askForInput(scanner, "Introdueix la data d'alta (format: MM/dd/yyyy):", "\\d{2}/\\d{2}/\\d{4}");
            categoria = askForInput(scanner, "Introdueix la categoria del client (A, B, C, D, E):", "[A-E]");
            localitat = askForInput(scanner, "Introdueix la localitat i codi postal (exemple: Bergenfield, NJ 07621):", ".+");
        } else {
            // Generar datos aleatorios
            Random random = new Random();
            clientId = generateUniqueId(doc);
            nom = "Jorge Flores";
            dataNaixement = "01/01/" + (1950 + random.nextInt(60));
            adreça = "Carrer de l'Alzina 291";
            telèfon = String.format("(%03d) %03d-%04d", random.nextInt(1000), random.nextInt(1000), random.nextInt(10000));
            correu = "jflores@gmail";
            dataAlta = "12/12/2024";
            categoria = "A";
            localitat = "Bosnia, CP " + random.nextInt(99999);
        }

        String adreçaCompleta = adreça + ", " + localitat;

        // Crear el nuevo nodo <client>
        Element client = doc.createElement("client");
        client.setAttribute("id", clientId);

        client.appendChild(Functions.createElement(doc, "nom", nom));
        client.appendChild(Functions.createElement(doc, "dataNaixement", dataNaixement));
        client.appendChild(Functions.createElement(doc, "adreça", adreçaCompleta));
        client.appendChild(Functions.createElement(doc, "telèfon", telèfon));
        client.appendChild(Functions.createElement(doc, "correu", correu));
        client.appendChild(Functions.createElement(doc, "dataAlta", dataAlta));
        client.appendChild(Functions.createElement(doc, "categoria", categoria));

        // Añadir el nuevo nodo al documento
        root.appendChild(client);

        // Guardar el documento actualizado localmente
        Functions.saveDocument(doc, xmlFilePath);

        // Guardar el documento actualizado en eXistDB
        updateExistDB(collection, doc, xmlFilePath);

        // Log de la operación
        String logMessage = String.format("""
                Client afegit:
                ID: %s
                Nom: %s
                Data de Naixement: %s
                Adreça: %s
                Telèfon: %s
                Correu: %s
                Data d'Alta: %s
                Categoria: %s
                """, clientId, nom, dataNaixement, adreçaCompleta, telèfon, correu, dataAlta, categoria);
        // Functions.writeLog("ex4_b.log", logMessage, true); // Logging

        System.out.println("\u001B[32mExercici 4.b fet!✅ Client afegit al XML: ID=" + clientId + ", Nom=" + nom + "\u001B[0m");
    }

    private void updateExistDB(Collection collection, Document doc, String resourceName) throws Exception {
        // Obtener el recurso de eXistDB
        XMLResource resource = (XMLResource) collection.createResource(resourceName, "XMLResource");

        // Actualizar el contenido del recurso
        resource.setContentAsDOM(doc);
        collection.storeResource(resource);

        System.out.println("\u001B[34mArchivo actualizado en eXistDB: " + resourceName + "\u001B[0m");
    }

    private boolean idExists(Document doc, String id) {
        NodeList clients = doc.getElementsByTagName("client");
        for (int i = 0; i < clients.getLength(); i++) {
            Element client = (Element) clients.item(i);
            if (client.getAttribute("id").equals(id)) {
                return true;
            }
        }
        return false;
    }

    private String generateUniqueId(Document doc) {
        int counter = 1;
        String newId;
        do {
            newId = String.format("C%04d", counter++);
        } while (idExists(doc, newId));
        return newId;
    }

    private String askForInput(Scanner scanner, String prompt, String regex) {
        String input;
        Pattern pattern = Pattern.compile(regex);

        while (true) {
            System.out.println(prompt);
            input = scanner.nextLine();
            if (pattern.matcher(input).matches()) {
                break;
            } else {
                System.out.println("\u001B[31mError: El format no és correcte. Torna-ho a intentar.\u001B[0m");
            }
        }
        return input;
    }
}
