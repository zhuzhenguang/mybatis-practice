import org.junit.Test;
import org.mybatis.practice.dao.ProductDao;
import org.mybatis.practice.dao.ShoppingCartDao;
import org.mybatis.practice.dao.UserDao;
import org.mybatis.practice.entity.Product;
import org.mybatis.practice.entity.ShoppingCart;
import org.mybatis.practice.entity.ShoppingCartItem;
import org.mybatis.practice.entity.User;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShoppingCartTest extends TestBase {
    @Test
    public void should_add_a_product_to_shopping_cart() {
        Long userId = Login();
        Long productId = inputFruit("苹果");
        ShoppingCartDao shoppingCartDao = new ShoppingCartDao();

        shoppingCartDao.add(productId, userId, 2);

        ShoppingCart result = shoppingCartDao.queryByUserId(userId);
        assertEquals(userId, result.getUserId());
        assertEquals(1, result.getItems().size());
        assertEquals(productId, result.getItems().get(0).getProductId());
        assertEquals(Integer.valueOf(2), result.getItems().get(0).getCount());
    }

    @Test
    public void should_list_products_of_shopping_cart() {
        Long userId = Login();
        ShoppingCartDao shoppingCartDao = new ShoppingCartDao();
        Long appleId = inputFruit("苹果");
        Long bananaId = inputFruit("香蕉");
        shoppingCartDao.add(appleId, userId, 1);
        shoppingCartDao.add(bananaId, userId, 1);

        ShoppingCart result = shoppingCartDao.queryByUserId(userId);

        assertEquals(userId, result.getUserId());
        assertEquals(2, result.getItems().size());
        assertEquals(Integer.valueOf(1), result.getItems().get(0).getCount());
        assertEquals(Integer.valueOf(1), result.getItems().get(1).getCount());
        assertNotNull(result.getItems().get(0).getCreatedAt());
        assertNotNull(result.getItems().get(1).getCreatedAt());
        Product product1 = result.getItems().get(0).getProduct();
        Product product2 = result.getItems().get(1).getProduct();
        assertEquals(appleId, product1.getId());
        assertEquals(bananaId, product2.getId());
        assertEquals("苹果", product1.getName());
        assertEquals("香蕉", product2.getName());
        assertEquals("Food", product1.getCategory());
        assertEquals("Food", product2.getCategory());
        assertEquals(Double.valueOf(10), product1.getPrice());
        assertEquals(Double.valueOf(10), product2.getPrice());
        assertEquals(Integer.valueOf(1000), product1.getStorage());
        assertEquals(Integer.valueOf(1000), product2.getStorage());
    }

    @Test
    public void should_change_count_of_items_in_shopping_cart() {
        Long userId = Login();
        Long productId = inputFruit("苹果");
        ShoppingCartDao shoppingCartDao = new ShoppingCartDao();
        shoppingCartDao.add(productId, userId, 2);
        ShoppingCartItem itemToChange = shoppingCartDao.queryByUserId(userId).getItems().get(0);

        shoppingCartDao.changeCount(itemToChange.getId(), 8);

        ShoppingCartItem result = shoppingCartDao.queryByUserId(userId).getItems().get(0);
        assertEquals(Integer.valueOf(8), result.getCount());
    }

    @Test
    public void should_remove_item_from_shopping_cart() {
        Long userId = Login();
        Long productId = inputFruit("苹果");
        ShoppingCartDao shoppingCartDao = new ShoppingCartDao();
        shoppingCartDao.add(productId, userId, 2);
        ShoppingCartItem itemToDelete = shoppingCartDao.queryByUserId(userId).getItems().get(0);

        shoppingCartDao.deleteItem(itemToDelete.getId());

        List<ShoppingCartItem> result = shoppingCartDao.queryByUserId(userId).getItems();
        assertEquals(0, result.size());
    }

    private Long Login() {
        UserDao userDao = new UserDao();
        return userDao.registerNewUser(new User(
                "Ming",
                "123",
                "ming@sohu.com",
                "11111111111", true));
    }

    private Long inputFruit(String name) {
        ProductDao productDao = new ProductDao();
        return productDao.addNew(new Product("Food", name, 10, 1000));
    }
}
