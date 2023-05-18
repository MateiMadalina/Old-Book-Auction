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

    public static void main(String[] args) {
        final int bookCount = 20;
        final int minPrice = 100;
        final int maxPrice = 300;

        final int bidderCount = 10;
        final int minimumCapital = 100;
        final int maximumCapital = 1000;

        List<Book> books = createBooks(bookCount, minPrice, maxPrice);
        List<Bidder> bidders = createBidders(bidderCount, minimumCapital, maximumCapital);

        while (books.size() > 0) {
            Book randomBook = randomBookForBid(books);
            books.remove(randomBook);

            // Afisare detalii carte:
            System.out.println("Carte pentru licitație: " + randomBook.getTitle());
            System.out.println("Tema cărții: " + randomBook.getTopic());
            System.out.println("Prețul cărții: " + randomBook.getPrice());

            // Vrem să vedem cumpărătorii interesați de carte:
            List<Bidder> buyers = filterBuyers(bidders, randomBook);
            System.out.println("Potentiali cumpărători: " + buyers);

            if (buyers.size() > 0) {
                Bidder currentBidder = randomBidTurn(buyers);
                Bid currentBid = new Bid(currentBidder.getId(), currentBidder, randomBook.getPrice());

                System.out.println("Licitatorul curent: " + currentBidder.getName());
                System.out.println("Oferta curentă: " + currentBid.getBidPrice());

                Bidder winner = null;

                boolean continueBidding = true;

                while (continueBidding) {
                    continueBidding = false; // Vom verifica dacă există un licitator care poate continua licitația
                    int highestBid = currentBid.getBidPrice(); // Cea mai mare ofertă curentă

                    for (Bidder bidder : buyers) {
                        if (bidder != currentBidder) {
                            Bid newBid = bidder.getBid(randomBook, currentBid);
                            if (newBid.getBidPrice() > highestBid) {
                                highestBid = newBid.getBidPrice();
                                currentBidder = bidder; // Actualizare licitator curent
                                winner = bidder;
                                System.out.println("Noua cea mai mare ofertă: " + highestBid);
                                System.out.println("Licitatorul curent: " + bidder.getName());
                                continueBidding = true; // Setăm continuarea licitației
                            } else {
                                continueBidding = false;
                            }
                        }
                    }
                }

                if (winner == null) {
                    winner = currentBidder; // Assign currentBidder as the winner
                }

                // Licitația s-a încheiat, cartea este vândută
                System.out.println("Cartea a fost vândută lui " + winner.getName());
                System.out.println("\n");
                winner.setCapital(winner.getCapital() - randomBook.getPrice());
            } else {
                System.out.println("Cartea nu a fost vândută");
                System.out.println("----------------------");
            }
        }

        List<Book> soldBooks = new ArrayList<>();
    }

}
