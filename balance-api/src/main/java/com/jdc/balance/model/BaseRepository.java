package com.jdc.balance.model;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID>{

	<R> Page<R> search(Function<CriteriaBuilder, CriteriaQuery<R>> queryFunc, 
			Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc, int page, int size);

	<R> List<R> search(Function<CriteriaBuilder, CriteriaQuery<R>> queryFunc);
	
	<R> Optional<R> searchOne(Function<CriteriaBuilder, CriteriaQuery<R>> queryFunc);
	
	Long count(Function<CriteriaBuilder, CriteriaQuery<Long>> queryFunc);
}
