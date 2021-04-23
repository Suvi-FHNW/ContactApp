package project.appClasses;


import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import project.ServiceLocator;
import project.abstractClasses.Controller;
import project.commonClasses.Translator;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class App_Controller extends Controller<App_Model, App_View> {
    ServiceLocator serviceLocator;
    Translator translator;

    public App_Controller(App_Model model, App_View view) {
        super(model, view);

        serviceLocator = ServiceLocator.getServiceLocator();
        translator = serviceLocator.getTranslator();

        for (Label l : view.getContactList()) {


            l.setOnMouseClicked(e -> {
                updateDetails(l);
                serviceLocator.getLogger().info("Label clicked");
                view.getGdMain().getBtnMorePhone().setDisable(false);
                view.getGdMain().getBtnMoreEmail().setDisable(false);
            });

            l.setOnMouseEntered(e -> {
                l.getStyleClass().add("lbl_hover");
            });

            l.setOnMouseExited(e -> {
                l.getStyleClass().remove("lbl_hover")
;            });
        }



        view.getBtnEdit().setOnAction(e -> {
           for (TextField tf : view.getGdMain().getListTfDetails()) {
               tf.setEditable(true);
           }
        });

        view.getBtnSave().setOnAction(e -> {
            saveChangesToObject();
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
            if (createContact()) {
                serviceLocator.getLogger().info("New Contact successfully created");
            } else {
                serviceLocator.getLogger().info("Creating new contact failed");
            }
        });

        view.getGdMain().getBtnMorePhone().setOnAction(e -> {
            if (view.getGdMain().isPhoneCollapsed()) {
                view.getGdMain().getChildren().remove(view.getGdMain().getGridMorePhone());
                view.getGdMain().add(view.getGdMain().getLblPhone(), 0, 5);
                view.getGdMain().add(view.getGdMain().getTfPhone(), 1, 5);
                view.getGdMain().setPhoneCollapsed(false);
                view.getGdMain().getBtnMorePhone().setText(translator.getString("btn.more"));
            } else {
                if (!view.getGdMain().getGridMorePhone().gridCreated) {
                    view.getGdMain().getGridMorePhone().setModel(model);
                    view.getGdMain().getGridMorePhone().createGrid("Phone");
                }
                view.getGdMain().add(view.getGdMain().getGridMorePhone(), 0, 5, 2, 1);
                view.getGdMain().getChildren().remove(view.getGdMain().getLblPhone());
                view.getGdMain().getChildren().remove(view.getGdMain().getTfPhone());
                view.getGdMain().setPhoneCollapsed(true);
                view.getGdMain().getBtnMorePhone().setText(translator.getString("btn.less"));
            }
        });

        view.getGdMain().getBtnMoreEmail().setOnAction(e -> {
            if (view.getGdMain().isEmailCollapsed()) {
                view.getGdMain().getChildren().remove(view.getGdMain().getGridMoreEmail());
                view.getGdMain().add(view.getGdMain().getLblEmail(), 0, 6);
                view.getGdMain().add(view.getGdMain().getTfEmail(), 1, 6);
                view.getGdMain().setEmailCollapsed(false);
                view.getGdMain().getBtnMoreEmail().setText(translator.getString("btn.more"));
            } else {
                if (!view.getGdMain().getGridMoreEmail().gridCreated) {
                    view.getGdMain().getGridMoreEmail().setModel(model);
                    view.getGdMain().getGridMoreEmail().createGrid("Email");
                }
                view.getGdMain().add(view.getGdMain().getGridMoreEmail(), 0, 6, 2, 1);
                view.getGdMain().getChildren().remove(view.getGdMain().getLblEmail());
                view.getGdMain().getChildren().remove(view.getGdMain().getTfEmail());
                view.getGdMain().setEmailCollapsed(true);
                view.getGdMain().getBtnMoreEmail().setText(translator.getString("btn.less"));
            }
        });

        view.getGdNewEntry().getBtnMorePhone().setOnAction(e -> {
           if (view.getGdNewEntry().getGridMorePhone().gridCreated) {
               view.getGdNewEntry().getGridMorePhone().addNewPhone();
           } else {
               view.getGdNewEntry().getChildren().remove(view.getGdNewEntry().getLblPhone());
               view.getGdNewEntry().getChildren().remove(view.getGdNewEntry().getTfPhone());
               view.getGdNewEntry().getGridMorePhone().initNewPhone();
               view.getGdNewEntry().add(view.getGdNewEntry().getGridMorePhone(), 0, 5, 2, 1);
               view.getGdNewEntry().getGridMorePhone().addNewPhone();
           }
        });

        view.getGdNewEntry().getBtnMoreEmail().setOnAction(e -> {
            if (view.getGdNewEntry().getGridMoreEmail().gridCreated) {
                view.getGdNewEntry().getGridMoreEmail().addEmail();
            } else {
                view.getGdNewEntry().getChildren().remove(view.getGdNewEntry().getLblEmail());
                view.getGdNewEntry().getChildren().remove(view.getGdNewEntry().getTfEmail());
                view.getGdNewEntry().getGridMoreEmail().initNewEmail();
                view.getGdNewEntry().add(view.getGdNewEntry().getGridMoreEmail(), 0, 6, 2, 1);
                view.getGdNewEntry().getGridMoreEmail().addEmail();
            }
        });


        view.getBtnCreateCancel().setOnAction(e -> {
            view.closeWindowNewContact();
        });

        view.getStageNewEntry().setOnCloseRequest(e-> {
            view.closeWindowNewContact();
        });

        view.getStage().setOnCloseRequest(e -> {
            model.saveDataToFile();
            serviceLocator.getLogger().info("Data saved to file");
        });



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
        view.getGdMain().getTfPhone().setText(contact.getPhoneNumbers().get(0));
        view.getGdMain().getTfEmail().setText(contact.getEmailAdresses().get(0));
    }

    private boolean createContact() {
        boolean success = false;
        Contact c = new Contact();
        c.setFirstName(view.getGdNewEntry().getTfFirstName().getText());
        c.setLastName(view.getGdNewEntry().getTfLastName().getText());
        c.setAddress((view.getGdNewEntry().getTfAddress().getText()));
        c.setCity(view.getGdNewEntry().getTfCity().getText());
        c.setZip((view.getGdNewEntry().getTfZip().getText()));
        if (view.getGdNewEntry().getGridMorePhone().getPhoneCount() > 1) {
            for (TextField t : view.getGdNewEntry().getGridMorePhone().getListTextFields()) {
                if (t.getText().equals("")) {
                    // do nothing, empty phone number
                } else {
                    c.getPhoneNumbers().add(t.getText());
                }
            }
        } else {
            c.getPhoneNumbers().add(view.getGdNewEntry().getTfPhone().getText());
        }
        if (view.getGdNewEntry().getGridMoreEmail().getEmailCount() > 1) {
            for (TextField t : view.getGdNewEntry().getGridMoreEmail().getListTextFields()) {
                if (t.getText().equals("")) {
                    // do nothing
                } else {
                    c.getEmailAdresses().add(t.getText());
                }
            }
        } else {
            c.getEmailAdresses().add(view.getGdNewEntry().getTfEmail().getText());
        }
        if (view.getGdNewEntry().checkEntry()) {
            model.getContactTree().add(c);
            success = true;
            view.updateContactList();
            updateEventHandlerContactList();
            serviceLocator.getLogger().info("Contact successfully created");
            view.createSuccessAlert();
            resetDetails();
        } else {
            view.createErrorAlert();
            success = false;
        }
        return success;
    }

    private void saveChangesToObject() {
        for (Contact c : model.getContactTree()) {
            if (c.isActive()) {
                c.setFirstName(view.getGdMain().getTfFirstName().getText());
                c.setLastName(view.getGdMain().getTfLastName().getText());
                c.setAddress(view.getGdMain().getTfAddress().getText());
                c.setCity(view.getGdMain().getTfCity().getText());
                c.setZip(view.getGdMain().getTfZip().getText());
                // TODO PhoneNumbers and Email
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

    private void resetDetails() {
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
