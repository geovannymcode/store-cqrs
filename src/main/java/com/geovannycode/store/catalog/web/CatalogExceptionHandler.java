package com.geovannycode.store.catalog.web;

import com.geovannycode.store.catalog.exception.InvalidVoteException;
import com.geovannycode.store.catalog.exception.ProductException;
import com.geovannycode.store.catalog.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

class CatalogExceptionHandler {
/**

 Handles ProductNotFoundException.
 @return 404 Not Found with error details
 */
@ExceptionHandler(ProductNotFoundException.class)
public ResponseEntity<Object> handleProductNotFoundException(
        ProductNotFoundException ex, WebRequest request) {
    return createErrorResponse(ex, HttpStatus.NOT_FOUND);
}

/**

 Handles InvalidVoteException.
 @return 400 Bad Request with error details
 */
@ExceptionHandler(InvalidVoteException.class)
public ResponseEntity<Object> handleInvalidVoteException(
        InvalidVoteException ex, WebRequest request) {
    return createErrorResponse(ex, HttpStatus.BAD_REQUEST);
}

/**

 Handles all other ProductExceptions.
 @return 500 Internal Server Error with error details
 */
@ExceptionHandler(ProductException.class)
public ResponseEntity<Object> handleProductException(
        ProductException ex, WebRequest request) {
    return createErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
}

/**

 Creates a standardized error response with timestamp and message.
 */
private ResponseEntity<Object> createErrorResponse(Exception ex, HttpStatus status) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", status.value());
    body.put("error", status.getReasonPhrase());
    body.put("message", ex.getMessage());
    return new ResponseEntity<>(body, status);
}
}
