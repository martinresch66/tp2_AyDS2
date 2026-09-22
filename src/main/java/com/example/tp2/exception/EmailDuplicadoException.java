package com.example.tp2.exception;


/*EXCEPCION PERSONALIZADA
 "el email que quieren registrar ya existe en la base de datos */

 /* Extiende de RuntimeException para que sea una excepción "unchecked":
 esto significa que no estamos obligados a declarar "throws" en
cada método que la use, ni a envolverla en try/catch obligatoriamente.
 */
public class EmailDuplicadoException extends RuntimeException {
        /*
      Constructor: recibe el mensaje de error 
      y se lo pasa al constructor de la clase padre (RuntimeException).
     
      Ese mensaje es el que luego recuperamos en el GlobalExceptionHandler
      con "ex.getMessage()", para armar la respuesta JSON del error 400.
     */
    public EmailDuplicadoException(String message) {
        super(message);
    }
}
