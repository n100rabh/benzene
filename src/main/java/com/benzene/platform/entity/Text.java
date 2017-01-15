package com.benzene.platform.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.benzene.util.entity.AbstractEntity;

@Entity
@Table(name = "Text")
@Component
@Scope("prototype")
public class Text extends AbstractEntity {

	private String text;
	
	public Text() {
		super();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}