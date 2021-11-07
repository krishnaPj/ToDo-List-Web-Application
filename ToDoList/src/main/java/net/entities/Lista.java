package net.entities;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "list")
public class Lista {	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String content;
	
	public Lista() {}
	public Lista(int id, String content) { super(); this.id = id; this.content = content; }

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public String getContent() { return content; }
	public void setContent(String content) { this.content = content; }
	
	@Override
	public int hashCode() { return Objects.hash(content, id); }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lista other = (Lista) obj;
		return Objects.equals(content, other.content) && id == other.id;
	}

	@Override
	public String toString() { return "List [id=" + id + ", content=" + content + "]"; }	
}
