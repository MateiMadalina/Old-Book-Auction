package com.codecool.old_book_auction.model;

import java.util.ArrayList;
import java.util.List;

public class Bidder {
    private final List<Book> books = new ArrayList<Book>();
    private final Topic favourite;
    private final Topic[] interested;

    private double capital;

    private int id;
    private String name;


    public Bidder(int id, double capital, Topic favourite, Topic[] interested){
        this.id = id;
        this.capital = capital;
        this.favourite = favourite;
        this.interested = interested;
        this.name = "Bidder #"+ id;
    }

    public boolean interested(Book book){
        return false;
        //TODO
    }

    public boolean canBid(Book book, int currentPrice){
        return false;
        //TODO
    }

    public Bid getBid(Book book, Bid currentBid){
        return null;
        //TODO
    }

    private static int getBidPrice(int currentPrice, int threshold){
        return 0;
        //TODO
    }

    private int getThresholdPrice(Topic topic){
        return 0;
        //TODO
    }

    public void buyBook(Book book){
        //TODO
    }
}
