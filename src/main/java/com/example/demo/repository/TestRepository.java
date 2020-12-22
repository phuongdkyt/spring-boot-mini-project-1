package com.example.demo.repository;

import com.example.demo.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
	List<TestEntity> findByUserId(@Param("user_id") Integer user_id);

	@Query(value = "SELECT      \n" +
			"TE.id,\n" +
			"TE.test_name,\n" +
			"TE.test_date_begin,\n" +
			"TE.test_date_finish,\n" +
			"TE.test_time\n" +
			"FROM tbl_users U \n" +
			"LEFT JOIN tbl_tasks T ON T.user_id=U.id\n" +
			"LEFT JOIN tbl_test_questions TQ ON TQ.id=T.question_test_id\n" +
			"LEFT JOIN tbl_questions Q ON Q.id=TQ.question_id\n" +
			"LEFT JOIN tbl_tests TE ON TE.id = TQ.test_id\n" +
			"WHERE U.id=3 AND Q.question_type='TN'\n" +
			"GROUP BY TE.id",nativeQuery = true)
	List<TestEntity> findAllTestAlready(@Param("user_id") Integer user_id);

}
