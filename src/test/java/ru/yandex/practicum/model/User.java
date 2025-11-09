package ru.yandex.practicum.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.qameta.allure.Step;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class User {

    private String email;
    private String password;
    private String name;


    public String getEmail() {
        return email;
    }


    @Step
    public void setEmail(String email) {
        this.email = email;
    }

    @Step
    public String getPassword() {
        return password;
    }


    @Step
    public void setPassword(String password) {
        this.password = password;
    }

    @Step
    public String getName() {
        return name;
    }


    @Step
    public void setName(String userName) {
        this.name = userName;
    }


}
