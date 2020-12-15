package org.arifjehoh.Entity;

import org.arifjehoh.Model.RentalDTO;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

public class Rental implements RentalDTO {
    private final int id;
    private final int studentId;
    private final String city;
    private final String zipCode;
    private final String streetName;
    private final String country;
    private final String dueDate;
    private final double totalCost;

    public Rental(Builder builder) {
        this.id = builder.id;
        this.studentId = builder.studentId;
        this.city = builder.city;
        this.zipCode = builder.zipCode;
        this.streetName = builder.streetName;
        this.country = builder.country;
        this.dueDate = builder.dueDate;
        this.totalCost = builder.totalCost;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getStudentId() {
        return studentId;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getZipCode() {
        return zipCode;
    }

    @Override
    public String getStreetName() {
        return streetName;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public String getDueDate() {
        return dueDate;
    }

    @Override
    public double getTotalCost() {
        return totalCost;
    }

    @Override
    public String toString() {
        return "Rental ID: " + getId() +
                "\t| Student ID: " + getStudentId() +
                "\t| Due Date: " + getDueDate() +
                "\t\t| Total cost: " + getTotalCost();
    }

    public static class Builder {
        private final int studentId;
        private final String city;
        private final String zipCode;
        private final String streetName;
        private final String country;
        private String dueDate = LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).toString();
        private int id = 0;
        private double totalCost = 0;

        public Builder(int studentId, String city, String zipCode, String streetName, String country) {
            this.studentId = studentId;
            this.city = city;
            this.zipCode = zipCode;
            this.streetName = streetName;
            this.country = country;
        }

        public Builder(String id, String studentId, String city, String zipCode, String streetName, String country, String dueDate, String totalCost) {
            this(Integer.parseInt(studentId), city, zipCode, streetName, country);
            id(id);
            dueDate(dueDate);
            totalCost(totalCost);

        }

        public Builder id(String rentalId) {
            id = Integer.parseInt(rentalId);
            return this;
        }

        public Builder totalCost(String cost) {
            totalCost = cost != null ? Double.parseDouble(cost) : 0;
            return this;
        }

        public Builder dueDate(String date) {
            dueDate = date;
            return this;
        }

        public Rental build() {
            return new Rental(this);
        }
    }
}
