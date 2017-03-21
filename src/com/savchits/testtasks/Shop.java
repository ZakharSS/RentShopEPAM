package com.savchits.testtasks;

import com.google.gson.Gson;

import java.io.*;
import java.util.*;

public class Shop {

    // Map: value - quantity of goods
    private Map<SportEquipment, Integer> goods;
    private ArrayList<SportEquipment> rentList = new ArrayList<>(3);
    private RentUnit rentUnit;


    public void startShopping() {
        goods = new HashMap<>();
        Gson gson = new Gson();
        try {
            SportEquipment[] equipmentList = gson.fromJson(new FileReader("equipment.json"), SportEquipment[].class);
            for (SportEquipment equipment : equipmentList) {
                goods.put(equipment, equipment.getQuantity());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        startWelcomeMenu();
    }

    private void startWelcomeMenu() {

        Scanner in = new Scanner(System.in);
        try {
            while (true) {
                int menu;
                System.out.println("____Welcome to our RentShop____");
                System.out.println("Who can I help you? ");
                System.out.println("1. Show price list");
                System.out.println("2. Search a good");
                System.out.println("3. Rent a good");
                System.out.println("4. Show my rented goods");
                System.out.println("5. Exit");

                menu = in.nextInt();

                switch (menu) {
                    case 1:
                        System.out.println("CATEGORY |" + "EQUIPMENT |" + "PRICE |" + "QUANTITY|");
                        for (Map.Entry<SportEquipment, Integer> entry : goods.entrySet()) {
                            System.out.println(entry.getKey().toString());
                        }
                        break;
                    case 2:
                        System.out.println("Please enter the equipment name: ");
                        String searchName = in.next();
                        System.out.println(searchGoods(searchName));
                        break;
                    case 3:
                        System.out.println("Please enter the equipment name: ");
                        String equipmentName = in.next();
                        System.out.println(rentEquipment(equipmentName));
                        Scanner repeatInput = new Scanner(System.in);
                        System.out.println("Anything else? " + "\n" + "yes/no?");
                        String answer = repeatInput.next();
                        if (answer.compareToIgnoreCase("yes") == 0) {
                            continue;
                        } else if (answer.compareToIgnoreCase("no") == 0) {
                            System.out.println("Thanks for coming, good luck!");
                            System.exit(0);
                        } else System.out.println("Incorrect input");
                        break;
                    case 4:
                        System.out.println(showRentedGoods());
                        break;
                    case 5:
                        System.out.println("Bye!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Please, make the right choice");
                        break;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Incorrect input");
            startWelcomeMenu();
        }
    }


    private String rentEquipment(String equipmentName) {

        for (Map.Entry<SportEquipment, Integer> entry : goods.entrySet()) {
            if ((entry.getKey().getEquipmentName()).compareToIgnoreCase(equipmentName) == 0) {
                if (entry.getValue() > 0 && rentList.size() < 3) {
                    int newQuantity = entry.getValue() - 1;

                    entry.getKey().setQuantity(newQuantity);
                    entry.setValue(newQuantity);
                    rentList.add(entry.getKey());

                    return entry.getKey().getEquipmentName() + " " + "was added to your cart";

                } else
                    return "We have out of stock or your cart has more than 3 items";
            }
        }
        return "There is now such a good";
    }

    private String searchGoods(String name) {

        System.out.println("EQUIPMENT |" + "AVAILABLE|");
        for (Map.Entry<SportEquipment, Integer> entry : goods.entrySet()) {
            if ((entry.getKey().getEquipmentName()).compareToIgnoreCase(name) == 0) {
                return entry.getKey().getEquipmentName() + "  " + entry.getValue();
            }
        }
        return "There is now such a good";
    }

    private String showRentedGoods() {
        if (rentList.size() > 0) {
            rentUnit = new RentUnit(rentList);
            String result = "";
            int sum = 0;
            ArrayList<SportEquipment> rentedItems = rentUnit.getUnits();
            for (SportEquipment items : rentedItems) {
                sum += items.getPrice();
                result += items.getEquipmentName() + " " + items.getPrice() + "\n";
            }
            System.out.println("EQUIPMENT | PRICE|");
            return result + "Total sum: " + sum;
        } else return "You have no rented goods yet";
    }
}
