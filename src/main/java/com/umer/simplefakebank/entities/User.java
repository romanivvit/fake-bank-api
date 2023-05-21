package com.umer.simplefakebank.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

	private static final String TABLE_PREFIX = "user_";

	@Id
	@Column(name = TABLE_PREFIX + "id")
	@GeneratedValue
	private Long id;

	@NotNull
	@NotBlank(message = "Name is mandatory")
	@Column(name = TABLE_PREFIX + "username")
	private String username;

	@NotNull
	@NotBlank(message = "Email is mandatory")
	@Column(name = TABLE_PREFIX + "email")
	private String email;

	@NotNull
	@NotBlank(message = "Password")
	@Column(name = TABLE_PREFIX + "password")
	private String password;

}
