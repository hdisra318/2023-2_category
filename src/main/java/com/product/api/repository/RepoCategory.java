package com.product.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.product.api.entity.Category;

/**
 * Repositorio
 * 
 * @author Israel Hernandez Dorantes - 318206604
 * @version 1.0
 *
 */
@Repository
public interface RepoCategory extends JpaRepository<Category, Integer>{

	
	/* Consultas */
	
	/**
	 * Realiza la consulta de todas las regiones que esten activas (status = 1)
	 */
	@Query(value = "SELECT * FROM category WHERE status = :status", nativeQuery = true)
	List<Category> findByStatus(@Param("status") Integer status);

	/**
	 * Realiza la consulta de una categoria por su id, si esta activa (status = 1)
	 */
	@Query(value = "SELECT * FROM category WHERE category_id = :category_id AND status = 1", nativeQuery = true)
	Category findByCategoryId(@Param("category_id") Integer category_id);
	
	/**
	 * Realiza la consulta de la categoria con el nombre de la categoria dado
	 */
	@Query(value = "SELECT * FROM category WHERE category = :category", nativeQuery = true)
	Category findByCategory(@Param("category") String category);
	
	/**
	 * Agregar una categoria
	 */
	@Modifying
	@Transactional
	@Query(value = "INSERT INTO category (category,acronym,status) VALUES(:category,:acronym,1)", nativeQuery = true)
	void createCategory(@Param("category") String category, @Param("acronym") String acronym);
	
	/**
	 * Actualiza la tabla category con el nombre de la categoria dado para las categorias que
	 * tengan el id dado
	 * 
	 * @return 0 si se logro actualizar
	 */
	@Modifying
	@Transactional
	@Query(value = "UPDATE category SET category = :category, acronym = :acronym WHERE category_id = :category_id", nativeQuery = true)
	Integer updateCategory(@Param("category_id") Integer category_id, @Param("category") String category, @Param("acronym") String acronym);
	
	/**
	 * Actualiza la tabla category para modificar el estatus de una categoria
	 */
	@Modifying
	@Transactional
	@Query(value = "UPDATE category SET status = 1 WHERE category_id = :category_id", nativeQuery = true)
	Integer activateCategory(@Param("category_id") Integer category_id);
	
	/**
	 * Desactiva una categoria
	 */
	@Modifying
	@Transactional
	@Query(value = "UPDATE category SET status = 0 WHERE category_id = :category_id", nativeQuery = true)
	void deleteById(@Param("category_id") Integer category_id);
	
	
	
	
	
	
}