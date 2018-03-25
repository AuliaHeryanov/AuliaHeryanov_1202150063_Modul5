package com.example.auliaheryanov.auliaheryanov_1202150063_modul5;

/**
 * Created by Aulia Heryanov on 25/03/2018.
 */

public class Model {
    private int id;
    private String Name;
    private String Description;
    private int Priority;

    public Model(String name, String description, int priority) {
        Name = name;
        Description = description;
        Priority = priority;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriority() {
        return Priority;
    }


}

