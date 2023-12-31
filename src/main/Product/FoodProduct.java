package main;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FoodProduct {
    private int id;
    private String SKU;
    private  String description;
    private  String category;
    private long price;

    public FoodProduct(int ID, String SKU, String description, String category, long price ){
     this.id= ID;
     this.SKU = SKU;
     this.description = description;
     this.category = category;
     this.price = price;
    }

    public void  assignParameters(PreparedStatement statement) throws SQLException{
        statement.setInt(1, id);
        statement.setString(2, SKU);
        statement.setString(3, description);
        statement.setString(4, category);
        statement.setLong(5, price);
    }

    /**
     * This method convert Html code for each product to a string format.
     * @return HTML code in a string form to display product details, view and delete options.
     */
    public String toHTMLString() {
        return "<tr><td>" + id + "</td>" +
                "<td>" + SKU + "</td>" +
                "<td>" + description + "</td>" +
                "<td>" + category + "</td>" +
                "<td>" + price + "</td>" +
                "<td><a href=\"/product?id="+id+"\" class=\"btn btn-primary\"> View </a></td>"+
                "<td><a href=\"/delete?id="+id+"\" class=\"btn btn-primary\"> Delete </a></td>" +
                "<td><a href=\"/edit?id="+id+"\" class=\"btn btn-primary\"> Edit </a></td>" +
                "</tr>";
    }

    /**
     * The method formats the output of each product in a string format.
     * @return The details of each product on menu console.
     */
    @Override
    public String toString() {
        return "Product [ID=" + this.id + ", SKU=" + this.SKU + ", Description=" + this.description + ", Category=" + this.category
                + ", Price=" + this.price + " ]";
    }

    /**
     * The method converts the HTML code for each product to a string form.
     * @return String format of an HTML code.
     */
    public String toProductHTMLString(){
        return
                "<h5 class=\"card-title\">" + SKU +"</h5>" + "<p class=\"card-text\">"+ description+ "</p>"+
                "<a href=\"/\" class=\"btn btn-primary\"> Home </a>";
    }

}
