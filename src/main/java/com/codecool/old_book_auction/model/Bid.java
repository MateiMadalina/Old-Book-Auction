package com.codecool.old_book_auction.model;

public class Bid {
    private int id;
    private Bidder bidder;
    private int bidPrice;

    public Bid (int id, Bidder bidder, int bidPrice) {
        this.id = id;
        this.bidder = bidder;
        this.bidPrice = bidPrice;
    }

    public int getBidPrice() {
        return bidPrice;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBidder(Bidder bidder) {
        this.bidder = bidder;
    }

    public void setBidPrice(int bidPrice) {
        this.bidPrice = bidPrice;
    }
}
