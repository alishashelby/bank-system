package com.businesslogic.dto;

import java.util.UUID;

public class UserDTO {
    private UUID id;
    private String login;
    private String name;
    private int age;
    private String gender;
    private String hairColor;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getHairColor() { return hairColor; }
    public void setHairColor(String hairColor) { this.hairColor = hairColor; }
}