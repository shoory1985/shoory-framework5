package com.shoory.framework.starter.db.annotation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
	public <S extends T> S save(S entity);
}
