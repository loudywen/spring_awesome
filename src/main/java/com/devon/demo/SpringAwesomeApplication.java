package com.devon.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SpringBootApplication
//@EnableWebMvc
public class SpringAwesomeApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
    SpringApplication.run(SpringAwesomeApplication.class, args);
  }

    // This is used to load css and js that kind of stuff
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		   registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
           registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
}
