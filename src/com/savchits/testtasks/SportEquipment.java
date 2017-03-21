package com.savchits.testtasks;


public class SportEquipment {

    private Category category;
    private String equipmentName;
    private int price;
    private int quantity;


    public String getEquipmentName() {
        return equipmentName;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return category.getCategoryName() + " " + getEquipmentName() + " " + getPrice() + " " + getQuantity();
    }
}
