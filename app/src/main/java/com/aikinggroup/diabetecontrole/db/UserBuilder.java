package com.aikinggroup.diabetecontrole.db;


import com.aikinggroup.diabetecontrole.db.entity.UserEntity;

public class UserBuilder {
    private String name;
    private String preferredLanguage;
    private String ville;
    private String country;
    private String firstname;
    private String lastname;
    private String email;
    private String telephone;
    private String password;
    private int age;
    private String gender;
    private int diabetesType;
    private String preferredUnit;
    private String a1cUnit;
    private String preferredWeightUnit;
    private String pRange;
    private double minRange;
    private double maxRange;
    private String ssn;
    private String cni;
    private boolean privacyChecked;
    private String birthdate;
    public UserBuilder setId(int id) {
        return this;
    }

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setPreferredLanguage(String language) {
        this.preferredLanguage = language;
        return this;
    }

    public UserBuilder setCountry(String country) {
        this.country = country;
        return this;
    }

    public UserBuilder setAge(int age) {
        this.age = age;
        return this;
    }

    public UserBuilder setPrivacyChecked(Boolean privacyChecked) {
        this.privacyChecked = privacyChecked;
        return this;
    }
    public UserBuilder setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public UserBuilder setDiabetesType(int dType) {
        this.diabetesType = dType;
        return this;
    }

    public UserBuilder setPreferredUnit(String unit) {
        this.preferredUnit = unit;
        return this;
    }

    public UserBuilder setPreferredA1CUnit(String a1cUnit) {
        this.a1cUnit = a1cUnit;
        return this;
    }

    public UserBuilder setPreferredWeightUnit(String weightUnit) {
        this.preferredWeightUnit = weightUnit;
        return this;
    }

    public UserBuilder setPreferredRange(String pRange) {
        this.pRange = pRange;
        return this;
    }

    public UserBuilder setMinRange(double minRange) {
        this.minRange = minRange;
        return this;
    }

    public UserBuilder setMaxRange(double maxRange) {
        this.maxRange = maxRange;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public UserBuilder setFirstname(String firstname) {
        this.firstname = firstname;
        return this;

    }

    public UserBuilder setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public UserEntity createUser() {
        return new UserEntity( firstname,privacyChecked,email,telephone,password);
    }

}
