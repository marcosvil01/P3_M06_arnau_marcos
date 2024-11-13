import db.DBConnection;
import Exercici_01.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Connexió inicial
            var collection = DBConnection.getCollection();

            // Crida als diferents subapartats
            new AddNewOrder().execute(collection);
            new DeleteOrderById().execute(collection);
            new UpdateOrderQuantity().execute(collection);
            new ModifyCustomerAddress().execute(collection);
            new AddPriorityAttribute().execute(collection);
            new CalculateTotalCost().execute(collection);
            new ListExpensiveItems().execute(collection);
            new GenerateSeattleOrders().execute(collection);
            new AddContactInfo().execute(collection);

            collection.close(); // Tanca la col·lecció després d'executar totes les operacions
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
