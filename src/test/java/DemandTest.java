import Input.Input;
import Input.StubInput;
import Input.ValidateInput;
import Service.DBService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DemandTest {

    static DBService dbService;
    static Input input;
    static Goods goods;
    static StartUi startUi;
    static PrintStream stdout = System.out;

    /**
     * Подготовка тестовой среды.
     */
    @BeforeClass
    public static void loadClass() {
        dbService = new DBService("jdbc:h2:mem:test", "user", "1234", "schema.sql");
        input = new StubInput(new ValidateInput());
        goods = new Goods(dbService.getConnection());
        startUi = new StartUi(dbService, input, goods);
    }

    /**
     * Отключение тестовой среды.
     */
    @AfterClass
    public static void backClass() {
        //dbService.dropAllData();
        startUi.stop();
        System.out.flush();
    }

    @After
    public void backTest() {
        System.setOut(stdout);
        //dbService.dropAllData();
    }

    @Test
    public void whenAddNewTwoOrderAndDemandToMuchAmountOrderThenOutputIsError() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        startUi.initTest("NEWPRODUCT iphone");
        startUi.initTest("PURCHASE iphone 1 1000 01.01.2017");
        startUi.initTest("PURCHASE iphone 2 2000 01.02.2017");
        System.setOut(new PrintStream(baos));
        startUi.initTest("DEMAND iphone 10 5000 01.03.2017");
        assertThat(new String(baos.toByteArray()), is("ERROR"));
    }

    @Test
    public void whenAddNewTwoOrderAndDemandThenOutputIsOk() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        startUi.initTest("NEWPRODUCT iphone");
        startUi.initTest("PURCHASE iphone 1 1000 01.01.2017");
        startUi.initTest("PURCHASE iphone 2 2000 01.02.2017");
        System.setOut(new PrintStream(baos));
        startUi.initTest("DEMAND iphone 2 5000 01.03.2017");
        assertThat(new String(baos.toByteArray()), is("OK"));
    }
}
