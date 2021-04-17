package project.appClasses;

import project.ServiceLocator;
import project.abstractClasses.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.TreeSet;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class App_Model extends Model {
    private ServiceLocator serviceLocator;

    private TreeSet<Contact> contactTree = new TreeSet<>();
    
    public App_Model() {
        serviceLocator = ServiceLocator.getServiceLocator();
        readData();

        /*
        myFirstContact = new Contact();
        myFirstContact.setFirstName("Abel");
        contactTree.add(myFirstContact);
         */

        for (Contact c : contactTree) {
            System.out.println(c.getFirstName());
        }


        serviceLocator.getLogger().info("Application model initialized");
    }

    public void readData() {
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("project/appClasses/ressources/contacts.csv");
        String dataString ="";
        String data[] = new String[10];
        try (BufferedReader fileIn = new BufferedReader(new InputStreamReader(inStream))) {
            for (int i = 0; i < 20; i++) {
                dataString = fileIn.readLine();
                data = dataString.split(",");
                Contact c = new Contact();
                c.setFirstName(data[0]);
                c.setLastName(data[1]);
                c.setAddress(data[2]);
                c.setCity(data[3]);
                c.setZip(data[4]);

                contactTree.add(c);
            }
        } catch (IOException e) {
            serviceLocator.getLogger().info("Input file not found!");
        }

    }

    public TreeSet<Contact> getContactTree() {
        return contactTree;
    }

}
