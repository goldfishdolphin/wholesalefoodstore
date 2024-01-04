package main.ShoppingBasket;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ShoppingBasket {
private int product_id;
    private String product;
    private int units;
    private long unitPrice;
    private long totalPrice;


    public ShoppingBasket(int productId, String product, int units, long unitPrice, long totalPrice) {
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
        statement.setLong(4, unitPrice);
        statement.setLong(5, totalPrice);
    }


    public long getTotalPrice() {
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
