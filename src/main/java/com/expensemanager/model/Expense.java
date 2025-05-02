package com.expensemanager.model;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Expense {
    private final String id;
    private final SimpleStringProperty amount;
    private final SimpleStringProperty category;
    private final SimpleStringProperty date;

    public Expense() {
        this.id = System.currentTimeMillis()+"";

        this.amount = new SimpleStringProperty("0");

        this.category = new SimpleStringProperty("Không lý do");

        this.date = new SimpleStringProperty(DateUtil.parseToLocalDate(LocalDate.now().toString()).toString());

        System.out.println("Đã tạo một Expense mới với id = " + this.id + " - amount = " + this.amount
                + " - category = " + this.category + " - date = " + this.date);
    }


    public Expense(String amount) {
       // this.id = 0;
        this.id = System.currentTimeMillis()+"";

        this.amount = new SimpleStringProperty(amount);

        this.category = new SimpleStringProperty("Không lý do");

        this.date = new SimpleStringProperty(DateUtil.parseToLocalDate(LocalDate.now().toString()).toString());

        System.out.println("Đã tạo một Expense mới với id = " + this.id + " - amount = " + this.amount
                + " - category = " + this.category + " - date = " + this.date);
    }


    public Expense(String amount, String category) {
        this.id = System.currentTimeMillis()+"";
        this.amount = new SimpleStringProperty(amount);

        this.category = new SimpleStringProperty(category);

        this.date = new SimpleStringProperty(DateUtil.parseToLocalDate(LocalDate.now().toString()).toString());

        System.out.println("Đã tạo một Expense mới với id = " + getId() + " - amount = " + getAmount()
                + " - category = " + getCategory() + " - date = " + getDate());
    }


    public Expense(String amount, String category, String date) {
       // this.id = 0;
        this.id = System.currentTimeMillis()+"";
        this.amount = new SimpleStringProperty(amount);

        this.category = new SimpleStringProperty(category);

        this.date = new SimpleStringProperty(DateUtil.parseToLocalDate(date).toString());

        System.out.println("Đã tạo một Expense mới với id = " + getId() + " - amount = " + getAmount()
                + " - category = " + getCategory() + " - date = " + getDate());
    }

    public Expense(String id, String amount, String category, String date) {
        this.id = id;

        this.amount = new SimpleStringProperty(amount);

        this.category = new SimpleStringProperty(category);

        this.date = new SimpleStringProperty(DateUtil.parseToLocalDate(date).toString());

        System.out.println("Đã tạo một Expense mới với id = " + getId() + " - amount = " + getAmount()
                + " - category = " + getCategory() + " - date = " + getDate());
    }

    public String getAmount() {
        return this.amount.get();
    }

    public String getCategory() {
        return this.category.get();
    }

    public String getDate() {
        return this.date.get();
    }

    public String getId() {
        return this.id;
    }

/*    public Double getAmountDouble(){
        return Double.parseDouble(this.amount.get());
    }
    public String getCategoryStr() {
        return this.category.get();
    }

    public String getDateStr() {
        return this.date.get();
    }*/
   /* public void setAmount(String amount) {
        this.amount.set(amount);
    }

    public void setAmount(int amount) {
        this.amount.set(amount + "");
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public void setDate(String date) {
        this.date.set(date);
    }*/
}
