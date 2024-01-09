package main.Customer;

/**
 * This class creates an address object.
 */
public class Address {
    private String addressLine1;
    private  String addressLine2;
    private String addressLine3;
    private String country;
    private String postCode;



    public Address(String addressLine1, String addressLine2, String addressLine3, String country, String postCode) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.country = country;
        this.postCode = postCode;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public String getCountry() {
        return country;
    }

    public String getPostCode() {
        return postCode;
    }
    @Override
    public String toString(){
        return " "+ this.addressLine1+ ", "+ this.addressLine2+ ", "+this.addressLine3+ ", "+ this.country+", "+this.postCode+ " ";

    }

}
