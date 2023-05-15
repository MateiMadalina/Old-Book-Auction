package com.codecool.old_book_auction.model;

import java.util.Date;

public class Transaction {
    private int id;
    private Date timestamp;
    private Bidder bidder;
    private Bid bid;

    public Transaction (int id, Date timestamp, Bidder bidder, Bid bid) {
        this.id = id;
        this.timestamp = timestamp;
        this.bidder = bidder;
        this.bid = bid;
    }
}
