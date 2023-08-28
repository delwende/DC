

package com.aikinggroup.diabetecontrole.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Profile extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private String firstname;
    private String lastname;
    private String email;
    private String telephone;
    private String password;
    private String preferred_language;
    private String ville;
    private String country;
    private int age;
    private String birthdate;
    private String gender;
    private int d_type;
    private String preferred_unit;
    @Required
    private String preferred_unit_a1c;
    @Required
    private String preferred_unit_weight;
    private String preferred_range;
    private double custom_range_min;
    private double custom_range_max;
    private String ssn;
    private String cni;
    public Profile() {

    }

    public Profile(Profile copy) {
        id = copy.id;
        name = copy.name;
        preferred_language = copy.preferred_language;
        country = copy.country;
        age = copy.age;
        gender = copy.gender;
        d_type = copy.d_type;
        preferred_unit = copy.preferred_unit;
        preferred_unit_a1c = copy.preferred_unit_a1c;
        preferred_unit_weight = copy.preferred_unit_weight;
        preferred_range = copy.preferred_range;
        custom_range_max = copy.custom_range_max;
        custom_range_min = copy.custom_range_min;
        password =copy.password;
        birthdate =copy.birthdate;
        email = copy.email;
        telephone =copy.telephone;
        firstname =copy.firstname;
        lastname =copy.lastname;
        ville=copy.ville;
    }

    Profile(String name, String firstname, String lastname, String email, String telephone, String password, String preferred_language, String ville, String country, int age, String gender, int dType,
            String pUnit, String a1cUnit, String weightUnit, String pRange, double minRange, double maxRange, String ssn, String cni, String birthdate) {
        this.name = name;
        this.preferred_language = preferred_language;
        this.country = country;
        this.age = age;
        this.gender = gender;
        this.d_type = dType;
        this.preferred_unit = pUnit;
        this.preferred_unit_a1c = a1cUnit;
        this.preferred_unit_weight = weightUnit;
        this.preferred_range = pRange;
        this.custom_range_max = maxRange;
        this.custom_range_min = minRange;
        this.ville =ville;
        this.firstname=firstname;
        this.lastname =lastname;
        this.password=password;
        this.telephone =telephone;
        this.email =email;
        this.birthdate=birthdate;
        this.ssn =ssn;
        this.cni=cni;

    }

    public int getD_type() {
        return this.d_type;
    }

    public void setD_type(int dType) {
        this.d_type = dType;
    }

    public String getPreferred_unit() {
        return this.preferred_unit;
    }

    public void setPreferred_unit(String pUnit) {
        this.preferred_unit = pUnit;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPreferred_language() {
        return this.preferred_language;
    }

    public void setPreferred_language(String preferred_language) {
        this.preferred_language = preferred_language;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPreferred_range() {
        return preferred_range;
    }

    public void setPreferred_range(String preferred_range) {
        this.preferred_range = preferred_range;
    }

    public double getCustom_range_min() {
        return custom_range_min;
    }

    public void setCustom_range_min(double custom_range_min) {
        this.custom_range_min = custom_range_min;
    }

    public double getCustom_range_max() {
        return custom_range_max;
    }

    public void setCustom_range_max(double custom_range_max) {
        this.custom_range_max = custom_range_max;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPreferred_unit_a1c() {
        return preferred_unit_a1c;
    }

    public void setPreferred_unit_a1c(String preferred_unit_a1c) {
        this.preferred_unit_a1c = preferred_unit_a1c;
    }

    public String getPreferred_unit_weight() {
        return preferred_unit_weight;
    }

    public void setPreferred_unit_weight(String preferred_unit_weight) {
        this.preferred_unit_weight = preferred_unit_weight;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getCni() {
        return cni;
    }

    public void setCni(String cni) {
        this.cni = cni;
    }
}
