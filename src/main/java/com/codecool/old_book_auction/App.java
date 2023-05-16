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
        for (int i = 0; i < books.size(); i++) {
            System.out.println(books.get(i).getTitle());
            System.out.println(books.get(i).getTopic());
            System.out.println(books.get(i).getPrice());
            List<Bidder> buyers = new ArrayList<>();
            for (int j = 0; j < bidders.size(); j++) {
                if (bidders.get(j).interested(books.get(i))){
                    System.out.println(bidders.get(j).getName() + " " + bidders.get(j).getFavourite() + " " + Arrays.toString(bidders.get(j).getInterested()));
                    System.out.println("--------");
                    if (bidders.get(j).canBid(books.get(i), books.get(i).getPrice())) {
                        buyers.add(bidders.get(j));
                    }
                }
            }
            if (buyers.size() == 1){
                soldBooks.add(books.get(i));
            }
//            System.out.println(buyers);
        }
        System.out.println(soldBooks.size());
    }
}
