package edu.fje.roulette;

import java.util.HashMap;
import java.util.Map;

public class Bet {
    private String userName;
    private double betAmount;
    private int winningNumber;
    private String betType;
    private boolean isWinner;

    // Constructor vacío necesario para Firebase Firestore
    public Bet() {
    }

    // Constructor con parámetros
    public Bet(String userName, double betAmount, int winningNumber, String betType, boolean isWinner, long timestamp) {
        this.userName = userName;
        this.betAmount = betAmount;
        this.winningNumber = winningNumber;
        this.betType = betType;
        this.isWinner = isWinner;
        this.timestamp = timestamp;
    }
    public Map<String, Object> toMap() {
        Map<String, Object> betMap = new HashMap<>();
        betMap.put("userName", userName);
        betMap.put("betAmount", betAmount);
        betMap.put("winningNumber", winningNumber);
        betMap.put("betType", betType);
        betMap.put("isWinner", isWinner);
        betMap.put("timestamp", timestamp);
        return betMap;
    }

    // Getters
    public String getUserName() {
        return userName;
    }

    public double getBetAmount() {
        return betAmount;
    }

    public int getWinningNumber() {
        return winningNumber;
    }

    public String getBetType() {
        return betType;
    }

    // Setters (útiles si necesitas modificar valores)
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setBetAmount(double betAmount) {
        this.betAmount = betAmount;
    }

    public void setWinningNumber(int winningNumber) {
        this.winningNumber = winningNumber;
    }

    public void setBetType(String betType) {
        this.betType = betType;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean isWinner) {
        this.isWinner = isWinner;
    }

    private long timestamp; // Almacenará la fecha y hora de la apuesta

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }



    // Método toString para depuración
    @Override
    public String toString() {
        return "Bet{" +
                "userName='" + userName + '\'' +
                ", betAmount=" + betAmount +
                ", winningNumber=" + winningNumber +
                ", betType='" + betType + '\'' +
                '}';
    }
}
