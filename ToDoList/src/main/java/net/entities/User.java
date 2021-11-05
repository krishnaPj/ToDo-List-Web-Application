package net.entities;

import javax.persistence.*;
import java.util.*;

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
	private UserStatus userStatus;
	private Date created;

	public User() {}	

	public User(String name, String surname, String email, String password, UserStatus userStatus, Date created) {
		super();
		this.name = name; this.surname = surname;
		this.email = email; this.password = password;
		this.userStatus = userStatus; this.created = created;
	}

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getSurname() { return surname; }
	public void setSurname(String surname) { this.surname = surname; }
	
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	
	public UserStatus getUserStatus() { return userStatus; }
	public void setUserStatus(UserStatus userStatus) { this.userStatus = userStatus; }

	public Date getCreated() { return created; }
	public void setCreated(Date created) { this.created = created; }

	@Override
	public int hashCode() { return Objects.hash(id); }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id="); builder.append(id);
		builder.append(", name="); builder.append(name);
		builder.append(", surname="); builder.append(surname);
		builder.append(", email="); builder.append(email);
		builder.append(", password=XXXXXX"); 
		builder.append(", userStatus="); builder.append(userStatus);
		builder.append(", created="); builder.append(created);
		builder.append("]");
		return builder.toString();
	}
}
