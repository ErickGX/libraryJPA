package com.erickgx.libraryapi.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                                .httpBasic(Customizer.withDefaults()) //autenticação Basic auth baseado em base64
                .formLogin(configurer -> {
                    configurer.loginPage("/login");
                })
                .authorizeHttpRequests(authorize -> {


                    //Use Roles para regras de URL (grossas) e Authorities para regras de metodo/domínio (finas)

                    //hasRole() para proteger seções inteiras do seu site - Bons usos
                    //hasAuthority() para proteger a execução de lógicas de negócio específicas
                    //authorize.requestMatchers(HttpMethod.POST, "/autores/**").hasRole("ADMIN");
                    //authorize.requestMatchers(HttpMethod.POST, "/autores/**").hasAuthority("CADASTRAR_AUTOR");
                    //authorize.requestMatchers(HttpMethod.PUT, "/autores/**").hasRole("ADMIN"); //Definicao de acesso a metodos especificos

                    authorize.requestMatchers("/login").permitAll(); //permite qualquer usuario acessar
                    authorize.requestMatchers(HttpMethod.POST,"/usuarios/**").permitAll(); //permite qualquer usuario acessar
                    authorize.requestMatchers("/autores/**").hasRole("ADMIN");
                    authorize.requestMatchers("/livros/**").hasAnyRole("USER","ADMIN");

                    authorize.anyRequest().authenticated(); //Qualquer coisa abaixo dessa linha é ignorada
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
                        return new BCryptPasswordEncoder(10); // '10' refers to the strength (log rounds) of the BCrypt algorithm; the salt is generated automatically
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails user1 = User.builder()
                .username("erick")
                .password(encoder.encode("12345"))
                .roles("ADMIN")
                .build();

        UserDetails user2 = User.builder()
                .username("usuario")
                .password(encoder.encode("321"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user1, user2);

    }

}
