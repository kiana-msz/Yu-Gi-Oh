package controller;

import model.Card;
import model.Deck;
import model.User;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ImportExportUserController {

    private static ImportExportUserController instance = null;

    public static ImportExportUserController getInstance() {
        if (instance == null) instance = new ImportExportUserController();
        return instance;
    }

    public void exportNewUser(User user) {
        if (user != null) {
            String username = user.getUsername();
            String password = user.getPassword();
            String nickname = user.getNickname();
            int highScore = user.getScore();
            int balance = user.getMoney();
            try {
                FileWriter writer = new FileWriter("Users/" + username + ".txt");
                writer.write(username + "\n" + password + "\n" + nickname + "\n" + highScore + "\n" + balance);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void exportAllUsers(List<User> allUsers) {
        try {
            FileWriter writer = new FileWriter("allUsers.txt");
            for (User user : allUsers) {
                String username = user.getUsername();
                writer.write(username + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importAllUsers() {
        String username;
        String password = "";
        String nickname = "";
        String highScore = "";
        String balance = "";
        File file = new File("allUsers.txt");
        try {
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    username = scanner.nextLine();
                    File userFile = new File("Users/" + username + ".txt");
                    if (userFile.exists()) {
                        Scanner userScanner = new Scanner(userFile);
                        int counter = 5;
                        while (userScanner.hasNextLine()) {
                            if (counter == 5)
                                username = userScanner.nextLine();
                            if (counter == 4)
                                password = userScanner.nextLine();
                            if (counter == 3)
                                nickname = userScanner.nextLine();
                            if (counter == 2)
                                highScore = userScanner.nextLine();
                            if (counter == 1)
                                balance = userScanner.nextLine();
                            if (counter == 0)
                                break;
                            counter--;
                        }
                        User user = new User(username, nickname, password, false);
                        if (balance.matches("\\d+") && highScore.matches("\\d+")) {
                            user.setMoney(Integer.parseInt(balance));
                            user.setScore(Integer.parseInt(highScore));
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void exportAchievements(List<User> allUsers) {
        try {
            for (User user : allUsers) {
                String username = user.getUsername();
                FileWriter fileWriter = new FileWriter("Achievements/" + username + ".txt");
                fileWriter.write(user.getWonGamesInARow() + "\n");
                fileWriter.write(user.getNumberOfWonGamesWithoutMonster() + "\n");
                fileWriter.write(user.getNumberOfBronze() + "\n");
                fileWriter.write(user.getNumberOfSilver() + "\n");
                fileWriter.write(user.getNumberOfGold() + "\n");
                fileWriter.write(user.getNumberOfTrophy() + "\n");

                fileWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importAchievements() {
        if (User.getAllUsers() != null) {
            for (User user : User.getAllUsers()) {
                String username = user.getUsername();
                String wonGamesInRow;
                String wonGameWithoutMonster;
                String numberOfBronze;
                String numberOfSilver;
                String numberOfGold;
                String numberOfTrophy;

                int number = 6;
                File file = new File("Achievements/" + username + ".txt");
                try {
                    if (file.exists()) {
                        Scanner scanner = new Scanner(file);
                        while (scanner.hasNextLine()) {
                            if (number == 6) {
                                wonGamesInRow = scanner.nextLine();
                                if (wonGamesInRow.matches("\\d+"))
                                    user.setWonGamesInARow(Integer.parseInt(wonGamesInRow));
                            }
                            if (number == 5) {
                                wonGameWithoutMonster = scanner.nextLine();
                                if (wonGameWithoutMonster.matches("\\d+"))
                                    user.setNumberOfWonGamesWithoutMonster(Integer.parseInt(wonGameWithoutMonster));
                            }
                            if (number == 4) {
                                numberOfBronze = scanner.nextLine();
                                if (numberOfBronze.matches("\\d+"))
                                    user.setNumberOfBronze(Integer.parseInt(numberOfBronze));
                            }
                            if (number == 3) {
                                numberOfSilver = scanner.nextLine();
                                if (numberOfSilver.matches("\\d+"))
                                    user.setNumberOfSilver(Integer.parseInt(numberOfSilver));
                            }
                            if (number == 2) {
                                numberOfGold = scanner.nextLine();
                                if (numberOfGold.matches("\\d+"))
                                    user.setNumberOfGold(Integer.parseInt(numberOfGold));
                            }
                            if (number == 1) {
                                numberOfTrophy = scanner.nextLine();
                                if (numberOfTrophy.matches("\\d+"))
                                    user.setNumberOfTrophy(Integer.parseInt(numberOfTrophy));
                            }
                            if (number == 0) {
                                break;
                            }
                            number--;
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void importProfileNumber() {
        if (User.getAllUsers() != null) {
            for (User user : User.getAllUsers()) {
                String username = user.getUsername();
                String profileNumber;
                int number;
                File file = new File("ProfileNumbers/" + username + ".txt");
                try {
                    if (file.exists()) {
                        Scanner scanner = new Scanner(file);
                        if (scanner.hasNextLine()) {
                            profileNumber = scanner.nextLine();
                            if (profileNumber.matches("\\d+")) {
                                number = Integer.parseInt(profileNumber);
                                Objects.requireNonNull(User.getUserByUsername(username)).setProfileNumber(number);
                            }
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void exportProfileNumber(String username, int number) {
        try {
            FileWriter fileWriter = new FileWriter("ProfileNumbers/" + username + ".txt");
            fileWriter.write(String.valueOf(number));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importActiveDeck() {
        if (User.getAllUsers() != null) {
            for (User user : User.getAllUsers()) {
                String username = user.getUsername();
                String activeDeckName;
                File file = new File("ActivateDecks/" + username + ".txt");
                try {
                    if (file.exists()) {
                        Scanner scanner = new Scanner(file);
                        if (scanner.hasNextLine()) {
                            activeDeckName = scanner.nextLine();
                            Objects.requireNonNull(User.getUserByUsername(username)).setActiveDeck(Objects.requireNonNull(User.getUserByUsername(username)).getDeckByName(activeDeckName));
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void exportActiveDeck(User user, String deckName) {
        String username = user.getUsername();
        try {
            FileWriter fileWriter = new FileWriter("ActivateDecks/" + username + ".txt");
            fileWriter.write(deckName);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportAllDecksName(List<Deck> allDecks, User user) {
        String username = user.getUsername();
        try {
            FileWriter fileWriter = new FileWriter("UsersDecks/" + username + "AllDecks.txt");
            for (Deck deck : allDecks) {
                String deckName = deck.getDeckName();
                fileWriter.write(deckName + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportCardsInMainDeck(User user, String deckName) {
        String username = user.getUsername();
        try {
            FileWriter writer = new FileWriter("Deck/" + username + deckName + "MainDeck.txt");
            Deck toBeExportedDeck = user.getDeckByName(deckName);
            for (Card card : toBeExportedDeck.getMainDeck()) {
                String cardName = card.getNamePascalCase();
                writer.write(cardName + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportCardsInSideDeck(User user, String deckName) {
        String username = user.getUsername();
        try {
            FileWriter writer = new FileWriter("Deck/" + username + deckName + "SideDeck.txt");
            Deck toBeExportedDeck = user.getDeckByName(deckName);
            for (Card card : toBeExportedDeck.getSideDeck()) {
                String cardName = card.getNamePascalCase();
                writer.write(cardName + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importAllDecks() {
        if (User.getAllUsers() != null) {
            for (User user : User.getAllUsers()) {
                String username = user.getUsername();
                File file = new File("UsersDecks/" + username + "AllDecks.txt");
                String deckName;
                try {
                    if (file.exists()) {
                        Scanner scanner = new Scanner(file);
                        while (scanner.hasNextLine()) {
                            deckName = scanner.nextLine();
                            Deck deck = new Deck(deckName);
                            File mainDeckFile = new File("Deck/" + user.getUsername() + deckName + "MainDeck.txt");
                            File sideDeckFile = new File("Deck/" + user.getUsername() + deckName + "SideDeck.txt");
                            if (mainDeckFile.exists()) {
                                Scanner mainDeckScanner = new Scanner(mainDeckFile);
                                while (mainDeckScanner.hasNextLine()) {
                                    String cardName = mainDeckScanner.nextLine();
                                    deck.addCardToMainDeck(DeckController.getInstance(user).getCardByName(cardName));
                                }
                            }
                            if (sideDeckFile.exists()) {
                                Scanner sideDeckScanner = new Scanner(sideDeckFile);
                                while (sideDeckScanner.hasNextLine()) {
                                    String cardName = sideDeckScanner.nextLine();
                                    deck.addCardToSideDeck(DeckController.getInstance(user).getCardByName(cardName));
                                }
                            }
                            user.addDeck(deck);
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void exportAllCards(User user) {
        try {
            FileWriter writer = new FileWriter("Cards/" + user.getUsername() + ".txt");
            for (Card card : user.getAllCards()) {
                String cardName = card.getNamePascalCase();
                writer.write(cardName + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importAllCards() {
        if (User.getAllUsers() != null) {
            for (User user : User.getAllUsers()) {
                String username = user.getUsername();
                File file = new File("Cards/" + username + ".txt");
                if (file.exists()) {
                    try {
                        Scanner scanner = new Scanner(file);
                        while (scanner.hasNextLine()) {
                            String cardName = scanner.nextLine();
                            user.addCardToUsersAllCards(DeckController.getInstance(user).getCardByName(cardName));
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
