import java.util.Scanner;

/**
 * This class creates a menu console to select the options
 */
public class MenuConsole {
    /**
     * Food Product class
     * @method: deleteProduct
     */
    FoodProduct foodProduct = new FoodProduct();
    void displayMenu(){
        Scanner in = new Scanner(System.in);
        int selected;
        do{
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

        selected= in.nextInt();
        switch(selected) {

            case 1:
                System.out.println("Listing product");
                break;
            case 2:
                System.out.println("Searching product");
                break;
            case 3:
                System.out.println("Listing a product");
                break;
            case 4:
                System.out.println("Updating a product");
                break;
            case 5:
                System.out.println("Please enter the id of the product you want to delete");
                int id = in.nextInt();
               foodProduct.deleteProduct(id);
                break;
            default:
                System.out.println("Exit");
        }
        }   while (selected != 6);
    }
}
