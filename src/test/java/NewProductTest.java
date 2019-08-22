import Input.Input;
import Input.StubInput;
import Input.ValidateInput;
import Service.DBService;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class NewProductTest {

    private static DBService dbService;
    private static Input input;
    private static Goods goods;
    private static StartUi startUi;

    @Test
    public void whenAddNewProductThenOutputIsOk() {
        dbService = new DBService("jdbc:h2:mem:test", "user", "1234", "schema.sql");
        input = new StubInput(new ValidateInput());
        goods = new Goods(dbService.getConnection());
        startUi = new StartUi(dbService, input, goods);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        startUi.initTest("NEWPRODUCT iphone");
        assertThat(new String(baos.toByteArray()), is("OK"));
    }

    @Test
    public void whenAddExistingProductThenOutputIsError() {
        dbService = new DBService("jdbc:h2:mem:test2", "user", "1234", "schema.sql");
        input = new StubInput(new ValidateInput());
        goods = new Goods(dbService.getConnection());
        startUi = new StartUi(dbService, input, goods);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        startUi.initTest("NEWPRODUCT iphone");
        System.setOut(new PrintStream(baos));
        startUi.initTest("NEWPRODUCT iphone");
        assertThat(new String(baos.toByteArray()), is("ERROR"));
    }
}
