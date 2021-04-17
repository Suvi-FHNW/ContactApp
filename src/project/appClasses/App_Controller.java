package project.appClasses;


import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import project.ServiceLocator;
import project.abstractClasses.Controller;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class App_Controller extends Controller<App_Model, App_View> {
    ServiceLocator serviceLocator;

    public App_Controller(App_Model model, App_View view) {
        super(model, view);
        
        for (Label l : view.getContactList()) {
            l.setOnMouseClicked(e -> {
                updateDetails(l);
                System.out.println("Label clicked");
            });
        }

        view.getBtnEdit().setOnAction(e -> {
           for (TextField tf : view.getGdMain().getListTfDetails()) {
               tf.setEditable(true);
           }
        });

        view.getBtnSave().setOnAction(e -> {
            updateDetails();
            view.updateContactList();
            updateEventHandlerContactList();
        });

        view.getBtnDelete().setOnAction(e -> {
            deleteContact();
            updateEventHandlerContactList();
        });

        view.getBtnCreate().setOnAction(e -> {
            view.createWindowNewContact();
        });
        
        serviceLocator = ServiceLocator.getServiceLocator();        
        serviceLocator.getLogger().info("Application controller initialized");
    }
    
    private void updateDetails(Label l) {
        String s = l.getText();
        String[] name = s.split(" ");
        Contact contact = null;
        for (Contact c : model.getContactTree()) {
            if ((c.getFirstName().equals(name[0])) && (c.getLastName().equals(name[1]))) {
                contact = c;
                c.setActive(true);
                serviceLocator.getLogger().info("Contact found");
            } else {
                c.setActive(false);
            }
        }

        view.getGdMain().getTfFirstName().setText(contact.getFirstName());
        view.getGdMain().getTfLastName().setText(contact.getLastName());
        view.getGdMain().getTfAddress().setText(contact.getAddress());
        view.getGdMain().getTfCity().setText(contact.getCity());
        view.getGdMain().getTfZip().setText(contact.getZip());
    }

    private void updateDetails() {
        for (Contact c : model.getContactTree()) {
            if (c.isActive()) {
                c.setFirstName(view.getGdMain().getTfFirstName().getText());
                c.setLastName(view.getGdMain().getTfLastName().getText());
                c.setAddress(view.getGdMain().getTfAddress().getText());
                c.setCity(view.getGdMain().getTfCity().getText());
                c.setZip(view.getGdMain().getTfZip().getText());
            }
        }
    }

    private void deleteContact() {
        Contact contact = null;
        for (Contact c : model.getContactTree()) {
            if (c.isActive()) {
                contact = c;
            }
        }

        model.getContactTree().remove(contact);
        view.updateContactList();
    }

    private void createContact() {
        view.getGdMain().getTfFirstName().setText("");
        view.getGdMain().getTfLastName().setText("");
        view.getGdMain().getTfAddress().setText("");
        view.getGdMain().getTfCity().setText("");
        view.getGdMain().getTfZip().setText("");

    }

    private void updateEventHandlerContactList(){
        for (Label l : view.getContactList()) {
            l.setOnMouseClicked(event -> {
                updateDetails(l);
            });
        }
    }
}
