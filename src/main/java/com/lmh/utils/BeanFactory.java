package com.lmh.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanFactory implements ApplicationContextAware{

	private static ApplicationContext applicationContext;
	
	/**
	 * Función que te devuelve un bean existente en el contexto.
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(Class<T> clazz) {
		try {
			return applicationContext.getBean(clazz);
		}catch(NoSuchBeanDefinitionException e) {
			return null;
		}
	}

	@Autowired
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
