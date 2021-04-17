package project.appClasses;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import project.ServiceLocator;
import project.commonClasses.Translator;

import java.util.ArrayList;

public class GridDetails extends GridPane {
    private Label lblFirstName;
    private Label lblLastName;
    private Label lblAddress;
    private Label lblCity;
    private Label lblZip;

    private TextField tfFirstName;
    private TextField tfLastName;
    private TextField tfAddress;
    private TextField tfCity;
    private TextField tfZip;
    private ArrayList<TextField> listTfDetails;

    public GridDetails() {
        super();
        Translator t = ServiceLocator.getServiceLocator().getTranslator();
        lblFirstName = new Label(t.getString("lbl.firstName"));
        lblLastName = new Label(t.getString("lbl.lastName"));
        lblAddress = new Label(t.getString("lbl.address"));
        lblCity = new Label(t.getString("lbl.city"));
        lblZip = new Label(t.getString("lbl.zip"));

        this.add(lblFirstName, 0, 0);
        this.add(lblLastName, 0, 1);
        this.add(lblAddress, 0, 2);
        this.add(lblCity, 0, 3);
        this.add(lblZip, 0, 4);

        tfFirstName = new TextField();
        tfLastName = new TextField();
        tfAddress = new TextField();
        tfCity = new TextField();
        tfZip = new TextField();
        listTfDetails = new ArrayList<>();
        listTfDetails.add(tfFirstName);
        listTfDetails.add(tfLastName);
        listTfDetails.add(tfAddress);
        listTfDetails.add(tfCity);
        listTfDetails.add(tfZip);

        this.add(tfFirstName, 1, 0);
        this.add(tfLastName, 1, 1);
        this.add(tfAddress, 1, 2);
        this.add(tfCity, 1, 3);
        this.add(tfZip, 1, 4);
    }

    public Label getLblFirstName() {
        return lblFirstName;
    }

    public void setLblFirstName(Label lblFirstName) {
        this.lblFirstName = lblFirstName;
    }

    public Label getLblLastName() {
        return lblLastName;
    }

    public void setLblLastName(Label lblLastName) {
        this.lblLastName = lblLastName;
    }

    public Label getLblAddress() {
        return lblAddress;
    }

    public void setLblAddress(Label lblAddress) {
        this.lblAddress = lblAddress;
    }

    public Label getLblCity() {
        return lblCity;
    }

    public void setLblCity(Label lblCity) {
        this.lblCity = lblCity;
    }

    public Label getLblZip() {
        return lblZip;
    }

    public void setLblZip(Label lblZip) {
        this.lblZip = lblZip;
    }

    public TextField getTfFirstName() {
        return tfFirstName;
    }

    public void setTfFirstName(TextField tfFirstName) {
        this.tfFirstName = tfFirstName;
    }

    public TextField getTfLastName() {
        return tfLastName;
    }

    public void setTfLastName(TextField tfLastName) {
        this.tfLastName = tfLastName;
    }

    public TextField getTfAddress() {
        return tfAddress;
    }

    public void setTfAddress(TextField tfAddress) {
        this.tfAddress = tfAddress;
    }

    public TextField getTfCity() {
        return tfCity;
    }

    public void setTfCity(TextField tfCity) {
        this.tfCity = tfCity;
    }

    public TextField getTfZip() {
        return tfZip;
    }

    public void setTfZip(TextField tfZip) {
        this.tfZip = tfZip;
    }

    public ArrayList<TextField> getListTfDetails() {
        return listTfDetails;
    }

    public void setListTfDetails(ArrayList<TextField> listTfDetails) {
        this.listTfDetails = listTfDetails;
    }
}
