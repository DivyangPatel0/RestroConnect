package com.Divyang.restroconnect;

public class Menu {
    private String dish_name;
    private int dish_prize;

    public Menu() {}
    public String getDish_name() {
        return dish_name;
    }

    public Menu(String dish_name, int dish_prize) {
        this.dish_name = dish_name;
        this.dish_prize = dish_prize;
    }

    public int getDish_prize() {
        return dish_prize;
    }
}
