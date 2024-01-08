import main.Product.FoodProduct;
import main.Product.FoodProductDAO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

/**
 * This class tests the Data Access Object of the Food Product class.
 */
class TestFoodProduct {
    @Test
    void testListProductOnSpy() {
        //Arrange
        FoodProductDAO foodTestProducts = new FoodProductDAO();
        FoodProductDAO spiedDAO = Mockito.spy(foodTestProducts);
        List<FoodProduct> list = new ArrayList<>();
        Mockito.when(spiedDAO.listProduct()).thenReturn(list);
        // Act
        spiedDAO.listProduct();
        spiedDAO.listProduct();
        List<FoodProduct> foodProductList = spiedDAO.listProduct();
        List<FoodProduct> foodProductList1 = spiedDAO.listProduct();
        // Assert
        verify(spiedDAO, Mockito.times(4)).listProduct();
    }

    @Test
    void testSelectProductByID() throws SQLException {
        // Arrange
        FoodProductDAO foodTestProducts = Mockito.mock(FoodProductDAO.class);
        Mockito.when(foodTestProducts.selectProduct(1899)).thenReturn(new FoodProduct(1899, "Te1", "test1", "Fruit", 100));
        Mockito.when(foodTestProducts.selectProduct(2899)).thenReturn(new FoodProduct(2899, "Te2", "test2", "Vegetable", 200));
        Mockito.when(foodTestProducts.selectProduct(3899)).thenReturn(new FoodProduct(3899, "Te3", "test3", "Fruit", 100));

        //Act
        FoodProduct p = foodTestProducts.selectProduct(1899);
        //Assert
        assert (p.getId() == 1899);
        assert (p.toString().equals(("Product [ID=1899, SKU=Te1, Description=test1, Category=Fruit, Price=100.0 ]")));

        //Act
        FoodProduct p1 = foodTestProducts.selectProduct(2899);
        //Assert
        assert (p1.getId() == 2899);
        assert (p1.toString().equals(("Product [ID=2899, SKU=Te2, Description=test2, Category=Vegetable, Price=200.0 ]")));
    }


    @Test
    void testUpsertProductMethodOnSpy() throws SQLException {
        //Arrange
        FoodProductDAO foodTestProducts = new FoodProductDAO();
        FoodProductDAO spiedDAO = Mockito.spy(foodTestProducts);

        FoodProduct newProduct = new FoodProduct(111, "TE", "TestingItem", "Vegetable", 1000.0);
        FoodProduct newProduct1 = new FoodProduct(222, "T1", "TestingItem1", "Vegetable", 2000.0);
        FoodProduct newProduct2 = new FoodProduct(111, "TE", "TestingItem", "Vegetable", 2000.0);
        doNothing().when(spiedDAO).upsert(Mockito.any(FoodProduct.class));
        //Act
        spiedDAO.upsert(newProduct);
        spiedDAO.upsert(newProduct2);
        //Assert
        verify(spiedDAO, Mockito.never()).upsert(newProduct1);
        verify(spiedDAO, Mockito.times(1)).upsert(newProduct);
        verify(spiedDAO, Mockito.atLeastOnce()).upsert(newProduct2);
    }

    @Test
    void testDeleteProductMethod() throws SQLException {
        //Arrange
        FoodProductDAO foodTestProducts = new FoodProductDAO();
        FoodProductDAO spiedDAO = Mockito.spy(foodTestProducts);
        spiedDAO.upsert(new FoodProduct(131, "TE", "TestingItem3", "Fruit", 1000.0));

        //Act
        spiedDAO.deleteProduct(131);

        //Assert
        verify(spiedDAO, Mockito.times(1)).deleteProduct(131);
    }
}