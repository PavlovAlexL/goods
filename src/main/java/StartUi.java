import Exceptions.WrongInputException;
import Input.ConsoleInput;
import Input.Input;
import Input.ValidateInput;
import Service.DBService;

import java.sql.SQLException;

/**
 * Старт приложения.
 */
public class StartUi {

    private DBService dbService;
    private Input consoleInput;
    private Goods goods;
    private boolean stopProgram = false;

    /**
     * Старт обслуживающих модулей.
     * @param dbService Объект для обслуживания БД.
     * @param consoleInput Выбранный метод ввода.
     * @param goods Сервис, занимающийся обслуживанием бизнеспроцессов.
     */
    public StartUi(DBService dbService, Input consoleInput, Goods goods) {
        this.dbService = dbService;
        this.consoleInput = consoleInput;
        this.goods = goods;
    }

    /**
     *  Запуск приложения.
     */
    public static void main(String[] args){
        try(DBService dbService = new DBService()){
            ConsoleInput consoleInput = new ConsoleInput(new ValidateInput());
            Goods goods = new Goods(dbService.getConnection());
            new StartUi(dbService, consoleInput, goods).init();
        }
    }

    /**
     * Инициализация.
     */
    protected void init(){
        do{
            try {
                String command = consoleInput.ask("Input command:");
                if(command.equals("exit")){
                    stop();
                    return;
                }
                goods.execute(command);
            } catch (WrongInputException e){
                System.out.print(e.getMessage());
            }
        } while (!stopProgram);
    }

    /**
     * Инициализация теста.
     *
     * @param input
     */
    protected void initTest(String input) {
        try {
            String command = consoleInput.ask(input);
            goods.execute(command);
        } catch (WrongInputException e) {
            System.out.print(e.getMessage());
        }
    }

    /**
     * Завершение работы.
     */
    public void stop(){
        try {
            dbService.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException("Program stopping error.", e);
        }
        this.stopProgram = true;
    }

}
