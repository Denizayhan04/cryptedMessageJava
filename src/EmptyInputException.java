public class EmptyInputException extends CustomException {

    // Boş girişler için  hata mesajı
    public EmptyInputException() {
        super("Input cannot be empty");
    }

    // Özelleştirilmiş hata mesajı almak için bir constructor
    public EmptyInputException(String message) {
        super(message);
    }
}
