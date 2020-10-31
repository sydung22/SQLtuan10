package com.example.sqlite;

public class monhoc {
    private int id;
    private String monhoc;

    public monhoc(int id, String monhoc) {
        this.id = id;
        this.monhoc = monhoc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMonhoc() {
        return monhoc;
    }

    public void setMonhoc(String monhoc) {
        this.monhoc = monhoc;
    }
}
