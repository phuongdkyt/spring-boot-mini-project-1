package com.example.demo.repository;

import com.example.demo.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface TestRepository extends JpaRepository<TestEntity, Integer> {
	@Query(value = "select * from tbl_tests where test_name = :name", nativeQuery = true)
	Optional<TestEntity> findByName(String name);

	Optional<TestEntity> findById(Integer id);

	@Query(value = "SELECT TE.id, TE.test_name, TE.test_time, TE.test_date_begin, TE.test_date_finish FROM \n" +
			"tbl_tests TE\n" +
			"LEFT JOIN tbl_test_users TU ON TU.test_id = TE.id\n" +
			"where TU.user_id = :user_id", nativeQuery = true)
	List<TestEntity> findByUserId(Integer user_id);

}
