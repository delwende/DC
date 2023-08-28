package com.aikinggroup.diabetecontrole.db;


public class ProfileBuilder {
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
    private String birthdate;
    public ProfileBuilder setId(int id) {
        return this;
    }

    public ProfileBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProfileBuilder setPreferredLanguage(String language) {
        this.preferredLanguage = language;
        return this;
    }

    public ProfileBuilder setCountry(String country) {
        this.country = country;
        return this;
    }

    public ProfileBuilder setAge(int age) {
        this.age = age;
        return this;
    }

    public ProfileBuilder setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public ProfileBuilder setDiabetesType(int dType) {
        this.diabetesType = dType;
        return this;
    }

    public ProfileBuilder setPreferredUnit(String unit) {
        this.preferredUnit = unit;
        return this;
    }

    public ProfileBuilder setPreferredA1CUnit(String a1cUnit) {
        this.a1cUnit = a1cUnit;
        return this;
    }

    public ProfileBuilder setPreferredWeightUnit(String weightUnit) {
        this.preferredWeightUnit = weightUnit;
        return this;
    }

    public ProfileBuilder setPreferredRange(String pRange) {
        this.pRange = pRange;
        return this;
    }

    public ProfileBuilder setMinRange(double minRange) {
        this.minRange = minRange;
        return this;
    }

    public ProfileBuilder setMaxRange(double maxRange) {
        this.maxRange = maxRange;
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Profile createProfile() {
        return new Profile( name,firstname,lastname,email,telephone,password, preferredLanguage, ville,country, age, gender, diabetesType, preferredUnit, a1cUnit,
                preferredWeightUnit, pRange, minRange, maxRange,ssn,cni,birthdate);
    }

}
