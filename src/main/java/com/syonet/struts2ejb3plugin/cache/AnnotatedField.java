package com.syonet.struts2ejb3plugin.cache;

import java.lang.reflect.Field;

import com.syonet.struts2ejb3plugin.EJB;

public class AnnotatedField {

	private EJB annotation;
	private Field field;
	

	public AnnotatedField( EJB a, Field f ) {
		this.annotation = a;
		this.field = f;
	}
	
	
	public EJB getAnnotation() {
		return annotation;
	}
	public void setAnnotation( EJB annotation ) {
		this.annotation = annotation;
	}
	public Field getField() {
		return field;
	}
	public void setField( Field field ) {
		this.field = field;
	}
	
}
