package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Chicken {

    private int id;

    private String name;

    @Override
    public String toString() {
        return "Chicken{" + id + ", " + name + "}";
    }
}
