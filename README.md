struts2ejb3jboss7.1-plugin
==========================

Slight modification of [struts2ejb3-plugin-jboss](https://code.google.com/p/struts2ejb3-jboss-plugin/) integration for JBOSS 7.1.  
Using for a specific situation, patches are welcome.  
Unit tests not yet implemented.

Enables to inject EJB3 beans into actions via annotations. For example:
```java
@EJB(name="MyProject/UserSessionBean")
private UserSessionLocal userSessionLocal;
```

###Configure using interceptors
```xml
<interceptors>
   <interceptor name="ejb3" class="com.syonet.struts2ejb3plugin.EJBInterceptor"></interceptor>
                
   <interceptor-stack name="new.stack">
       <interceptor-ref name="ejb3" />
       <interceptor-ref name="defaultStack" />
   </interceptor-stack>
</interceptors>
                
<default-interceptor-ref name="new.stack" />
```
