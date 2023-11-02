package com.example.Calculator_Project.model.form;

import com.example.Calculator_Project.model.constant.AppUserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class SearchRequest {


    private  String email;


    private  String toDate;


    private String fromDate;

    public boolean isBlank(){
        return email.isBlank() || email==null || toDate.isBlank() || toDate==null || fromDate.isBlank() || fromDate==null;
    }

    public boolean isDateEmpty(){
        return toDate.isEmpty() || toDate.isEmpty();
    }


}
