import db.DBConnection;
import Exercici_01.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Connexió inicial
            var collection = DBConnection.getCollection();


            // a
            new AddNewOrder().execute(collection);

            // b
            new DeleteOrderById().execute(collection);

            // c
            new UpdateOrderQuantity().execute(collection);

            // d
            new ModifyCustomerAddress().execute(collection);

            // e
            new AddPriorityAttribute().execute(collection);

            // f
            new CalculateTotalCost().execute(collection);

            // g
            new ListExpensiveItems().execute(collection);

            // h
            new GenerateSeattleOrders().execute(collection);

            //i
            new AddContactInfo().execute(collection);

            collection.close(); // Tanca la col·lecció després d'executar totes les operacions
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
