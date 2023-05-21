//package com.umer.simplefakebank.configuration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/api/users/register").permitAll()
//                .antMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").hasRole("USER")
//                .antMatchers("/h2-console/**").permitAll() // Allow access to H2 console
//                .anyRequest().authenticated()
//                .and().formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/swagger-ui.html")
//                .permitAll()
//                .and().logout()
//                .logoutUrl("/logout")
//                .permitAll();
//
//        // Enable frame options for H2 console
//        http.headers().frameOptions().sameOrigin();
//
//        // Disable X-Content-Type-Options header to prevent blocking
//        http.headers().contentTypeOptions().disable();
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("root") // Replace with your desired username
//                .password(passwordEncoder().encode("root")) // Replace with your desired password
//                .roles("USER");
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
