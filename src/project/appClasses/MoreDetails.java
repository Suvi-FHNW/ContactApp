package project.appClasses;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import project.ServiceLocator;
import project.commonClasses.Translator;

import java.util.ArrayList;

public class MoreDetails extends GridPane {
    private Translator t;
    private ServiceLocator sl;

    private App_Model model;
    ArrayList<Label> listLabels;
    ArrayList<TextField> listTextFields;
    String type;
    Contact activeContact;


    public MoreDetails() {
        super();
        sl = ServiceLocator.getServiceLocator();
        t = sl.getTranslator();
        listLabels = new ArrayList<>();
        listTextFields = new ArrayList<>();
    }

    public void setModel(App_Model model) {
        this.model = model;
    }

    public void createGrid(String type) {
        int counter = 1;
        int position;
        for (Contact c : model.getContactTree()) {
            if (c.isActive()) {
                activeContact = c;
            }
        }

        if (type.equals("Phone") || type.equals("Telefon")) {
            for (String s : activeContact.getPhoneNumbers()) {
                position = counter-1;
                Label l = new Label(t.getString("lbl.phone") + " " + counter);
                listLabels.add(l);
                this.add(l, 0, position);
                TextField tf = new TextField(s);
                listTextFields.add(tf);
                tf.setEditable(false);
                this.add(tf, 1, position);
                counter++;
            }
        } else if (type.equals("Email")) {
            for (String s : activeContact.getEmailAdresses()) {
                position = counter-1;
                Label l = new Label(t.getString("Email") + " " + counter);
                listLabels.add(l);
                this.add(l, 0, position);
                TextField tf = new TextField(s);
                listTextFields.add(tf);
                tf.setEditable(false);
                this.add(tf, 1, position);
                counter++;
            }
        }
    }
}
