package jp.co.sss.crud.util;

import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.form.EmployeeForm;

/**
 * Beanのプロパティをコピーするクラス
 * ※アクセサを実装し終えたらコメントを解除すること
 * ※コピーする手段として、別途BeanUtils.copyPropertiesメソッドを使用しても構わない。
 *
 */
public class BeanCopy {
	    /**
	     * Formクラスの各フィールドの値をエンティティ(Employee)にコピー
	     *
	     * @param form
	     *            入力された社員情報
	     * @return エンティティ
	     */
	    public static Employee copyFormToEmployee(EmployeeForm form) {
	        Employee entity = new Employee();
	        Department department = new Department();

	        entity.setEmpId(form.getEmpId());
	        entity.setEmpPass(form.getEmpPass());
	        entity.setEmpName(form.getEmpName());
	        entity.setGender(form.getGender());
	        entity.setAddress(form.getAddress());
	        entity.setBirthday(form.getBirthday());
	        entity.setAuthority(form.getAuthority());

	        department.setDeptId(form.getDeptId());
	        entity.setDepartment(department);

	        return entity;
	    }

	    /**
	     * エンティティ(Employee)の各フィールドの値をFormクラスにコピー
	     *
	     * @param entity
	     *            エンティティ
	     * @return Formクラス
	     */
	    public static EmployeeForm copyEntityToForm(Employee entity, EmployeeForm form) {

	        form.setEmpId(entity.getEmpId());
	        form.setEmpPass(entity.getEmpPass());
	        form.setEmpName(entity.getEmpName());
	        form.setGender(entity.getGender());
	        form.setAddress(entity.getAddress());
	        form.setBirthday(entity.getBirthday());
	        form.setAuthority(entity.getAuthority());
	        form.setDeptId(entity.getDepartment().getDeptId());

	        return form;
	    }
}
