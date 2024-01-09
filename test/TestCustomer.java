import main.Customer.Address;
import main.Customer.Customer;
import main.Customer.CustomerDAO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

/**
 * This class tests the Data Access Object of the Customer class.
 */
public class TestCustomer {
    @Test
    void testGetAllCustomerList()  {
        // Arrange
        CustomerDAO testCustomer = new CustomerDAO();
        CustomerDAO spiedCustomerDAO = Mockito.spy(testCustomer);

        List<Customer> list = new ArrayList<>();
        Mockito.when(spiedCustomerDAO.listCustomer()).thenReturn(list);

        // Act
        List<Customer> list1 = spiedCustomerDAO.listCustomer();
        List<Customer> list2 = spiedCustomerDAO.listCustomer();
        List<Customer> list3 = spiedCustomerDAO.listCustomer();
        List<Customer> list4 = spiedCustomerDAO.listCustomer();

        // Assert
        verify(spiedCustomerDAO, Mockito.times(4)).listCustomer();
    }
    @Test
    void testSelectCustomerByIDOnSpy() throws SQLException {
        // Arrange
        CustomerDAO testCustomer = new CustomerDAO();
        CustomerDAO spiedCustomerDAO = Mockito.spy(testCustomer);

        Address testAddress = new Address("23", "Hall Road", "Test", "Test", "N1,TEST");
        Customer customer = new Customer(100, "Test Customer", testAddress, "98980808");
        Mockito.when(spiedCustomerDAO.selectCustomer(Mockito.anyInt())).thenReturn(customer);
        // Act
        Customer customer1 = spiedCustomerDAO.selectCustomer(1001);
        Customer customer2 = spiedCustomerDAO.selectCustomer(1003);
        Customer customer3 = spiedCustomerDAO.selectCustomer(1002);
        Customer customer4 = spiedCustomerDAO.selectCustomer(1004);
        System.out.println(spiedCustomerDAO.selectCustomer(200));
        // Assert
        verify(spiedCustomerDAO, Mockito.times(5)).selectCustomer(Mockito.anyInt());
    }


    @Test
    void testUpsertCustomer() {
        //Arrange
        CustomerDAO testCustomer = new CustomerDAO();
        CustomerDAO spiedCustomerDAO = Mockito.spy(testCustomer);
        Address testAddress = new Address("23", "Hall Road", "Test", "Test", "N1,TEST");
        Customer testCustomer1 = new Customer(100, "Test Customer", testAddress, "98980808");
        Customer testCustomer2 = null;
        doNothing().when(spiedCustomerDAO).upsert(Mockito.any(Customer.class));
        //Act
        spiedCustomerDAO.upsert(testCustomer1);

        //Assert
        verify(spiedCustomerDAO, Mockito.atLeastOnce()).upsert(testCustomer1);
        verify(spiedCustomerDAO, Mockito.never()).upsert(testCustomer2);
        verify(spiedCustomerDAO, Mockito.times(1)).upsert(testCustomer1);
    }

    @Test
    void testDeleteCustomerMethod() {
        //Arrange
        CustomerDAO testCustomer = new CustomerDAO();
        CustomerDAO spiedCustomerDAO = Mockito.spy(testCustomer);
        Address testAddress = new Address("23", "Hall Road", "Test", "Test", "N1,TEST");
        Customer testCustomer1 = new Customer(100, "Test Customer", testAddress, "98980808");
        Customer testCustomer2 = null;
        spiedCustomerDAO.upsert(testCustomer1);
        doNothing().when(spiedCustomerDAO).upsert(Mockito.any(Customer.class));
        doNothing().when(spiedCustomerDAO).deleteCustomer(Mockito.anyInt());

        //Act
        spiedCustomerDAO.deleteCustomer(100);
        //Assert

        verify(spiedCustomerDAO, Mockito.atLeastOnce()).deleteCustomer(100);
        verify(spiedCustomerDAO, Mockito.times(1)).deleteCustomer(100);

    }
}