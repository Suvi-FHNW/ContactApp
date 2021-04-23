package project.appClasses;

import javafx.scene.control.Button;
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
    private Label lblPhone;
    private Label lblEmail;

    private TextField tfFirstName;
    private TextField tfLastName;
    private TextField tfAddress;
    private TextField tfCity;
    private TextField tfZip;
    private TextField tfPhone;
    private TextField tfEmail;

    private ArrayList<TextField> listTfDetails;

    private Button btnMorePhone;
    private Button btnMoreEmail;

    private boolean phoneCollapsed;
    private boolean emailCollapsed;

    MoreDetails gridMorePhone = new MoreDetails();
    MoreDetails gridMoreEmail = new MoreDetails();

    private int lengthOfLabels = 150;


    public GridDetails() {
        super();
        Translator t = ServiceLocator.getServiceLocator().getTranslator();
        lblFirstName = new Label(t.getString("lbl.firstName"));
        lblFirstName.setMaxWidth(lengthOfLabels);
        lblLastName = new Label(t.getString("lbl.lastName"));
        lblLastName.setMaxWidth(lengthOfLabels);
        lblAddress = new Label(t.getString("lbl.address"));
        lblAddress.setMaxWidth(lengthOfLabels);
        lblCity = new Label(t.getString("lbl.city"));
        lblCity.setMaxWidth(lengthOfLabels);
        lblZip = new Label(t.getString("lbl.zip"));
        lblZip.setMaxWidth(lengthOfLabels);
        lblPhone = new Label(t.getString("lbl.phone"));
        lblPhone.setMaxWidth(lengthOfLabels);
        lblEmail = new Label(t.getString("lbl.email"));
        lblEmail.setMaxWidth(lengthOfLabels);

        this.add(lblFirstName, 0, 0);
        this.add(lblLastName, 0, 1);
        this.add(lblAddress, 0, 2);
        this.add(lblCity, 0, 3);
        this.add(lblZip, 0, 4);
        this.add(lblPhone, 0, 5);
        this.add(lblEmail, 0, 6);

        tfFirstName = new TextField();
        tfLastName = new TextField();
        tfAddress = new TextField();
        tfCity = new TextField();
        tfZip = new TextField();
        tfPhone = new TextField();
        tfEmail = new TextField();
        listTfDetails = new ArrayList<>();
        listTfDetails.add(tfFirstName);
        listTfDetails.add(tfLastName);
        listTfDetails.add(tfAddress);
        listTfDetails.add(tfCity);
        listTfDetails.add(tfZip);
        listTfDetails.add(tfPhone);
        listTfDetails.add(tfEmail);


        this.add(tfFirstName, 1, 0);
        this.add(tfLastName, 1, 1);
        this.add(tfAddress, 1, 2);
        this.add(tfCity, 1, 3);
        this.add(tfZip, 1, 4);
        this.add(tfPhone, 1, 5);
        this.add(tfEmail, 1, 6);

        btnMorePhone = new Button(t.getString("btn.more"));
        btnMoreEmail = new Button(t.getString("btn.more"));

        this.add(btnMorePhone, 2, 5);
        this.add(btnMoreEmail, 2, 6);
    }

    public boolean checkEntry(){
        // check textfields for whitespace in name as this would lead to a nullpointer
        if (tfFirstName.getText().contains(" ")) {
            return false;
        } else if (tfLastName.getText().contains(" ")) {
            return false;
        }
        return true;
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

    public Label getLblPhone() {
        return lblPhone;
    }

    public Label getLblEmail() {
        return lblEmail;
    }

    public TextField getTfPhone() {
        return tfPhone;
    }

    public TextField getTfEmail() {
        return tfEmail;
    }

    public Button getBtnMorePhone() {
        return btnMorePhone;
    }

    public Button getBtnMoreEmail() {
        return btnMoreEmail;
    }

    public void setLblPhone(Label lblPhone) {
        this.lblPhone = lblPhone;
    }

    public void setLblEmail(Label lblEmail) {
        this.lblEmail = lblEmail;
    }

    public void setTfPhone(TextField tfPhone) {
        this.tfPhone = tfPhone;
    }

    public void setTfEmail(TextField tfEmail) {
        this.tfEmail = tfEmail;
    }

    public void setBtnMorePhone(Button btnMorePhone) {
        this.btnMorePhone = btnMorePhone;
    }

    public void setBtnMoreEmail(Button btnMoreEmail) {
        this.btnMoreEmail = btnMoreEmail;
    }

    public boolean isPhoneCollapsed() {
        return phoneCollapsed;
    }

    public void setPhoneCollapsed(boolean phoneCollapsed) {
        this.phoneCollapsed = phoneCollapsed;
    }

    public boolean isEmailCollapsed() {
        return emailCollapsed;
    }

    public void setEmailCollapsed(boolean emailCollapsed) {
        this.emailCollapsed = emailCollapsed;
    }

    public MoreDetails getGridMorePhone() {
        return gridMorePhone;
    }

    public MoreDetails getGridMoreEmail() {
        return gridMoreEmail;
    }

    public void setGridMorePhone(MoreDetails gridMorePhone) {
        this.gridMorePhone = gridMorePhone;
    }

    public void setGridMoreEmail(MoreDetails gridMoreEmail) {
        this.gridMoreEmail = gridMoreEmail;
    }
}
