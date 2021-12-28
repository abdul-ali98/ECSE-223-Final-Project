package ca.mcgill.ecse.climbsafe.javafx.fxml.controllers;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse.climbsafe.controller.AssignmentController;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet1Controller;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet2Controller;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet4Controller;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet5Controller;
import ca.mcgill.ecse.climbsafe.controller.InvalidInputException;
import ca.mcgill.ecse.climbsafe.controller.TOBundle;
import ca.mcgill.ecse.climbsafe.controller.TOEquipment;
import ca.mcgill.ecse.climbsafe.controller.TOGenericItem;
import ca.mcgill.ecse.climbsafe.controller.TOMember;
import ca.mcgill.ecse.climbsafe.javafx.fxml.ClimbSafeFxmlView;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;

public class MemberManagementController {
  
    
  @FXML
  private Text errorMessage;  
  @FXML
  private HBox EquipmentPane;//root UI object for adding a new bundle
  @FXML
  private HBox BundlePane;
  
  
  // button
  @FXML
  private Button deleteMemberButton;
  
  @FXML
  private Button updateMemberButton;
  
  @FXML
  private Button newMemberButton;
  
  // promptField
  
  @FXML
  private Label addPromptLabel;
  
  // textfield
  
  @FXML
  private TextField emailTextField;
  
  @FXML
  private TextField fullNameTextField;
  
  @FXML
  private TextField weeksToClimbTextField;
  
  @FXML
  private PasswordField passwordTextField;
  
  @FXML
  private TextField emergencyContactTextField;
  
  @FXML
  private CheckBox needAGuideCheckBox;
  
  @FXML
  private CheckBox needHotelCheckBox;
  
  
  
  // Bundle table
  
  @FXML
  private TableView<TOBundle> selectBundlesTable;
  
  //references to columns in the table
  @FXML
  private TableColumn<TOBundle, String> bundleNameColumn;
  @FXML
  private TableColumn<TOBundle, String> bundleDiscountColumn;
  @FXML
  private TableColumn<TOBundle, String> bundlePriceColumn;  
  
  @FXML
  private TableView<TOGenericItem> selectedItemsTable;
  
  @FXML
  private TableColumn<TOGenericItem, String> selectedItemsName;
  @FXML
  private TableColumn<TOGenericItem, Integer> selectedItemsQty;
  
  // Equipment table
  
  @FXML
  private TableView<TOEquipment> selectEquipmentsTable;
  
 

  @FXML
  private TableColumn<TOEquipment, String> equipmentNameColumn;
  @FXML
  private TableColumn<TOEquipment, Integer> equipmentWeightColumn;
  @FXML
  private TableColumn<TOEquipment, Integer> equipmentPriceColumn;

  
  @FXML
  private ComboBox<String> listOfMembersComboBox;
  
  private TOMember TOMember;
  
  /**
   * List the member in the combo box
   * @author yujieqin
   * @param event
   */
  
  @FXML
  public void onListOfMembersComboBox(Event event) {
	  needAGuideCheckBox.setDisable(false);
		needHotelCheckBox.setDisable(false);
    for (TOMember aTOMember : ClimbSafeFeatureSet2Controller.getMembers()) {
        if (aTOMember.getEmail().equals(listOfMembersComboBox.getValue())) {
            this.TOMember = aTOMember;
            break;
        }
    }
    
    //Added by Abdul
    if (listOfMembersComboBox.getValue() != null) {
    	String email = listOfMembersComboBox.getValue();
    	TOMember member = null;
    	for (TOMember m :ClimbSafeFeatureSet2Controller.getMembers())
    		if (m.getEmail().equals(email)) {
    			member = m;
    			break;
    		}
    	emailTextField.setText(email);
    	fullNameTextField.setText(member.getName());
    	weeksToClimbTextField.setText(member.getWeeks()+"");
    	emergencyContactTextField.setText(member.getEmergancyContact());
    	needAGuideCheckBox.setSelected(member.isNeedGuide());
    	needHotelCheckBox.setSelected(member.getNeedHotel());
    	passwordTextField.setText(member.getPassword());
    	
    	//added by fred
    	selectedItemsTable.getItems().clear();
    	selectedItemsTable.getItems().addAll(member.getItems());
    	if (AssignmentController.hasAssignment(email)) {
    		needAGuideCheckBox.setDisable(true);
    		needHotelCheckBox.setDisable(true);
    	}
    }
   
}
  
  
  
