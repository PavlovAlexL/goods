package Input;

import Exceptions.WrongInputException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Реализация ввода чеерз консоль
 */
public class ConsoleInput implements Input{

    private Scanner scanner = new Scanner(System.in);

    /**
     * Метод запроса на ввод.
     * @param question Вводимые данные.
     * @return Валидированная комманда.
     */
    public String ask(String question) {

        System.out.println(question);
        String answer = scanner.nextLine();

        if(!validate(answer)){
            throw new WrongInputException("ERROR");
        }
        return answer;
    }

    /**
     * Метод валлидации вводимых данных.
     * @param input комманда.
     * @return результат.
     */
    public Boolean validate(String input){

        if(input.equals("exit")){
            return true;
        }

        String[] command = null;
        if(input.matches("\\s")) {
            command = input.split("\\s");
        } else {
            return false;
        }

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

    /**
     * Валидация даты.
     * @param value Дата в формате String.
     * @return Результат.
     */
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
