package main.ShoppingBasket;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class creates a shopping basket object.
 */
public class ShoppingBasket {
private int product_id;
    private String product;
    private int units;
    private double unitPrice;
    private double totalPrice;


    public ShoppingBasket(int productId, String product, int units, double unitPrice, double totalPrice) {
        product_id = productId;
        this.product = product;
        this.units = units;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }


    public void assignParameters(PreparedStatement statement)throws SQLException {
        statement.setInt(1, product_id);
        statement.setString(2, product);
        statement.setInt(3, units);
        statement.setDouble(4, unitPrice);
        statement.setDouble(5, totalPrice);
    }


    public double getTotalPrice() {
        return totalPrice;
    }

    public String toHTMLString() {
        return "<tr>"+
                "<td>" + product_id + "</td>" +
                "<td>" + product+ "</td>" +
                "<td>" + units + "</td>" +
                "<td>" + unitPrice + "</td>" +
                "<td>" + totalPrice + "</td>" +

                "<td><a href=\"/product?id="+product_id+"\" class=\"btn btn-primary\"> View </a></td>"+
                "</tr>";
    }
    @Override
    public String toString() {
        return  "Product ID="+product_id+ ", Product=" + product+ ", Quantity="+units+ ", Unit Price=" + unitPrice+ ", Total Price="+ units*unitPrice ;
    }
}
