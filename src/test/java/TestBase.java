import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mybatis.practice.MybatisFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

public class TestBase {
    @Before
    public void setUp() throws Exception {
        runScript(MybatisFactory.getFactory().getConfiguration().getEnvironment().getDataSource(), "sql/create.sql");
    }

    private static void runScript(DataSource ds, String resource) throws IOException, SQLException {
        try (Connection connection = ds.getConnection()) {
            ScriptRunner runner = new ScriptRunner(connection);
            runner.setAutoCommit(true);
            runner.setFullLineDelimiter(false);
            runner.setDelimiter(";");
            runner.setSendFullScript(false);
            runner.setStopOnError(false);
            runner.setLogWriter(null);
            runScript(runner, resource);
        }
    }

    private static void runScript(ScriptRunner runner, String resource) throws IOException, SQLException {
        try (Reader reader = Resources.getResourceAsReader(resource)) {
            runner.runScript(reader);
        }
    }
}
