package NicoleZarch_StasLibman.model;

import java.io.Serializable;
import java.util.ArrayList;

import NicoleZarch_StasLibman.model.Employee.preference;

public class Company implements Serializable {
	
	private ArrayList<CompanyEventsListener> allListeners = new ArrayList<CompanyEventsListener>();
	private String companyName;
	private ArrayList<Department> allDepartments;
	public static final double PROFIT_PERCENT = 0.2;
	public static final double PROFIT_PERCENT_HOME = 0.1;
	public static final int START_TIME = 8;
	
	public Company() {
		this.allDepartments = new ArrayList<Department>();
		
	}
	
	public void registerListener(CompanyEventsListener newListener) {
		allListeners.add(newListener);
	}
	
	public void setName(String name) {
		this.companyName = name;
	}
	
	public String getName() {
		return companyName;
	}
	
	public ArrayList<Department> getAllDepartments(){
		return allDepartments;
	}
	
	public void setAllDepartments(ArrayList<Department> allDepartments) {
		this.allDepartments = allDepartments;
	}
	
	public Department getDepartmentByName(String deptName) {
		for (Department dep : allDepartments) {
			if (dep.getName().equalsIgnoreCase(deptName)) {
				return dep;
			}
		}
		
		return null;
	}
	
	public Role getRoleByName(String roleName) {
		for (Department dep : allDepartments) {
			if (dep.getRoleByName(roleName) != null) {
				return dep.getRoleByName(roleName);
			}
		}
		
		return null;
	}
	
	// 1 - Add Department
	public boolean addDepartment(String deptName, preference workMethod, int hourOffset, boolean isSynch, boolean isChangeable) throws Exception {
		if (getDepartmentByName(deptName) == null) {
			allDepartments.add(new Department(deptName, workMethod, hourOffset, isSynch, isChangeable));
			fireDepartmentAddedEvent(deptName);
			return true;
		} else {
			throw new Exception("This department already exists!");
		}
	}
	
	// 1 - Add Role
	public boolean addRole(String deptName, String roleName, preference workMethod, int hourOffset, boolean isSynch, boolean isChangeable) throws Exception {
		if (getDepartmentByName(deptName) != null) {
			return getDepartmentByName(deptName).addRole(roleName, getDepartmentByName(deptName), workMethod, hourOffset, isSynch, isChangeable);	
		} else {
			throw new Exception("Department does not exist!");
		}
	}
	
	// 1 - Add Employee
	public boolean addEmployee(String deptName, String roleName, String type, String name, preference preference, int hourOffset, double profit, double salary, double percentage) throws Exception {
		if (getDepartmentByName(deptName) != null) {
			if (getDepartmentByName(deptName).getRoleByName(roleName) != null) {
				getDepartmentByName(deptName).getRoleByName(roleName).addEmployee(type, name, preference, getDepartmentByName(deptName).getRoleByName(roleName).getWorkMethod(), hourOffset, profit, salary, percentage);
				return true; 
			} else {
				throw new Exception("Role does not match department!");
			}
		} else {
			throw new Exception("Department does not exist!");
		}
	}
	
	// 2 - View Departments
	public String showDepartmentsList() {
		
		if (allDepartments.size() == 0) {
			return "No departments are currently available";
		}
		
		String res = "The departments in the company are: ";
		for (Department dep : allDepartments) {
			res = res.concat("\n - " + dep.toString());
		}
		return res;
	}
	
	// 2 - View Roles
	public String showRolesList() {
		String res = "All roles in the company are: ";	
		for (Department dep : allDepartments) {
			for (Role role : dep.getAllRoles()) {
				res = res.concat("\n - " + role.toString());
			}
		}
		
		if (res.equals("All roles in the company are: ")) {
			return "No roles are currently available"; 
		}
		
		return res;
	}
	
