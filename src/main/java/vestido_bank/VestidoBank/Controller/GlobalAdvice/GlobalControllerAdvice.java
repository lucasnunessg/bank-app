package vestido_bank.VestidoBank.Controller.GlobalAdvice;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vestido_bank.VestidoBank.Exceptions.ClientDuplicateException;
import vestido_bank.VestidoBank.Exceptions.EmailDuplicateException;
import vestido_bank.VestidoBank.Exceptions.NameOrEmailDuplicateException;

@RestControllerAdvice
public class GlobalControllerAdvice {


  @ExceptionHandler()
  public ResponseEntity<String> handleNotFound(NotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ex.getMessage());
  }

  @ExceptionHandler({EmailDuplicateException.class})
  public ResponseEntity<String> handleEmailDuplicate(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ex.getMessage());
  }

  @ExceptionHandler({ClientDuplicateException.class})
  public ResponseEntity<String> handleClientDuplicate(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ex.getMessage());
  }

  @ExceptionHandler({NameOrEmailDuplicateException.class})
  public ResponseEntity<String> handleEOrNDuplicate(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ex.getMessage());
  }
}
