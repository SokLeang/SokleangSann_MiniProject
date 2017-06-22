package com.hrd.configuration;

import java.util.Locale;

import org.springframework.boot.autoconfigure.web.WebMvcProperties.LocaleResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@EnableWebMvc
public class MainConfiguration extends WebMvcConfigurerAdapter {

	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// Front End
				registry.addViewController("/about").setViewName("/about");
				registry.addViewController("/contact").setViewName("/contact");
				registry.addViewController("/home").setViewName("/home");
				registry.addViewController("/").setViewName("/home");
		
		registry.addViewController("/admin/").setViewName("/admin/dashboard");
		registry.addViewController("/admin/dashboard").setViewName("/admin/dashboard");
		registry.addViewController("/admin/user").setViewName("/admin/user");
		registry.addViewController("/admin/user-cu").setViewName("/admin/user-cu");
		registry.addViewController("/admin/role-list").setViewName("/admin/role-list");
		registry.addViewController("/admin/role-cu").setViewName("/admin/role-cu");
	}
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/*
		 * Static Resources store in the project
		 */
		registry.addResourceHandler("/resources/**")
					.addResourceLocations("classpath:/static/");
		/*
		 * Static Resources store outside the project
		 */
		registry.addResourceHandler("/files/**")
					.addResourceLocations("file:/opt/FILES_MANAGEMENT/images/");
	}

	/*
	 * Internalization i18n
	 */
	
	/*
	 * In order for our application to be able to determine which locale is
	 * currently being used, we need to add a LocaleResolver bean:
	 */

	
	@Bean
	public org.springframework.web.servlet.LocaleResolver localeResolver() {
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setDefaultLocale(new Locale("kh"));
		resolver.setCookieName("Spring_Locale");
		resolver.setCookieMaxAge(4800);
		return resolver;
	}

	/*
	 * Next, we need to add an interceptor bean that will switch to a new locale
	 * based on the value of the lang parameter appended to a request:
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	/*
	 * In order to take effect, this bean needs to be added to the application’s
	 * interceptor registry.
	 *
	 * To achieve this, our @Configuration class has to extend the
	 * WebMvcConfigurerAdapter class and override the addInterceptors() method:
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	/*
	 * Defining the Message Sources
	 */
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:/i18n/messages/message");
		messageSource.setCacheSeconds(0);
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
}
