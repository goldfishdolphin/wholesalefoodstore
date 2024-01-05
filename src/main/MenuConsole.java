package main;

import main.Customer.CustomerMenuConsole;
import main.Product.FoodProduct;
import main.Product.FoodProductConsole;
import main.Product.FoodProductDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class creates a console to select the  menu options to view products list, search, delete, add and update a product.
 */
public class MenuConsole {
    FoodProductDAO foodProducts = new FoodProductDAO();

    int id;
    String SKU;
    String description;
    String category;
    Long price;

    void displayMenu() throws SQLException {
        Scanner in = new Scanner(System.in);
        int selected;
        do {
            System.out.println("_____________________");
            System.out.println("The Food Store");
            System.out.println("Choose from these options");
            System.out.println("_____________________");
            System.out.println("[1] Food Products");
            System.out.println("[2] Customers");
            System.out.println("[3] Exit");
            selected = in.nextInt();

            switch (selected) {

                case 1:
                    FoodProductConsole productConsole= new FoodProductConsole();
                    productConsole.displayMenu();
                    break;
                case 2:
                    CustomerMenuConsole customerMenuConsole = new CustomerMenuConsole();
                    customerMenuConsole.displayMenu();
                    break;
                default:
                    System.out.println("Exit");
            }
        }
        while (selected != 3);
    }
}
