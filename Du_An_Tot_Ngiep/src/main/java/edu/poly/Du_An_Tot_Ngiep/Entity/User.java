package edu.poly.Du_An_Tot_Ngiep.Entity;

import java.util.Base64;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
//	private String email;
	@Column(columnDefinition = "nvarchar(50)")
	private String fullname;
	private boolean gender;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "birthday")
	private Date birthday;
	private String phone;
	private String password;
	@JsonIgnore
	@Lob
	@Column(name = "image")
	private byte[] image;
	@Column(columnDefinition = "bit default 1")
	private boolean role;
	@Column(columnDefinition = "nvarchar(150)")
	private String address;

	public User() {
		super();
	}

	public User(int userId, String fullname, boolean gender, Date birthday, String phone, String password, byte[] image,
			boolean role, String address) {
		super();
		this.userId = userId;
//		this.email = email;
		this.fullname = fullname;
		this.gender = gender;
		this.birthday = birthday;
		this.phone = phone;
		this.password = password;
		this.image = image;
		this.role = role;
		this.address = address;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte[] getImage() {
		return image;
	}

	public String getImageBase64() {
		if (this.getImage() == null) {
			return "";
		} else {
			return Base64.getEncoder().encodeToString(this.image);
		}
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public boolean isRole() {
		return role;
	}

	public void setRole(boolean role) {
		this.role = role;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
