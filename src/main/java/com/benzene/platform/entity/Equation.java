package com.benzene.platform.entity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.benzene.util.entity.AbstractEntity;

@Entity
@Table(name = "Equation")
@Component
@Scope("prototype")
public class Equation extends AbstractEntity {

	@Lob
	private String text;
	
	public Equation() {
		super();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Equation [text=").append(text).append(", toString()=").append(super.toString()).append("]");
		return builder.toString();
	}
}