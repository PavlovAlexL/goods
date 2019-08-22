package Input;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidateInput {

    /**
     * Метод валлидации вводимых данных.
     *
     * @param input комманда.
     * @return результат.
     */
    public Boolean validate(String input) {

        if (input.equals("exit")) {
            return true;
        }

        String[] command = null;
        if (input.contains(" ")) {
            command = input.split("\\s");
        } else {
            return false;
        }


        switch (command[0]) {

            case "NEWPRODUCT":
                if (
                        command.length == 2 &
                                command[1].matches("^[a-zA-Z][a-zA-Z0-9-_]{1,50}$")) {
                    return true;
                }
                break;

            case "PURCHASE":
                if (
                        command.length == 5 &
                                command[1].matches("^[a-zA-Z0-9][a-zA-Z0-9-_]{1,50}$") &
                                command[2].matches("[1-9]") &
                                command[3].matches("\\d{1,4}(?:[.,]\\d{3})*(?:[.,]\\d{2})?") &
                                validateDate(command[4])
                ) {
                    return true;
                }
                break;

            case "DEMAND":
                if (
                        command.length == 5 &
                                command[1].matches("^[a-zA-Z0-9][a-zA-Z0-9-_]{1,50}$") &
                                command[2].matches("[1-9]") &
                                command[3].matches("\\d{1,4}(?:[.,]\\d{3})*(?:[.,]\\d{2})?") &
                                validateDate(command[4])
                ) {
                    return true;
                }
                break;

            case "SALESREPORT":
                if (
                        command.length == 3 &
                                command[1].matches("^[a-zA-Z0-9][a-zA-Z0-9-_]{1,50}$") &
                                validateDate(command[2])
                ) {
                    return true;
                }
                break;

            default:
                return false;
        }
        return false;
    }

    /**
     * Валидация даты.
     *
     * @param value Дата в формате String.
     * @return Результат.
     */
    private boolean validateDate(String value) {
        String datePattern = "dd.MM.yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
        formatter.setLenient(false);
        try {
            Date date = formatter.parse(value);
            return !new Date().before(date);
        } catch (ParseException e) {
            return false;
        }
    }

}
