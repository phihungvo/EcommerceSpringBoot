package com.example.Ecommerce_Project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer sellerId;

    @NotNull(message = "Please enter the first name")
    @Pattern(regexp = "[A-Za-z\\s]+", message = "First name should contain alphabets only")
    private String firstName;

    @NotNull(message = "Please enter the last name")
    @Pattern(regexp = "[A-Za-z\\s]+", message= "Last name should contain alphabets only")
    private String lastName;

    @Pattern(regexp="[A-Za-z0-9!@#$%^&*_]{8,15}", message="Please Enter a valid Password")
    private String password;

    @NotNull(message = "Please enter your mobile number")
    @Pattern(regexp = "[6789]{1}[0-9]{9}", message="Enter a valid Mobile Number")
    private String mobile;

    @Email
    @Column(unique = true)
    private String emailId;

    @OneToMany
    @JsonIgnore
    private List<Product> product;
}




























