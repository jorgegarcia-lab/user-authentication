package com.bci.exercise.user_authentication.model;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "phone")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int phoneId;

    @Column
    private long number;

    @Column
    private int cityCode;

    @Column
    private String countryCode;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
