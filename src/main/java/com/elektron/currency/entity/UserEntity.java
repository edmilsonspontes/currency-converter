package com.elektron.currency.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The database generated user ID")
    private Long id;
    
    @ApiModelProperty(notes = "The name of the user")
    private String name;
    
    @ApiModelProperty(notes = "The email of the user" ,name="email", required=true, value="test email")
    private String email;
    
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<TransactionEntity> transactions;
   
	public UserEntity() {
		super();
	}
    
    @Override
    public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				'}';
    }
}
