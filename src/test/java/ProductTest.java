import org.junit.Test;
import org.mybatis.practice.dao.ProductDao;
import org.mybatis.practice.entity.Product;

import java.util.List;

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

    @Test
    public void should_list_all_products() {
        ProductDao productDao = new ProductDao();
        productDao.addNew(new Product("Food", "苹果", 10, 1000));
        productDao.addNew(new Product("Food", "鸭梨", 20, 2000));

        List<Product> results = productDao.listAll();

        assertEquals(2, results.size());
        assertEquals("Food", results.get(0).getCategory());
        assertEquals("苹果", results.get(0).getName());
        assertEquals(Double.valueOf(10), results.get(0).getPrice());
        assertEquals(Integer.valueOf(1000), results.get(0).getStorage());
        assertNotNull(results.get(0).getCreatedAt());
        assertEquals("Food", results.get(1).getCategory());
        assertEquals("鸭梨", results.get(1).getName());
        assertEquals(Double.valueOf(20), results.get(1).getPrice());
        assertEquals(Integer.valueOf(2000), results.get(1).getStorage());
        assertNotNull(results.get(1).getCreatedAt());
    }

    @Test
    public void should_change_product() {
        ProductDao productDao = new ProductDao();
        Long id = productDao.addNew(new Product("Food", "苹果", 10, 1000));

        productDao.change(new Product(id, "Food", "苹果", 20, 2000));

        Product result = productDao.queryById(id);
        assertEquals("Food", result.getCategory());
        assertEquals("苹果", result.getName());
        assertEquals(Double.valueOf(20), result.getPrice());
        assertEquals(Integer.valueOf(2000), result.getStorage());
    }

    @Test
    public void should_delete_product() {
        ProductDao productDao = new ProductDao();
        Long id = productDao.addNew(new Product("Food", "苹果", 10, 1000));

        productDao.delete(id);

        Product result = productDao.queryById(id);
        assertNull(result);
    }
}
