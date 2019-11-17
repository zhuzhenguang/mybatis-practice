import org.junit.Test;
import org.mybatis.practice.dao.OrderDao;
import org.mybatis.practice.dao.ProductDao;
import org.mybatis.practice.dao.ShoppingCartDao;
import org.mybatis.practice.dao.UserDao;
import org.mybatis.practice.entity.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderTest extends TestBase {
    @Test
    public void should_create_order_from_shopping_cart() {
        Long userId = Login();
        Long appleId = inputProduct("苹果");
        Order order = new Order(userId);
        List<OrderItem> orderItems = Collections.singletonList(new OrderItem(appleId, 2));

        OrderDao orderDao = new OrderDao();
        orderDao.createOrder(order, orderItems);

        assertNotNull(order.getId());
        Order result = orderDao.queryById(order.getId());
        assertEquals(OrderStatus.Unpaid, result.getStatus());
        assertNotNull(result.getCreatedAt());
        assertEquals(Double.valueOf(10), result.totalPrice());
        User user = result.getUser();
        assertEquals("Ming", user.getName());
        assertEquals(1, result.getItems().size());
        OrderItem orderItem = result.getItems().get(0);
        assertEquals(Integer.valueOf(2), orderItem.getCount());
        assertEquals("苹果", orderItem.getProduct().getName());
        assertEquals(appleId, orderItem.getProduct().getId());
        assertEquals(Double.valueOf(10), orderItem.getProduct().getPrice());
    }

    @Test
    public void should_calculate_total_price() {
        Long userId = Login();
        Long appleId = inputProduct("苹果");
        Long bananaId = inputProduct("苹果");
        Order order = new Order(userId);
        List<OrderItem> orderItems = Arrays.asList(new OrderItem(appleId, 2), new OrderItem(bananaId, 1));

        OrderDao orderDao = new OrderDao();
        orderDao.createOrder(order, orderItems);

        Order result = orderDao.queryById(order.getId());
        assertEquals(Double.valueOf(30), result.totalPrice());
    }

    private Long Login() {
        UserDao userDao = new UserDao();
        return userDao.registerNewUser(new User(
                "Ming",
                "123",
                "ming@sohu.com",
                "11111111111", true));
    }

    private Long inputProduct(String name) {
        ProductDao productDao = new ProductDao();
        return productDao.addNew(new Product("Food", name, 10, 1000));
    }

    private void addShoppingCart(Long productId, Long userId, int count) {
        ShoppingCartDao shoppingCartDao = new ShoppingCartDao();
        shoppingCartDao.add(productId, userId, count);
    }
}
