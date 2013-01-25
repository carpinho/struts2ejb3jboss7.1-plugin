package com.syonet.struts2ejb3plugin.cache;

import java.lang.reflect.Field;

import com.syonet.struts2ejb3plugin.annotations.EJB;

public class AnnotatedField {

	private EJB annotation;
	private Field field;
	

	public AnnotatedField( final EJB a, final Field f ) {
		this.annotation = a;
		this.field = f;
	}
	
	
	public EJB getAnnotation() {
		return annotation;
	}
	public void setAnnotation( final EJB annotation ) {
		this.annotation = annotation;
	}
	public Field getField() {
		return field;
	}
	public void setField( final Field field ) {
		this.field = field;
	}
	
}
