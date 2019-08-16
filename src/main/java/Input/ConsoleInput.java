package Input;

import Exceptions.WrongInputException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ConsoleInput implements Input{

    private Scanner scanner = new Scanner(System.in);

    public String ask(String question) {

        System.out.println(question);

        String answer = scanner.nextLine();

        if(answer.equals("help")){
            System.out.println("Список допустимых комманд:" +
                    "\n" + "NEWPRODUCT <name>" +
                    "\n" + "PURCHASE <name> <amount> <price> <date>" +
                    "\n" + "DEMAND <name> <amount> <price> <date>" +
                    "\n" + "SALESREPORT <name> <date>");
            return "repeat";
        }

        if(!validate(answer)){
            throw new WrongInputException("Wrong input" + "\n" + "Try 'help' for more information.");
        }

        return answer;
    }

    public Boolean validate(String input){

        if(!input.matches("^.*(NEWPRODUCT |PURCHASE |DEMAND |SALESREPORT ).*")){
            return false;
        }

        String[] command = input.split("\\s");

        switch (command[0]) {

            case "NEWPRODUCT":
                if(command.length == 2&command[1].matches("^[a-zA-Z][a-zA-Z0-9-_]{1,50}$")) {
                    return true;
                }
                break;

            case "PURCHASE":
                if(
                        command.length == 5&
                        command[1].matches("^[a-zA-Z0-9][a-zA-Z0-9-_]{1,50}$")&
                        command[2].matches("[0-9]+")&
                        command[3].matches("\\d{1,4}(?:[.,]\\d{3})*(?:[.,]\\d{2})?")&
                        validateDate(command[4])
                ){
                    return true;
                }
                break;

            case "DEMAND":
                if(
                        command.length == 5&
                        command[1].matches("^[a-zA-Z0-9][a-zA-Z0-9-_]{1,50}$")&
                        command[2].matches("[0-9]+")&
                        command[3].matches("\\d{1,4}(?:[.,]\\d{3})*(?:[.,]\\d{2})?")&
                        validateDate(command[4])
                ) {
                    return true;
                }
                break;

            case "SALESREPORT":
                if(
                        command.length == 3&
                        command[1].matches("^[a-zA-Z0-9][a-zA-Z0-9-_]{1,50}$")&
                        validateDate(command[2])
                ) {
                    return true;
                }
                break;

                default:return false;
        }

        return false;
    }


    private boolean validateDate(String value) {
        String datePattern = "dd.MM.yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        try {
            Date date = formatter.parse(value);
            System.out.println(date);
            if(new Date().before(date)){
                return false;
            }
            return true;
        }catch (ParseException e) {
            return false;
        }
    }
}
