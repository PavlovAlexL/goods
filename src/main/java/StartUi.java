import Exceptions.WrongInputException;
import Input.ConsoleInput;
import Input.Input;
import Service.DBService;

public class StartUi {

    private DBService dbService;
    private Input consoleInput;
    private Goods goods;
    private boolean stopProgram = false;

    public StartUi(DBService dbService, ConsoleInput consoleInput, Goods goods) {
        this.dbService = dbService;
        this.consoleInput = consoleInput;
        this.goods = goods;
    }

    public static void main(String[] args) throws Exception {
        try(DBService dbService = new DBService()){
            ConsoleInput consoleInput = new ConsoleInput();
            Goods goods = new Goods(dbService.getConnection());
            new StartUi(dbService, consoleInput, goods).init();
        }
    }

    protected void init(){
        String command;
        do{
            try {
                command = consoleInput.ask("Введите команду:");
                if(command.equals("repeat")){
                    continue;
                }
                if(command.equals("exit")){
                    stop();
                }
                goods.execute(command);
            } catch (WrongInputException e){
                System.out.println(e.getMessage());
            }
        } while (!stopProgram);
    }

    public void stop(){
        dbService.cleanUp();
        this.stopProgram = true;
    }

}
