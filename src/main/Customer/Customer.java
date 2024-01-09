package main.Customer;


import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class creates a customer object.
 */

public class Customer {
    private int customerID;
    private String businessName;
    private Address address;
    private String phoneNumber;

    public Customer(int customerID, String businessName, Address address, String phoneNumber) {
        this.customerID = customerID;
        this.businessName = businessName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }


    public void assignParameters(PreparedStatement statement) throws SQLException {
        statement.setInt(1, customerID);
        statement.setString(2, businessName);
        statement.setString(3, phoneNumber);
        statement.setString(4, address.getAddressLine1());
        statement.setString(5, address.getAddressLine2());
        statement.setString(6, address.getAddressLine3());
        statement.setString(7, address.getCountry());
        statement.setString(8, address.getPostCode());

    }

    public Address getAddress() {
        return address;
    }

    public String toHTMLString() {
        return "<tr><td>" + customerID + "</td>" +
                "<td>" + businessName + "</td>" +
                "<td>" + phoneNumber + "</td>" +
                "<td>" + address + "</td>" +
                "<td><a href=\"/customer?id="+customerID+"\" class=\"btn btn-dark\"> View </a></td>"+
                "<td><a href=\"/deletecustomer?id="+customerID+"\" class=\"btn btn-dark\"> Delete </a></td>" +
                "<td><a href=\"/editcustomer?id="+customerID+"\" class=\"btn btn-dark\"> Edit </a></td>" +
                "</tr>";
    }

    @Override
    public String toString() {
        return "Customer [Customer ID=" + this.customerID + ", Business Name=" + this.businessName
                + ", Address=" + this.address + ", Phone Number=" + this.phoneNumber + " ]";
    }

    public String toCustomertHTMLString() {
        return
                "<h5 class=\"card-title\">" + customerID + "</h5>" + "<p class=\"card-text\">" + businessName + "</p>" +
                        "<a href=\"/\" class=\"btn btn-dark\"> Home </a>";
    }
}