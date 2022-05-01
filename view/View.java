package NicoleZarch_StasLibman.view;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import NicoleZarch_StasLibman.model.Company;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class View {
	
	private ArrayList<CompanyUIEventsListener> allListeners = new ArrayList<CompanyUIEventsListener>();
	private Stage primaryStage;
	private String companyName;
	ArrayList<String> early;
	ArrayList<String> later;
	
	private Label lblAsk;
	private Label lblHeadTitle;
	private Label lblSubTitle;
	private Button btnYes;
	private Button btnNo;
	private Button btnSubmit;
	private Button btnStart;
	private Button addDepartment;
	private Button addRole;
	private Button addEmployee;
	private Button viewDepartments;
	private Button viewRoles;
	private Button viewEmployees;
	private Button changeRoleWork;
	private Button changeDepartmentWork;
	private Button totalDepartmentsProfit;
	private Button totalRolesProfit;
	private Button totalEmployeesProfit;
	private Button exit;
	private TextArea txtArea;
	private Button btnReturn;
	
	private Label lblName;
	private Label lblWorkMethod;
	private Label lblWorkTime;
	private TextField tfName;
	private ComboBox<String> cmbWorkMethod;
	private ComboBox<String> cmbTime;
	private RadioButton rbtSynch;
	private RadioButton rbtChangeable;
	
	private Label lblChooseDepartment;
	private ComboBox<String> cmbChooseDepartment;
	private Label lblChooseRole;
	private ComboBox<String> cmbChooseRole;
	private Label lblProfit;
	private TextField tfProfit;
	private Label lblChooseType;
	private ComboBox<String> cmbChooseType;
	private Label lblSalary;
	private TextField tfSalary;
	private Label lblCommission;
	private TextField tfCommission;
	
	
	public View(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.companyName = "noName";
		lblAsk = new Label();
		lblHeadTitle = new Label();
		lblSubTitle = new Label();
		btnYes = new Button("Yes");
		btnNo = new Button("No");
		btnSubmit = new Button("Submit");
		btnStart = new Button("Let's Start!");
		
		addDepartment = new Button("Add Department");
		addRole = new Button("Add Role");
		addEmployee = new Button("Add Employee");
		viewDepartments = new Button("View Departments");
		viewRoles = new Button("View Roles");
		viewEmployees = new Button("View Employees");
		changeRoleWork = new Button("Change Role Work");
		changeDepartmentWork = new Button("Change Department Work");
		totalDepartmentsProfit = new Button("Total Departments Profit");
		totalRolesProfit = new Button("Total Roles Profit");
		totalEmployeesProfit = new Button("Total Employees Profit");
		exit = new Button("Exit");
		btnReturn = new Button("Return to Menu");
		txtArea = new TextArea();
		txtArea.setEditable(false); 
		
		addDepartment.setPrefSize(200, 10);
		addRole.setPrefSize(200, 10);
		addEmployee.setPrefSize(200, 10);
		viewDepartments.setPrefSize(200, 10);
		viewRoles.setPrefSize(200, 10);
		viewEmployees.setPrefSize(200, 10);
		changeRoleWork.setPrefSize(200, 10);
		changeDepartmentWork.setPrefSize(200, 10);
		totalDepartmentsProfit.setPrefSize(200, 10);
		totalRolesProfit.setPrefSize(200, 10);
		totalEmployeesProfit.setPrefSize(200, 10);
		exit.setPrefSize(200, 10);

		
		lblName = new Label();
		tfName = new TextField();
		lblWorkMethod = new Label();
		lblWorkTime = new Label();
		cmbWorkMethod = new  ComboBox<String>();
		cmbTime = new  ComboBox<String>();
		rbtSynch = new RadioButton("Demands synchronized work");
		rbtChangeable = new RadioButton("Cannot change work method");
		
		lblChooseDepartment = new Label();
		cmbChooseDepartment = new  ComboBox<String>();
		lblChooseRole = new Label();
		cmbChooseRole = new  ComboBox<String>();
		lblProfit = new Label();
		tfProfit = new TextField();
		lblChooseType = new Label();
		cmbChooseType = new  ComboBox<String>();
		lblSalary = new Label();
		tfSalary = new TextField();
		lblCommission = new Label();
		tfCommission = new TextField();
		
		early = new ArrayList<String>();
		later = new ArrayList<String>();
		for (int i = 0; i < Company.START_TIME; i++) {
			early.add("0" + i + ":00");
		}
		for (int i = Company.START_TIME+1; i < 17; i++) {
			if (i < 10) {
				later.add("0" + i + ":00");
			} else {
				later.add(i + ":00");
			}
		}
		
		switchScene(askToLoad());
		
	}
	
	public void registerListener(CompanyUIEventsListener newListener) {
		allListeners.add(newListener);
	}
	
	private void switchScene(Scene scene) {
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}
	
	public Scene askToLoad() {
		lblAsk.setText("Would you like to read Comapny data from a saved file?");

		btnYes.setOnAction(e -> {
			boolean res = false;
			for (CompanyUIEventsListener l : allListeners) {
				res = l.selectFromFile(true);
			}
			
			if (res) {
				updateChooseDepComboBox();
				switchScene(companyMenu());
			} else {
				switchScene(addCompany());
			}
			
			
		});

		btnNo.setOnAction(e -> {
			for (CompanyUIEventsListener l : allListeners) {
				l.selectFromFile(false);
			}
			switchScene(addCompany());
		});

		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(20);
		hbox.setPadding(new Insets(15));
		hbox.getChildren().setAll(btnYes, btnNo);

		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(15));
		vbox.getChildren().setAll(lblAsk, hbox);
		
		primaryStage.setTitle("To load or not to load?");
		return new Scene(vbox, 350, 150);
	}

	public Scene addCompany() {
		primaryStage.setTitle("Let's create a new Company!");
		lblName.setText("Company name: ");
		tfName.setPrefSize(200, 10);
		tfName.clear();
		
		btnStart.setOnAction(e ->{
			for (CompanyUIEventsListener l : allListeners) {
				l.newModelUI(tfName.getText());
			}
			companyName = tfName.getText();
			boolean res = tfName.getText().isEmpty();
			if (res) {
				messageToUI("Please make sure to enter company name!");
			} else {
				switchScene(companyMenu());
			}
		});
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(15));
		gpRoot.setAlignment(Pos.CENTER_LEFT);
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		gpRoot.add(lblName, 0, 0);
		gpRoot.add(tfName, 1, 0);
		gpRoot.add(btnStart, 1, 1);
		return new Scene(gpRoot, 350, 150);
	}
	
	public Scene companyMenu() {
		primaryStage.setTitle("Menu");
		lblHeadTitle.setText(companyName);
		lblSubTitle.setText("Choose from the following options: ");
		lblHeadTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 20");
		lblSubTitle.setStyle("-fx-font-size: 16");
		
		addDepartment.setOnAction(e ->{
			switchScene(addDepartment());
		});
		
		addRole.setOnAction(e ->{
			switchScene(addRole());
		});
		
		addEmployee.setOnAction(e ->{
			switchScene(addEmployee());
		});
		
		viewDepartments.setOnAction(e ->{
			switchScene(viewDepartments());
		});
		
		viewRoles.setOnAction(e ->{
			switchScene(viewRoles());
		});
		
		viewEmployees.setOnAction(e ->{
			switchScene(viewEmployees());
		});
		
		changeDepartmentWork.setOnAction(e ->{
			switchScene(changeDepartmentWork());
		});
		
		changeRoleWork.setOnAction(e ->{
			switchScene(changeRoleWork());
		});
		
		totalDepartmentsProfit.setOnAction(e ->{
			switchScene(totalDepartmentsProfit());
		});
		
		totalRolesProfit.setOnAction(e ->{
			switchScene(totalRolesProfit());
		});
		
		totalEmployeesProfit.setOnAction(e ->{
			switchScene(totalEmployeesProfit());
		});
		
		exit.setOnAction(e ->{
			for (CompanyUIEventsListener l : allListeners) {
				l.saveCompany();
			}
			messageToUI("Goodbye!");
			primaryStage.close();
		});
		
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(10);
		vBox.setPadding(new Insets(15));
		vBox.getChildren().setAll(	lblHeadTitle, lblSubTitle, addDepartment, addRole, addEmployee,
									viewDepartments, viewRoles, viewEmployees,changeDepartmentWork, changeRoleWork,
									totalDepartmentsProfit, totalRolesProfit, totalEmployeesProfit, exit);
		
		return new Scene(vBox, 350, 600);
		
	}
	
	public Scene addDepartment() {

		resetSelections();
		
		primaryStage.setTitle("Adding New Department");
		lblName.setText("Department name: ");
		tfName.setPrefSize(200, 10);
		tfName.clear();
		lblWorkMethod.setText("Work Method: ");
		lblWorkTime.setText("Choose start time: ");
		cmbWorkMethod.getItems().setAll("Start Earlier", "Start Later", "Work from Home", "As Usual");
		cmbWorkMethod.setPrefSize(200, 10);
		
		lblWorkTime.setVisible(false);
		cmbTime.setVisible(false);

		btnSubmit.setOnAction(e ->{
			boolean res = false;
			for (CompanyUIEventsListener l : allListeners) {
				if (cmbWorkMethod.getValue() != null && cmbWorkMethod.getValue().equals("Work from Home")) {
					res = l.addDepartment(tfName.getText(), cmbWorkMethod.getValue(), "08", rbtSynch.isSelected(), !rbtChangeable.isSelected());
				} else if (cmbWorkMethod.getValue() != null && cmbWorkMethod.getValue().equals("As Usual")) {
					res = l.addDepartment(tfName.getText(), cmbWorkMethod.getValue(), "00", rbtSynch.isSelected(), !rbtChangeable.isSelected());
				} else {
					res = l.addDepartment(tfName.getText(), cmbWorkMethod.getValue(), cmbTime.getValue(), rbtSynch.isSelected(), !rbtChangeable.isSelected());
				}
			}
			if (res) {
				switchScene(companyMenu());
			}
			
		});
		
		cmbWorkMethod.setOnAction(e ->{
			if (cmbWorkMethod.getValue() != null && cmbWorkMethod.getValue().equals("Start Earlier")) {
				lblWorkTime.setVisible(true);
				cmbTime.setVisible(true);
				cmbTime.getItems().setAll(early);
			}
			
			else if (cmbWorkMethod.getValue() != null && cmbWorkMethod.getValue().equals("Start Later")) {
				lblWorkTime.setVisible(true);
				cmbTime.setVisible(true);
				cmbTime.getItems().setAll(later);
			} else {
				lblWorkTime.setVisible(false);
				cmbTime.setVisible(false);
			}
		});
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(15));
		gpRoot.setAlignment(Pos.CENTER_LEFT);
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		gpRoot.add(lblName, 0, 0);
		gpRoot.add(tfName, 1, 0);
		gpRoot.add(lblWorkMethod, 0, 1);
		gpRoot.add(cmbWorkMethod, 1, 1);
		gpRoot.add(lblWorkTime, 0, 2);
		gpRoot.add(cmbTime, 1, 2);
		gpRoot.add(rbtSynch, 2, 0);
		gpRoot.add(rbtChangeable, 2, 1);
		gpRoot.add(btnSubmit, 2, 2);
		return new Scene(gpRoot, 550, 200);
	}
	
	public Scene addRole() {

		resetSelections();
		
		primaryStage.setTitle("Adding New Role");
		lblChooseDepartment.setText("Department: ");
		lblName.setText("Role name: ");
		tfName.setPrefSize(200, 10);
		tfName.clear();
		lblWorkMethod.setText("Work Method: ");
		lblWorkTime.setText("Choose start time: ");
		cmbWorkMethod.getItems().setAll("Start Earlier", "Start Later", "Work from Home", "As Usual");
		cmbWorkMethod.setPrefSize(200, 10);
		cmbChooseDepartment.setPrefSize(200, 10);
		
		lblWorkTime.setVisible(false);
		cmbTime.setVisible(false);

		btnSubmit.setOnAction(e ->{
			boolean res = false;
			for (CompanyUIEventsListener l : allListeners) {
				if (cmbWorkMethod.getValue() != null && cmbWorkMethod.getValue().equals("As Usual")) {
					res = l.addRole(cmbChooseDepartment.getValue(), tfName.getText(), cmbWorkMethod.getValue(), "00", rbtSynch.isSelected(), !rbtChangeable.isSelected());
				}
				else if (cmbWorkMethod.getValue() != null && cmbWorkMethod.getValue().equals("Work from Home")) {
					res = l.addRole(cmbChooseDepartment.getValue(), tfName.getText(), cmbWorkMethod.getValue(), "08", rbtSynch.isSelected(), !rbtChangeable.isSelected());
				} else {
					res = l.addRole(cmbChooseDepartment.getValue(), tfName.getText(), cmbWorkMethod.getValue(), cmbTime.getValue(), rbtSynch.isSelected(), !rbtChangeable.isSelected());
				}
			}
			if (res) {
				switchScene(companyMenu());
			}
		});
		
		cmbWorkMethod.setOnAction(e ->{
			if (cmbWorkMethod.getValue() != null && cmbWorkMethod.getValue().equals("Start Earlier")) {
				lblWorkTime.setVisible(true);
				cmbTime.setVisible(true);
				cmbTime.getItems().setAll(early);
			}
			
			else if (cmbWorkMethod.getValue() != null && cmbWorkMethod.getValue().equals("Start Later")) {
				lblWorkTime.setVisible(true);
				cmbTime.setVisible(true);
				cmbTime.getItems().setAll(later);
			} else {
				lblWorkTime.setVisible(false);
				cmbTime.setVisible(false);
			}
		});
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(15));
		gpRoot.setAlignment(Pos.CENTER);
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		gpRoot.add(lblChooseDepartment, 0, 0);
		gpRoot.add(cmbChooseDepartment, 1, 0);
		gpRoot.add(lblName, 0, 1);
		gpRoot.add(tfName, 1, 1);
		gpRoot.add(lblWorkMethod, 0, 2);
		gpRoot.add(cmbWorkMethod, 1, 2);
		gpRoot.add(lblWorkTime, 0, 3);
		gpRoot.add(cmbTime, 1, 3);
		gpRoot.add(rbtSynch, 2, 0);
		gpRoot.add(rbtChangeable, 2, 1);
		gpRoot.add(btnSubmit, 2, 2);
		return new Scene(gpRoot, 550, 200);
	}
	
	public Scene addEmployee() {

		resetSelections();
		
		primaryStage.setTitle("Adding New Employee");
		lblChooseDepartment.setText("Department: ");
		lblChooseRole.setText("Role: ");
		lblChooseType.setText("Employee Type: ");
		lblName.setText("Name: ");
		lblProfit.setText("Profit: ");
		tfName.setPrefSize(200, 10);
		tfName.clear();
		lblWorkMethod.setText("Work Preference: ");
		lblWorkTime.setText("Choose start time: ");
		cmbWorkMethod.getItems().setAll("Start Earlier", "Start Later", "Work from Home", "As Usual");

		cmbWorkMethod.setPrefSize(200, 10);
		cmbChooseDepartment.setPrefSize(200, 10);
		cmbChooseRole.setPrefSize(200, 10);
		cmbChooseType.setPrefSize(200, 10);
		cmbChooseType.getItems().setAll("Base Salary", "Hourly Employee", "Commission Employee");
		lblSalary.setVisible(false);
		lblCommission.setVisible(false);
		tfSalary.setVisible(false);
		tfCommission.setVisible(false);
		lblWorkTime.setVisible(false);
		cmbTime.setVisible(false);
		
		btnSubmit.setOnAction(e ->{
			boolean res = false;
			String commission = "";
			if (tfCommission.getText().isEmpty()) {
				commission = "0";
			} else {
				commission = tfCommission.getText();
			}
			
			for (CompanyUIEventsListener l : allListeners) {
				if ((cmbWorkMethod.getValue() != null && cmbWorkMethod.getValue().equals("Work from Home"))) {
					res = l.addEmployee(cmbChooseDepartment.getValue(), cmbChooseRole.getValue(), cmbChooseType.getValue(), tfName.getText(), cmbWorkMethod.getValue(), "08", tfProfit.getText(), tfSalary.getText(), commission);
				}
				else if ((cmbWorkMethod.getValue() != null && cmbWorkMethod.getValue().equals("As Usual"))) {
					res = l.addEmployee(cmbChooseDepartment.getValue(), cmbChooseRole.getValue(), cmbChooseType.getValue(), tfName.getText(), cmbWorkMethod.getValue(), "00", tfProfit.getText(), tfSalary.getText(), commission);
				
				} else {
					res = l.addEmployee(cmbChooseDepartment.getValue(), cmbChooseRole.getValue(), cmbChooseType.getValue(), tfName.getText(), cmbWorkMethod.getValue(), cmbTime.getValue(), tfProfit.getText(), tfSalary.getText(), commission);
				}
			}
			if (res) {
				switchScene(companyMenu());
			}
		});
		
		
		cmbChooseDepartment.setOnAction(e -> {
			if (cmbChooseDepartment.getValue() != null) {
				updateRoleComboBox(cmbChooseDepartment.getValue());
			}
		});
		
		cmbChooseType.setOnAction(e ->{
			tfSalary.clear();
			tfCommission.clear();
			if (cmbChooseType.getValue() != null && cmbChooseType.getValue().equals("Base Salary")) {
				lblSalary.setText("Base Salary: ");
				lblSalary.setVisible(true);
				tfSalary.setVisible(true);
				lblCommission.setVisible(false);
				tfCommission.setVisible(false);
			} 
			else if (cmbChooseType.getValue() != null && cmbChooseType.getValue().equals("Hourly Employee")){
				lblSalary.setText("Hourly Salary: ");
				lblSalary.setVisible(true);
				tfSalary.setVisible(true);
				lblCommission.setVisible(false);
				tfCommission.setVisible(false);
			} else {
				lblSalary.setText("Base Salary: ");
				lblCommission.setText("Commission Percentage: ");
				lblSalary.setVisible(true);
				lblCommission.setVisible(true);
				tfSalary.setVisible(true);
				tfCommission.setVisible(true);
			}
		});
		
		cmbWorkMethod.setOnAction(e ->{
			if (cmbWorkMethod.getValue() != null && cmbWorkMethod.getValue().equals("Start Earlier")) {
				lblWorkTime.setVisible(true);
				cmbTime.setVisible(true);
				cmbTime.getItems().setAll(early);
			}
			
			else if (cmbWorkMethod.getValue() != null && cmbWorkMethod.getValue().equals("Start Later")) {
				lblWorkTime.setVisible(true);
				cmbTime.setVisible(true);
				cmbTime.getItems().setAll(later);
			} else {
				lblWorkTime.setVisible(false);
				cmbTime.setVisible(false);
			}
		});
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(15));
		gpRoot.setAlignment(Pos.CENTER);
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		gpRoot.add(lblChooseDepartment, 0, 0);
		gpRoot.add(cmbChooseDepartment, 1, 0);
		gpRoot.add(lblChooseRole, 0, 1);
		gpRoot.add(cmbChooseRole, 1, 1);
		gpRoot.add(lblName, 0, 2);
		gpRoot.add(tfName, 1, 2);
		gpRoot.add(lblWorkMethod, 0, 3);
		gpRoot.add(cmbWorkMethod, 1, 3);
		gpRoot.add(lblWorkTime, 0, 4);
		gpRoot.add(cmbTime, 1, 4);
		gpRoot.add(lblProfit, 2, 0);
		gpRoot.add(tfProfit, 3, 0);
		gpRoot.add(lblChooseType, 2, 1);
		gpRoot.add(cmbChooseType, 3, 1);
		gpRoot.add(lblSalary, 2, 2);
		gpRoot.add(tfSalary, 3, 2);
		gpRoot.add(lblCommission, 2, 3);
		gpRoot.add(tfCommission, 3, 3);
		gpRoot.add(btnSubmit, 3, 4);
		return new Scene(gpRoot, 700, 250);
	}
	
	public Scene viewDepartments() {
		String allDepartments = "";
		
		for (CompanyUIEventsListener l : allListeners) {
			allDepartments = l.viewDepartments();
		}
		
		txtArea.setText(allDepartments);
		btnReturn.setOnAction(e ->{
			switchScene(companyMenu());
		});
		
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setAlignment(Pos.CENTER);
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		gpRoot.add(txtArea, 0, 0);
		gpRoot.add(btnReturn, 0, 1);
		
		primaryStage.setTitle("View Departments");
		return new Scene(gpRoot, 350, 250);
	}
	
	public Scene viewRoles() {
		String allRoles = "";
		
		for (CompanyUIEventsListener l : allListeners) {
			allRoles = l.viewRoles();
		}
		
		txtArea.setText(allRoles);
		btnReturn.setOnAction(e ->{
			switchScene(companyMenu());
		});
		
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setAlignment(Pos.CENTER);
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		gpRoot.add(txtArea, 0, 0);
		gpRoot.add(btnReturn, 0, 1);
		
		primaryStage.setTitle("View Roles");
		return new Scene(gpRoot, 350, 250);
	}
	
	public Scene viewEmployees() {
		String allEmployees = "";
		
		for (CompanyUIEventsListener l : allListeners) {
			allEmployees = l.viewEmployees();
		}
		
		txtArea.setText(allEmployees);
		btnReturn.setOnAction(e ->{
			switchScene(companyMenu());
		});
		
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setAlignment(Pos.CENTER);
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		gpRoot.add(txtArea, 0, 0);
		gpRoot.add(btnReturn, 0, 1);
		
		primaryStage.setTitle("View Employees");
		return new Scene(gpRoot, 350, 250);
	}
	
	public Scene changeRoleWork() {

		resetSelections();
		
		primaryStage.setTitle("Change Role Working Method");
		lblChooseDepartment.setText("Choose Department: ");
		lblChooseRole.setText("Choose Role: ");
		lblWorkMethod.setText("Work Method: ");
		lblWorkTime.setText("Choose start time: ");
		cmbWorkMethod.getItems().setAll("Start Earlier", "Start Later", "Work from Home", "As Usual");

		cmbWorkMethod.setPrefSize(200, 10);
		cmbChooseDepartment.setPrefSize(200, 10);
		cmbChooseRole.setPrefSize(200, 10);
		lblWorkTime.setVisible(false);
		cmbTime.setVisible(false);
		
		btnSubmit.setOnAction(e ->{
			boolean res = false;
			for (CompanyUIEventsListener l : allListeners) {
				if ((cmbWorkMethod.getValue() != null && cmbWorkMethod.getValue().equals("Work from Home"))) {
					res = l.changeRole(cmbChooseRole.getValue(), cmbWorkMethod.getValue(), "08");
				}
				else if ((cmbWorkMethod.getValue() != null && cmbWorkMethod.getValue().equals("As Usual"))) {
					res = l.changeRole(cmbChooseRole.getValue(), cmbWorkMethod.getValue(), "00");
				} else {
					res = l.changeRole(cmbChooseRole.getValue(), cmbWorkMethod.getValue(), cmbTime.getValue());
				}
			}
			if (res) {
				switchScene(companyMenu());
			}
		});
		
		
		cmbChooseDepartment.setOnAction(e -> {
			if (cmbChooseDepartment.getValue() != null) {
				updateChangeableRoleComboBox(cmbChooseDepartment.getValue());
			}
		});
		
		cmbWorkMethod.setOnAction(e ->{
			if (cmbWorkMethod.getValue() != null && cmbWorkMethod.getValue().equals("Start Earlier")) {
				lblWorkTime.setVisible(true);
				cmbTime.setVisible(true);
				cmbTime.getItems().setAll(early);
			}
			
			else if (cmbWorkMethod.getValue() != null && cmbWorkMethod.getValue().equals("Start Later")) {
				lblWorkTime.setVisible(true);
				cmbTime.setVisible(true);
				cmbTime.getItems().setAll(later);
			} else {
				lblWorkTime.setVisible(false);
				cmbTime.setVisible(false);
			}
		});
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(15));
		gpRoot.setAlignment(Pos.CENTER);
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		gpRoot.add(lblChooseDepartment, 0, 0);
		gpRoot.add(cmbChooseDepartment, 1, 0);
		gpRoot.add(lblChooseRole, 0, 1);
		gpRoot.add(cmbChooseRole, 1, 1);
		gpRoot.add(lblWorkMethod, 0, 3);
		gpRoot.add(cmbWorkMethod, 1, 3);
		gpRoot.add(lblWorkTime, 0, 4);
		gpRoot.add(cmbTime, 1, 4);
		gpRoot.add(btnSubmit, 1, 5);
		return new Scene(gpRoot, 400, 250);
	}
	
	public Scene changeDepartmentWork() {

		resetSelections();
		
		primaryStage.setTitle("Change Department Work Method");
		lblChooseDepartment.setText("Choose Department: ");
		lblWorkMethod.setText("Work Method: ");
		lblWorkTime.setText("Choose start time: ");
		cmbWorkMethod.getItems().setAll("Start Earlier", "Start Later", "Work from Home", "As Usual");
		cmbWorkMethod.setPrefSize(200, 10);
		cmbChooseDepartment.setPrefSize(200, 10);
		
		
		for (CompanyUIEventsListener l : allListeners) {
			cmbChooseDepartment.getItems().setAll(l.getChangeableDep());
		}
		
		
		lblWorkTime.setVisible(false);
		cmbTime.setVisible(false);

		btnSubmit.setOnAction(e ->{
			boolean res = false;
			for (CompanyUIEventsListener l : allListeners) {
				if ((cmbWorkMethod.getValue() != null && cmbWorkMethod.getValue().equals("Work from Home"))) {
					res = l.changeDepartment(cmbChooseDepartment.getValue(), cmbWorkMethod.getValue(), "08");
				}
				else if ((cmbWorkMethod.getValue() != null && cmbWorkMethod.getValue().equals("As Usual"))) {
					res = l.changeDepartment(cmbChooseDepartment.getValue(), cmbWorkMethod.getValue(), "00");
				} else {
					res = l.changeDepartment(cmbChooseDepartment.getValue(), cmbWorkMethod.getValue(), cmbTime.getValue());
				}
			}
			if (res) {
				switchScene(companyMenu());
			}
		});
		
		cmbWorkMethod.setOnAction(e ->{
			if (cmbWorkMethod.getValue() != null && cmbWorkMethod.getValue().equals("Start Earlier")) {
				lblWorkTime.setVisible(true);
				cmbTime.setVisible(true);
				cmbTime.getItems().setAll(early);
			}
			
			else if (cmbWorkMethod.getValue() != null && cmbWorkMethod.getValue().equals("Start Later")) {
				lblWorkTime.setVisible(true);
				cmbTime.setVisible(true);
				cmbTime.getItems().setAll(later);
			} else {
				lblWorkTime.setVisible(false);
				cmbTime.setVisible(false);
			}
		});
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(15));
		gpRoot.setAlignment(Pos.CENTER);
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		gpRoot.add(lblChooseDepartment, 0, 0);
		gpRoot.add(cmbChooseDepartment, 1, 0);
		gpRoot.add(lblWorkMethod, 0, 1);
		gpRoot.add(cmbWorkMethod, 1, 1);
		gpRoot.add(lblWorkTime, 0, 2);
		gpRoot.add(cmbTime, 1, 2);
		gpRoot.add(btnSubmit, 1, 3);
		return new Scene(gpRoot, 400, 200);
	}
	
	public Scene totalDepartmentsProfit() {
		String departmentsProfit = "";
		
		for (CompanyUIEventsListener l : allListeners) {
			departmentsProfit = l.calcDepartmentsProfit();
		}
		
		txtArea.setText(departmentsProfit);
		
		btnReturn.setOnAction(e ->{
			switchScene(companyMenu());
		});
		
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setAlignment(Pos.CENTER);
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		gpRoot.add(txtArea, 0, 0);
		gpRoot.add(btnReturn, 0, 1);
		
		primaryStage.setTitle("Total Departments Profit");
		return new Scene(gpRoot, 350, 250);
	}
	
	public Scene totalRolesProfit() {
		String rolesProfit = "";
		
		for (CompanyUIEventsListener l : allListeners) {
			rolesProfit = l.calcRolesProfit();
		}
		
		txtArea.setText(rolesProfit);
		
		btnReturn.setOnAction(e ->{
			switchScene(companyMenu());
		});
		
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setAlignment(Pos.CENTER);
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		gpRoot.add(txtArea, 0, 0);
		gpRoot.add(btnReturn, 0, 1);
		
		primaryStage.setTitle("Total Roles Profit");
		return new Scene(gpRoot, 350, 250);
	}
	
	public Scene totalEmployeesProfit() {
		String empProfit = "";
		
		for (CompanyUIEventsListener l : allListeners) {
			empProfit = l.calcEmployeesProfit();
		}
		
		txtArea.setText(empProfit);
		
		btnReturn.setOnAction(e ->{
			switchScene(companyMenu());
		});
		
		
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setAlignment(Pos.CENTER);
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		gpRoot.add(txtArea, 0, 0);
		gpRoot.add(btnReturn, 0, 1);
		
		primaryStage.setTitle("Total Employees Profit");
		return new Scene(gpRoot, 350, 250);
	}
	
	public void messageToUI(String string) {
		JOptionPane.showMessageDialog(null, string);
	}
	
	public void renameCompany(String string) {
		this.companyName = string;
	}
	
	public void departmentAdded(String name) {
		if (!cmbChooseDepartment.getItems().contains(name)) {
			cmbChooseDepartment.getItems().add(name);
		}
	}
	
	public void updateChooseDepComboBox() {
		
		for (CompanyUIEventsListener l : allListeners) {
			cmbChooseDepartment.getItems().setAll(l.getAllDepartments());
		}
	}
	
	public void updateRoleComboBox(String name) {
		
		for (CompanyUIEventsListener l : allListeners) {
			cmbChooseRole.getItems().setAll(l.updateRoles(name));
		}
	}
	
	public void updateChangeableRoleComboBox(String name) {
		
		for (CompanyUIEventsListener l : allListeners) {
			cmbChooseRole.getItems().setAll(l.updateChangeableRoles(name));
		}
	}
	
	public void resetSelections() {
		cmbChooseDepartment.setValue(null);
		cmbChooseRole.setValue(null);
		cmbWorkMethod.setValue(null);
		cmbChooseType.setValue(null);
		cmbTime.setValue(null);
		tfProfit.clear();
		tfSalary.clear();
		tfCommission.clear();
		tfName.clear();
		rbtChangeable.setSelected(false);
		rbtSynch.setSelected(false);
	}
}
