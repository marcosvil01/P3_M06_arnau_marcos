import db.DBConnection;
import Exercici_01.*;
import Exercici_02.*;
import Exercici_03.*;
import org.xmldb.api.base.Collection;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Collection collection = DBConnection.getCollection();
            if (collection == null) {
                System.err.println("No se pudo establecer conexión con la base de datos.");
                return;
            }

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("\nSeleccione el ejercicio que desea ejecutar:");
                System.out.println("1. Exercici_01");
                System.out.println("2. Exercici_02");
                System.out.println("3. Exercici_03");
                System.out.println("0. Salir");
                System.out.print("Opción: ");
                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        executeExercici01(collection);
                        break;
                    case 2:
                        executeExercici02(collection);
                        break;
                    case 3:
                        executeExercici03(collection);
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            }

            collection.close(); // Cerrar la colección después de todas las operaciones
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void executeExercici01(Collection collection) throws Exception {
        System.out.println("Ejecutando Exercici_01...");

        // Ejecuta las operaciones del Exercici_01
        new AddNewOrder().execute(collection);  // 1.a
        new DeleteOrderById().execute(collection);  // 1.b
        new UpdateOrderQuantity().execute(collection);  // 1.c
        new ModifyCustomerAddress().execute(collection);    // 1.d
        new AddPriorityAttribute().execute(collection); // 1.e
        new CalculateTotalCost().execute(collection);   // 1.f
        new ListExpensiveItems().execute(collection);   // 1.g
        new GenerateSeattleOrders().execute(collection);    // 1.h
        new AddContactInfo().execute(collection);   // 1.i
    }

    private static void executeExercici02(Collection collection) throws Exception {
        System.out.println("Ejecutando Exercici_02...");

        // Ejecución de las operaciones de Exercici_02 con mensaje de estado
        new ShowAllOrdersUnderVenta().execute(collection);   // 2.a
        new ShowOrdersFromSeattle().execute(collection);     // 2.b
        new ShowOrdersWithPriceAbove50().execute(collection); // 2.c
        new ShowOrdersWithMoreThanTwoItems().execute(collection); // 2.d
        new RenamePurchaseOrderToVentas().execute(collection);    // 2.e
        new ModifyFirstOrderQuantity().execute(collection);   // 2.f
        new DeleteThirdPurchaseOrder().execute(collection);   // 2.g
        new CountAllPurchaseOrders().execute(collection);     // 2.h
    }

    private static void executeExercici03(Collection collection) throws Exception {
        System.out.println("Ejecutando Exercici_03...");

        // Executar els subapartats de l'Exercici 3
        new AddRecipe().execute(collection);  // 3.a
        new UpdateRecipeDifficulty().execute(collection);  // 3.b
        new AddIngredientToTiramisu().execute(collection);  // 3.c
        new DeleteRecipe().execute(collection);  // 3.d
        new ListIngredients().execute(collection);  // 3.e
        new SumCalories().execute(collection);  // 3.f
        new AddNoteToRecipes().execute(collection);  // 3.g
        new UpdateRecipeStep().execute(collection);  // 3.h
        new GenerateRecipeIndex().execute(collection);  // 3.i
    }

}
