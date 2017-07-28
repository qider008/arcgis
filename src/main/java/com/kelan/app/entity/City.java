package com.kelan.app.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sys_city")
public class City implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -945924200824168539L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 编号

    private String name; // 用户名

    public City() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
	@Override
	public String toString() {
		return "City [id=" + id + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 
}
