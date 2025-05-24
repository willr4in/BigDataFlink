package org.example.utils;

import java.sql.Date;
import java.time.LocalDate;

public class DataModel {
    public static class Customer {
        public int customerId;
        public String firstName;
        public String lastName;
        public int age;
        public String email;
        public String country;
        public String postalCode;
        public String petType;
        public String petName;
        public String petBreed;
    }

    public static class Product {
        public int productId;
        public String name;
        public String category;
        public double price;
        public double weight;
        public String color;
        public String size;
        public String brand;
        public String material;
        public String description;
        public double rating;
        public Date expiryDate;
    }

    public static class Sale {
        public int saleId;
        public int customerId;
        public int productId;
        public LocalDate saleDate;
        public int quantity;
        public double totalPrice;
    }
}
