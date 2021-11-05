package it.mattiaciraldo.spring.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import it.mattiaciraldo.spring.security.StringAttributeConverter;

@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	private String username;
	private String nome;
	private String cognome;
	@Convert(converter = StringAttributeConverter.class)
	private String email;
	private String password;
	private Boolean active = true;
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<Role>();
	

	public User() {}
	
	public User(Long id, String username, @Email String email, String password, String nome, String cognome) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
    }
	
	public User(String username, String nome, String email, String password, Boolean active) {
		this.username = username;
		this.nome = nome;
		this.email = email;
		this.password = password;
		this.active = active;
		}
	
	public User(String username, String nome, String email, String password, Boolean active, Set<Role> roles) {
		this.username = username;
		this.nome = nome;
		this.email = email;
		this.password = password;
		this.active = active;
		this.roles = roles;
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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
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
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", usurname=" + username + ", nome=" + nome + ", email=" + email + ", password="
				+ password + ", active=" + active + ", roles=" + roles.toString() + "]";
	}
}
