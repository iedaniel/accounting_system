package com.system.accounting.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.*;
import java.util.Arrays;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_id_seq")
    @SequenceGenerator(name = "employee_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private EmployeeType role;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Transient
    public String getFio() {
        return Strings.join(Arrays.asList(lastName, firstName, middleName), ' ');
    }
}
