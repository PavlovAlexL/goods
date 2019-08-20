package Exceptions;

/**
 * Ошибка ввода.
 */
public class WrongInputException extends RuntimeException {

    /**
     * Если допущена ошибка ввода, бросается это исключение.
     * @param message
     */
    public WrongInputException(String message) {
        super(message);
    }
}