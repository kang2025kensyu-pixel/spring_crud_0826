package jp.co.sss.crud.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

	@Entity
	@Table(name = "department")
	public class Department {

	    @Id
	    @Column(name = "dept_id")
	    private Integer deptId;

	    @Column(name = "dept_name")
	    private String deptName;

	    public Integer getDeptId() {
	        return deptId;
	    }

	    public void setDeptId(Integer deptId) {
	        this.deptId = deptId;
	    }

	    public String getDeptName() {
	        return deptName;
	    }

	    public void setDeptName(String deptName) {
	        this.deptName = deptName;
	    }
	   

	}
