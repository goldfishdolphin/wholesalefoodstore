package main.ShoppingBasket;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ShoppingBasket {
    private int basket_id;
    private int product_id;
    private String product;
    private int quantity;
    private long unitPrice;
    private long totalPrice;


    public ShoppingBasket(int basketId, int productId, String product, int quantity, long unitPrice, long totalPrice) {
        basket_id = basketId;
        product_id = productId;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }
    public void incrementQuantity(){
        quantity += 1;
    }
    public void decreaseQuantity(){
        quantity -= 1;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    private long getTotalPrice(long totalPrice, long unitPrice, int quantity){
               return   totalPrice= unitPrice* quantity;
    }
    public void assignParameters(PreparedStatement statement)throws SQLException {
        statement.setInt(1, basket_id);
        statement.setInt(2, product_id);
        statement.setString(3, product);
        statement.setInt(4, quantity);
        statement.setLong(5, unitPrice);
    }

    public String toHTMLString() {
        return "<tr>"+
                "<td>" + basket_id + "</td>" +
                "<td>" + product_id + "</td>" +
                "<td>" + product+ "</td>" +
                "<td>" + quantity + "</td>" +
                "<td>" + unitPrice + "</td>" +
                "<td><a href=\"/product?id="+product_id+"\" class=\"btn btn-primary\"> View </a></td>"+
                "</tr>";
    }
    @Override
    public String toString() {
        return  "ID="+basket_id+" Product ID"+product_id+ ", Product=" + product+ ", Quantity="+quantity+ ", Unit Price=" + unitPrice+ " Total Price="+ totalPrice ;
    }
}
