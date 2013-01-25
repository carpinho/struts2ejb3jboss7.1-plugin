package com.syonet.struts2ejb3plugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FieldsManager {
	
	/**
	 * Get all declared fields from the given class
	 * including all of its subclasses.
	 */
	public static Field[] getAllDeclaredFields( Class<?> clazz ) {
		List<Field> fields = new ArrayList<Field>();
		fields.addAll( Arrays.asList( clazz.getDeclaredFields() ) );
		if ( clazz.getSuperclass() != null ) {
			fields.addAll( Arrays.asList( getAllDeclaredFields( clazz.getSuperclass() ) ) );
		}
		return fields.toArray( new Field[] {} );
	}
}
