import Exceptions.WrongInputException;
import Input.ConsoleInput;

public class StartUi {

    private DBService dbService;
    private ConsoleInput consoleInput;
    private boolean stopProgram = false;

    public StartUi(DBService dbService, ConsoleInput consoleInput) {
        this.dbService = dbService;
        this.consoleInput = consoleInput;
    }

    public static void main(String[] args) throws Exception {
        try(DBService dbService = new DBService();){
            new StartUi(dbService, new ConsoleInput()).init();
        }
    }

    protected void init(){
        String command;
        do{
            try {
                command = consoleInput.ask("Введите команду:");


            } catch (WrongInputException e){
                System.out.println(e.getMessage());
            }
        } while (!stopProgram);
    }

    public void stop(){
        this.stopProgram = true;
    }

}
