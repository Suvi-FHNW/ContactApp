package project.appClasses;

import project.ServiceLocator;
import project.abstractClasses.Model;

import java.io.*;
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

        serviceLocator.getLogger().info("Application model initialized");
    }

    public void readData() {
        InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("project/appClasses/ressources/contacts.csv");
        String dataString ="";
        String data[];
        String dataPhone[];
        String dataEmail[];
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
                dataPhone = data[5].split("\\.");
                for (String s : dataPhone) {
                    c.getPhoneNumbers().add(s);
                }
                dataEmail = data[6].split("%");
                for (String s : dataEmail) {
                    c.getEmailAdresses().add(s);
                }
                contactTree.add(c);
            }
        } catch (IOException e) {
            serviceLocator.getLogger().info("Input file not found!");
        }
    }

    public void saveDataToFile() {
        File file = new File("src/project/appClasses/ressources/contacts.csv");
        String data="";
        try (FileWriter fileOut = new FileWriter(file)) {
            for (Contact c : contactTree) {
                data = c.getFirstName()+","+c.getLastName()+","+c.getAddress()+","+c.getCity()+","+c.getZip()+",";
                for (int i = 0; i < c.getPhoneNumbers().size(); i++) {
                    data += c.getPhoneNumbers().get(i);
                    if (i < c.getPhoneNumbers().size()-1) {
                        data += ".";
                    }
                }
                data += ",";
                for (int i = 0; i < c.getEmailAdresses().size(); i++) {
                    data += c.getEmailAdresses().get(i);
                    if (i < c.getEmailAdresses().size()-1) {
                        data += "%";
                    }
                }
                fileOut.write(data+"\n");
                fileOut.flush();
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public TreeSet<Contact> getContactTree() {
        return contactTree;
    }

}
