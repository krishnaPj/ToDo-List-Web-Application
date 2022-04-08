package net.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String surname;
	@Column(unique = true, length = 256)
	private String email;
	private String password;
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;
	private Date created;
	
	public User(String name, String surname, String email, String password, UserStatus userStatus,
			Date created) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.userStatus = userStatus;
		this.created = created;
	}
}
