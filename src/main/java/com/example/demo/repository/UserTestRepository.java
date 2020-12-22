package com.example.demo.repository;

import com.example.demo.entity.UserTestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserTestRepository extends JpaRepository<UserTestEntity, Integer> {
	List<UserTestEntity> findAllByUser_Id(Integer id);

	@Query(value = "select TU.id,TU.test_id,TU.user_id,TU.status_notice,TU.status_result from tbl_test_users TU\n" +
			"INNER JOIN tbl_tests TE ON TE.id=TU.test_id\n" +
			"INNER JOIN tbl_users U ON U.id=TU.user_id\n" +
			"where TU.test_id=:test_id and TU.user_id=:user_id", nativeQuery = true)
	UserTestEntity findByTest_IdAndUser_Id(@Param("user_id") Integer user_id, @Param("test_id") Integer test_id);

	boolean existsByUserIdAndTestId(Integer user_id, Integer test_id);
}
