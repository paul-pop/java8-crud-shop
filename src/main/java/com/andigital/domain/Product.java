package com.andigital.domain;

/**
 * Created by Paul Pop on 15/08/15.
 */
public class Product {

    private Integer id;
    private String name;
    private Double price;

    public Product(Integer id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "{id=" + id +
                ", name='" + name + "\'" +
                ", price=" + price + "}";
    }

}
