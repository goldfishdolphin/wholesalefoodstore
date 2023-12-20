import java.util.Scanner;

/**
 * This class creates a menu console to select the options
 */
public class MenuConsole {
    /**
     * Food Product class
     * @method: deleteProduct
     */
    FoodProductDAO foodProduct = new FoodProductDAO();
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
                foodProduct.listProduct();
                break;
            case 2:
//                System.out.println("Please enter the id of the product you want to select");
//                int id = in.nextInt();
//                foodProduct.selectProduct(id);
                System.out.println("Finding a product by id");
                break;
            case 3:
//                System.out.println("Please enter the id of the product you want to add");
//                int id1 = in.nextInt();
//                foodProduct.addProduct(id1);
                System.out.println("Adding a product");
                break;
            case 4:
//                System.out.println("Please enter the id of the product you want to update");
//                int id2 = in.nextInt();
//                foodProduct.updateProduct(id2);
                System.out.println("Updating the product");
                break;
            case 5:
//                System.out.println("Please enter the id of the product you want to delete");
//                int id3 = in.nextInt();
//               foodProduct.deleteProduct(id3);
                System.out.println("Deleting the product");
                break;
            default:
                System.out.println("Exit");
        }
        }   while (selected != 6);
    }
}
