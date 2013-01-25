package com.syonet.struts2ejb3plugin;

import java.lang.reflect.Field;

import javax.naming.InitialContext;

import com.syonet.struts2ejb3plugin.annotations.EJB;
import com.syonet.struts2ejb3plugin.cache.AnnotatedField;

public class EJBInjector {
	
	/**
	 * Inject the EJB into the action via JNDI
	 * 
	 * @param action The action reference
	 * @param aField The annotated field with the injection information
	 * @throws Exception 
	 */
	public void inject( final Object action, final AnnotatedField aField ) throws Exception {
		
		EJB annotation = aField.getAnnotation();
		Field field = aField.getField();
		
		//Determine service name
		StringBuilder serviceName = new StringBuilder( "java:app/" );
		serviceName.append( annotation.name() );
		serviceName.append( "!" );
		serviceName.append( field.getType().getName() );
		
		//Try to access the service from the JNDI
		Object service = null;
		try {
			InitialContext ic = new InitialContext();
			service = ic.lookup( serviceName.toString() );
		} finally {
			if ( service != null ) {
				boolean wasAccessible = field.isAccessible();
				field.setAccessible( true );
				field.set( action, service );
				field.setAccessible( wasAccessible );
			}
		}
	}
	
}
