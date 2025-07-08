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
public class Error {

    private Timestamp timestamp;
    private int code;
    private String detail;
}
