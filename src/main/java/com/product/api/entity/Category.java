package com.product.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Clase Category
 * 
 * @author Israel Hernandez Dorantes - 318206604
 * @version 1.0
 */
@Entity
@Table(name = "category")
public class Category {

    /* Atributos */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
    private Integer category_id;
    
	@NotNull
	@Column(name = "category")
    private String category;
    
	@NotNull
	@Column(name = "acronym")
    private String acronym;
    
	@Column(name = "status")
	@Min(value = 0, message="status must be 0 or 1")
	@Max(value = 1, message="status must be 0 or 1")
	@JsonIgnore
    private Integer status;


    /**
     * Constructor de Category
     * @param category_id id de la categoria
     * @param category categoria
     * @param acronym acronimo de la categoria
     * @param status estatus de la categoria
     */
    public Category(Integer category_id, String category, String acronym, Integer status){
        this.category_id = category_id;
        this.category = category;
        this.acronym = acronym;
        this.status = status;
    }
    
    
    /**
     * Constructor vacio de Category
     */
    public Category() {
    	
    }
    
    
    /**
     * Regresa el category_id
     * @return el category_id
     */
    public Integer getCategory_id() {
    	return category_id;
    }
    
    
    /**
     * Regresa la categoria
     * @return la categoria
     */
    public String getCategory() {
    	return category;
    }
    
    /**
     * Regresa el acronimo de la categoria
     * @return el acronimo de la categoria
     */
    public String getAcronym() {
    	return acronym;
    }
    
    
    /**
     * Regresa el estatus de la categoria
     * @return el estatus de la categoria
     */
    public Integer getStatus() {
    	return status;
    }
    
    
    /** 
     * Modifica el category_id
     * @param id el nuevo category_id
     */
    public void setCategory_id(Integer id) {
    	category_id = id;
    }
     
    
    /** 
     * Modifica la catagoria
     * @param category la nueva categoria
     */
    public void setCategory(String category) {
    	this.category = category;
    }
    
    
    /** 
     * Modifica el acronimo
     * @param acro el nuevo acronimo
     */
    public void setAcronym(String acro) {
    	acronym = acro;
    }
    
    
    /** 
     * Modifica el estatus
     * @param status el nuevo estatus
     */
    public void setStatus(Integer status) {
    	this.status = status;
    }


	@Override
	public String toString() {
		return "Category [category_id=" + category_id + ", category=" + category + ", acronym=" + acronym + ", status="
				+ status + "]";
	}
    
    
    

}