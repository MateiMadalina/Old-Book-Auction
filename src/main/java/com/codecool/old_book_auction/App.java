package com.codecool.old_book_auction;

import com.codecool.old_book_auction.model.*;

import java.util.Arrays;
import java.util.Random;

import java.util.ArrayList;
import java.util.List;

public class App {

    private static final Random random = new Random();

    public static Topic getRandomTopic() {
        Topic[] topics = Topic.values();
        Random random = new Random();
        int randomIndex = random.nextInt(topics.length);
        return topics[randomIndex];
    }


    private static List<Book> createBooks (
            int bookCount,
            int minPrice,
            int maxPrice
    ) {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < bookCount; i++) {
            int price = (int)Math.floor(Math.random() * (maxPrice - minPrice + 1) + minPrice);
            books.add(new Book(i+1, "Book " + (i +1), getRandomTopic(), price));
        }
        return books;
    }

    private static List<Bidder> createBidders (
            int bidderCount,
            int minimumCapital,
            int maximumCapital
    ) {
        List<Bidder> bidders = new ArrayList<Bidder>();
        for(int i = 0; i < bidderCount; i++) {
            double capital = (double)Math.floor(Math.random() * (maximumCapital - minimumCapital + 1) + minimumCapital);
            Topic favoriteTopic = getRandomTopic();

            Topic[] topicsOfInterest = new Topic[2];
            for (int j = 0; j < topicsOfInterest.length; j++) {
                topicsOfInterest[j] = getRandomTopic();
                if(topicsOfInterest[j] == favoriteTopic) {
                    j--;
                }
                if(topicsOfInterest[0] == topicsOfInterest[1]) {
                    j--;
                }
            }
            bidders.add(new Bidder(i+1, capital, favoriteTopic, topicsOfInterest));
        }
        return bidders;
    }

    public static List<Bidder> filterBuyers(List<Bidder> bidders,Book book) {
        List<Bidder> buyers = new ArrayList<>();
        for (Bidder bidder : bidders) {
            if (bidder.interested(book)) {
                System.out.println(bidder.getName() + " " + bidder.getFavourite() + " " + Arrays.toString(bidder.getInterested()));
                System.out.println("--------");
                if (bidder.canBid(book, book.getPrice())) {
                    buyers.add(bidder);
                }
                System.out.println(buyers);
            }
        }
        return buyers;
    }
    public static void main(String[] args) {
        final int bookCount = 20;
        final int minPrice = 100;
        final int maxPrice = 300;

        final int bidderCount = 10;
        final int minimumCapital = 100;
        final int maximumCapital = 1000;

        List<Book> books = createBooks(bookCount, minPrice, maxPrice);
        List<Bidder> bidders = createBidders(bidderCount, minimumCapital, maximumCapital);
        List<Book> soldBooks = new ArrayList<>();
        for (Book book : books) {
            System.out.println(book.getTitle());
            System.out.println(book.getTopic());
            System.out.println(book.getPrice());

            List<Bidder> buyers =  filterBuyers(bidders,book);
            if (buyers.size() == 1) {
                soldBooks.add(book);
            }else if(buyers.size() > 1){
               int currentBidderIndex =  random.nextInt(0,buyers.size());
               System.out.println(bidders.get(currentBidderIndex).getName() + " bids " + book.getPrice());
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    // Handle the interrupted exception
//                    e.printStackTrace();
//                }
            }
//            System.out.println(buyers);
        }


     //   System.out.println(soldBooks.size());
    }
}
