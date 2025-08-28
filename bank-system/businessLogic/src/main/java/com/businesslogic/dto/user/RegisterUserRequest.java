package com.businesslogic.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request object for creating user")
public class RegisterUserRequest {
    @Schema(description = "User login", example = "alishashelby", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Login field must be filled")
    private String login;

    @Schema(description = "User's full name", example = "Alisha Shelby", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Name field must be filled")
    private String name;

    @Schema(description = "User's age", example = "30", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(value = 14, message = "Age field must be at least 14")
    private int age;

    @Schema(description = "User's gender", example = "FEMALE", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Gender field must be filled")
    private String gender;

    @Schema(description = "User's hair color", example = "AUBURN", requiredMode = Schema.RequiredMode.REQUIRED)
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
