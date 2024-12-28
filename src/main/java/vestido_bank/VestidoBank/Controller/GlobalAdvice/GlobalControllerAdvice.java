package vestido_bank.VestidoBank.Controller.GlobalAdvice;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import vestido_bank.VestidoBank.Exceptions.ClientDuplicateException;
import vestido_bank.VestidoBank.Exceptions.ClientNotFoundException;
import vestido_bank.VestidoBank.Exceptions.ConnectionFailedException;
import vestido_bank.VestidoBank.Exceptions.ContaCorrentNotFoundException;
import vestido_bank.VestidoBank.Exceptions.ContaPoupancaNotFoundException;
import vestido_bank.VestidoBank.Exceptions.DepositInvalid;
import vestido_bank.VestidoBank.Exceptions.EmailDuplicateException;
import vestido_bank.VestidoBank.Exceptions.InvalidTransaction;
import vestido_bank.VestidoBank.Exceptions.NameOrEmailDuplicateException;
import vestido_bank.VestidoBank.Exceptions.SakeInvalid;

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

  @ExceptionHandler({ClientNotFoundException.class})
  public ResponseEntity<String> handleNotFoundClient(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ex.getMessage());
  }

  @ExceptionHandler({ContaCorrentNotFoundException.class})
  public ResponseEntity<String> handleContaNotFound(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ex.getMessage());
  }

  @ExceptionHandler({DepositInvalid.class})
  public ResponseEntity<String> handleDepositInvalid(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
        .body(ex.getMessage());
  }

  @ExceptionHandler({SakeInvalid.class})
  public ResponseEntity<String> handleSakeInvalid(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ex.getMessage());
  }

  @ExceptionHandler({ContaPoupancaNotFoundException.class})
  public ResponseEntity<String> handleNotFoundCountaPoupanca(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ex.getMessage());

  }

  @ExceptionHandler({ConnectionFailedException.class})
  public ResponseEntity<String> handleConnectionFailed(ResourceAccessException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ex.getMessage());
  }

  @ExceptionHandler({InvalidTransaction.class})
  public ResponseEntity<String> handleInvalidTransaction(RuntimeException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ex.getMessage());
  }

}
