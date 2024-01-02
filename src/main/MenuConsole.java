package main;

import main.Product.FoodProduct;
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
            System.out.println("[1] List all products");
            System.out.println("[2] Search by the product ID");
            System.out.println("[3] Add a new product");
            System.out.println("[4] Update a product by ID");
            System.out.println("[5] Delete a product by ID");
            System.out.println("[6] Exit");

            selected = in.nextInt();

            switch (selected) {

                case 1:
                  foodProducts.listProduct();
                    break;
                case 2:
                    System.out.println("Please enter the id of the product you want to select");
                    id = in.nextInt();
                    var foodProduct = foodProducts.selectProduct(id);
                    System.out.println(foodProduct);
                    break;
                case 3:
                    System.out.println("Please enter the id of the product you want to add");
                    id = in.nextInt();
                    System.out.println("Please enter the SKU of the product you want to add");
                    SKU = in.next();
                    in.nextLine();
                    System.out.println("Please enter the Description of the product you want to add");
                    description = in.nextLine();

                    System.out.println("Please enter the category of the product you want to add");
                    category = in.nextLine();
                    System.out.println("Please enter the Price of the product you want to add");
                    price = in.nextLong();

                    foodProducts.upsert(new FoodProduct(id, SKU, description, category, price));
                    System.out.println("Product Added!");
                    break;
                case 4:
                    System.out.println("Please enter the id of the product you want to update");
                    id = in.nextInt();
                    in.nextLine();
                    if (foodProducts.selectProduct(id) != null) {
                        FoodProduct currentProduct = (foodProducts.selectProduct(id));
                        String productString = String.valueOf(currentProduct);
                        Map<String, String> productMap = Util.productKeyValuePairs(productString);
                        SKU = productMap.get("SKU");
                        description = productMap.get("Description");
                        category = productMap.get("Category");
                        price = Long.parseLong(productMap.get("Price"));
                        System.out.println("Do you want to edit SKU? y/n");
                        String option = in.next();
                        in.nextLine();
                        if (option.equals("y")) {
                            System.out.println("Enter new SKU: ");
                            SKU = in.next();
                            in.nextLine();
                        }
                        System.out.println("Do you want to edit Description? y/n");
                        option = in.next();
                        in.nextLine();

                        if (option.equals("y")) {
                            System.out.println("Enter new Description: ");
                            description = in.nextLine();
                        }

                        System.out.println("Do you want to edit the category? y/n");
                        option = in.next();
                        in.nextLine();

                        if (option.equals("y")) {
                            System.out.println("Enter new Category: ");
                            category = in.nextLine();
                        }

                        System.out.println("Do you want to edit the price? y/n");
                        option = in.next();
                        in.nextLine();

                        if (option.equals("y")) {
                            System.out.println("Enter new Price: ");
                            price = in.nextLong();
                        }
                        foodProducts.upsert(new FoodProduct(id, SKU, description, category, price));
                    }


                    System.out.println("Updated the product with id "+ id);
                    break;
                case 5:
                    System.out.println("Please enter the id of the product you want to delete");
                    id = in.nextInt();
                    if (foodProducts.selectProduct(id) == null) {
                        System.out.println("Make sure the item with id " + id + " before you try to delete it");
                        break;
                    } else {
                        foodProducts.deleteProduct(id);
                    }
                    break;
                default:
                    System.out.println("Exit");
            }
        }
        while (selected != 6);
    }
}
