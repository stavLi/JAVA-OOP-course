package NicoleZarch_StasLibman.model;

import NicoleZarch_StasLibman.model.Employee.preference;

public class CommissionEmployee extends BaseSalaryEmployee{
	
	private double percentage;
	
	public CommissionEmployee(Role role, String name, preference preference, preference workMethod, int hourOffset, double profit, double salary, double percentage) {
		super(role, name, preference, workMethod, hourOffset, profit, salary);
		this.percentage = percentage;
	}
}
