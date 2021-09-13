# JWT with Spring Security
eLearning: https://bit.ly/layer-service-jwt

## Overview

[GitHub - una-eif509-desarrolloweb-master/taskapp-layer-service-restapi-errorhandler-jwt: A basic example of a application service layer with the security Spring Boot](https://github.com/una-eif509-desarrolloweb-master/taskapp-layer-service-restapi-errorhandler-jwt.git)

Authentication and authorization on RESTful APIs written with Spring Boot.

> JSON Web Token (JWT) is a compact, URL-safe means of representing claims to be transferred between two parties. The claims in a JWT are encoded as a JSON object that is used as the payload of a JSON Web Signature (JWS) structure or as the plaintext of a JSON Web Encryption (JWE) structure, enabling the claims to be digitally signed or integrity protected with a Message Authentication Code (MAC) and/or encrypted.

For any application with restricted access, we must validate the requests without processing the client's login credential in every request. To solve this problem, we have the JWT, which authenticates the users without cookies or login info.

## Description

JWT composed by:

header.payload.signature

### Header

The header contains all pertinent info to understand the token.

### Payload

The payload contains the claims. In short, the claims are the user’s data (or any entity) plus additional important information (not mandatory) that adds functionality to the token.

We can find three types of claims: registered, public, private.

- ***Registered claims\*** are used to provide additional information about the token, such as the time it was created or when the token expires. These claims are not mandatory.
- ***Public claims\*** are defined by those who use JWT. One should be careful about which names they use since they can cause a collision.
- ***Private claims\*** can be used to store information without using *registered claims* or *public claims*. Keep in mind that they are susceptible to collision.

### Signature

The signature is composed of the encoded header, encoded payload, a secret and the coding algorithm (also present in the header). And all that is signed.

This is a rough look at the composition of a JWT. For more information visit this [link](https://jwt.io/) to find an example of a JWT and its parts, and in case you want to analyze it in more detail check out this [link](http://self-issued.info/docs/draft-jones-json-web-token-01.html#anchor3).

## Securing RESTful APIs with JWTs

- *JSON Web Tokens*, commonly known as JWTs, are tokens that are used to authenticate users on applications.
- It enables backends to accept requests simply by validating the contents of these JWTS. That is, applications that use JWTS no longer have to hold cookies or other session data about their users. This characteristic facilitates scalability while keeping applications secure.
- During the authentication process, when a user successfully logs in using their credentials, a JSON Web Token is returned and must be saved locally (typically in local storage).
- Whenever the user wants to access a protected route or resource (an endpoint), the user agent must send the JWT, usually in the `Authorization` header using the *[Bearer schema](http://self-issued.info/docs/draft-ietf-oauth-v2-bearer.html)*, along with the request.
- When a backend server receives a request with a JWT, the first thing to do is to validate the token. This consists of a series of steps, and if any of these fails then, the request must be rejected. The following list shows the validation steps needed:
  - Check that the JWT is well-formed
  - Check the signature
  - Validate the standard claims
  - Check the Client permissions (scopes)

## Resources

[Securing applications with JWT Spring Boot](https://medium.com/wolox/securing-applications-with-jwt-spring-boot-da24d3d98f83)

[Spring Boot Security and JWT - DZone Security](https://dzone.com/articles/spring-boot-security-json-web-tokenjwt-hello-world)

[Securing a Spring Boot API with JWTs](https://curity.io/resources/learn/spring-boot-api/)