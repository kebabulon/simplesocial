package io.github.kebabulon.simplesocial.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
//@Entity(name="User") создаст таблицу user, а это зарезервированное слово в PostgreSQL.
public class User
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(nullable = false, length = 50, unique = true)
	private String username;

    @NotBlank
    @Column(nullable = false)
	private String password;

    @Column(nullable = false)
	private Boolean admin = false;

    @Column(nullable = false)
	private Boolean moderator = false;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    protected User(){}
    //пустой обязательно нужен Hibernate без него не создаст объект
	
	public long getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}

    public boolean isPoweruser() {
        return admin || moderator;
    }
    public boolean isAdmin() {
        return admin;
    }
    public boolean isModerator() {
        return moderator;
    }
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    public void setModerator(boolean moderator) {
        this.moderator = moderator;
    }
}
