import org.junit.Test;
import org.mybatis.practice.dao.UserDao;
import org.mybatis.practice.entity.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserTest extends TestBase {
    @Test
    public void should_register_new_user() {
        UserDao userDao = new UserDao();
        User user = new User("Ming", "123", "ming@sina.com", "11111111111");

        userDao.registerNewUser(user);

        User result = userDao.queryUser("Ming");
        assertEquals("Ming", result.getName());
        assertEquals("123", result.getPassword());
        assertEquals("ming@sina.com", result.getEmail());
        assertEquals("11111111111", result.getPhone());
        assertEquals(false, result.getLogin());
        assertNotNull(result.getCreatedAt());
    }
}
