import org.junit.Test;
import org.mybatis.practice.dao.UserDao;
import org.mybatis.practice.entity.User;

import static org.junit.Assert.*;

public class UserTest extends TestBase {
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
}
