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

    public void setId(int id) {
        this.id = id;
    }

    public void setBidder(Bidder bidder) {
        this.bidder = bidder;
    }

    public void setBidPrice(double bidPrice) {
        this.bidPrice = bidPrice;
    }
}
