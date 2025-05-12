package com.adk.kost.handler;

import com.adk.kost.exception.ValidateException;
import com.adk.kost.response.Response;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class RepsonseHandlerException {

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<String> handleDatabaseException(DataAccessException ex){
        return ResponseEntity.status(500).body("Database connection error : " + ex.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> handleSqlException(SQLException ex){
        return ResponseEntity.status(500).body("SQL error : " + ex.getMessage());
    }

    @ExceptionHandler(value = {ValidateException.class})
    public ResponseEntity<Response> handleValidateException(ValidateException ex){
        Response response = new Response();
        response.setMessage(ex.getMessage());

        return ResponseEntity.status(400).body(response);
    }


//    @ExceptionHandler(value = {Exception.class})
//    public ResponseEntity<String> handleException(Exception ex){
//        return ResponseEntity.status(404).body("Not Found " + ex.getMessage());
//    }

}
