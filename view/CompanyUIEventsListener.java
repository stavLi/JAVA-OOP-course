package NicoleZarch_StasLibman.view;

import java.util.ArrayList;

import NicoleZarch_StasLibman.model.Employee.preference;

public interface CompanyUIEventsListener {
	boolean selectFromFile(boolean ans);
	boolean addDepartment(String deptName, String workMethod, String time, boolean isSynch, boolean isChangeable);
	boolean addRole(String deptName, String roleName, String workMethod, String time, boolean isSynch, boolean isChangeable);
	boolean addEmployee(String deptName, String roleName, String type, String name, String preference, String time, String profit, String salary, String percentage);
	ArrayList<String> updateRoles(String deptName);
	ArrayList<String> updateChangeableRoles(String deptName);
	ArrayList<String> getAllDepartments();
	ArrayList<String> getChangeableDep();
	String getModelName();
	boolean newModelUI(String name);
	String viewDepartments();
	String viewRoles();
	String viewEmployees();
	boolean changeRole(String roleName, String workMethod, String time);
	boolean changeDepartment(String deptName, String workMethod, String time);
	String calcDepartmentsProfit();
	String calcRolesProfit();
	String calcEmployeesProfit();
	boolean saveCompany();
	}
