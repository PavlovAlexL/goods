import Exceptions.WrongInputException;
import Input.ConsoleInput;
import Input.Input;
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
    public StartUi(DBService dbService, ConsoleInput consoleInput, Goods goods) {
        this.dbService = dbService;
        this.consoleInput = consoleInput;
        this.goods = goods;
    }

    /**
     *  Запуск приложения.
     */
    public static void main(String[] args){
        try(DBService dbService = new DBService()){
            ConsoleInput consoleInput = new ConsoleInput();
            Goods goods = new Goods(dbService.getConnection());
            new StartUi(dbService, consoleInput, goods).init();
        }
    }

    /**
     * Инициализация.
     */
    protected void init(){
        String command;
        do{
            try {
                command = consoleInput.ask("Введите команду:");
                if(command.equals("exit")){
                    stop();
                    return;
                }
                goods.execute(command);
            } catch (WrongInputException e){
                System.out.println(e.getMessage());
            }
        } while (!stopProgram);
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
