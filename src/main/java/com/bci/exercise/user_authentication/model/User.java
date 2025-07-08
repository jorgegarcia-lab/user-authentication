package com.bci.exercise.user_authentication.model;

import com.bci.exercise.user_authentication.constants.Constants;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(unique = true)
    private UUID id;

    @Column
    private String name;

    @Column
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE, message = Constants.EMAIL_ERROR_MESSAGE)
    private String email;

    @Column
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Phone> phones;

    @Column
    private String createdAt;

    @Column
    @JsonProperty("isActive")
    private boolean isActive;

    @Column
    private String lastLogin;

    @PrePersist
    private void persistCreatedAt() throws ParseException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
        String formattedDate = sdf.format(date);
        setCreatedAt(formattedDate);
    }
}
