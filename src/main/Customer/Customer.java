package main.Customer;


import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Customer {
    private int customerID;
    private String businessName;
    private Address address;
    private String phoneNumber;

    public Customer(int customerID, String businessName, Address address, String phoneNumber){
        this.customerID= customerID;
        this.businessName= businessName;
        this.address = address;
        this.phoneNumber=phoneNumber;
    }


    public void  assignParameters(PreparedStatement statement) throws SQLException {
        statement.setInt(1,customerID);
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

    @Override
    public String toString(){
        return "Customer [Customer ID=" + this.customerID +  ", Business Name=" + this.businessName
                + ", Address=" + this.address   + ", Phone Number=" + this.phoneNumber + " ]";
    }
}
