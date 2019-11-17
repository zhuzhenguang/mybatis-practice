import org.junit.Test;
import org.mybatis.practice.dao.ProductDao;
import org.mybatis.practice.entity.Product;

import static org.junit.Assert.assertTrue;

public class ProductTest extends TestBase {
    @Test
    public void should_add_new_product() {
        Product product = new Product("Food", "苹果", 10, 1000);
        ProductDao productDao = new ProductDao();

        Long id = productDao.addNew(product);

        assertTrue(id > 0);
    }
}
