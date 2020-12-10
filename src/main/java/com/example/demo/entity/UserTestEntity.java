package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tbl_test_user")
public class UserTestEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   public Integer id;

   @JsonIgnore
   @ManyToOne
   @JoinColumn(name = "user_id")
   private  UserEntity user;

   @JsonIgnore
   @ManyToOne
   @JoinColumn(name = "test_entity")
   private TestEntity test;

}
