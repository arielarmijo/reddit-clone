package tk.monkeycode.redditclone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/api/auth/**").permitAll()
			.antMatchers("/h2-console/**").permitAll()
			.anyRequest().authenticated();
		http.headers().frameOptions().disable(); // Acceso a ./h2-console
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
