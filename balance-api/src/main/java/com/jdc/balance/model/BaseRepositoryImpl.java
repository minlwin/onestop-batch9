package com.jdc.balance.model;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

public class BaseRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID>{

	private EntityManager entityManager;
	
	public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}

	@Override
	public <R> Page<R> search(Function<CriteriaBuilder, CriteriaQuery<R>> queryFunc,
			Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc, int page, int size) {
		
		var count = count(countFunc);
		var cq = queryFunc.apply(entityManager.getCriteriaBuilder());
		var query = entityManager.createQuery(cq);
		query.setFirstResult(page * size);
		query.setMaxResults(size);
		
		var contents = query.getResultList();
		
		return new PageImpl<R>(contents, PageRequest.of(page, size), count);
	}

	@Override
	public <R> List<R> search(Function<CriteriaBuilder, CriteriaQuery<R>> queryFunc) {
		var cq = queryFunc.apply(entityManager.getCriteriaBuilder());
		return entityManager.createQuery(cq).getResultList();
	}

	@Override
	public <R> Optional<R> searchOne(Function<CriteriaBuilder, CriteriaQuery<R>> queryFunc) {
		var cq = queryFunc.apply(entityManager.getCriteriaBuilder());
		return Optional.ofNullable(entityManager.createQuery(cq).getSingleResult());
	}

	@Override
	public Long count(Function<CriteriaBuilder, CriteriaQuery<Long>> queryFunc) {
		var cq = queryFunc.apply(entityManager.getCriteriaBuilder());
		return entityManager.createQuery(cq).getSingleResult();
	}

}
