import org.junit.Test;
import org.mybatis.practice.dao.ProductDao;
import org.mybatis.practice.entity.Product;

import static org.junit.Assert.*;

public class ProductTest extends TestBase {
    @Test
    public void should_add_new_product() {
        Product product = new Product("Food", "苹果", 10, 1000);
        ProductDao productDao = new ProductDao();

        Long id = productDao.addNew(product);

        assertTrue(id > 0);
        Product result = productDao.queryById(id);
        assertEquals("Food", result.getCategory());
        assertEquals("苹果", result.getName());
        assertEquals(Double.valueOf(10), result.getPrice());
        assertEquals(Integer.valueOf(1000), result.getStorage());
        assertNotNull(result.getCreatedAt());
    }
}
