import Input.Input;
import Input.StubInput;
import Input.ValidateInput;
import Service.DBService;
import org.junit.After;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SalesReportTest {

    private DBService dbService;
    private Input input;
    private Goods goods;
    private StartUi startUi;

    @After
    public void back(){
        dbService.close();
    }

    @Test
    public void whenAddNewFiveDifferentOrderAndDemandAndGetSalesReportThenOutputIsOk() {
        dbService = new DBService("jdbc:h2:mem:test2", "user", "1234", "schema.sql");
        input = new StubInput(new ValidateInput());
        goods = new Goods(dbService.getConnection());
        startUi = new StartUi(dbService, input, goods);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        startUi.initTest("NEWPRODUCT iphone");
        startUi.initTest("NEWPRODUCT iphone2");
        startUi.initTest("PURCHASE iphone 1 1000 01.01.2017");
        startUi.initTest("PURCHASE iphone2 1 1500 02.01.2017");
        startUi.initTest("PURCHASE iphone 2 1000 01.02.2017");
        startUi.initTest("PURCHASE iphone2 2 2000 02.02.2017");
        startUi.initTest("DEMAND iphone 2 7000 01.03.2017");
        System.setOut(new PrintStream(baos));
        startUi.initTest("SALESREPORT iphone 02.03.2017");
        assertThat(new String(baos.toByteArray()), is("12000"));
    }
}
