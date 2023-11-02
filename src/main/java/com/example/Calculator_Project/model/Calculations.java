package com.example.Calculator_Project.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Calculations {
    @SequenceGenerator(
            name = "calculator_sequence",
            sequenceName = "calculator_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "calculator_sequence"
    )

    private Long id;
    String input;
    double output;
    private String formattedDateTime;
    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "appUser"
    )
    private AppUser appUser;

    public Calculations(String input, double output, String formattedDateTime, AppUser appUser) {
        this.input = input;
        this.output = output;
        this.formattedDateTime = formattedDateTime;
        this.appUser = appUser;
    }
}
