package com.codecool.old_book_auction;

import com.codecool.old_book_auction.model.*;

import java.util.*;

public class App {

    private static final Random random = new Random();

    public static Topic getRandomTopic() {
        Topic[] topics = Topic.values();
        int randomIndex = random.nextInt(topics.length);
        return topics[randomIndex];
    }

    private static List<Book> createBooks(int bookCount, int minPrice, int maxPrice) {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < bookCount; i++) {
            int price = (int) Math.floor(Math.random() * (maxPrice - minPrice + 1) + minPrice);
            books.add(new Book(i + 1, "Book " + (i + 1), getRandomTopic(), price));
        }
        return books;
    }

    private static List<Bidder> createBidders(int bidderCount, int minimumCapital, int maximumCapital) {
        List<Bidder> bidders = new ArrayList<>();
        for (int i = 0; i < bidderCount; i++) {
            int capital = (int) Math.floor(Math.random() * (maximumCapital - minimumCapital + 1) + minimumCapital);
            Topic favoriteTopic = getRandomTopic();

            Topic[] topicsOfInterest = new Topic[2];
            for (int j = 0; j < topicsOfInterest.length; j++) {
                topicsOfInterest[j] = getRandomTopic();
                if (topicsOfInterest[j] == favoriteTopic) {
                    j--;
                }
                if (topicsOfInterest[0] == topicsOfInterest[1]) {
                    j--;
                }
            }
            bidders.add(new Bidder(i + 1, capital, favoriteTopic, topicsOfInterest));
        }
        return bidders;
    }

    public static List<Bidder> filterBuyers(List<Bidder> bidders, Book book) {
        List<Bidder> buyers = new ArrayList<>();
        for (Bidder bidder : bidders) {
            if (bidder.interested(book)) {
                if (bidder.canBid(book, book.getPrice())) {
                    buyers.add(bidder);
                }
            }
        }
        return buyers;
    }

    public static Book randomBookForBid(List<Book> books) {
        int randomIndex = random.nextInt(books.size());
        return books.get(randomIndex);
    }

    public static Bidder randomBidTurn(List<Bidder> bidders) {
        int randomIndex = random.nextInt(bidders.size());
        return bidders.get(randomIndex);
    }

    public static void displayBuyers(List<Bidder>buyers) {
        if(buyers.size() > 0) {
            System.out.println("Interested buyers: " + buyers);
        } else {
            System.out.println("No interested buyers.");
        }
    }

    private static void startAuction(List<Book> books, List<Bidder> bidders, List<Book> soldBooks, List<Book> notSoldBooks) {
        while (books.size() > 0) {
            Book currentRandomBook = randomBookForBid(books);
            books.remove(currentRandomBook);

            System.out.println(
                    "Currently on auction: " + currentRandomBook.getTitle() + "\n"
                            + "On the topic: " + currentRandomBook.getTopic() + "\n"
                            + "With the starting bid: " + currentRandomBook.getPrice());
            System.out.println("-------------------------------------------------------");

            List<Bidder> buyers = filterBuyers(bidders, currentRandomBook);
            displayBuyers(buyers);
            System.out.println("-------------------------------------------------------");

            checkIfBuyers(buyers, currentRandomBook, soldBooks, notSoldBooks);
        }
    }

    private static void checkIfBuyers(List<Bidder> buyers, Book currentRandomBook, List<Book> soldBooks, List<Book> notSoldBooks) {
        if (buyers.size() > 0) {
            Bidder currentBidder = randomBidTurn(buyers);
            Bid currentBid = new Bid(currentBidder.getId(), currentBidder, currentRandomBook.getPrice());

            System.out.println("The first bidder is " + currentBidder.getName() + " with the offer " + currentBid.getBidPrice());

            Bidder winner = auctionProcess(currentBid, buyers, currentBidder, currentRandomBook);

            if (winner == null) {
                winner = currentBidder;
            }

            winner.buyBook(currentRandomBook);

            winner.setCapital(winner.getCapital() - currentRandomBook.getPrice());
            soldBooks.add(currentRandomBook);
            System.out.println("\n");
        } else {
            System.out.println("The book was not sold");
            System.out.println("\n");
            notSoldBooks.add(currentRandomBook);
        }
    }

    public static Bidder auctionProcess(Bid currentBid, List<Bidder> bidders, Bidder currentBidder, Book currentRandomBook) {
        Bidder winner = null;
        boolean continueBidding = true;
        while (continueBidding) {
            continueBidding = false;
            int highestBid = currentBid.getBidPrice();

            for (Bidder bidder : bidders) {
                if (bidder != currentBidder) {
                    Bid newBid = bidder.getBid(currentRandomBook, currentBid);
                    if (newBid.getBidPrice() > highestBid) {
                        highestBid = newBid.getBidPrice();
                        currentBidder = bidder;
                        winner = bidder;
                        System.out.println(bidder.getName() + " offers " + highestBid);
                        continueBidding = true;
                    } else {
                        continueBidding = false;
                    }
                }
            }
        }
        return winner;
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
        List<Book> notSoldBooks = new ArrayList<>();

        startAuction(books, bidders, soldBooks, notSoldBooks);

        System.out.println("Number of sold books: " + soldBooks.size());
        System.out.println("Books that weren't sold: " + notSoldBooks);
    }

}
