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
    public String toHTMLString() {
        return "<tr><td>" + id + "</td><td>" + SKU + "</td><td>" + description + "</td><td>" + category + "</td><td>" + price
                + "</td>" +
                "<td><a href=\"/product?id="+id+"\" >View</a></td>" +
                "</tr>";
    }

    @Override
    public String toString() {
        return "Product [ID=" + this.id + ", SKU=" + this.SKU + ", Description=" + this.description + ", Category=" + this.category
                + ", Price=" + this.price + " ]";
    }

    public String toHTMLString1() {
        return "<h1> Hello there</h1>"+
                "<tr><td>" + id + "</td><td>" + SKU + "</td><td>" + description + "</td><td>" + category + "</td><td>" + price
                + "</td>" +

//                "<td><a href=\"/product?id="+id+"\" >View</a></td>" +
                "</tr>";
    }

    public String toHTMLStringProduct(){
        return
                "<h5 class=\"card-title\">" + SKU +"</h5>" + "<p class=\"card-text\">"+ description+ "</p>"+
                "<a href=\"/\" class=\"btn btn-primary\"> Home </a>";


    }

}
