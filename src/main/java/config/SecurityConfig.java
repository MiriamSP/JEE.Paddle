package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import data.entities.Role;
import business.api.Uris;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        // auth.inMemoryAuthentication().//
        // withUser("user").password("123456").roles("USER").and().//
        // withUser("manager").password("123456").roles("MANAGER").and().//
        // withUser("admin").password("123456").roles("ADMIN", "MANAGER", "USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()//
                .antMatchers(Uris.SERVLET_MAP + Uris.TOKENS + "/**").authenticated()//
                .antMatchers(Uris.SERVLET_MAP + Uris.COURTS + "/**").hasRole(Role.ADMIN.name())//
                .antMatchers(Uris.SERVLET_MAP + Uris.TRAININGS + Uris.SHOW).authenticated()//
                .antMatchers(Uris.SERVLET_MAP + Uris.TRAININGS + Uris.REGISTER).authenticated()//
                .antMatchers(Uris.SERVLET_MAP + Uris.TRAININGS + "/**").hasRole(Role.TRAINER.name())//
                .and().httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
