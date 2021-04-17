package project.appClasses;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Logger;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
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
	private ServiceLocator sl;
    Menu menuFile;
    Menu menuFileLanguage;
    Menu menuHelp;

    private Pane root;
    private VBox boxOverview;
    private GridPane gridDetails;
    private VBox rootRight;
    private GridDetails gdMain;
    private GridDetails gdNewEntry;

    private Label title;
    private ArrayList<Label> contactList;
    private ScrollPane paneContactList;
    private VBox boxContactList;

    private Button btnEdit;
    private Button btnSave;
    private Button btnDelete;
    private Button btnCreate;

	public App_View(Stage stage, App_Model model) {
        super(stage, model);
        ServiceLocator.getServiceLocator().getLogger().info("Application view initialized");
    }

	@Override
	protected Scene create_GUI() {
	    sl = ServiceLocator.getServiceLocator();
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
	    menuBar.setTranslateX(500);
		
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
			Translator t = sl.getTranslator();
	        // The menu entries
	       menuFile.setText(t.getString("program.menu.file"));
	       menuFileLanguage.setText(t.getString("program.menu.file.language"));
           menuHelp.setText(t.getString("program.menu.help"));
	        
	        // Other controls
           // btnClick.setText(t.getString("button.clickme"));
           gdMain.getLblFirstName().setText(t.getString("lbl.firstName"));
           gdMain.getLblLastName().setText(t.getString("lbl.lastName"));
           gdMain.getLblAddress().setText(t.getString("lbl.address"));
           gdMain.getLblCity().setText(t.getString("lbl.city"));
           gdMain.getLblZip().setText(t.getString("lbl.zip"));
           title.setText(t.getString("lbl.title"));
           btnEdit.setText(t.getString("btn.edit"));
           btnSave.setText(t.getString("btn.save"));
           btnDelete.setText(t.getString("btn.delete"));
           btnCreate.setText(t.getString("btn.create"));
           stage.setTitle(t.getString("program.name"));
	   }

	   private VBox setRootLeft() {
	    boxOverview = new VBox(20);
	    paneContactList = new ScrollPane();
	    boxContactList = new VBox();
	    title = new Label(t.getString("lbl.title"));
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

       private VBox setRootRight() {
		   rootRight = new VBox(50);
		   gdMain = new GridDetails();
		   rootRight.getChildren().add(gdMain);

		   btnEdit = new Button(t.getString("btn.edit"));
		   btnSave = new Button(t.getString("btn.save"));
		   btnDelete = new Button(t.getString("btn.delete"));
		   btnCreate = new Button(t.getString("btn.create"));
		   HBox box = new HBox(5);
		   box.getChildren().addAll(btnCreate, btnEdit, btnSave, btnDelete);
		   rootRight.getChildren().add(box);

		   for (TextField f : gdMain.getListTfDetails()) {
			   f.setEditable(false);
			   f.setMinWidth(200);
		   }

		   rootRight.setTranslateX(440);
		   rootRight.setTranslateY(100);

		   return rootRight;
	   }

	public void updateContactList() {
		boxContactList.getChildren().removeAll(contactList);
		contactList = new ArrayList<>();
		for (Contact c : model.getContactTree()) {
			Label l = new Label(c.getFirstName()+" "+c.getLastName());
			contactList.add(l);
			l.setMinWidth(300);
		}
		boxContactList.getChildren().addAll(contactList);
	}

	   public void createWindowNewContact() {
		Pane root = new Pane();
		gdNewEntry = new GridDetails();
		root.getChildren().add(gdNewEntry);



		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	   }

	public ArrayList<Label> getContactList() {
		return contactList;
	}

	public void setContactList(ArrayList<Label> contactList) {
		this.contactList = contactList;
	}



	public Button getBtnEdit() {
		return btnEdit;
	}


	public Button getBtnSave() {
		return btnSave;
	}



	public Button getBtnDelete() {
		return btnDelete;
	}

	public Button getBtnCreate() {
		return btnCreate;
	}

	public GridDetails getGdMain() {
		return gdMain;
	}
}

