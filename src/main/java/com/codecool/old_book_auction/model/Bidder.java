package com.codecool.old_book_auction.model;

import java.util.ArrayList;
import java.util.List;

public class Bidder {
    private final List<Book> books = new ArrayList<Book>();

    private final Topic favourite;
    private final Topic[] interested;

    private int capital;

    private int id;
    private String name;


    public Bidder(int id, int capital, Topic favourite, Topic[] interested){
        this.id = id;
        this.capital = capital;
        this.favourite = favourite;
        this.interested = interested;
        this.name = "Bidder #"+ id;
    }

    public Topic getFavourite() {
        return favourite;
    }

    public Topic[] getInterested() {
        return interested;
    }

    public int getCapital() {
        return capital;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    public boolean interested(Book book){
        if (this.favourite == book.getTopic()){
            return true;
        } else if (this.interested[0] == book.getTopic() || this.interested[1] == book.getTopic()){
            return true;
        }
        return false;
    }

    public boolean canBid(Book book, int currentPrice){
        if (this.favourite == book.getTopic() && (this.capital * 0.5) >= currentPrice){
            return true;
        } else if ((this.interested[0] == book.getTopic() || this.interested[1] == book.getTopic()) && (this.capital * 0.25) >= currentPrice) {
            return true;
        }
        return false;
    }

    public Bid getBid(Book book, Bid currentBid){
            currentBid.setId(this.id);
            currentBid.setBidder(this);
            int threshold = this.capital / getThresholdPrice(book.getTopic()) - currentBid.getBidPrice();
            int bidPrice = getBidPrice(book.getPrice(),threshold);
            currentBid.setBidPrice(bidPrice);
            book.setPrice(bidPrice);
            return currentBid;
    }


    //When pricing a bid, the bidder looks at the difference of its threshold (the max amount it's willing to pay) and the price,
    // divides the range in half, and adds the result to the current price to outbid the most recent bidder.
    private static int getBidPrice(int currentPrice, int threshold){
        return currentPrice + threshold/2;
    }

    private int getThresholdPrice(Topic topic){
        if (this.favourite == topic ){
            return 2;
        } else if (this.interested[0] == topic || this.interested[1] == topic)  {
            return 4;
        }
        return 0;
    }

//    public void buyBook(Book book){
//    }


    @Override
    public String toString() {
        return "Bidder{" +
                "capital=" + capital +
                ", name='" + name + '\'' +
                '}';
    }
}
