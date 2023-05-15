package com.codecool.old_book_auction;

import com.codecool.old_book_auction.model.*;

import java.util.Arrays;
import java.util.Random;

import java.util.ArrayList;
import java.util.List;

public class App {

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
        List<Book> books = new ArrayList<Book>();
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

    public static void main(String[] args) {
        final int bookCount = 20;
        final int minPrice = 100;
        final int maxPrice = 300;

        final int bidderCount = 10;
        final int minimumCapital = 100;
        final int maximumCapital = 1000;

        List<Book> books = createBooks(bookCount, minPrice, maxPrice);

//        for (Book book : books) {
//            System.out.println("ID: " + book.getId());
//            System.out.println("Title: " + book.getTitle());
//            System.out.println("Topic: " + book.getTopic());
//            System.out.println("Price: " + book.getPrice());
//            System.out.println();
//        }

        List<Bidder> bidders = createBidders(bidderCount, minimumCapital, maximumCapital);

            for (Bidder bidder : bidders) {
            System.out.println("ID: " + bidder.getId());
            System.out.println("Name: " + bidder.getName());
            System.out.println("Favorite: " + bidder.getFavourite());
            System.out.println("Interested: " + Arrays.toString(bidder.getInterested()));
            System.out.println("Capital: " + bidder.getCapital());
            System.out.println();
        }

    }
}
