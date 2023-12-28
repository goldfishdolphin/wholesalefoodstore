package main.Customer;

import main.Util;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class CustomerMenuConsole {
    CustomerDAO customers = new CustomerDAO();

    int customer_id;
    String businessName;
    String phoneNumber;

    String addressLine1;
    String addressLine2;
    String addressLine3;
    String country;
    String postCode;
    Address address = new Address(addressLine1, addressLine2, addressLine3, country, postCode);

    public void displayMenu() throws SQLException {
        Scanner in = new Scanner(System.in);
        int selected;
        do {
            System.out.println("_____________________");
            System.out.println("The Food Store");
            System.out.println("Choose from these options");
            System.out.println("_____________________");
            System.out.println("[1] List all customers");
            System.out.println("[2] Search customer by the customer ID");
            System.out.println("[3] Add a new customer");
            System.out.println("[4] Update a customer by ID");
            System.out.println("[5] Delete a customer by ID");
            System.out.println("[6] Exit");

            selected = in.nextInt();

            switch (selected) {

                case 1:
                    System.out.println("All customers");
                    customers.listCustomer();
                    break;
                case 2:
                    System.out.println("Please enter the id of the customer you want to select");
                    customer_id = in.nextInt();
                    var customer = customers.selectCustomer(customer_id);
                    if (customer != null) {
                        System.out.println("The customer found by ID: " + customer_id);
                        System.out.println(customer);
                    }
                    break;
                case 3:
                    System.out.println("Please enter the id of the customer you want to add");
                    customer_id = in.nextInt();
                    in.nextLine();

                    System.out.println("Please enter the Business Name of the customer you want to add");
                    businessName = in.nextLine();
                    System.out.println("Please enter the First Line of the Address of the customer you want to add");
                    addressLine1 = in.nextLine();
                    System.out.println("Please enter the Second Line of the Address of the customer you want to add");
                    addressLine2 = in.nextLine();
                    System.out.println("Please enter the Third Line of the Address of the customer you want to add");
                    addressLine3 = in.nextLine();
                    System.out.println("Please enter the Country of the customer you want to add");
                    country = in.nextLine();
                    System.out.println("Please enter the Postcode of the Address of the customer you want to add");
                    postCode = in.nextLine();
                    address = new Address(addressLine1, addressLine2, addressLine3, country, postCode);
                    System.out.println("Please enter the Phone of the customer you want to add");
                    phoneNumber = in.nextLine();

                    customers.upsert(new Customer(customer_id, businessName, address, phoneNumber));
                    System.out.println("Customer Added!");
                    break;
                case 4:
                    System.out.println("Please enter the id of the customer you want to update");
                    customer_id = in.nextInt();
                    in.nextLine();
                    if (customers.selectCustomer(customer_id) != null) {
                        Customer currentCustomer = customers.selectCustomer(customer_id);
                        String customerString = String.valueOf(currentCustomer);
                        Map<String, String> customerMap = Util.customerKeyValuePairs(customerString);
                        businessName = customerMap.get("Business Name");
                        phoneNumber = customerMap.get("Phone Number");
                        String addressString = String.valueOf(currentCustomer.getAddress());
                        String[] addressParts = addressString.split(",\\s*");
                        if (addressParts.length >= 5) {
                            addressLine1 = addressParts[0].trim();
                            addressLine2 = addressParts[1].trim();
                            addressLine3 = addressParts[2].trim();
                            country = addressParts[3].trim();
                            postCode = addressParts[4].trim();

                            address = new Address(addressLine1, addressLine2, addressLine3, country, postCode);
                        } else {
                            System.out.println("Invalid address format");
                        }
                        System.out.println("Do you want to update the business name? y/n");
                        String option = in.nextLine();
                        if (option.equals("y")) {
                            System.out.println("Enter new Business Name:");
                            businessName = in.nextLine();
                        }
                        System.out.println("Do you want to update the First Line of the address? y/n");
                        option = in.nextLine();
                        if (option.equals("y")) {
                            System.out.println("Enter new First Line of the address:");
                            addressLine1 = in.nextLine();
                        }
                        System.out.println("Do you want to update the Second Line of the address? y/n");
                        option = in.nextLine();
                        if (option.equals("y")) {
                            System.out.println("Enter new Second Line of the address:");
                            addressLine2 = in.nextLine();
                        }
                        System.out.println("Do you want to update the Third Line of the address? y/n");
                        option = in.nextLine();
                        if (option.equals("y")) {
                            System.out.println("Enter new Third Line of the address:");
                            addressLine3 = in.nextLine();
                        }
                        System.out.println("Do you want to update the country? y/n");
                        option = in.nextLine();
                        if (option.equals("y")) {
                            System.out.println("Enter new Country:");
                            country = in.nextLine();
                            System.out.println(country);
                        }
                        System.out.println("Do you want to update the the Postcode? y/n");
                        option = in.nextLine();
                        if (option.equals("y")) {
                            System.out.println("Enter new Postcode:");
                            postCode = in.nextLine();
                            System.out.println(postCode);
                        }
                        System.out.println("Do you want to update the Phone Number? y/n");
                        option = in.nextLine();
                        if (option.equals("y")) {
                            System.out.println("Enter new Phone Number:");
                            phoneNumber = in.nextLine();
                        }
                        address = new Address(addressLine1, addressLine2, addressLine3, country, postCode);
                        customers.upsert(new Customer(customer_id, businessName, address, phoneNumber));
                    }

                    System.out.println("Updated the customer with id " + customer_id);
                    break;
                case 5:
                    System.out.println("Please enter the id of the customer you want to delete");
                    customer_id = in.nextInt();
                    if (customers.selectCustomer(customer_id) == null) {
                        System.out.println("Make sure the customer with id " + customer_id + " exists before you try to delete it");
                        break;
                    } else {
                        customers.deleteCustomer(customer_id);
                    }
                    System.out.println("Customer Removed !");
                    break;
                default:
                    System.out.println("Exit");
            }
        }
        while (selected != 6);
    }
}

