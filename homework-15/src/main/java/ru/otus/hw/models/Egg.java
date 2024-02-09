package ru.otus.hw.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Egg {

    private int id;

    private String name;

    @Override
    public String toString() {
        return "Egg{" + id + ", " + name + "}";
    }
}
