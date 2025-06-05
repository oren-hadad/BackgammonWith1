package com.example.backgammon;

public class User {
    private int coins;

    public void setEmail(String email) {
        this.email = email;
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




    public User(String email) {
        this.email = email;
        this.coins = 0;
        this.numOfGames = 0;


    }

    public String getEmail(){
        return email;
    }

}
