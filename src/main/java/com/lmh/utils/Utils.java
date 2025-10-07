package com.lmh.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.EntityPathBase;

@Component
public class Utils {

	/**
	 * Función auxiliar que te devuelve una contraseña nueva
	 * @param contrasenia
	 * @return
	 */
	/*public static Tuple2<String, String> generarContrasenia(String contrasenia){
        String salt = BCrypt.gensalt(12);
        String password = BCrypt.hashpw(contrasenia, salt);
        
        return new Tuple2<String, String>(password, salt);
	}*/
	
	/**
	 * Función auxiliar que te devuelve una class de una superclass
	 * @param superclass
	 * @param idArgumento
	 * @return
	 */
	public static Class<?> getClassFromSuperclass(Type superclass, Integer idArgumento){
		if(superclass instanceof ParameterizedType) {
			return (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[idArgumento];			
		}else {
			superclass = ((Class) superclass).getGenericSuperclass();
			return (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[idArgumento];
		}
	}
	
	/**
	 * Función auxiliar que te devuelve el Path de una class
	 * @param <Q>
	 * @param clazz
	 * @return
	 */
	public static <Q> Q getEntityPathBase(Class clazz) {
		Class<Q> clazzQ = null;
		
		Type superclass = clazz.getGenericSuperclass();

		if(superclass instanceof ParameterizedType) {
			clazzQ = (Class<Q>) ((ParameterizedType) superclass).getActualTypeArguments()[1];			
		}else {
			superclass = ((Class) superclass).getGenericSuperclass();
			clazzQ = (Class<Q>) ((ParameterizedType) superclass).getActualTypeArguments()[1];
		}
		
		try {
			return (Q) getEntityPath(clazzQ);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Función auxiliar que te devuelve el Path de una class
	 * @param <Q>
	 * @param clazz
	 * @return
	 */
	public static EntityPath getEntityPath(Class clazz) throws IllegalArgumentException, IllegalAccessException {
		for (Field f : clazz.getDeclaredFields()) {
			if(Modifier.isStatic(f.getModifiers())) {
				if(EntityPathBase.class.isAssignableFrom(f.getType())) {
					return (EntityPath) f.get(clazz);
				}
			}
		}
		return null;
	}
	
	/**
	 * Función auxiliar que te devuelve el Path de una class
	 * @param <Q>
	 * @param clazz
	 * @return
	 */
	public static Path getPathFromParent(Path parent, String field) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if(field.contains(".")) {
			String[] fields = field.split("[.]");
			Path path = null;
			
			for (String f : fields) {
				path = getPath(path == null ? parent : path, f);
			}
			
			return path;
		}
		
		return getPath(parent, field);
	}

	/**
	 * Función auxiliar que te devuelve el Path de una class
	 * @param <Q>
	 * @param clazz
	 * @return
	 */
	private static Path getPath(Path object, String f) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = MethodUtils.getMatchingAccessibleMethod(object.getClass(), f, new Class[] {});
		
		if(method != null) {
			return (Path) method.invoke(object, (Object[]) null);
		}
		
		return (Path) FieldUtils.readField(object, f);
	}
	
	/*public static UserLogged getUserlogged() {
		return (UserLogged) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("user");
	}*/
}
