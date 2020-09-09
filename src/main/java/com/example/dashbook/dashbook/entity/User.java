package com.example.dashbook.dashbook.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="user")
@Data
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int id;
    private  String userName;
    private  String email;
}