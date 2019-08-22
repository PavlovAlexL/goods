package Input;

import Exceptions.WrongInputException;

/**
 * Класс - заглушка, для теста.
 */
public class StubInput implements Input {

    private ValidateInput validateInput;

    public StubInput(ValidateInput validate) {
        this.validateInput = validate;
    }

    /**
     * Метод запроса на ввод для теста.
     *
     * @param question Вводимые данные.
     * @return Валидированная комманда.
     */

    public String ask(String question) {

        if (!validateInput.validate(question)) {
            throw new WrongInputException("ERROR");
        }
        return question;
    }

}
