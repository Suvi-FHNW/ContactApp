package project.appClasses;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import jdk.nashorn.internal.ir.Labels;
import project.ServiceLocator;
import project.abstractClasses.View;
import project.commonClasses.Translator;
import javafx.scene.Scene;
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

    private Stage stageNewEntry;

    private Pane root;
    private VBox boxOverview;
    private GridPane gridDetails;
    private VBox rootRight;
    private GridDetails gdMain;
    private GridDetails gdNewEntry = new GridDetails();

    private Label title;
    private ArrayList<Label> contactList;
    private ScrollPane paneContactList;
    private VBox boxContactList;

    private Button btnEdit;
    private Button btnSave;
    private Button btnDelete;
    private Button btnOpenCreate;
    private Button btnCreate = new Button();
    private Button btnCreateCancel = new Button();

	private Alert alertError;
	private Alert alertSuccess;


	public App_View(Stage stage, App_Model model) {
        super(stage, model);
        ServiceLocator.getServiceLocator().getLogger().info("Application view initialized");
    }

	@Override
	protected Scene create_GUI() {
	    sl = ServiceLocator.getServiceLocator();
	    Logger logger = sl.getLogger();
	    t = sl.getTranslator();

	    initializeAlerts();
	    
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

	    menuBar.getMenus().add(menuFile);
	    menuBar.setTranslateX(400);
		
		root = new Pane();
		root.getChildren().add(menuBar);
		root.getChildren().add(setRootLeft());
		root.getChildren().add(setRootRight());

		root.getChildren().add(btnOpenCreate);
		btnOpenCreate.setTranslateX(350);
		btnOpenCreate.setTranslateY(500);
        stageNewEntry = new Stage();
        stageNewEntry.setResizable(false);
        updateTexts();
		
        Scene scene = new Scene(root, 550, 700);
        scene.getStylesheets().add(
                getClass().getResource("app.css").toExternalForm());
        return scene;
	}

	private void initializeAlerts() {
		alertError = new Alert(Alert.AlertType.ERROR);
		alertError.setContentText(t.getString("msg.inputError"));
		alertSuccess = new Alert(Alert.AlertType.CONFIRMATION);
		alertSuccess.setContentText(t.getString("msg.inputSuccess"));
	}

	protected void updateTexts() {
			Translator t = sl.getTranslator();
	        // The menu entries
	       menuFile.setText(t.getString("program.menu.file"));
	       menuFileLanguage.setText(t.getString("program.menu.file.language"));
	        
	        // Other controls
           // btnClick.setText(t.getString("button.clickme"));
           gdMain.getLblFirstName().setText(t.getString("lbl.firstName"));
           gdMain.getLblLastName().setText(t.getString("lbl.lastName"));
           gdMain.getLblAddress().setText(t.getString("lbl.address"));
           gdMain.getLblCity().setText(t.getString("lbl.city"));
           gdMain.getLblZip().setText(t.getString("lbl.zip"));
           if (gdMain.isPhoneCollapsed()) {
			   gdMain.getBtnMorePhone().setText(t.getString("btn.less"));
		   } else {
           	   gdMain.getBtnMorePhone().setText(t.getString("btn.more"));
		   }
           if (gdMain.isEmailCollapsed()) {
           	   gdMain.getBtnMoreEmail().setText(t.getString("btn.less"));
		   } else {
           	   gdMain.getBtnMoreEmail().setText(t.getString("btn.more"));
		   }
           gdMain.getLblPhone().setText(t.getString("lbl.phone"));
           gdMain.getLblEmail().setText(t.getString("lbl.email"));


           for (int i = 0; i < gdMain.getGridMorePhone().getListLabels().size(); i++) {
           		int pos = i+1;
           		gdMain.getGridMorePhone().getListLabels().get(i).setText(t.getString("lbl.phone") + " " + pos);
		   }

           title.setText(t.getString("lbl.title"));
           btnEdit.setText(t.getString("btn.edit"));
           btnSave.setText(t.getString("btn.save"));
           btnDelete.setText(t.getString("btn.delete"));
           btnOpenCreate.setText(t.getString("btn.create"));
           stage.setTitle(t.getString("program.name"));
           if (btnCreate != null) {
           	btnCreate.setText(t.getString("btn.create"));
           	btnCreateCancel.setText(t.getString("btn.createCancel"));
		   }
           alertError.setContentText(t.getString("msg.inputError"));
           alertSuccess.setContentText(t.getString("msg.inputSuccess"));

           stageNewEntry.setTitle(t.getString("lbl.titleNewContact"));

	   }

	   private VBox setRootLeft() {
	    boxOverview = new VBox(20);
	    paneContactList = new ScrollPane();
	    paneContactList.setMaxWidth(200);
	    boxContactList = new VBox();
	    title = new Label(t.getString("lbl.title"));
	    title.setId("h1");
	    boxContactList.setMaxWidth(200);
	    boxContactList.setId("box");
	    boxOverview.getChildren().add(title);
	    contactList = new ArrayList<>();

	    for (Contact c : model.getContactTree()) {
	        Label l = new Label(c.getFirstName() + " " + c.getLastName());
	        l.setMinWidth(200);
	        contactList.add(l);
        }
	    boxContactList.getChildren().addAll(contactList);
	    paneContactList.setContent(boxContactList);
	    paneContactList.setMaxHeight(500);
	    paneContactList.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	    paneContactList.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
	    boxOverview.getChildren().add(paneContactList);
	    boxOverview.setTranslateY(10);
	    boxOverview.setTranslateX(10);
	    return boxOverview;
       }

       private VBox setRootRight() {
		   rootRight = new VBox(50);
		   gdMain = new GridDetails();

		   gdMain.getBtnMorePhone().setDisable(true);
		   gdMain.getBtnMoreEmail().setDisable(true);
		   rootRight.getChildren().add(gdMain);

		   btnEdit = new Button(t.getString("btn.edit"));
		   btnSave = new Button(t.getString("btn.save"));
		   btnDelete = new Button(t.getString("btn.delete"));
		   btnOpenCreate = new Button(t.getString("btn.create"));
		   HBox box = new HBox(20);
		   box.setTranslateX(10);
		   box.getChildren().addAll(btnEdit, btnSave, btnDelete);
		   rootRight.getChildren().add(box);


		   for (TextField f : gdMain.getListTfDetails()) {
			   f.setEditable(false);
			   f.setMinWidth(150);
		   }

		   rootRight.setTranslateX(220);
		   rootRight.setTranslateY(100);

		   return rootRight;
	   }

	public void updateContactList() {
		boxContactList.getChildren().removeAll(contactList);
		contactList = new ArrayList<>();
		for (Contact c : model.getContactTree()) {
			Label l = new Label(c.getFirstName()+" "+c.getLastName());
			contactList.add(l);
			l.setMinWidth(200);
		}
		boxContactList.getChildren().addAll(contactList);
	}

	   public void createWindowNewContact() {
		ScrollPane root = new ScrollPane();
		root.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		root.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		gdNewEntry.setId("gridNewContact");
		root.setContent(gdNewEntry);

		btnCreate.setText(t.getString("btn.create"));
		btnCreateCancel.setText(t.getString("btn.createCancel"));
		HBox box = new HBox(150);
		box.getChildren().addAll(btnCreate, btnCreateCancel);
		Region spacer = new Region();
		spacer.setMinHeight(20);
		gdNewEntry.add(spacer, 0, 10, 2, 1);
		gdNewEntry.add(box, 0, 11, 2, 1);

		gdNewEntry.getBtnMorePhone().setText(t.getString("btn.addPhone"));
		gdNewEntry.getBtnMoreEmail().setText(t.getString("btn.addEmail"));

		gdNewEntry.getStyleClass().add("root");

		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
		stageNewEntry.setScene(scene);
		stageNewEntry.setTitle(t.getString("lbl.titleNewContact"));
		stageNewEntry.show();
	   }

	   public void closeWindowNewContact() {
		gdNewEntry = new GridDetails();
		gdNewEntry.getBtnMorePhone().setOnAction(e -> {
			if (gdNewEntry.getGridMorePhone().gridCreated) {
				gdNewEntry.getGridMorePhone().addNewPhone();
			} else {
				gdNewEntry.getChildren().remove(gdNewEntry.getLblPhone());
				gdNewEntry.getChildren().remove(gdNewEntry.getTfPhone());
				gdNewEntry.getGridMorePhone().initNewPhone();
				gdNewEntry.add(gdNewEntry.getGridMorePhone(), 0, 5, 2, 1);
				gdNewEntry.getGridMorePhone().addNewPhone();
			}
		});

		   gdNewEntry.getBtnMoreEmail().setOnAction(e -> {
			   if (gdNewEntry.getGridMoreEmail().gridCreated) {
				   gdNewEntry.getGridMoreEmail().addEmail();
			   } else {
				   gdNewEntry.getChildren().remove(gdNewEntry.getLblEmail());
				   gdNewEntry.getChildren().remove(gdNewEntry.getTfEmail());
				   gdNewEntry.getGridMoreEmail().initNewEmail();
				   gdNewEntry.add(gdNewEntry.getGridMoreEmail(), 0, 6, 2, 1);
				   gdNewEntry.getGridMoreEmail().addEmail();
			   }
		   });
		stageNewEntry.close();
	   }

	   public void createErrorAlert() {
		alertError = new Alert(Alert.AlertType.ERROR);
		alertError.setContentText(t.getString("msg.inputError"));
		alertError.show();
	   }

	   public void createSuccessAlert() {
		   alertSuccess = new Alert(Alert.AlertType.CONFIRMATION);
		   alertSuccess.setContentText(t.getString("msg.inputSuccess"));

		   Optional<ButtonType> result = alertSuccess.showAndWait();

		   if (result.get() == ButtonType.OK) {
			   //ok button is pressed
			   stageNewEntry.close();
		   } else if(!result.isPresent()){
		   		stageNewEntry.close();
		   } else if(result.get() == ButtonType.CANCEL) {
		   		stageNewEntry.close();
		   }
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

	public Button getBtnOpenCreate() {
		return btnOpenCreate;
	}

	public GridDetails getGdMain() {
		return gdMain;
	}

	public Button getBtnCreate() {
		return btnCreate;
	}

	public Button getBtnCreateCancel() {
		return btnCreateCancel;
	}

	public GridDetails getGdNewEntry() {
		return gdNewEntry;
	}

	public Stage getStageNewEntry() {
		return stageNewEntry;
	}
}

