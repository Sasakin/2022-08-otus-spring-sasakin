package ru.otus.spring.book.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.spring.book.domain.Role;
import ru.otus.spring.book.domain.User;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure( WebSecurity web ) {
        web.ignoring()
                .antMatchers( "/" );
    }

    @Override
    public void configure( HttpSecurity http ) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/index", "/login").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers( "/public/" ).anonymous()
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasAuthority(Role.ADMIN.getAuthority())
                .and()
                .authorizeRequests()
                .antMatchers("/list/**", "/book/**", "/comment/**", "/api/**").hasAuthority(Role.USER.getAuthority())
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .anonymous()
                .principal(new User((long) -1, "anonymous", "", Role.ANONYMOUS))
                .and()
                .logout()
                .permitAll()
                .and();
                //.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

   /* @Autowired
    public void configure( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }*/
}
