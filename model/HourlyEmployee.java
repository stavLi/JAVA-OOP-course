package NicoleZarch_StasLibman.model;

import NicoleZarch_StasLibman.model.Employee.preference;

public class HourlyEmployee extends Employee{

	private double perHour;
	
	public HourlyEmployee(Role role, String name, preference preference, preference workMethod, int hourOffset, double profit, double salary) {
		super(role, name, preference, workMethod, hourOffset, profit);
		this.perHour = salary;
	}
}
