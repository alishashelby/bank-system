package com.businesslogic.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegisterRequest {
    @NotBlank(message = "Login field must be filled")
    private String login;

    @NotBlank(message = "Name field must be filled")
    private String name;

    @Min(value = 14, message = "Age field must be at least 14")
    private int age;

    @NotNull(message = "Gender field must be filled")
    private String gender;

    @NotNull(message = "Hair color field must be filled")
    private String hairColor;

    public String getLogin() { return this.login; }
    public String getName() { return this.name; }
    public int getAge() { return this.age; }
    public String getGender() { return this.gender; }
    public String getHairColor() { return this.hairColor; }

    public void setLogin(String login) { this.login = login; }
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setGender(String gender) { this.gender = gender; }
    public void setHairColor(String hairColor) { this.hairColor = hairColor; }
}