package com.codecool.old_book_auction.model;

public class Bid {
    private int id;
    private Bidder bidder;
    private double bidPrice;

    public Bid (int id, Bidder bidder, double bidPrice) {
        this.id = id;
        this.bidder = bidder;
        this.bidPrice = bidPrice;
    }

    public double getBidPrice() {
        return bidPrice;
    }
}
