package com.codecool.old_book_auction.model;

public class Book {
    private final int id;
    private final String title;
    private final Topic topic;
    private double price;

    public Book(int id, String title, Topic topic, double price){
        this.id = id;
        this.title = title;
        this.topic = topic;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Topic getTopic() {
        return topic;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
