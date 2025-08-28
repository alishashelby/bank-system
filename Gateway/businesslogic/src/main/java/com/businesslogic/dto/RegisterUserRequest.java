package com.businesslogic.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegisterUserRequest {
    @NotBlank(message = "Login field must be filled")
    private String login;

    @NotBlank(message = "Password filed must be filled")
    private String password;

    @NotBlank(message = "Name field must be filled")
    private String name;

    @Min(value = 14, message = "Age field must be at least 14")
    private int age;

    @NotNull(message = "Gender field must be filled")
    private String gender;

    @NotNull(message = "Hair color field must be filled")
    private String hairColor;

    public String getLogin() { return login; }
    public void setLogin(String username) { this.login = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getHairColor() { return hairColor; }
    public void setHairColor(String hairColor) { this.hairColor = hairColor; }
}
