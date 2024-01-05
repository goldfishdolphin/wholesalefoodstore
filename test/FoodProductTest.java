import main.Product.FoodProduct;
import main.Product.FoodProductDAO;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FoodProductTest {
    FoodProductDAO foodProduct = new FoodProductDAO();

    @Test
    public void testProductList() {
        List<FoodProduct> result = foodProduct.listProduct();
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testSelectProductByID() throws SQLException {
        FoodProduct testFoodProduct = foodProduct.selectProduct(1);
        assertNotNull(testFoodProduct);
        assertInstanceOf(  FoodProduct.class, testFoodProduct);

    }
}

