package NicoleZarch_StasLibman.model;

import static NicoleZarch_StasLibman.model.Employee.preference.FromHome;

import java.io.Serializable;

import NicoleZarch_StasLibman.model.Employee.preference;

public class Employee implements Synchronizable, ModeChangable, Profitable, Serializable{
	
	public static enum preference {Early, Later, Usual, FromHome};
	public final int ID_GEN = 1000;
	public static int idCounter;
	private Role employeeRole;
	private preference preference;
	private String name;
	private int id;
	private double profit;
	private preference workMethod;
	private int hourOffset;
	private boolean isSynch;
	private boolean isChangeable;
	
	public Employee(Role role, String name, preference preference, preference workMethod, int hourOffset, double profit) {
		setRole(role);
		this.preference = preference;
		this.name = name;
		idCounter++;
		this.profit = profit;
		this.id = ID_GEN + idCounter;
		this.workMethod = workMethod;
		this.hourOffset = hourOffset;
		this.isSynch = role.getIsSynch();
		this.isChangeable = role.getIsChangeable();
		
		if (!employeeRole.getIsChangeable()) {
			this.hourOffset = employeeRole.getHourOffset();
			this.workMethod = employeeRole.getWorkMethod();
		}
		
		if (!employeeRole.getDepartment().getIsChangeable()) {
			this.hourOffset = employeeRole.getDepartment().getHourOffset();
			this.workMethod = employeeRole.getDepartment().getWorkMethod();
		}
	}
	
	public boolean setRole(Role role) {
		this.employeeRole = role;
		return true;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Employee)) {
			return false;
		}
		
		Employee temp = (Employee)obj;
		if (temp.getName().equals(this.getName())) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "Name: " + name + ",\tID: " + id + ",\tRole: " + employeeRole.getName();
	}

	@Override
	public boolean isSynchronyse(int hourOffset) {
		if (isSynch) {
			this.hourOffset = hourOffset;
			return true;
		}
		return false;
	}


	@Override
	public boolean changeMode(preference workMethod, int hourOffset) {
		if (isChangeable) {
			this.workMethod = workMethod;
			this.hourOffset = hourOffset;
			return true;
		}
		return false;
	}
	
	@Override
	public double calcMoneyProfit() {
		return calcHourProfit()*profit;
	}

	@Override
	public double calcHourProfit() {
		double profit = 0;
		if (workMethod.name().equals(preference.name())) {
			if (workMethod.name().equals(FromHome.name())) {
				profit = 8*(Company.PROFIT_PERCENT_HOME);
			} else {
				profit = hourOffset*(Company.PROFIT_PERCENT);
			}
		} else {
			if (preference.name().equals(FromHome.name())) {
				profit = -(8*(Company.PROFIT_PERCENT_HOME));
			} else {
				profit = -(hourOffset*(Company.PROFIT_PERCENT));
			}
		}
		
		profit =  (Math.round(profit*100))/100.0;
		return profit;
	}
}
