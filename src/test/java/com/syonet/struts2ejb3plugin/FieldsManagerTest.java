package com.syonet.struts2ejb3plugin;

import java.lang.reflect.Field;

import org.junit.Assert;

import org.junit.Test;

public class FieldsManagerTest {
	
	@SuppressWarnings( "unused" )
	private class TestSuperClass {
		private String superField1;
		public String superField2;
		protected String superField3;
	}
	
	@SuppressWarnings( "unused" )
	private class TestClass extends TestSuperClass {
		
		private String field1;
		public String field2;
		protected String field3;
		
	}
	
	@Test
	public void testGetFields() {
		Field[] fields = FieldsManager.getAllDeclaredFields( TestClass.class );
		
		Assert.assertEquals( fields.length, 8 );
		Assert.assertEquals( fields[ 0 ].getName(), "field1" );
		Assert.assertEquals( fields[ 1 ].getName(), "field2" );
		Assert.assertEquals( fields[ 2 ].getName(), "field3" );
		Assert.assertEquals( fields[ 4 ].getName(), "superField1" );
		Assert.assertEquals( fields[ 5 ].getName(), "superField2" );
		Assert.assertEquals( fields[ 6 ].getName(), "superField3" );
	}
	
}