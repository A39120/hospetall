package pt.hospetall.web.person;

import pt.hospetall.web.account.Account;

import javax.persistence.*;

@Entity
@Table(name="PERSON")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Person {

    @Id
    @GeneratedValue
    private int id;
    private String familyName;
    private String givenName;
    private String email;
    private String telephone;

    @OneToOne
    private Account account;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
