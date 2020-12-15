package org.arifjehoh.Entity;

import org.arifjehoh.Model.StudentDTO;

public class Student implements StudentDTO {
    private final int studentId;
    private final String firstName;
    private final String lastName;
    private final int age;
    private final String city;
    private final String streetName;
    private final String ssn;
    private final String zipCode;
    private final String country;

    public Student(Builder builder) {
        this.studentId = builder.studentId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.city = builder.city;
        this.streetName = builder.streetName;
        this.ssn = builder.ssn;
        this.zipCode = builder.zipCode;
        this.country = builder.country;
    }

    @Override
    public int getId() {
        return studentId;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getStreetName() {
        return streetName;
    }

    @Override
    public String getSocialSecurityNumber() {
        return ssn;
    }

    @Override
    public String getZipCode() {
        return zipCode;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

    public static class Builder {
        private final String ssn;
        private final String streetName;
        private final String city;
        private final int age;
        private final String lastName;
        private final String firstName;
        private final int studentId;
        public String zipCode = null;
        public String country = null;

        public Builder(int studentId, String firstName, String lastName, int age, String city, String streetName, String ssn) {
            this.studentId = studentId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.city = city;
            this.streetName = streetName;
            this.ssn = ssn;
        }

        public Builder zipCode(String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }
}
