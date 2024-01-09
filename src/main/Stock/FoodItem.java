package main.Stock;

import main.ShoppingBasket.ShoppingBasket;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This is an object for the stock level in the store.
 */
public class FoodItem {

    private int id;
    private String expiryDate;
    private String foodProduct;
    private int quantity;
    private int price;

    public FoodItem(int id, String foodProduct, int quantity, String expiryDate, int price) {
        this.id = id;
        this.foodProduct= foodProduct;
        this.expiryDate = expiryDate;
        this.quantity= quantity;
        this.price = price;
    }

    public void assignParameters(PreparedStatement statement)throws SQLException{
        statement.setInt(1, id);
        statement.setString(2, foodProduct);
        statement.setInt(3, price);
        statement.setString(4, expiryDate);
        statement.setInt(5, quantity);

    }

    public String toHTMLString() {
        return "<tr>"+
                "<td>" + id + "</td>" +
                "<td>" + foodProduct + "</td>" +
                "<td>" + quantity + "</td>" +
                "<td>" +price + "</td>" +
                "<td>" + expiryDate + "</td>" +
                "<td><a href=\"/product?id="+id+"\" class=\"btn btn-primary\"> View </a></td>"+
                "<td><a href=\"/delete?id="+id+"\" class=\"btn btn-primary\"> Delete </a></td>" +
                "<td><a href=\"/edit?id="+id+"\" class=\"btn btn-primary\"> Edit </a></td>" +
                "</tr>";
    }
    @Override
    public String toString() {
        return  "ID="+id+ ", Product=" + this.foodProduct+ ", Quantity="+quantity+ ", Expiry Date=" + this.expiryDate + ", Price ="+price;
    }
}

