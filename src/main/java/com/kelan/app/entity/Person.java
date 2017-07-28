package com.kelan.app.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sys_person")
public class Person implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -945924200824168539L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 编号

    private String username; // 用户名

    public Person() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


	@Override
	public String toString() {
		return "User [id=" + id + "]";
	} 
}
