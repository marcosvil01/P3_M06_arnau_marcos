package Exercici_04;

import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.XMLResource;
import org.apache.poi.ss.usermodel.*;
import org.w3c.dom.*;
import Functions.Functions;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

public class ConvertExcelToXML {
    public void execute(String excelFilePath, String outputXmlFileName, Collection collection) throws Exception {
        // Verificar si el archivo Excel existe
        File excelFile = new File(excelFilePath);
        if (!excelFile.exists()) {
            throw new FileNotFoundException("El archivo no se encuentra en la ruta especificada: " + excelFilePath);
        }

        // Cargar el archivo Excel
        Workbook workbook = WorkbookFactory.create(excelFile);
        Sheet sheet = workbook.getSheetAt(0); // Asume que los datos están en la primera hoja

        // Crear el documento XML
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        // Nodo raíz <clients>
        Element rootElement = doc.createElement("clients");
        doc.appendChild(rootElement);

        // Iterar sobre las filas del Excel
        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue; // Saltar la fila de encabezados

            // Crear el nodo <client>
            Element client = doc.createElement("client");
            client.setAttribute("id", getCellValue(row.getCell(0))); // ID de cliente
            rootElement.appendChild(client);

            // Añadir los campos como elementos hijos
            client.appendChild(Functions.createElement(doc, "nom", getCellValue(row.getCell(1))));
            client.appendChild(Functions.createElement(doc, "dataNaixement", getCellValue(row.getCell(2))));
            client.appendChild(Functions.createElement(doc, "adreça",
                    getCellValue(row.getCell(3)) + ", " + getCellValue(row.getCell(4)))); // Dirección completa
            client.appendChild(Functions.createElement(doc, "telèfon", getCellValue(row.getCell(5))));
            client.appendChild(Functions.createElement(doc, "correu", getCellValue(row.getCell(6))));
            client.appendChild(Functions.createElement(doc, "dataAlta", getCellValue(row.getCell(7))));
            client.appendChild(Functions.createElement(doc, "categoria", getCellValue(row.getCell(8))));
        }

        // Guardar el archivo XML localmente
        File outputFile = Functions.saveDocument(doc, "output/" + outputXmlFileName);

        // Subir el archivo XML a eXistDB
        uploadToExistDB(collection, outputFile, outputXmlFileName);

        System.out.println("\u001B[32mExercici 4.a fet!✅ XML guardado y subido a eXistDB como: " + outputXmlFileName + "\u001B[0m");
    }

    private void uploadToExistDB(Collection collection, File file, String resourceName) throws Exception {
        if (!file.exists()) {
            throw new FileNotFoundException("No se encontró el archivo para subir a eXistDB: " + file.getAbsolutePath());
        }

        // Crear un recurso en eXistDB
        XMLResource resource = (XMLResource) collection.createResource(resourceName, "XMLResource");
        resource.setContent(file);
        collection.storeResource(resource);

        System.out.println("\u001B[34mArchivo subido a eXistDB: " + resourceName + "\u001B[0m");
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return ""; // Si la celda está vacía

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    return dateFormat.format(cell.getDateCellValue());
                } else {
                    return String.valueOf((int) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
