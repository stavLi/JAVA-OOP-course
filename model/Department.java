package NicoleZarch_StasLibman.model;

import java.io.Serializable;
import java.util.ArrayList;

import NicoleZarch_StasLibman.model.Employee.preference;

public class Department implements Synchronizable, ModeChangable, Profitable, Serializable {

	private String deptName;
	private ArrayList<Role> allRoles;
	private preference workMethod;
	private int hourOffset;
	private boolean isSynch;
	private boolean isChangeable;

	public Department(String deptName, preference workMethod, int hourOffset, boolean isSynch, boolean isChangeable) {
		this.deptName = deptName;
		this.workMethod = workMethod;
		this.hourOffset = hourOffset;
		this.isSynch = isSynch;
		this.isChangeable = isChangeable;
		this.allRoles = new ArrayList<Role>();
	}

	public String getName() {
		return deptName;
	}

	public boolean getIsSynch() {
		return isSynch;
	}

	public boolean getIsChangeable() {
		return isChangeable;
	}

	public int getHourOffset() {
		return hourOffset;
	}

	public preference getWorkMethod() {
		return workMethod;
	}

	public ArrayList<Role> getAllRoles() {
		return allRoles;
	}

	public Role getRoleByName(String roleName) {
		for (Role role : allRoles) {
			if (role.getName().equalsIgnoreCase(roleName)) {
				return role;
			}
		}

		return null;
	}

	public boolean addRole(String roleName, Department dept, preference workMethod, int hourOffset, boolean isSynch, boolean isChangeable) throws Exception {
		if (getRoleByName(roleName) == null) {

			if (workMethod == preference.FromHome ) {
				hourOffset = 8;
			}
			else if (workMethod == preference.Usual) {
				hourOffset = 0;
			} else {
				if (isSynch) {
					hourOffset = this.hourOffset;
				} else if (!isChangeable) {
					hourOffset = this.hourOffset;
					workMethod = this.workMethod;
				}
			}

			allRoles.add(new Role(roleName, dept, workMethod, hourOffset, isSynch, isChangeable));
			return true;
		} else {
			throw new Exception("This role already exists!");
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Department)) {
			return false;
		}

		Department temp = (Department) obj;
		if (temp.getName().equals(this.getName())) {
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		if (allRoles.isEmpty()) {
			return deptName + " Department *Currently Empty*";
		}

		StringBuffer str = new StringBuffer();
		str.append(deptName + " Department: ");
		for (Role role : allRoles) {
			str.append("\n\t - " + role.getName());
		}

		return str.toString();
	}

	@Override
	public boolean isSynchronyse(int hourOffset) {
		if (isSynch) {
			this.hourOffset = hourOffset;
			for (Role role : allRoles) {
				role.isSynchronyse(hourOffset);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean changeMode(preference workMethod, int hourOffset) {
		if (isChangeable) {
			this.workMethod = workMethod;
			this.hourOffset = hourOffset;
			for (Role role : allRoles) {
				role.changeMode(workMethod, hourOffset);
			}
			return true;
		}
		return false;
	}

	@Override
	public double calcMoneyProfit() {
		double profit = 0;
		for (Role role : allRoles) {
			profit += role.calcMoneyProfit();
		}
		profit =  (Math.round(profit*100))/100.0;
		return profit;
	}

	@Override
	public double calcHourProfit() {
		double profit = 0;
		for (Role role : allRoles) {
			profit += role.calcHourProfit();
		}
		
		profit =  (Math.round(profit*100))/100.0;
		return profit;
	}

}
