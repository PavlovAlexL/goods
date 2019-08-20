package Input;

/**
 * Интерфейс ввода данных.
 */
public interface Input {
    /**
     * Приглашение на ввод комманды.
     * @param question Текст приглашения.
     * @return Комманда.
     */
    String ask(String question);

}
