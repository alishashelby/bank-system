package com.businesslogic.dto;

import jakarta.validation.constraints.NotBlank;

public class RegisterAdminRequest {
    @NotBlank(message = "Login field must be filled")
    private String login;

    @NotBlank(message = "Password filed must be filled")
    private String password;

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
