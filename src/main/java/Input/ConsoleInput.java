package Input;

import Exceptions.WrongInputException;
import java.util.Scanner;

public class ConsoleInput implements Input{

    private Scanner scanner = new Scanner(System.in);

    public String ask(String question) {
        System.out.println(question);
        String answer = scanner.nextLine();
        if(!answer.contains(" ")){
            throw new WrongInputException("Некорректные данные1");
        }
        String[] command = answer.split("\\s");
        if(command.length < 2||command.length > 5||!ValidCommands.contains(command[0])){
            throw new WrongInputException("Некорректные данные2");
        }
        System.out.println("Command " + command);
        return answer;
    }

}
