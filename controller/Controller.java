package NicoleZarch_StasLibman.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import NicoleZarch_StasLibman.model.Company;
import NicoleZarch_StasLibman.model.CompanyEventsListener;
import NicoleZarch_StasLibman.model.Department;
import NicoleZarch_StasLibman.model.Employee;
import NicoleZarch_StasLibman.model.Role;
import NicoleZarch_StasLibman.model.Employee.preference;
import NicoleZarch_StasLibman.view.CompanyUIEventsListener;
import NicoleZarch_StasLibman.view.View;

public class Controller implements CompanyEventsListener, CompanyUIEventsListener, Serializable {

	private Company model;
	private transient View view;

	public Controller(Company model, View view) {
		this.model = model;
		this.view = view;

		model.registerListener(this);
		view.registerListener(this);

	}

	@Override
	public String getModelName() {
		return model.getName();
	}

	@Override
	public boolean selectFromFile(boolean ans) {
		if (ans) {
			try {
				// read id counter
				ObjectInputStream inFile2 = new ObjectInputStream(new FileInputStream("StaticEmployeeIdCounter.dat"));
				Employee.idCounter = (Integer) inFile2.readObject();
				inFile2.close();

				// read Company (model)
				ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("Company.dat"));
				this.model.setAllDepartments((ArrayList<Department>) inFile.readObject());
				this.model.setName((String) inFile.readObject());
				this.view.renameCompany(model.getName());
				this.model.registerListener(this);
				inFile.close();

				if (model == null) {
					throw new IllegalArgumentException("Previous file is empty. Continuing as usual.");
				}
				model.registerListener(this);
				view.messageToUI("File loaded successfuly!");
				return true;

			} catch (FileNotFoundException e) {
				view.messageToUI("There are no files to load from yet. Continuing as usual.");
				return false;
			} catch (IllegalArgumentException e) {
				view.messageToUI(e.getMessage());
				return false;
			} catch (IOException e) {
				view.messageToUI(e.getMessage());
				return false;
			} catch (ClassNotFoundException e) {
				view.messageToUI(e.getMessage());
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean saveCompany() {
		try {
			ObjectOutputStream outFile2 = new ObjectOutputStream(new FileOutputStream("StaticEmployeeIdCounter.dat"));
			outFile2.writeObject(Employee.idCounter);
			outFile2.close();

			// write Company
			ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("Company.dat"));
			outFile.writeObject(model.getAllDepartments());
			outFile.writeObject(model.getName());
			outFile.close();
			view.messageToUI("File saved successfuly!");
			return true;

		} catch (FileNotFoundException e) {
			view.messageToUI("File not found :(");
			return false;
		} catch (IOException e) {
			view.messageToUI(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean newModelUI(String name) {
		model.setName(name);
		return true;
	}

	@Override
	public boolean addDepartment(String deptName, String workMethod, String time, boolean isSynch,
			boolean isChangeable) {

		try {
			if (deptName == null || workMethod == null || time == null) {
				throw new IllegalArgumentException("Some fields remained empty");
			}
			
			int hourOffset = 0;
			preference method = preference.Usual;
			if (workMethod.equals("Start Earlier")) {
				method = preference.Early;
				hourOffset = Company.START_TIME - Integer.parseInt(time.substring(0, 2));
			} else if (workMethod.equals("Start Later")) {
				method = preference.Later;
				hourOffset = Integer.parseInt(time.substring(0, 2)) - Company.START_TIME;
			} else if (workMethod.equals("Work from Home")) {
				method = preference.FromHome;
				hourOffset = 8;
			} else if (workMethod.equals("As Usual")) {
				method = preference.Usual;
				hourOffset = 0;
			}
			model.addDepartment(deptName, method, hourOffset, isSynch, isChangeable);
			view.messageToUI(deptName + " department was added!");
			return true;
		} catch (IllegalArgumentException e) {
			view.messageToUI(e.getMessage());
			return false;
		} catch (Exception e) {
			view.messageToUI(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean addRole(String deptName, String roleName, String workMethod, String time, boolean isSynch, boolean isChangeable) {
		try {
			if (deptName == null || roleName == null || workMethod == null || time == null) {
				throw new IllegalArgumentException("Some fields remained empty");
			}

			int hourOffset = 0;
			preference method = preference.Usual;
			if (workMethod.equals("Start Earlier")) {
				method = preference.Early;
				hourOffset = Company.START_TIME - Integer.parseInt(time.substring(0, 2));
			} else if (workMethod.equals("Start Later")) {
				method = preference.Later;
				hourOffset = Integer.parseInt(time.substring(0, 2)) - Company.START_TIME;
			} else if (workMethod.equals("Work from Home")) {
				method = preference.FromHome;
				hourOffset = 8;
			} else if (workMethod.equals("As Usual")) {
				method = preference.Usual;
				hourOffset = 0;
			}
			boolean res = model.addRole(deptName, roleName, method, hourOffset, isSynch, isChangeable);
			if (res && !model.getDepartmentByName(deptName).getIsChangeable()) {
				view.messageToUI(roleName + " role was added. Work method was overriden by Department.");
			} else if (res) {
				view.messageToUI(roleName + " role was added!");
			}
			
			return true;
		
		} catch (IllegalArgumentException e) {
			view.messageToUI(e.getMessage());
			return false;
		} catch (Exception e) {
			view.messageToUI(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean addEmployee(String deptName, String roleName, String type, String name, String myPreference,
			String time, String profit, String salary, String percentage) {
		try {
			if (deptName == null || roleName == null || time == null || name == null || myPreference == null
					|| salary == null || profit == null) {
				throw new IllegalArgumentException("Some fields remained empty");
			}

			double dblProfit = 0;
			double dblSalary = 0;
			double dblPercentage = 0;
			int hourOffset = 0;
			
			dblProfit =  Math.round((Double.parseDouble(profit))*100)/100;
			dblSalary = Math.round((Double.parseDouble(salary))*100)/100;
			
			if (percentage == null) {
				dblPercentage = 0;
			} else  {
				dblPercentage = Math.round((Double.parseDouble(percentage))*100)/100;
				if (dblPercentage > 100 || dblPercentage < 0) {
					throw new IllegalArgumentException("Percentage should be between 0-100");
				}
			}

			preference prfrnc = preference.Usual;
			if (myPreference.equals("Start Earlier")) {
				prfrnc = preference.Early;
				hourOffset = Company.START_TIME - Integer.parseInt(time.substring(0, 2));
			} else if (myPreference.equals("Start Later")) {
				prfrnc = preference.Later;
				hourOffset = Integer.parseInt(time.substring(0, 2)) - Company.START_TIME;
			} else if (myPreference.equals("Work from Home")) {
				prfrnc = preference.FromHome;
				hourOffset = 8;
			} else if (myPreference.equals("As Usual")) {
				prfrnc = preference.Usual;
				hourOffset = 0;
			}
			model.addEmployee(deptName, roleName, type, name, prfrnc, hourOffset, dblProfit, dblSalary, dblPercentage);
			view.messageToUI(name + " was added!");
			return true;
		} catch (NumberFormatException e) {
			view.messageToUI("Either profit, salary or percentage are not a number!");
			return false;
		} catch (IllegalArgumentException e) {
			view.messageToUI(e.getMessage());
			return false;
		} catch (Exception e) {
			view.messageToUI(e.getMessage());
			return false;
		}
	}

	@Override
	public String viewDepartments() {
		return model.showDepartmentsList();
	}

	@Override
	public String viewRoles() {
		return model.showRolesList();
	}

	@Override
	public String viewEmployees() {
		return model.showEmployeesList();
	}
	
	@Override
	public boolean changeRole(String roleName, String workMethod, String time) {
		try {
			if (roleName == null || workMethod == null || time == null) {
				throw new IllegalArgumentException("Some fields remained empty");
			}

			int hourOffset = 0;
			preference method = preference.Usual;
			if (workMethod.equals("Start Earlier")) {
				method = preference.Early;
				hourOffset =Company.START_TIME - Integer.parseInt(time.substring(0, 2));
			} else if (workMethod.equals("Start Later")) {
				method = preference.Later;
				hourOffset = Integer.parseInt(time.substring(0, 2)) - Company.START_TIME;
			} else if (workMethod.equals("Work from Home")) {
				method = preference.FromHome;
				hourOffset = 8;
			} else if (workMethod.equals("As Usual")) {
				method = preference.Usual;
				hourOffset = 0;
			}
			model.changeRoleWork(roleName, method, hourOffset);
			view.messageToUI(roleName + " Role was updated!");
			
			return true;
		} catch (IllegalArgumentException e) {
			view.messageToUI(e.getMessage());
			return false;
		} catch (Exception e) {
			view.messageToUI(e.getMessage());
			return false;
		}
	}
	
	@Override
	public boolean changeDepartment(String deptName, String workMethod, String time) {
		try {
			if (deptName == null || workMethod == null || time == null) {
				throw new IllegalArgumentException("Some fields remained empty");
			}

			int hourOffset = 0;
			preference method = preference.Usual;
			if (workMethod.equals("Start Earlier")) {
				method = preference.Early;
				hourOffset = Company.START_TIME - Integer.parseInt(time.substring(0, 2));
			} else if (workMethod.equals("Start Later")) {
				method = preference.Later;
				hourOffset = Integer.parseInt(time.substring(0, 2)) - Company.START_TIME;
			} else if (workMethod.equals("Work from Home")) {
				method = preference.FromHome;
				hourOffset = 8;
			} else if (workMethod.equals("As Usual")) {
				method = preference.Usual;
				hourOffset = 0;
			}
			model.changeDepartmentWork(deptName, method, hourOffset);
			view.messageToUI(deptName + " Department was updated!");
			
			return true;
		} catch (IllegalArgumentException e) {
			view.messageToUI(e.getMessage());
			return false;
		} catch (Exception e) {
			view.messageToUI(e.getMessage());
			return false;
		}
	}
	
	@Override
	public boolean departmentAdded(String name) {
		view.departmentAdded(name);
		return true;
	}

	@Override
	public ArrayList<String> updateRoles(String deptName) {
		ArrayList<String> roles = new ArrayList<>();

		for (Role role : model.getDepartmentByName(deptName).getAllRoles()) {
			roles.add(role.getName());
		}

		return roles;
	}

	@Override
	public ArrayList<String> getAllDepartments() {
		ArrayList<String> deps = new ArrayList<String>();
		for (Department dep : model.getAllDepartments()) {
			deps.add(dep.getName());
		}

		return deps;
	}

	@Override
	public ArrayList<String> getChangeableDep() {
		ArrayList<String> deps = new ArrayList<String>();
		for (Department dep : model.getAllDepartments()) {
			if (dep.getIsChangeable()) {
				deps.add(dep.getName());
			}
		}

		return deps;
	}

	@Override
	public ArrayList<String> updateChangeableRoles(String deptName) {
		ArrayList<String> roles = new ArrayList<>();

		for (Role role : model.getDepartmentByName(deptName).getAllRoles()) {
			if (role.getIsChangeable()) {
				roles.add(role.getName());
			}
		}

		return roles;
	}

	@Override
	public String calcDepartmentsProfit() {
		return model.showDepartmentsProfit();
	}

	@Override
	public String calcRolesProfit() {
		return model.showRolesProfit();
	}

	@Override
	public String calcEmployeesProfit() {
		return model.showEmployeesProfit();
	}

	

}
