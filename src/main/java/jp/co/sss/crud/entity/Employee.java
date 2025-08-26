package jp.co.sss.crud.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {
	@Id
	@Column(name = "emp_id")
	private Integer empId;

	@Column(name = "emp_pass")
	private String empPass;

	@Column(name = "emp_name")
	private String empName;

	@Column
	private Integer gender;

	@Column
	private String address;

	@Column
	private String birthday;

	@Column
	private Integer authority;

	@ManyToOne
	@JoinColumn(name = "dept_id", referencedColumnName = "dept_id")
	private Department department;

	// (Getters and Setters for all fields...)

 
	
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpPass(String empPass) {
		this.empPass = empPass;
	}

	public String getEmpPass() {
		return empPass;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpName() {
		return empName;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getGender() {
		return gender;
	}
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;

		}

	public String getBirthday() {
		return birthday;
	}

	public Integer getAuthority() {
		return authority;
	}

	public void setAuthority(Integer authority) {
		this.authority = authority;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
}