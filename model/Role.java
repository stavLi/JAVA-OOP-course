package NicoleZarch_StasLibman.model;

import java.io.Serializable;
import java.util.ArrayList;

import NicoleZarch_StasLibman.model.Employee.preference;

public class Role implements Synchronizable, ModeChangable, Profitable, Serializable{

	private ArrayList<Employee> roleEmployees;
	private String roleName;
	private Department department;
	private preference workMethod;
	private int hourOffset;
	private boolean isSynch;
	private boolean isChangeable;


	public Role(String name, Department dep, preference workMethod, int hourOffset, boolean isSynch, boolean isChangeable) {
		this.roleName = name;
		this.department = dep;
		this.workMethod = workMethod;
		this.hourOffset = hourOffset;
		this.isSynch = isSynch;
		this.isChangeable = isChangeable;
		this.roleEmployees = new ArrayList<Employee>();
	}
	
	public String getName() {
		return roleName;
	}
	
	public Department getDepartment() {
		return department;
	}
	
	public ArrayList<Employee> getAllEmployees() {
		return roleEmployees;
	}
	
	public preference getWorkMethod() {
		return workMethod;
	}
	
	public int getHourOffset() {
		return hourOffset;
	}
	
	public boolean getIsSynch() {
		return isSynch;
	}
	
	public boolean getIsChangeable() {
		return isChangeable;
	}
	
	public Employee getEmployeeByName(String empName) {
		for (Employee emp : roleEmployees) {
			if (emp.getName().equalsIgnoreCase(empName)) {
				return emp;
			}
		}
		
		return null;
	}
	
	public boolean addEmployee(String type, String name, preference pref, preference workMethod, int hourOffset, double profit, double salary, double percentage) {
		
		if (pref == preference.FromHome ) {
			hourOffset = 8;
		}
		else if (workMethod == preference.Usual) {
			hourOffset = 0;
		} else {
			if (isSynch || !department.getIsSynch()) {
				hourOffset = this.hourOffset;
			} else if (!isChangeable || !department.getIsChangeable()) {
				hourOffset = this.hourOffset;
				workMethod = this.workMethod;
			}
		}
		
		if (type.equalsIgnoreCase("Base Salary")) {
			BaseSalaryEmployee temp = new BaseSalaryEmployee(this, name, pref, workMethod, hourOffset, profit, salary);
			roleEmployees.add(temp);
		}
		
		if (type.equalsIgnoreCase("Hourly Employee")) {
			HourlyEmployee temp = new HourlyEmployee(this, name, pref, workMethod, hourOffset, profit, salary);
			roleEmployees.add(temp);
		}
		
		if (type.equalsIgnoreCase("Commission Employee")) {
			CommissionEmployee temp = new CommissionEmployee(this, name, pref, workMethod, hourOffset, profit, salary, percentage);
			roleEmployees.add(temp);
		}
		
		return true;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Role)) {
			return false;
		}
		
		Role temp = (Role)obj;
		if (temp.getName().equals(this.getName())) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		if (roleEmployees.isEmpty()) {
			return roleName + " Team (" + department.getName() + ") *Currently Empty*";
		}
		
		StringBuffer str = new StringBuffer();
		str.append(roleName + " Team: ");
		for (Employee emp : roleEmployees) {
			str.append("\n\t - " + emp.toString());
		}
		
		return str.toString();
	}

	@Override
	public boolean changeMode(preference workMethod, int hourOffset) {
		if (isChangeable) {
			this.workMethod = workMethod;
			this.hourOffset = hourOffset;
			for (Employee emp : roleEmployees) {
				emp.changeMode(workMethod, hourOffset);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean isSynchronyse(int hourOffset) {
		if (isSynch) {
			this.hourOffset = hourOffset;
			for (Employee emp : roleEmployees) {
				emp.isSynchronyse(hourOffset);
			}
			return true;
		}
		return false;
	}

	@Override
	public double calcMoneyProfit() {
		double profit = 0;
		for (Employee emp : roleEmployees) {
			profit += emp.calcMoneyProfit();
		}
		profit =  (Math.round(profit*100))/100.0;
		return profit;
	}

	@Override
	public double calcHourProfit() {
		double profit = 0;
		for (Employee emp : roleEmployees) {
			profit += emp.calcHourProfit();
		}
		profit =  (Math.round(profit*100))/100.0;
		return profit;
	}

	
	
}
