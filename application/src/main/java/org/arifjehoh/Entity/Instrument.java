package org.arifjehoh.Entity;

import org.arifjehoh.Model.InstrumentDTO;

public class Instrument implements InstrumentDTO {
    private final int id;
    private final String type;
    private final String category;
    private final double cost;
    private final int rentalId;
    private final int studentId;
    private final String dueDate;

    public Instrument(Builder builder) {
        this.id = builder.id;
        this.rentalId = builder.rentalId;
        this.studentId = builder.studentId;
        this.type = builder.type;
        this.category = builder.category;
        this.cost = builder.cost;
        this.dueDate = builder.dueDate;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getRentalId() {
        return rentalId;
    }

    @Override
    public int getStudentId() {
        return studentId;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public String getDueDate() {
        return dueDate;
    }

    public static class Builder {
        private final int id;
        private final String type;
        private final String category;
        private final double cost;

        // Optional parameters
        private String dueDate = null;
        private int rentalId = 0;
        private int studentId = 0;

        public Builder(String id, String type, String category, String cost) {
            this.id = Integer.parseInt(id);
            this.type = type;
            this.category = category;
            this.cost = Double.parseDouble(cost);
        }

        public Builder rentalId(String id) {
            rentalId = Integer.parseInt(id);
            return this;
        }

        public Builder studentId(String id) {
            studentId = Integer.parseInt(id);
            return this;
        }

        public Builder due(String date) {
            dueDate = date;
            return this;
        }

        public Instrument build() {
            return new Instrument(this);
        }
    }
}
