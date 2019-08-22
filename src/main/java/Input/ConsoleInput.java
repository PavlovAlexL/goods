package Input;

import Exceptions.WrongInputException;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Реализация ввода чеерз консоль
 */
public class ConsoleInput implements Input{

    private Scanner scanner = new Scanner(System.in);
    private ValidateInput validateInput;

    public ConsoleInput(ValidateInput validate) {
        this.validateInput = validate;
    }

    /**
     * Метод запроса на ввод.
     * @param question Вводимые данные.
     * @return Валидированная комманда.
     */
    public String ask(String question) {
        System.out.println("\n" + question);
        String answer = scanner.nextLine();

        if (!validateInput.validate(answer)) {
            throw new WrongInputException("ERROR");
        }

        return answer;
    }

    public String ask(String question, InputStream inputStream) {
        return null;
    }
}
