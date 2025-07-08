package com.bci.exercise.user_authentication.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationException extends Exception{

    private int errorCode;
    private String errorMessage;
    private Timestamp errorTimestamp;
}
