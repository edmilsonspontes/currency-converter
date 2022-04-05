package com.elektron.currency.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The database generated converter ID")
    private Long id;
    
    @ApiModelProperty(notes = "The user of the converter", name="user", required=true, value="test user")
    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;
    
    @ApiModelProperty(notes = "The source currency of the transaction")
    private String sourceCurrency;
    
    @ApiModelProperty(notes = "The source value of the transaction")
    private Double sourceValue;
    
    @ApiModelProperty(notes = "The destination currency of the transaction")
    private String destinationCurrency;
    
    @ApiModelProperty(notes = "The destination value of the transaction")
    private Double destinationValue;

    @ApiModelProperty(notes = "The conversion rate")
    private Double conversionRate;
    
    @ApiModelProperty(notes = "The date/time UTC the converter", name="dateTimeConverter", required=true, value="test dateTimeConverter")
    private Date dateTimeConverter;

	public TransactionEntity() {
		super();
	}
    
    
}
