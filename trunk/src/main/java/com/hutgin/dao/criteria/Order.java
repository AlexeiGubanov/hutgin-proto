package com.hutgin.dao.criteria;

public class Order {
    private boolean asc = true;
    private String property;

    public Order(String property) {
        this.property = property;
    }

    public Order(String property, boolean asc) {
        this.property = property;
        this.asc = asc;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public static Order ascending(String property) {
        return new Order(property, true);
    }

    public static Order descending(String property) {
        return new Order(property, false);
    }

    public String toString() {
        return property + ' ' + (asc ? "asc" : "desc");
    }

}
