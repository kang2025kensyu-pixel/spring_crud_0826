package jp.co.sss.crud.util;

/**
 * 独自JPQLを定義するためのクラス
 *
 * @author System Shared
 */

public class JPQLConstant {
	/** 社員情報の全件検索(社員ID順) */
	public static final String FIND_ALL_EMPLOYEE_ORDER_BY_EMP_ID = "SELECT e FROM Employee e INNER JOIN e.department d ORDER BY e.empId ASC";

	
	/** 社員情報の社員名検索(社員ID順) */
	public static final String FIND_EMPLOYEE_BY_EMP_NAME_ORDER_BY_EMP_ID = "SELECT e FROM Employee e INNER JOIN e.department d WHERE e.empName LIKE %:empName% ORDER BY e.empId ASC";

	/** 社員情報の部署ID検索(社員ID順) */
	public static final String FIND_EMPLOYEE_BY_DEPT_ID_ORDER_BY_EMP_ID = "SELECT e FROM Employee e INNER JOIN e.department d WHERE e.department.deptId = :deptId ORDER BY e.empId ASC";
}
