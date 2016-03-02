package authen.qrcode.webserver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author root
 */
public class VCard {
    String Name;
    String Email;
    String Address;
    String Title;
    String Company;
    String PhoneNumber;
    String Website;

    public VCard(String Name) {
        this.Name = Name;
    }

    public VCard(String Email, String Address, String Title, String Company, String PhoneNumber, String Website) {
        this.Email = Email;
        this.Address = Address;
        this.Title = Title;
        this.Company = Company;
        this.PhoneNumber = PhoneNumber;
        this.Website = Website;
    }

    public VCard() {
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getAddress() {
        return Address;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String Company) {
        this.Company = Company;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String Website) {
        this.Website = Website;
    }
    
}
