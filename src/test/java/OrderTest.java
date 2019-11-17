import org.junit.Test;
import org.mybatis.practice.dao.OrderDao;
import org.mybatis.practice.dao.ProductDao;
import org.mybatis.practice.dao.ShoppingCartDao;
import org.mybatis.practice.dao.UserDao;
import org.mybatis.practice.entity.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

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
        assertEquals(Double.valueOf(20), result.totalPrice());
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
        Long bananaId = inputProduct("香蕉");
        Order order = new Order(userId);
        List<OrderItem> orderItems = Arrays.asList(new OrderItem(appleId, 2), new OrderItem(bananaId, 1));

        OrderDao orderDao = new OrderDao();
        orderDao.createOrder(order, orderItems);

        Order result = orderDao.queryById(order.getId());
        assertEquals(Double.valueOf(30), result.totalPrice());
    }

    @Test
    public void should_pay_order() {
        OrderDao orderDao = new OrderDao();
        Long userId = Login();
        Long appleId = inputProduct("苹果");
        Order order = new Order(userId);
        orderDao.createOrder(order, Collections.singletonList(new OrderItem(appleId, 2)));

        orderDao.pay(order.getId());

        Order result = orderDao.queryById(order.getId());
        assertEquals(OrderStatus.Paid, result.getStatus());
    }

    @Test
    public void should_cancel_order() {
        OrderDao orderDao = new OrderDao();
        Long userId = Login();
        Long appleId = inputProduct("苹果");
        Order order = new Order(userId);
        orderDao.createOrder(order, Collections.singletonList(new OrderItem(appleId, 2)));

        orderDao.cancel(order.getId());

        Order result = orderDao.queryById(order.getId());
        assertEquals(OrderStatus.Canceled, result.getStatus());
    }

    @Test
    public void should_cancel_order_when_not_paid_beyond_30_mins() {
        OrderDao orderDao = new OrderDao();
        Long userId = Login();
        Long appleId = inputProduct("苹果");
        Order order = new Order(userId);
        order.setCreatedAt(createDateMinsAgo(31));
        orderDao.createOrder(order, Collections.singletonList(new OrderItem(appleId, 2)));

        orderDao.checkAndCancel(order.getId());

        Order result = orderDao.queryById(order.getId());
        assertEquals(OrderStatus.Canceled, result.getStatus());
    }

    @Test
    public void should_not_cancel_order_when_not_paid_below_30_mins() {
        OrderDao orderDao = new OrderDao();
        Long userId = Login();
        Long appleId = inputProduct("苹果");
        Order order = new Order(userId);
        order.setCreatedAt(createDateMinsAgo(29));
        orderDao.createOrder(order, Collections.singletonList(new OrderItem(appleId, 2)));

        orderDao.checkAndCancel(order.getId());

        Order result = orderDao.queryById(order.getId());
        assertEquals(OrderStatus.Unpaid, result.getStatus());
    }

    @Test
    public void should_list_all_orders() {
        OrderDao orderDao = new OrderDao();
        Long userId = Login();
        Long appleId = inputProduct("苹果");
        Long bananaId = inputProduct("香蕉");
        orderDao.createOrder(new Order(userId), Collections.singletonList(new OrderItem(appleId, 2)));
        orderDao.createOrder(new Order(userId), Collections.singletonList(new OrderItem(bananaId, 3)));

        List<Order> orders = orderDao.queryByUser(userId);

        assertEquals(2, orders.size());
        Order order1 = orders.get(0);
        assertEquals(OrderStatus.Unpaid, order1.getStatus());
        assertNotNull(order1.getCreatedAt());
        assertEquals(Double.valueOf(20), order1.totalPrice());
        User user1 = order1.getUser();
        assertEquals("Ming", user1.getName());
        assertEquals(1, order1.getItems().size());
        OrderItem orderItem1 = order1.getItems().get(0);
        assertEquals(Integer.valueOf(2), orderItem1.getCount());
        assertEquals("苹果", orderItem1.getProduct().getName());
        assertEquals(appleId, orderItem1.getProduct().getId());
        assertEquals(Double.valueOf(10), orderItem1.getProduct().getPrice());
        Order order2 = orders.get(1);
        assertEquals(OrderStatus.Unpaid, order2.getStatus());
        assertNotNull(order2.getCreatedAt());
        assertEquals(Double.valueOf(30), order2.totalPrice());
        User user2 = order2.getUser();
        assertEquals("Ming", user2.getName());
        assertEquals(1, order2.getItems().size());
        OrderItem orderItem2 = order2.getItems().get(0);
        assertEquals(Integer.valueOf(3), orderItem2.getCount());
        assertEquals("香蕉", orderItem2.getProduct().getName());
        assertEquals(bananaId, orderItem2.getProduct().getId());
        assertEquals(Double.valueOf(10), orderItem2.getProduct().getPrice());
    }

    private Date createDateMinsAgo(int minutes) {
        return Date.from(LocalDateTime.now().minusMinutes(minutes).atZone(ZoneId.systemDefault()).toInstant());
    }

    private Long Login() {
        UserDao userDao = new UserDao();
        User ming = new User(
                "Ming",
                "123",
                "ming@sohu.com",
                "11111111111", true);
        userDao.registerNewUser(ming);
        return ming.getId();
    }

    private Long inputProduct(String name) {
        ProductDao productDao = new ProductDao();
        Product food = new Product("Food", name, 10, 1000);
        productDao.addNew(food);
        return food.getId();
    }
}
