package task7.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import task7.jwt.JwtFilter;
import task7.model.Role;
import task7.service.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final UserDetailServiceImpl userDetailServiceImpl;
    private final JwtFilter jwtFilter;
    private final String REMEBERKEY = "secretKey";

    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
             .securityMatcher("/api/**")
             .authorizeRequests()
                .requestMatchers(HttpMethod.POST, "/api/v1.0/readings/**")
                    .hasAnyAuthority(Role.ADMIN.name(), Role.METER.name())
                .requestMatchers(HttpMethod.GET, "/api/v1.0/groups")
                    .hasAnyAuthority(Role.ADMIN.name(), Role.OPERATOR.name())
                .requestMatchers("/api/v1.0/register")
                    .permitAll()
                .requestMatchers("/api/v1.0/auth")
                    .permitAll()
                .anyRequest().authenticated()
             .and()
             .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
             .csrf().disable()
             .authorizeRequests()
                .requestMatchers(HttpMethod.GET, "/report/**")
                    .hasAnyAuthority(Role.ADMIN.name(), Role.OPERATOR.name())
                .requestMatchers("/users")
                    .hasAnyAuthority(Role.ADMIN.name())
                .anyRequest().authenticated()
                .and()
             .formLogin()
                .and()
             .rememberMe().key(REMEBERKEY);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder encoder) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
             .userDetailsService(userDetailServiceImpl)
             .passwordEncoder(encoder)
             .and().build();
    }
}
