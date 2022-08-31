package com.shoory.framework.starter.db;

import java.util.Optional;

import javax.persistence.EntityManager;

import com.shoory.framework.starter.db.annotation.BaseRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public class BaseRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {
	public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
	}

	public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
		this(JpaEntityInformationSupport.getEntityInformation(domainClass, em), em);
	}

	@CachePut
	@Override
	public <S extends T> S save(S entity) {
		return super.save(entity);
	}
	
	@CacheEvict
	@Override
	public void delete(T entity) {
		super.delete(entity);
	}
	
	@Cacheable
	@Override
	public Optional<T> findById(ID id) {
		return super.findById(id);
	}
}