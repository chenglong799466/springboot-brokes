package com.example.demo.config;

import com.example.demo.filter.RequestFilter;
import com.example.demo.filter.ResponseLogFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class ApplicationConfig {

    @Bean
    public Filter requestFilter() {
        return new RequestFilter();
    }

    @Bean
    public Filter responseLogFilter(){
        return  new ResponseLogFilter();
    }


}
