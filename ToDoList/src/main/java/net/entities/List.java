package net.entities;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "list")

public class List {	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String content;
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public String getContent() { return content; }
	public void setContent(String content) { this.content = content; }
	
	

	
}
