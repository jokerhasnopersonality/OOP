package io.github.jokerhasnopersonality;

import java.util.ArrayList;
import java.util.List;

public class Pizzaiolo implements Runnable {
    private int cookingTime;
    private int maxCountAtOnce;
    private List<Pizza> pizzaList;

    public Pizzaiolo(int cookingTime, int maxCountAtOnce) throws IllegalArgumentException {
        if (cookingTime <= 0 || maxCountAtOnce <= 0) {
            throw new IllegalArgumentException();
        }
        this.cookingTime = cookingTime;
        this.maxCountAtOnce = maxCountAtOnce;
        pizzaList = new ArrayList<>();
    }

    public void takeOrders() {
    }

    public void makePizza() {
    }

    public void passToStorage() {
    }

    @Override
    public void run() {

    }
}