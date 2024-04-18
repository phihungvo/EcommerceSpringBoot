package com.example.Ecommerce_Project.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class SellerDTO {
    @NotNull(message = "Please enter your mobile number")
    @Pattern(regexp="[6789]{1}[0-9]{9}", message="Enter a valid Mobile Number")
    private String mobile;

    @Pattern(regexp="[A-Za-z0-9!@#$%^&*_]{8,15}", message="Please Enter a valid Password")
    private String password;
}
