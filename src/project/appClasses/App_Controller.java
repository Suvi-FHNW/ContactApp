package project.appClasses;


import javafx.scene.control.Alert;
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

        view.getBtnOpenCreate().setOnAction(e -> {
            view.createWindowNewContact();
        });

        view.getBtnCreate().setOnAction(e -> {
                Contact c = new Contact();
                c.setFirstName(view.getGdNewEntry().getTfFirstName().getText());
                c.setLastName(view.getGdNewEntry().getTfLastName().getText());
                c.setAddress((view.getGdNewEntry().getTfAddress().getText()));
                c.setCity(view.getGdNewEntry().getTfCity().getText());
                c.setZip((view.getGdNewEntry().getTfZip().getText()));
                if (view.getGdNewEntry().checkEntry()) {
                    model.getContactTree().add(c);
                    view.updateContactList();
                    updateEventHandlerContactList();
                    serviceLocator.getLogger().info("Contact successfully created");
                    view.createSuccessAlert();

                } else {
                    view.createErrorAlert();
                }
        });

        view.getGdMain().getBtnMorePhone().setOnAction(e -> {
            if (view.getGdMain().isPhoneCollapsed()) {
                view.getGdMain().getChildren().remove(view.getGdMain().getGridMorePhone());
                view.getGdMain().add(view.getGdMain().getLblPhone(), 0, 5);
                view.getGdMain().add(view.getGdMain().getTfPhone(), 1, 5);
                view.getGdMain().setPhoneCollapsed(false);
            } else {
                view.getGdMain().getGridMorePhone().setModel(model);
                view.getGdMain().getGridMorePhone().createGrid("Phone");
                view.getGdMain().add(view.getGdMain().getGridMorePhone(), 0, 5, 2, 1);
                view.getGdMain().getChildren().remove(view.getGdMain().getLblPhone());
                view.getGdMain().getChildren().remove(view.getGdMain().getTfPhone());
                view.getGdMain().setPhoneCollapsed(true);
            }
        });


        view.getBtnCreateCancel().setOnAction(e -> {
            view.closeWindowNewContact();
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
