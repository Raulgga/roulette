package edu.fje.roulette;

public class userClass {
    private String name;
    private double bet;
    private double money;

    public userClass(String name, double bet, double money) {
        this.name = name;
        this.bet = bet;
        this.money = money;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    public double getBet() {
        return bet;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getMoney() {
        return money;
    }
}
