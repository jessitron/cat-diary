package com.jessitron.catdiary.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  DataSource dataSource;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication().dataSource(dataSource)
        .usersByUsernameQuery(
            "select username, password, true from CAT_IDENTITIES " +
                "where username=?")
        .authoritiesByUsernameQuery(
            "select username, 'USER' from CAT_IDENTITIES " +
                "where username=?");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new DelegatingPasswordEncoder("bcrypt", Map.of(
        "argon", new Argon2PasswordEncoder(),
        "bcrypt", new BCryptPasswordEncoder(31),
        "noop", NoOpPasswordEncoder.getInstance()));
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    /*
     * Here's how to make h2-console work under security:
     */
    http.authorizeRequests().antMatchers("/h2-console/**").permitAll() // db access has its own login
        .and().csrf().ignoringAntMatchers("/h2-console/**") // it doesn't support CSRF
        .and().headers().frameOptions().sameOrigin() // it uses frames
//        .contentSecurityPolicy("script-src 'self'; " +
//            "style-src 'self'; " +
//            "report-uri http://namethiscat.localhost:3000/csp-report; ")
    ;

    http.authorizeRequests()
        .antMatchers("/", "/css/*.css", "/img/*.jpg", "/favicon.ico").permitAll() // home page
        .antMatchers("/cat/new", "/cat/welcome").permitAll() // registration page
        .anyRequest().authenticated()
        .and()
        .formLogin().loginPage("/login").defaultSuccessUrl("/entries").permitAll()
        .and()
        .logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll()
        .and()
        .httpBasic();

  }
}