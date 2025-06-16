package com.example.backgammon;

public class User {
    private int coins;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getNumOfGames() {
        return numOfGames;
    }

    public void setNumOfGames(int numOfGames) {
        this.numOfGames = numOfGames;
    }


    private String email;
    private int numOfGames;
    private String name;



    public User(String email, String name) {
        this.email = email;
        this.coins = 0;
        this.numOfGames = 0;
        this.name =  name;


    }
    public User() {
    }

    public String getEmail(){
        return email;
    }

}
