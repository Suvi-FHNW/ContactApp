package project.appClasses;

import java.util.ArrayList;

public class Contact implements Comparable<Contact>{
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone1;
    private String phone2;
    private ArrayList<String>  emailAdresses;
    static int idStatic = 1;
    private int id;
    private boolean active;

    public Contact() {
        id = idStatic;
        idStatic++;
    }


    @Override
    public int compareTo(Contact o) {
       int result = this.firstName.compareTo(o.firstName);
       if (result == 0) {
           result = this.id - o.id;
       }
       return result;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public ArrayList<String> getEmailAdresses() {
        return emailAdresses;
    }

    public void setEmailAdresses(ArrayList<String> emailAdresses) {
        this.emailAdresses = emailAdresses;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
