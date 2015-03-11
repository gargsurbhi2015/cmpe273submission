package hello;



import java.util.ArrayList;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

//import java.util.concurrent.atomic.AtomicLong;

public class Moderator {
	

	
	 @NotNull(message = "Name should not be Null \n")
	 //@NotEmpty(message = "Name cannot be empty \n")
	 String name;

	 private String created_at;
			
	private int id;
	
	@JsonIgnore
	private String [] polls;
	
	
	@NotNull(message = "Email should not be Null \n")
	@NotEmpty(message = "Email cannot be empty \n")
	String email;
	
	@NotNull(message = "password should not be Null \n")
	@NotEmpty(message = "password cannot be empty \n")
	String password;
	
	@JsonIgnore
	ArrayList<Poll>pollslist = new ArrayList<Poll>();
	public ArrayList<Poll> getPollslist(){
		return pollslist;
	}
	
	public void setPollslist(ArrayList<Poll> pollslist){
		this.pollslist= pollslist;
	}
	
	public Moderator(){
		super();
	}
	
	public Moderator(String name, String email, String password){
		super();
		this.name= name;
		this.email=email;
		this.password=password;
	}
	
public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
  
  
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getCreated_at() {
		return created_at;
	}
	
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	

	

	}

		 
 
 
		
