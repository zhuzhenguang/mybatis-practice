import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mybatis.practice.dao.BussinessException;
import org.mybatis.practice.dao.UserDao;
import org.mybatis.practice.entity.User;

import java.util.List;

import static org.junit.Assert.*;

public class UserTest extends TestBase {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void should_register_new_user() {
        UserDao userDao = new UserDao();
        User user = new User(
                "Ming",
                "123",
                "ming@sina.com",
                "11111111111");

        Long id = userDao.registerNewUser(user);

        User result = userDao.queryUser("Ming");
        assertTrue(id > 0);
        assertEquals("Ming", result.getName());
        assertEquals("123", result.getPassword());
        assertEquals("ming@sina.com", result.getEmail());
        assertEquals("11111111111", result.getPhone());
        assertFalse(result.getLogin());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    public void should_error_when_register_by_existing_name() {
        UserDao userDao = new UserDao();
        userDao.registerNewUser(new User(
                "Ming",
                "123",
                "ming@sohu.com",
                "11111111111"));
        User newUser = new User(
                "Ming",
                "456",
                "ming@sohu.com",
                "2222222222");

        exceptionRule.expect(BussinessException.class);
        exceptionRule.expectMessage("该用户已注册");

        userDao.registerNewUser(newUser);
    }

    @Test
    public void user_should_login() {
        UserDao userDao = new UserDao();
        Long userId = userDao.registerNewUser(new User(
                "Ming",
                "123",
                "ming@sohu.com",
                "11111111111"));

        userDao.login(userId);

        User result = userDao.queryUser("Ming");
        assertTrue(result.getLogin());
    }

    @Test
    public void user_should_logout() {
        UserDao userDao = new UserDao();
        Long userId = userDao.registerNewUser(new User(
                "Ming",
                "123",
                "ming@sohu.com",
                "11111111111",
                true));

        userDao.logout(userId);

        User result = userDao.queryUser("Ming");
        assertFalse(result.getLogin());
    }

    @Test
    public void should_delete_user() {
        UserDao userDao = new UserDao();
        Long userId = userDao.registerNewUser(new User(
                "Ming",
                "123",
                "ming@sohu.com",
                "11111111111"));

        userDao.delete(userId);

        User result = userDao.queryUser("Ming");
        assertNull(result);
    }

    @Test
    public void should_list_all_users() {
        UserDao userDao = new UserDao();
        userDao.registerNewUser(new User(
                "Ming",
                "123",
                "ming@sohu.com",
                "11111111111", false));
        userDao.registerNewUser(new User(
                "Guang",
                "456",
                "guang@sohu.com",
                "22222222222", true));

        List<User> results = userDao.listAll();

        assertEquals(2, results.size());
        assertEquals("Ming", results.get(0).getName());
        assertEquals("123", results.get(0).getPassword());
        assertEquals("ming@sohu.com", results.get(0).getEmail());
        assertEquals("11111111111", results.get(0).getPhone());
        assertFalse(results.get(0).getLogin());
        assertNotNull(results.get(0).getCreatedAt());
        assertEquals("Guang", results.get(1).getName());
        assertEquals("456", results.get(1).getPassword());
        assertEquals("guang@sohu.com", results.get(1).getEmail());
        assertEquals("22222222222", results.get(1).getPhone());
        assertTrue(results.get(1).getLogin());
        assertNotNull(results.get(1).getCreatedAt());
    }
}
