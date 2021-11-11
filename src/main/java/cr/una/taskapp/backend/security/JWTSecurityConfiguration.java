package cr.una.taskapp.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

/**
 * Configuration with JWT Token Access
 *
 * @author Maikol Guzman <mike@guzmanalan.com>
 * @create 9/12/21
 */
@Configuration
@EnableWebSecurity
public class JWTSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${url.login}")
    String URL_LOGIN;

    @Value ("${url.unsecure}")
    String URL_UNSECURE;

    @Value ("${url.user.signup}")
    String URL_SIGNUP;

    public static final String CSRF_COOKIE = "CSRF-TOKEN";
    public static final String CSRF_HEADER = "X-CSRF-TOKEN";

    static final long TOKEN_LIFETIME = 604_800_000;
    static final String TOKEN_PREFIX = "Bearer ";

    static final String TOKEN_SECRET = Base64.getEncoder().encodeToString("UNA-CostaRica".getBytes());

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private UserDetailsService userDetailsService;

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandlerImpl();
    }

    @Resource
    @Bean
    public PasswordEncoder passwordEncoder() throws NoSuchAlgorithmException {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.applyPermitDefaultValues();
        config.addExposedHeader(HttpHeaders.AUTHORIZATION);
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable();
        //TODO: OPTIONAL - the storage of that token on the client side add this [defending against CSRF attacks].
        //.addFilterBefore(statelessCsrfFilter, CsrfFilter.class);
        http.cors();
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

        http
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), URL_LOGIN))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/".concat(URL_UNSECURE).concat("/**")).permitAll()
                .antMatchers(HttpMethod.POST, URL_SIGNUP).permitAll()
                .antMatchers("/**").authenticated();
    }
}
