package pt.jcbox.shoppinglist.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;

	@Override // Configura as solicitações de acesso por Http
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * http.authorizeRequests().antMatchers("/").permitAll().and().authorizeRequests
		 * ().antMatchers("/h2-console/**").permitAll(); http.csrf().disable();
		 * http.headers().frameOptions().disable();
		 * 
		 * 
		 * 
		 * http.csrf() .disable() // Desativa as configurações padrão de memória.
		 * .authorizeRequests() // Pertimi restringir acessos
		 * .antMatchers(HttpMethod.GET, "/").permitAll() // Qualquer utilizador acessa a
		 * pagina inicial .antMatchers(HttpMethod.GET, "/registo").hasAnyRole("ADMIN")
		 * .anyRequest().authenticated() .and().formLogin().permitAll() // permite
		 * qualquer utilizador .loginPage("/autenticacao")
		 * .defaultSuccessUrl("/listaCompras") .failureUrl("/login?error=true") .and()
		 * .logout().logoutSuccessUrl("/login") // Mapeia URL de Logout e invalida
		 * utilizador autenticado .logoutRequestMatcher(new
		 * AntPathRequestMatcher("/logout"));
		 */

		http.csrf()
		.disable() // Desativa as configurações padrão de memória.
		.authorizeRequests() // Pertimi restringir acessos
		.antMatchers(HttpMethod.GET, "/").permitAll() // Qualquer utilizador acessa a pagina inicial
		// .antMatchers(HttpMethod.GET, "/registo").hasAnyRole("ADMIN", "VISITA")
		.anyRequest().authenticated()
		.and().formLogin().permitAll() // permite qualquer utilizador
		.loginPage("/login")
		.defaultSuccessUrl("/listar")
		.failureUrl("/login?error=true")
		.and()
		.logout().logoutSuccessUrl("/login") // Mapeia URL de Logout e invalida utilizador autenticado
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}

	@Override // Cria autenticação do utilizador com banco de dados
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(implementacaoUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/materialize/**");
	}
}
