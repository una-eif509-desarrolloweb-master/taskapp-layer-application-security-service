package cr.una.taskapp.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.una.taskapp.backend.dto.UserLoginInput;
import cr.una.taskapp.backend.dto.UserResult;
import cr.una.taskapp.backend.mapper.UserMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static cr.una.taskapp.backend.common.Constants.APPLICATION_JSON;
import static cr.una.taskapp.backend.common.Constants.UTF_8;
import static cr.una.taskapp.backend.security.JWTSecurityConfiguration.*;

/**
 * Handle the authentication of the User and assign an JWT token.
 *
 * @author Maikol Guzman <mike@guzmanalan.com>
 * @create 8/5/21
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    JWTAuthenticationFilter(AuthenticationManager authenticationManager, String URL_LOGIN) {
        setFilterProcessesUrl(URL_LOGIN);
        this.authenticationManager = authenticationManager;
        setAuthenticationManager(authenticationManager);
    }

    /**
     * Method to authenticate using JSON format
     * @param req
     * @param res
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {

        logger.info("Attempting REST API authentication in JWTAuthenticationFilter ");

        if (!req.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + req.getMethod());
        }

        try {
            UserLoginInput userLoginInput = new ObjectMapper()
                    .readValue(req.getInputStream(), UserLoginInput.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginInput.getUsername(),
                            userLoginInput.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) throws IOException, ServletException {

        ObjectMapper objectMapper = new ObjectMapper();

        String token = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(((User) authentication.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_LIFETIME))
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
                .compact();

        response.addHeader(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX + token);
        PrintWriter out = response.getWriter();
        response.setContentType(APPLICATION_JSON);
        response.setCharacterEncoding(UTF_8);
        out.print(objectMapper.writeValueAsString(authentication.getPrincipal()));
        out.flush();
    }
}
