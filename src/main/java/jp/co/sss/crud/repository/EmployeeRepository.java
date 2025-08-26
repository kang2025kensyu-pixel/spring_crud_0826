package jp.co.sss.crud.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.crud.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
//	1. List<User>란?
//
//			  List<User>는 이름 그대로 여러 개의 User 객체를 담을 수 있는 컬렉션 타입. 즉, 조건에 맞는 사용자 여러 명을 조회할 때 사용한다.
//
//			​
//
//			특징
//
//			0개 이상의 사용자 데이터를 담을 수 있습니다.
//
//			조회 결과가 없으면 빈 리스트(empty list)가 반환됩니다.
//
//			절대로 null이 아니라, 비어있는 리스트를 반환하는 것이 관례입니다.
//
//			여러 명의 데이터를 한 번에 처리할 때 매우 유용합니다.
//	2. Optional<User>란?
//
//			  Optional<User>는 0개 또는 1개의 User 객체를 담는 **컨테이너(wrapper)**이다. 주로 단일 사용자 정보를 조회할 때 사용하며, 데이터가 존재하지 않을 수도 있음을 명확하게 표현한다.
//			[출처] [Java/Spring] List<User>와 Optional<User>의 차이|작성자 vision21000

	  Optional<Employee> findTopByOrderByEmpIdDesc();
	    Optional<Employee> findByEmpId(Integer empId); 
	    List<Employee> findByEmpNameLikeOrderByEmpIdAsc(String empName);
	    
	    List<Employee> findByDepartment_DeptIdOrderByEmpIdAsc(Integer deptId);
//		List<Employee> findByEmpNameLikeOrderByEmpIdAsc(String string);
		List<Employee> findAllByOrderByEmpIdAsc();
	

	
}