package project.appClasses;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Logger;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import project.ServiceLocator;
import project.abstractClasses.View;
import project.commonClasses.Translator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Copyright 2015, FHNW, Prof. Dr. Brad Richards. All rights reserved. This code
 * is licensed under the terms of the BSD 3-clause license (see the file
 * license.txt).
 * 
 * @author Brad Richards
 */
public class App_View extends View<App_Model> {
	private Translator t;
    Menu menuFile;
    Menu menuFileLanguage;
    Menu menuHelp;
    
    Label lblNumber;
    Button btnClick;

    private Pane root;
    private VBox boxOverview;
    private GridPane gridDetails;

    private Label title;
    private ArrayList<Label> contactList = new ArrayList<>();
    private ScrollPane paneContactList;
    private VBox boxContactList;

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

	public App_View(Stage stage, App_Model model) {
        super(stage, model);
        ServiceLocator.getServiceLocator().getLogger().info("Application view initialized");
    }

	@Override
	protected Scene create_GUI() {
	    ServiceLocator sl = ServiceLocator.getServiceLocator();  
	    Logger logger = sl.getLogger();
	    t = sl.getTranslator();
	    
	    MenuBar menuBar = new MenuBar();
	    menuFile = new Menu();
	    menuFileLanguage = new Menu();
	    menuFile.getItems().add(menuFileLanguage);
	    
       for (Locale locale : sl.getLocales()) {
           MenuItem language = new MenuItem(locale.getLanguage());
           menuFileLanguage.getItems().add(language);
           language.setOnAction( event -> {
				sl.getConfiguration().setLocalOption("Language", locale.getLanguage());
                sl.setTranslator(new Translator(locale.getLanguage()));
                updateTexts();
            });
        }
	    
        menuHelp = new Menu();
	    menuBar.getMenus().addAll(menuFile, menuHelp);
		
		root = new Pane();
		root.getChildren().add(menuBar);
		root.getChildren().add(setRootLeft());
		root.getChildren().add(setRootRight());
        
        updateTexts();
		
        Scene scene = new Scene(root, 805, 500);
        scene.getStylesheets().add(
                getClass().getResource("app.css").toExternalForm());
        return scene;
	}
	
	   protected void updateTexts() {
	        
	        // The menu entries
	       menuFile.setText(t.getString("program.menu.file"));
	       menuFileLanguage.setText(t.getString("program.menu.file.language"));
           menuHelp.setText(t.getString("program.menu.help"));
	        
	        // Other controls
           // btnClick.setText(t.getString("button.clickme"));
           
           stage.setTitle(t.getString("program.name"));
	   }

	   private VBox setRootLeft() {
	    boxOverview = new VBox(20);
	    paneContactList = new ScrollPane();
	    boxContactList = new VBox();
	    title = new Label("Contacts");
	    boxContactList.setMaxWidth(500);
	    boxOverview.getChildren().add(title);
	    contactList = new ArrayList<>();

	    for (Contact c : model.getContactTree()) {
	        Label l = new Label(c.getFirstName() + " " + c.getLastName());
	        l.setMinWidth(300);
	        contactList.add(l);
        }
	    boxContactList.getChildren().addAll(contactList);
	    paneContactList.setContent(boxContactList);
	    paneContactList.setMaxHeight(350);
	    paneContactList.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	    paneContactList.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	    boxOverview.getChildren().add(paneContactList);
	    boxOverview.setTranslateY(50);
	    boxOverview.setTranslateX(50);
	    return boxOverview;
       }

       private GridPane setRootRight() {
		   gridDetails = new GridPane();

		   lblFirstName = new Label(t.getString("lbl.firstName"));
		   lblLastName = new Label(t.getString("lbl.lastName"));
		   lblAddress = new Label(t.getString("lbl.address"));
		   lblCity = new Label(t.getString("lbl.city"));
		   lblZip = new Label(t.getString("lbl.zip"));

		   gridDetails.add(lblFirstName, 0, 0);
		   gridDetails.add(lblLastName, 0, 1);
		   gridDetails.add(lblAddress, 0, 2);
		   gridDetails.add(lblCity, 0, 3);
		   gridDetails.add(lblZip, 0, 4);

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

		   gridDetails.add(tfFirstName, 1, 0);
		   gridDetails.add(tfLastName, 1, 1);
		   gridDetails.add(tfAddress, 1, 2);
		   gridDetails.add(tfCity, 1, 3);
		   gridDetails.add(tfZip, 1, 4);

		   for (TextField f : listTfDetails) {
			   f.setEditable(false);
		   }

		   gridDetails.setTranslateX(440);
		   gridDetails.setTranslateY(100);

		   return gridDetails;
	   }

}

