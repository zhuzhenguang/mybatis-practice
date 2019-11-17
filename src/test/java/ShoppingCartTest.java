import org.junit.Test;
import org.mybatis.practice.dao.ProductDao;
import org.mybatis.practice.dao.ShoppingCartDao;
import org.mybatis.practice.dao.UserDao;
import org.mybatis.practice.entity.Product;
import org.mybatis.practice.entity.ShoppingCart;
import org.mybatis.practice.entity.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShoppingCartTest extends TestBase {
    @Test
    public void should_add_a_product_to_shopping_cart() {
        Long userId = Login();
        Long productId = inputProduct();
        ShoppingCartDao shoppingCartDao = new ShoppingCartDao();

        shoppingCartDao.add(productId, userId, 2);

        ShoppingCart result = shoppingCartDao.queryByUserId(userId);
        assertEquals(userId, result.getUserId());
        assertEquals(1, result.getItems().size());
        assertEquals(productId, result.getItems().get(0).getProductId());
        assertEquals(Integer.valueOf(2), result.getItems().get(0).getCount());
    }

    private Long Login() {
        UserDao userDao = new UserDao();
        return userDao.registerNewUser(new User(
                "Ming",
                "123",
                "ming@sohu.com",
                "11111111111", true));
    }

    private Long inputProduct() {
        ProductDao productDao = new ProductDao();
        return productDao.addNew(new Product("Food", "苹果", 10, 1000));
    }
}
