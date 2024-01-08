package main.Product;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This is a Food Product object class.
 */
public class FoodProduct {
    private int id;
    private String SKU;
    private String description;
    private String category;
    private double price;

    /**
     * A constructor for the Food Product Class.
     *
     * @param ID          is an integer to reelect the id number of the food product.
     * @param SKU         is a string to identify the unique identifier for the product.
     * @param description is a string to describe the product.
     * @param category
     * @param price
     */
    public FoodProduct(int ID, String SKU, String description, String category, double price) {
        this.id = ID;
        this.SKU = SKU;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * This method creates precompiled SQL statements.
     *
     * @param statement SQL statement
     * @throws SQLException
     */
    public void assignParameters(PreparedStatement statement) throws SQLException {
        statement.setInt(1, id);
        statement.setString(2, SKU);
        statement.setString(3, description);
        statement.setString(4, category);
        statement.setDouble(5, price);
    }

    /**
     * This method convert Html code for each product to a string format for an admin user.
     *
     * @return HTML code in a string form to display product details, view and delete options.
     */
    public String toHTMLString() {
        return "<tr><td>" + id + "</td>" +
                "<td>" + SKU + "</td>" +
                "<td>" + description + "</td>" +
                "<td>" + category + "</td>" +
                "<td>" + price + "</td>" +
                "<td><a href=\"/product?id=" + id + "\" class=\"btn btn-primary\"> View </a></td>" +
                "<td><a href=\"/delete?id=" + id + "\" class=\"btn btn-primary\"> Delete </a></td>" +
                "<td><a href=\"/edit?id=" + id + "\" class=\"btn btn-primary\"> Edit </a></td>" +
                "</tr>";
    }

    /**
     * This method converts product into an HTML string for customers/ non admin users
     *
     * @return a string to display item details with view and add to basket buttons in HTML format.
     */
    public String toCustomerHTMLString() {
        return "<tr><td>" + id + "</td>" +
                "<td>" + SKU + "</td>" +
                "<td>" + description + "</td>" +
                "<td>" + category + "</td>" +
                "<td>" + price + "</td>" +
                "<td><a href=\"/product?id=" + id + "\" class=\"btn btn-primary\"> View </a></td>" +
                "<td><a href=\"/basket?id=" + id + "\" class=\"btn btn-primary\"> Add to Basket </a></td>" +
                "</tr>";
    }

    /**
     * The method formats the output of each product in a string format.
     *
     * @return The details of each product on menu console.
     */
    @Override
    public String toString() {
        return "Product [ID=" + this.id + ", SKU=" + this.SKU + ", Description=" + this.description + ", Category=" + this.category
                + ", Price=" + this.price + " ]";
    }

    /**
     * The method converts the HTML code for each product to a string form.
     *
     * @return String format of an HTML code.
     */
    public String toProductHTMLString() {
        return

                "<h5 class=\"card-title\">" + SKU + "</h5>" + "<p class=\"card-text\"> " + description + "</p>" +
                        "<a href=\"/basket?id=" + id + "\" class=\"btn btn-primary\"> <i class=\"bi bi-basket2-fill\"></i>Add to Basket </a>" +
                        "<br/><br/><br/>" +
                        "<a href=\"/\" class=\"btn btn-primary\"> Home </a>";
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}