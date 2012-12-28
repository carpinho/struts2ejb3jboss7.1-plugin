package com.syonet.struts2ejb3plugin.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * simple & naive cache for InjectEJBInterceptor
 * 
 * @author lucas_lech
 */
public class InjectEJBCache {
	
	/**
	 * internal annotations cache
	 */
	private static Map<String, List<AnnotatedField>> cache = new ConcurrentHashMap<String, List<AnnotatedField>>();
	
	/**
	 * holds info on classes - answers the basic question - has this class InjectEJB entries inside
	 */
	private static Map<String, Boolean> info = new ConcurrentHashMap<String, Boolean>();
	

	//Cache singleton
	private static InjectEJBCache instance;
	
	static {
		instance = new InjectEJBCache();
	}
	
	public static InjectEJBCache getInstance() {
		return instance;
	}
	
	/**
	 * returned cached information for specified class
	 * @param className 
	 * @return Get the annotated fields from cache
	 */
	public List<AnnotatedField> getAnnotatedFields( final String className ) {
		
		return cache.get( className );
	}
	
	/**
	 * cache class field (ejb) annotation info
	 * @param className 
	 * @param fields 
	 */
	public void cacheAnnotatedFields( final String className, final List<AnnotatedField> fields ) {
		cache.put( className, fields );
		info.put( className, Boolean.TRUE );
	}
	
	/**
	 * returns information on class having EJBInject annotations inside
	 * @param className 
	 * @return Check whether the given class has annotations
	 */
	public Boolean hasEJBAnnotations( final String className ) {
		return info.get( className );
	}
	
	/**
	 * set this class as not EJB aware
	 * @param className 
	 */
	public void noEJBAnnotations( final String className ) {
		info.put( className, Boolean.FALSE );
	}
	
}
