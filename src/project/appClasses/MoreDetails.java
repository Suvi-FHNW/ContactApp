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

    boolean gridCreated;

    private int phoneCount = 0;
    private int emailCount = 0;

    int tfWidth = 160;


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
        // listLabels = new ArrayList<>();
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
                l.setMinWidth(95);
                l.setMaxWidth(95);
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
                Label l = new Label(t.getString("lbl.email") + " " + counter);
                listLabels.add(l);
                l.setMinWidth(95);
                l.setMaxWidth(95);
                this.add(l, 0, position);
                TextField tf = new TextField(s);
                listTextFields.add(tf);
                tf.setEditable(false);
                this.add(tf, 1, position);
                counter++;
            }
        }
        gridCreated = true;
    }

    public void initNewPhone(){
        int i = phoneCount+1;
        Label l = new Label(t.getString("lbl.phone") +" "+ i);
        l.setMinWidth(95);
        l.setMaxWidth(95);
        listLabels.add(l);
        this.add(l, 0, 0);
        TextField tf = new TextField();
        tf.setMinWidth(tfWidth);
        listTextFields.add(tf);
        this.add(tf, 1, 0);
        phoneCount++;
        gridCreated = true;
    }

    public void addNewPhone(){
        int i = phoneCount+1;
        Label l = new Label(t.getString("lbl.phone")+ " "+ i);
        l.setMinWidth(95);
        l.setMaxWidth(95);
        listLabels.add(l);
        this.add(l, 0, phoneCount);
        TextField tf = new TextField();
        tf.setMinWidth(tfWidth);
        this.add(tf, 1, phoneCount);
        phoneCount++;
    }

    public void initNewEmail(){
        int i = emailCount+1;
        Label l = new Label(t.getString("lbl.email") +" "+ i);
        l.setMinWidth(95);
        l.setMaxWidth(95);
        listLabels.add(l);
        this.add(l, 0, 0);
        TextField tf = new TextField();
        tf.setMinWidth(tfWidth);
        listTextFields.add(tf);
        this.add(tf, 1, 0);
        emailCount++;
        gridCreated = true;
    }

    public void addEmail(){
        int i = emailCount+1;
        Label l = new Label(t.getString("lbl.email")+ " "+ i);
        l.setMinWidth(95);
        l.setMaxWidth(95);
        listLabels.add(l);
        this.add(l, 0, emailCount);
        TextField tf = new TextField();
        tf.setMinWidth(tfWidth);
        this.add(tf, 1, emailCount);
        emailCount++;
    }

    public ArrayList<Label> getListLabels() {
        return listLabels;
    }

    public ArrayList<TextField> getListTextFields() {
        return listTextFields;
    }

    public String getType() {
        return type;
    }

    public Contact getActiveContact() {
        return activeContact;
    }

    public void setListLabels(ArrayList<Label> listLabels) {
        this.listLabels = listLabels;
    }

    public void setListTextFields(ArrayList<TextField> listTextFields) {
        this.listTextFields = listTextFields;
    }

    public void setGridCreated(boolean gridCreated) {
        this.gridCreated = gridCreated;
    }

    public int getPhoneCount() {
        return phoneCount;
    }

    public void setPhoneCount(int phoneCount) {
        this.phoneCount = phoneCount;
    }

    public int getEmailCount() {
        return emailCount;
    }

    public void setEmailCount(int emailCount) {
        this.emailCount = emailCount;
    }
}
