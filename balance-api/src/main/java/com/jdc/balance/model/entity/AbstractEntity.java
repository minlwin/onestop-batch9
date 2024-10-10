package com.jdc.balance.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class AbstractEntity {

	private LocalDateTime createdAt;

	private String createdBy;

	private LocalDateTime updatedAt;

	private String updatedBy;

}