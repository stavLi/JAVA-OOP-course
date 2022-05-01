package NicoleZarch_StasLibman.model;

import NicoleZarch_StasLibman.model.Employee.preference;

public class BaseSalaryEmployee extends Employee{
	
	private double baseSalary;
	
	public BaseSalaryEmployee(Role role, String name, preference preference, preference workMethod, int hourOffset, double profit, double salary) {
		super(role, name, preference, workMethod, hourOffset, profit);
		this.baseSalary = salary;
	}
}

