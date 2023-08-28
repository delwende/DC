package com.aikinggroup.diabetecontrole.resources;

import com.aikinggroup.diabetecontrole.db.entity.UserEntity;

public class User {

    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private String telephone;
    private String password;
    private String ssn;
    private String cni;
    public User() {

    }

    public User(User copy) {

        password =copy.password;
        email = copy.email;
        telephone =copy.telephone;
        firstname =copy.firstname;
        lastname =copy.lastname;
    }

    public User(String firstname, String lastname, String email, String telephone, String password) {

        this.firstname=firstname;
        this.lastname =lastname;
        this.password=password;
        this.telephone =telephone;
        this.email =email;


    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
