package com.revature.organization.config;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import com.revature.organization.dao.UserRepository;
import com.revature.organization.filter.JwtRequestFilter;
import com.revature.organization.model.User;
import com.revature.organization.service.JwtUtil;
import com.revature.organization.service.MyUserDetailsService;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	public JwtRequestFilter jwtRequestFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(myUserDetailsService);
	
		
	}
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
        .antMatchers("/**").permitAll()  
		.anyRequest().authenticated()
		.and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS); 
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
	}
	@Bean
	public PasswordEncoder getpasswordEncoder() {
		return  NoOpPasswordEncoder.getInstance();
	}
	
	//StrictHTTPFirewall. It only allows some HTTP verbs per default   specified to block HTTP Verb tampering and XST attacks.
	@Bean
	public StrictHttpFirewall httpFirewall() {
	    StrictHttpFirewall firewall = new StrictHttpFirewall();
	    firewall.setAllowedHttpMethods(Arrays.asList("GET", "POST","PUT","DELETE","OPTIONS"));
	    return firewall;
	}
	
	@Bean
	@Primary
	public MyUserDetailsService MyUserDetailsService() {
		return new MyUserDetailsService();
		
	}
	
	@Bean
	public JwtRequestFilter JwtRequestFilter() {
		return new JwtRequestFilter();
	}
	
	@Bean
	public UserRepository userrepository() {
		return new UserRepository() {
			
			@Override
			public <S extends User> Optional<S> findOne(Example<S> example) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <S extends User> Page<S> findAll(Example<S> example, Pageable pageable) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <S extends User> boolean exists(Example<S> example) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public <S extends User> long count(Example<S> example) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public <S extends User> S save(S entity) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Optional<User> findById(Integer id) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean existsById(Integer id) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void deleteById(Integer id) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void deleteAll(Iterable<? extends User> entities) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void deleteAll() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void delete(User entity) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public long count() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Page<User> findAll(Pageable pageable) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <S extends User> S saveAndFlush(S entity) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <S extends User> List<S> saveAll(Iterable<S> entities) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public User getOne(Integer id) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void flush() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public List<User> findAllById(Iterable<Integer> ids) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <S extends User> List<S> findAll(Example<S> example, Sort sort) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public <S extends User> List<S> findAll(Example<S> example) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<User> findAll(Sort sort) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<User> findAll() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void deleteInBatch(Iterable<User> entities) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void deleteAllInBatch() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Optional<User> findByUserName(String userName) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
	
	@Bean
	public JwtUtil jwtutils() {
		return new  JwtUtil();
	}
	
}