  /**
   * Initialize the page
   * @author yujieqin
   * 
   */
  
  // initializaiton 
  @FXML
  public void initialize(){
    
	  needAGuideCheckBox.setDisable(false);
		needHotelCheckBox.setDisable(false);
    ObservableList<String> membersInList = FXCollections.observableArrayList();
    for (TOMember aTOMember : ClimbSafeFeatureSet2Controller.getMembers()) {
      membersInList.add(aTOMember.getEmail());
    }
    
    this.listOfMembersComboBox.getItems().addAll(membersInList);
    if (ClimbSafeFeatureSet2Controller.getMembers().size() < 1)
		 this.listOfMembersComboBox.setValue("No members");
		 else this.listOfMembersComboBox.setValue("Pick member");
	 
    
    
    addPromptLabel.setVisible(false);
    
    // for bundle table
    
    bundleNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    bundleDiscountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
    bundlePriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPricePerWeek"));
    
    selectBundlesTable.getItems().addAll(ClimbSafeFeatureSet5Controller.getEquipmentBundles());
    
    
    
    // for equipment table
    
    equipmentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    equipmentWeightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
    equipmentPriceColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerWeek"));
    
    selectEquipmentsTable.getItems().addAll(ClimbSafeFeatureSet4Controller.getEquipments());
    
    
    // for selected items
    selectedItemsName.setCellValueFactory(new PropertyValueFactory<>("name"));
    
    selectedItemsQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    selectedItemsQty.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));//make column editable with text field
    selectedItemsQty.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TOGenericItem, Integer>>() {
		@Override
		public void handle(CellEditEvent<TOGenericItem, Integer> event) {
			TOGenericItem item = event.getRowValue();// get row object
			item.setQuantity(event.getNewValue());// save new name to model
		}
	});
    	selectedItemsTable.setPlaceholder(new Label("Oops.. You did not select items :("));
  }
  
  /**
   * the equipment will be added once clicked
   * @author yujieqin
   * @param event
   */
  @FXML
  public void onAddEquipmentItem(ActionEvent event) {
	  var selected = selectEquipmentsTable.getSelectionModel().getSelectedItem();
	  if (selected!=null) {
		  var items =  selectedItemsTable.getItems();
		  for (var item :items ) {
			  if(item.getName().equals(selected.getName())) {
				  var quantity = item.getQuantity();
				  if (quantity == Integer.MAX_VALUE)
					  return;
				  items.remove(item);
				  selectedItemsTable.getItems().add(new TOGenericItem(quantity +1, selected.getName()));
				 return;
			  } 	 
		  }
		  selectedItemsTable.getItems().add(new TOGenericItem(1, selected.getName()));
	  }

  }
  
  /**
   * the bundle will be added once clicked
   * @author yujieqin
   * @param event
   */
  @FXML
  public void onAddBundleItem(ActionEvent event) {
	  var selected = selectBundlesTable.getSelectionModel().getSelectedItem();
	  if (selected!=null) {
		  var items =  selectedItemsTable.getItems();
		  for (var item :items ) {
			  if(item.getName().equals(selected.getName())) {
				  var quantity = item.getQuantity();
				  if (quantity == Integer.MAX_VALUE)
					  return;
				  items.remove(item);
				  selectedItemsTable.getItems().add(new TOGenericItem(quantity +1, selected.getName()));
				 return;
			  } 	 
		  }
		  selectedItemsTable.getItems().add(new TOGenericItem(1,selected.getName()));

	  }
  }

  
  PauseTransition visiblePause = new PauseTransition(Duration.seconds(3));  
  
  // event listener on button [Button].onAction
  /**
   * new member created once clicked
   * @author yujieqin
   * @param event
   */
  
  @FXML
  public void onNewMember(ActionEvent event)
  {	  
    String email = emailTextField.getText();
    String fullName = fullNameTextField.getText();
    String weeksToClimbString = weeksToClimbTextField.getText();
    String password = passwordTextField.getText();
    String emergencyContact = emergencyContactTextField.getText();
    boolean needAGuide = needAGuideCheckBox.isSelected();
    boolean needHotel = needHotelCheckBox.isSelected();
    
    int weeksToClimb=0;
	try {
		weeksToClimb = Integer.parseInt(weeksToClimbString);
	} catch (NumberFormatException e1) {
	
	      addPromptLabel.setText("Invalid number of weeks, need to be a number.");
	      addPromptLabel.setStyle("-fx-text-fill: red;");
	      addPromptLabel.setMinHeight(70);
	      addPromptLabel.setVisible(true);
	      visiblePause.setOnFinished(EventHandler -> addPromptLabel.setVisible(false));
	      visiblePause.play();
		return;
	}
    
    List<String> itemNames = new ArrayList<String>();
    List<Integer> itemQty = new ArrayList<Integer>();
    for (var i: selectedItemsTable.getItems()) {
    	itemNames.add(i.getName());
    	itemQty.add(i.getQuantity());
    }
    
    
    
    try 
    {
      ClimbSafeFeatureSet2Controller.registerMember(email, password, fullName, emergencyContact, weeksToClimb, needAGuide, needHotel, itemNames, itemQty);  
     
      emailTextField.setText("");
      fullNameTextField.setText("");
      weeksToClimbTextField.setText("");
      passwordTextField.setText("");
      emergencyContactTextField.setText("");
      needAGuideCheckBox.setSelected(false);
      needHotelCheckBox.setSelected(false);
      selectedItemsTable.getItems().clear();
      
                 
      listOfMembersComboBox.getSelectionModel().clearSelection();
      this.listOfMembersComboBox.getItems().addAll(email);
      
      addPromptLabel.setText("added!");
      addPromptLabel.setStyle("-fx-text-fill: green;");
      addPromptLabel.setVisible(true);
      visiblePause.setOnFinished(EventHandler -> addPromptLabel.setVisible(false));
      visiblePause.play();
     
      ClimbSafeFxmlView.getInstance().refresh();
      
    } catch (InvalidInputException e) {
      addPromptLabel.setText(e.getMessage());
      addPromptLabel.setStyle("-fx-text-fill: red;");
      addPromptLabel.setMinHeight(70);
      addPromptLabel.setVisible(true);
      visiblePause.setOnFinished(EventHandler -> addPromptLabel.setVisible(false));
      visiblePause.play();
    }
  }
  
  /**
   * member is updated once clicked
   * @author yujieqin
   * @param event
   */
  @FXML
  public void onUpdateMember(ActionEvent event)
  {
    String email = emailTextField.getText();
    String fullName = fullNameTextField.getText();
    String weeksToClimbString = weeksToClimbTextField.getText();
    String password = passwordTextField.getText();
    String emergencyContact = emergencyContactTextField.getText();
    boolean needAGuide = needAGuideCheckBox.isSelected();
    boolean needHotel = needHotelCheckBox.isSelected();
    
    int weeksToClimb=0;
	try {
		weeksToClimb = Integer.parseInt(weeksToClimbString);
	} catch (NumberFormatException e1) {
	  
      addPromptLabel.setText("error");
      addPromptLabel.setStyle("-fx-text-fill: red;");
      addPromptLabel.setMinHeight(70);
      addPromptLabel.setVisible(true);
      visiblePause.setOnFinished(EventHandler -> addPromptLabel.setVisible(false));
      visiblePause.play();	
		return;
	}
    
    List<String> itemNames = new ArrayList<String>();
    List<Integer> itemQty = new ArrayList<Integer>();
    for (var i: selectedItemsTable.getItems()) {
    	itemNames.add(i.getName());
    	itemQty.add(i.getQuantity());
    }
          
    
    try 
    {
      ClimbSafeFeatureSet2Controller.updateMember(email, password, fullName, emergencyContact, weeksToClimb, needAGuide, needHotel, itemNames, itemQty);
      
      emailTextField.setText("");
      fullNameTextField.setText("");
      weeksToClimbTextField.setText("");
      passwordTextField.setText("");
      emergencyContactTextField.setText("");
      needAGuideCheckBox.setSelected(false);
      needHotelCheckBox.setSelected(false);
      selectedItemsTable.getItems().clear();
      listOfMembersComboBox.getSelectionModel().clearSelection();
      
      addPromptLabel.setText("updated!");
      addPromptLabel.setStyle("-fx-text-fill: green;");
      addPromptLabel.setVisible(true);
      visiblePause.setOnFinished(EventHandler -> addPromptLabel.setVisible(false));
      visiblePause.play();
      
      ClimbSafeFxmlView.getInstance().refresh();
      
    } catch (InvalidInputException e) {
      addPromptLabel.setText(e.getMessage());
      addPromptLabel.setStyle("-fx-text-fill: red;");
      addPromptLabel.setMinHeight(70);
      addPromptLabel.setVisible(true);
      visiblePause.setOnFinished(EventHandler -> addPromptLabel.setVisible(false));
      visiblePause.play();
    }
    
  }
  
  
  /**
   * member is deleted once clicked
   * @author yujieqin
   * @param event
   */
  @FXML
  public void onDeleteMember(ActionEvent event)
  {
   try {
      ClimbSafeFeatureSet1Controller.deleteMember(listOfMembersComboBox.getValue());
      emailTextField.setText("");
      fullNameTextField.setText("");
      weeksToClimbTextField.setText("");
      passwordTextField.setText("");
      emergencyContactTextField.setText("");
      
      needAGuideCheckBox.setDisable(false);
      needHotelCheckBox.setDisable(false);
      needAGuideCheckBox.setSelected(false);
      needHotelCheckBox.setSelected(false);
      
      selectedItemsTable.getItems().clear();
      
      addPromptLabel.setText("deleted!");
      addPromptLabel.setStyle("-fx-text-fill: green;");
      addPromptLabel.setVisible(true);
      visiblePause.setOnFinished(EventHandler -> addPromptLabel.setVisible(false));
      visiblePause.play();
      
      
      
    } catch (InvalidInputException e) {
      errorMessage.setText(e.getMessage());
    }
    
    ObservableList<String> membersInList = FXCollections.observableArrayList();
    
    for (TOMember aTOMember : ClimbSafeFeatureSet2Controller.getMembers()) {
      membersInList.add(aTOMember.getEmail());
    }
    listOfMembersComboBox.getItems().clear();
    this.listOfMembersComboBox.getItems().addAll(membersInList);
    
    
    

    
  }
  

  public void refreshTab()
  {
    emailTextField.setText("");
    fullNameTextField.setText("");
    weeksToClimbTextField.setText("");
    passwordTextField.setText("");
    emergencyContactTextField.setText("");
    
    needAGuideCheckBox.setDisable(false);
	needHotelCheckBox.setDisable(false);
    needAGuideCheckBox.setSelected(false);
    needHotelCheckBox.setSelected(false);
    
    addPromptLabel.setVisible(false);
    
    selectedItemsTable.getItems().clear();
    selectBundlesTable.getItems().clear();
    selectEquipmentsTable.getItems().clear();
    
    selectBundlesTable.getItems().addAll(ClimbSafeFeatureSet5Controller.getEquipmentBundles());
    selectEquipmentsTable.getItems().addAll(ClimbSafeFeatureSet4Controller.getEquipments());
    
    ObservableList<String> membersInList = FXCollections.observableArrayList();
    
    for (TOMember aTOMember : ClimbSafeFeatureSet2Controller.getMembers()) {
      membersInList.add(aTOMember.getEmail());
    }
    listOfMembersComboBox.getItems().clear();
    this.listOfMembersComboBox.getItems().addAll(membersInList);
    
    ClimbSafeFxmlView.getInstance().refresh();
  }
}