	// 2 - View Employees
	public String showEmployeesList() {
		String res = "All employees in the company are: ";
		for (Department dep : allDepartments) {
			for (Role role : dep.getAllRoles()) {
				for (Employee emp : role.getAllEmployees()) {
					res = res.concat("\n - " + emp.toString());
				}
			}
		}
		
		if (res.equals("All employees in the company are: ")) {
			return "No employees are currently available"; 
		}
		
		return res;
	}
	
	// 2 - View All Company	
	public String showCompany() {
		String res = "In this company " + allDepartments.size() +" departments: " ;
		for (Department dep : allDepartments) {
			res = res.concat("\n\tIn department" + dep.getName() + dep.getAllRoles().size() + " roles: ");
			for (Role role : dep.getAllRoles()) {
				res = res.concat("\n\t This role has " + role.getAllEmployees().size() + " employees: ");
				for (Employee emp : role.getAllEmployees()) {
					res = res.concat("\n\t" + emp.toString());
				}
			}
		}
		return res;
	}
	
	
	// 3 - Update Role Work Method
	public boolean changeRoleWork(String roleName, preference workMethod, int hourOffset) throws Exception {

		if (!getRoleByName(roleName).getDepartment().getIsChangeable()) {
			throw new Exception("Role's Department is unchangeable");
		}
		
		else if (!getRoleByName(roleName).getIsChangeable()) {
			throw new Exception("Role is unchangeable");
		}
			
		else if (getRoleByName(roleName) != null) {
			getRoleByName(roleName).changeMode(workMethod, hourOffset);
			return true;
		}

		return false; 
	}
	
	// 4 - Update Department Work Method
	public boolean changeDepartmentWork(String departmentName, preference workMethod, int hourOffset) throws Exception { //note modeChangeable,synch...
		if (!getDepartmentByName(departmentName).getIsChangeable()) {
			throw new Exception("Department is unchangeable");
		}
		
		if (getDepartmentByName(departmentName) != null) {
			getDepartmentByName(departmentName).changeMode(workMethod, hourOffset);
			return true;
		}

		return false; 
	}
	
	// 5 - View Departments Profit
	public String showDepartmentsProfit() {
		if (allDepartments.size() == 0) {
			return "No departments are  currently available";
		}
		
		String res = "The departments profit in the company is: ";
		for (Department dep : allDepartments) {
			res = res.concat("\n - " + dep.getName() + ": \t\t" + dep.calcMoneyProfit() + " nis. " + (dep.calcMoneyProfit()>=0? "profit":"loss") + "\t(" + dep.calcHourProfit() + " hours/worker)");
		}
		return res;
	}

	// 5 - View Roles Profit
	public String showRolesProfit() {
		String res = "Roles profit in the company is: ";	
		for (Department dep : allDepartments) {
			for (Role role : dep.getAllRoles()) {
				res = res.concat("\n - " + role.getName() + ": \t\t" + role.calcMoneyProfit() + " nis. " + (role.calcMoneyProfit()>=0? "profit":"loss") + "\t(" + role.calcHourProfit() + " hours/worker)");
			}
		}
		
		if (res.equals("Roles profit in the company is: ")) {
			return "No roles are currently available"; 
		}
		
		return res;
	}

	// 5 - View Employees Profit
	public String showEmployeesProfit() {
		String res = "Employees profit in the company is: ";
		for (Department dep : allDepartments) {
			for (Role role : dep.getAllRoles()) {
				for (Employee emp : role.getAllEmployees()) {
					res = res.concat("\n - " + emp.getName() + ":\t\t" + emp.calcMoneyProfit() + " nis. " + (emp.calcMoneyProfit()>=0? "profit":"loss") + "\t(" + emp.calcHourProfit() + " hours/worker)");
				}
			}
		}
		
		if (res.equals("Employees profit in the company is: ")) {
			return "No employees are currently available"; 
		}
		
		return res;
	}
	
	private boolean fireDepartmentAddedEvent(String name) {
		for (CompanyEventsListener l : allListeners) {
			l.departmentAdded(name);
		}
		return true;
	}
	
	
	
}
