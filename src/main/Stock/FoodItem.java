package main.Stock;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;


public class FoodItem {

    private int id;
    private String expiryDate;
    private String foodProduct;
    private int quantity;

    public FoodItem(int id, String foodProduct, int quantity, String expiryDate) {
        this.id = id;
        this.foodProduct= foodProduct;
        this.expiryDate = expiryDate;
        this.quantity= quantity;
    }

    public void assignParameters(PreparedStatement statement)throws SQLException{
        statement.setInt(1, id);
        statement.setString(3, foodProduct);
        statement.setDate(2, Date.valueOf(expiryDate));
        statement.setInt(3, quantity);

    }

    public String toHTMLString() {
        return "<tr>"+
                "<td>" + id + "</td>" +
                "<td>" + foodProduct + "</td>" +
                "<td>" + quantity + "</td>" +
                "<td>" + expiryDate + "</td>" +
                "<td><a href=\"/product?id="+id+"\" class=\"btn btn-primary\"> View </a></td>"+
                "<td><a href=\"/delete?id="+id+"\" class=\"btn btn-primary\"> Delete </a></td>" +
                "<td><a href=\"/edit?id="+id+"\" class=\"btn btn-primary\"> Edit </a></td>" +
                "</tr>";
    }
    @Override
    public String toString() {
        return  "ID="+id+ ", Product=" + this.foodProduct+ ", Quantity="+quantity+ ", Expiry Date=" + this.expiryDate ;
    }
}

