package com.syonet.struts2ejb3plugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.InitialContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.syonet.struts2ejb3plugin.cache.AnnotatedField;
import com.syonet.struts2ejb3plugin.cache.InjectEJBCache;

/**
 * Looks for any fields annotated with the EJB annotation and injects the
 * given EJB into that field.
 */
public class EJBInterceptor extends AbstractInterceptor implements Interceptor {
	
	private static final long serialVersionUID = 1L;
	
	// cache of annotated fields
	private InjectEJBCache cache;
	
	public EJBInterceptor() {
		super();
		this.cache = InjectEJBCache.getInstance();
	}
	
	public String intercept( ActionInvocation actionInvocation ) throws Exception {
		
		Object action = actionInvocation.getAction();
		String actionClassName = action.getClass().getName();
		
		Boolean hasEJBAnnotations = this.cache.hasEJBAnnotations( actionClassName );
		if ( Boolean.TRUE.equals( hasEJBAnnotations ) ) {
			//Hit the cache
			List<AnnotatedField> aFields = this.cache.getAnnotatedFields( action.getClass().getName() );
			for ( Iterator<AnnotatedField> it = aFields.iterator(); it.hasNext(); ) {
				this.injectEJB( action, it.next() );
			}
		} else if ( hasEJBAnnotations == null ) {
			
			//Unknown yet
			List<AnnotatedField> annotatedFields = new ArrayList<AnnotatedField>();
			
			for ( Field field : action.getClass().getDeclaredFields() ) {
				
				if ( field.isAnnotationPresent( EJB.class ) ) {
					
					//Found EJBInject annotations
					AnnotatedField aField = new AnnotatedField( (EJB) field.getAnnotation( EJB.class ), field );
					this.injectEJB( action, aField );
					annotatedFields.add( aField );
				}
			}		
			
			//Cache data
			if ( annotatedFields.size() == 0 ) {
				cache.noEJBAnnotations( actionClassName );
			} else {
				cache.cacheAnnotatedFields( actionClassName, annotatedFields );
			}
		}
		
		return actionInvocation.invoke();
	}
	
	
	private void injectEJB( Object action, AnnotatedField aField ) throws Exception {
		
		EJB annotation = aField.getAnnotation();
		Field field = aField.getField();
		
		//Determine service name
		StringBuilder serviceName = new StringBuilder( "java:app/" );
		serviceName.append( annotation.name() );
		serviceName.append("!");
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
