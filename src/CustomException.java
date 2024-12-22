public class CustomException extends RuntimeException {

  // Hata mesajını özelleştiren bir oluşturucu.
  public CustomException(String message) {
    super(message);
  }

}
