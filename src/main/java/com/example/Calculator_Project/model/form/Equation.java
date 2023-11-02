package com.example.Calculator_Project.model.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class Equation {
    @NotEmpty(message = "Please add input !")
    private  String equation;

}
