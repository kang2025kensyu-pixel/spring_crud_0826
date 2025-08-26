package jp.co.sss.crud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.crud.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {



	 Optional<Department> findById(Integer deptId);
}