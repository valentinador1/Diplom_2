package ru.yandex.practicum.model;


import io.qameta.allure.Step;

import java.util.List;

public class Order {

    private List<String> ingredients;

    public List<String> getIngredients() {
        return ingredients;
    }


    @Step
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

}
