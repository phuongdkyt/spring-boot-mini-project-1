package com.example.demo.repository;

import com.example.demo.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {
	List<TaskEntity> findAllByUser_Id(Integer id);

	TaskEntity findByQuestionTestId(Integer id);
	@Query(value = "SELECT      \n" +
			"T.id,\n" +
			"T.answer,\n" +
			"Q.question,\n" +
			"T.mark\n" +
			"FROM tbl_users U \n" +
			"LEFT JOIN tbl_tasks T ON T.user_id=U.id\n" +
			"LEFT JOIN tbl_test_questions TQ ON TQ.id=T.question_test_id\n" +
			"LEFT JOIN tbl_questions Q ON Q.id=TQ.question_id\n" +
			"LEFT JOIN tbl_tests TE ON TE.id = TQ.test_id\n" +
			"WHERE U.id=:user_id AND Q.question_type='TL' and TE.id=:test_id and (T.mark is null)",nativeQuery = true)
	   List<Map<String,Object>> findAllByUserAndTest(@Param("user_id")Integer user_id, @Param("test_id") Integer test_id);

	TaskEntity findByQuestionTestIdAndUserId(Integer id1, Integer id2);

	List<TaskEntity> findAllByTest_IdAndUser_Id(Integer id1, Integer id2);

	@Query(value = "select count(*) a from testapi.tbl_tasks a, testapi.tbl_test_questions b where 1=1\n" +
			"            and a.user_id = :user_id" +
			"            and b.test_id = :test_id" +
			"            and a.question_test_id  = b.id\n" +
			"\t\t\tand a.mark is not null", nativeQuery = true)
	Long countResultMarked(@Param("user_id") Integer user_id, @Param("test_id") Integer test_id);

	@Query(value = "SELECT \n" +
			"    SUM(IF(Q.answer = T.answer, 1, 0)) mark_questions,\n" +
			"\tCOUNT(Q.question) total_questions\n" +
			"FROM tbl_users U \n" +
			"LEFT JOIN tbl_tasks T on T.user_id=U.id\n" +
			"LEFT JOIN tbl_test_questions TQ on TQ.id=T.question_test_id\n" +
			"LEFT JOIN tbl_questions Q on Q.id=TQ.question_id\n" +
			"LEFT JOIN tbl_tests TE ON TE.id = TQ.test_id\n" +
			"where U.id=:user_id and Q.question_type=:question_type and TE.id=:test_id", nativeQuery = true)
	Long getMarkOnTotalQuestion(@Param("user_id") Integer user_id, @Param("question_type") String question_type, @Param("test_id") Integer test_id);

	@Query(value =
			"SELECT \n" +
					"\t\t\tSUM(T.mark) mark_questions\n" +
					"            FROM tbl_users U \n" +
					"            LEFT JOIN tbl_tasks T on T.user_id=U.id\n" +
					"            LEFT JOIN tbl_test_questions TQ on TQ.id=T.question_test_id\n" +
					"            LEFT JOIN tbl_questions Q on Q.id=TQ.question_id\n" +
					"            LEFT JOIN tbl_tests TE ON TE.id = TQ.test_id \n" +
					"            where U.id=:user_id and Q.question_type=:question_type and TE.id=:test_id", nativeQuery = true)
	Long getEssayScoreResults(@Param("user_id") Integer user_id, @Param("question_type") String question_type, @Param("test_id") Integer test_id);

	@Query(value = "SELECT \n" +
			"\t\t\t-- SUM(T.mark) mark_questions\n" +
			"            COUNT(Q.question) total_questions\n" +
			"            FROM tbl_users U \n" +
			"            LEFT JOIN tbl_tasks T on T.user_id=U.id\n" +
			"            LEFT JOIN tbl_test_questions TQ on TQ.id=T.question_test_id\n" +
			"            LEFT JOIN tbl_questions Q on Q.id=TQ.question_id\n" +
			"            LEFT JOIN tbl_tests TE ON TE.id = TQ.test_id \n" +
			"            where U.id=:user_id and Q.question_type=:question_type and TE.id=:test_id", nativeQuery = true)
	Long getTotalQuestion(@Param("user_id") Integer user_id, @Param("question_type") String question_type, @Param("test_id") Integer test_id);

}
