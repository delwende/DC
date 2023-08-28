

package com.aikinggroup.diabetecontrole.db.entity;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserEntity extends RealmObject {

    @PrimaryKey
    public ObjectId _id;
    private String fullname;
    private boolean privacyChecked;
    private String email;
    private String telephone;
    private String password;
    private String ssn;
    private String cni;
    public UserEntity() {


    }

    public UserEntity(UserEntity copy) {
        _id = new ObjectId();

        password =copy.password;
        email = copy.email;
        telephone =copy.telephone;
        fullname =copy.fullname;
        privacyChecked =copy.privacyChecked;
    }

    public UserEntity(String fullname, boolean privacyChecked, String email, String telephone, String password) {

        this.fullname=fullname;
        this.privacyChecked =privacyChecked;
        this.password=password;
        this.telephone =telephone;
        this.email =email;


    }



    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean isPrivacyChecked() {
        return privacyChecked;
    }

    public void setPrivacyChecked(boolean privacyChecked) {
        this.privacyChecked = privacyChecked;
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
