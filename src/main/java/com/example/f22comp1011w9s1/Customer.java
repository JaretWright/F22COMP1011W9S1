package com.example.f22comp1011w9s1;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;

public class Customer {
    private int customerId;
    private String firstName, lastName, city, province, bloodType;
    private LocalDate birthday;

    public Customer(int customerId, String firstName, String lastName, String city, String province, String bloodType, LocalDate birthday) {
        setCustomerId(customerId);
        setFirstName(firstName);
        setLastName(lastName);
        setCity(city);
        setProvince(province);
        setBloodType(bloodType);
        setBirthday(birthday);
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        if (customerId>0)
            this.customerId = customerId;
        else
            throw new IllegalArgumentException("customerId must be greater than 0");
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName.length()>=2 && firstName.length()<=30)
            this.firstName = firstName;
        else
            throw new IllegalArgumentException("first name must be 2 to 30 characters");
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName.length()>=2 && lastName.length()<=30)
            this.lastName = lastName;
        else
            throw new IllegalArgumentException("last name must be 2-30 characters");
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        List<String> provinces = Arrays.asList("ON", "QC", "AB", "NS", "BC", "SK", "NT", "NB", "MB", "NL");
        if (provinces.contains(province))
            this.province = province;
        else
            throw new IllegalArgumentException(province + " was not in the list of valid provinces: "+provinces);
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        List<String> bloodTypes = Arrays.asList("B+","O+", "AB+", "O-", "A+", "B-", "A-", "AB-");
        if (bloodTypes.contains(bloodType))
            this.bloodType = bloodType;
        else
            throw new IllegalArgumentException(bloodType + " was not a recognize bloodtype, use: "+bloodTypes);
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        if (birthday.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("birthday cannot be in the future");
        else
            this.birthday = birthday;
    }

    public String getFullName()
    {
        return firstName + " " + lastName;
    }

    public boolean contains(String searchText)
    {
        return getFullName().contains(searchText) || province.contains(searchText)
                || bloodType.contains(searchText);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (city.length()>=2)
            this.city = city;
        else
            throw new IllegalArgumentException("city must be 2 or more characters");
    }
}
