package com.zaozao.vultrManager.data;

/**
 * Created by sean on 16/10/3.
 */
public class AccountInfo {
    private String balance;
    private String pending_charges;
    private String last_payment_date;
    private String last_payment_amount;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getPending_charges() {
        return pending_charges;
    }

    public void setPending_charges(String pending_charges) {
        this.pending_charges = pending_charges;
    }

    public String getLast_payment_date() {
        return last_payment_date;
    }

    public void setLast_payment_date(String last_payment_date) {
        this.last_payment_date = last_payment_date;
    }

    public String getLast_payment_amount() {
        return last_payment_amount;
    }

    public void setLast_payment_amount(String last_payment_amount) {
        this.last_payment_amount = last_payment_amount;
    }

    @Override
    public String toString() {
        return "AccountInfo{" +
                "balance='" + balance + '\'' +
                ", pending_charges='" + pending_charges + '\'' +
                ", last_payment_date='" + last_payment_date + '\'' +
                ", last_payment_amount='" + last_payment_amount + '\'' +
                '}';
    }
}
