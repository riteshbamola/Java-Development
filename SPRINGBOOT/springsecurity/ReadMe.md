# Spring Security JWT — Complete Flow Guide

## Table of Contents
1. [Project Overview](#project-overview)
2. [Architecture](#architecture)
3. [Login Flow](#login-flow)
4. [JWT Request Flow (After Login)](#jwt-request-flow-after-login)
5. [Key Components](#key-components)
6. [Security Context](#security-context)
7. [Common Questions](#common-questions)

---

## Project Overview

This project uses **Spring Security + JWT (JSON Web Token)** for stateless authentication.

- On **login** → credentials are verified → a JWT token is returned
- On **every subsequent request** → JWT token is validated → user is authenticated without hitting the DB for password check

---

## Architecture

```
HTTP Request
      │
      ▼
┌─────────────────────────────────────────────────┐
│                 Filter Chain                    │
│                                                 │
│  JWTFilter → UsernamePasswordAuthFilter → ...   │
└─────────────────────────────────────────────────┘
      │
      ▼
┌─────────────────────────────────────────────────┐
│              SecurityFilterChain                │
│                                                 │
│  /register, /login  →  permitAll()              │
│  any other route    →  authenticated()          │
└─────────────────────────────────────────────────┘
      │
      ▼
┌─────────────────────────────────────────────────┐
│              Your Controller                    │
└─────────────────────────────────────────────────┘
```

---

## Login Flow

### Step-by-step: `POST /login`

```
1. User sends POST /login  { username: "ayush", password: "1234" }
         │
         ▼
2. UserController.verify(user) is called

3. authManager.authenticate(
       new UsernamePasswordAuthenticationToken(username, password)
                                              ─────────────────────
                                              2-arg constructor
                                              authenticated = false
                                              just a "claim" so far
   )
         │
         ▼
4. AuthenticationManager (ProviderManager internally)
   loops through providers →  finds DaoAuthenticationProvider
         │
         ▼
5. DaoAuthenticationProvider calls:
   userDetailsService.loadUserByUsername("ayush")
         │
         ▼
6. RiteshUserDetailsService hits the DB
   fetches User entity
   wraps it → return new UserPrincipal(user)
         │
         ▼
7. DaoAuthenticationProvider now has:
   ┌──────────────────────────────────────────┐
   │  UserPrincipal  (from DB)                │
   │  raw password   (from login request)     │
   └──────────────────────────────────────────┘
         │
         ▼
8. BCryptPasswordEncoder.matches(rawPassword, hashedPassword)
         │
         ├── NO MATCH  → throws BadCredentialsException → 401
         │
         └── MATCH ↓
         ▼
9. Account status checks:
   isEnabled()               → false = account disabled
   isAccountNonLocked()      → false = account locked
   isAccountNonExpired()     → false = account expired
   isCredentialsNonExpired() → false = password expired

         │  all pass ↓
         ▼
10. Returns authenticated UsernamePasswordAuthenticationToken
    (3-arg constructor, authenticated = true)
         │
         ▼
11. Back in verify():
    if (authentication.isAuthenticated()) {
        return jwtService.generateToken(user);
    }
         │
         ▼
12. JWT generated:
    Jwts.builder()
        .subject("ayush")
        .issuedAt(now)
        .expiration(now + 10hrs)
        .signWith(secretKey)
        → "eyJhbGciOiJIUzI1NiJ9...."
         │
         ▼
13. Token returned to client
    Client stores it and sends it in every future request as:
    Authorization: Bearer eyJhbGciOiJIUzI1NiJ9....
```

---

## JWT Request Flow (After Login)

### Step-by-step: Any protected request

```
1. Client sends request with header:
   Authorization: Bearer eyJhbGciOiJIUzI1NiJ9....
         │
         ▼
2. JWTFilter.doFilterInternal() runs

3. String authHeader = request.getHeader("Authorization");
   token    = authHeader.substring(7);        // strip "Bearer "
   username = jwtService.extractUsername(token);
         │
         ▼
4. extractUsername(token)
       └── extractClaim(token, Claims::getSubject)
               └── extractAllClaims(token)
                       └── Jwts.parser()
                               .verifyWith(getKey())      // crypto verification
                               .parseSignedClaims(token)  // throws if tampered
                               .getPayload()
                               → returns Claims object
         │
         ▼
5. if (username != null
        && SecurityContextHolder.getContext().getAuthentication() == null)
   │
   ├── username null?        → skip, unauthenticated request
   └── already auth?         → skip, avoid duplicate auth
         │
         ▼
6. Load fresh user from DB:
   UserDetails userDetails = userDetailsService.loadUserByUsername(username);
   → returns UserPrincipal wrapping User entity
   (Why hit DB? To get fresh roles, check if account is still active)
         │
         ▼
7. jwtService.validateToken(token, userDetails)
       │
       ├── username in token == username from DB?
       └── token not expired?
         │
         ├── invalid → skip setAuthentication, request stays unauthenticated
         │
         └── valid ↓
         ▼
8. Create authenticated token (3-arg = authenticated: true):
   new UsernamePasswordAuthenticationToken(
       userDetails,                  // who they are
       null,                         // credentials cleared
       userDetails.getAuthorities()  // roles ["USER"]
   )
         │
         ▼
9. authtoken.setDetails(
       new WebAuthenticationDetailsSource().buildDetails(request)
   );
   → attaches IP address, session ID to the token
         │
         ▼
10. SecurityContextHolder.getContext().setAuthentication(authtoken);
    → Spring Security now knows who this request belongs to
         │
         ▼
11. filterChain.doFilter(request, response);
    → pass to next filter
         │
         ▼
12. Next filters see SecurityContext already has Authentication
    → NO password check again
    → AuthorizationFilter checks roles against the route
         │
         ├── has required role? → request reaches Controller
         └── missing role?      → 403 Forbidden
```

---

## Key Components

### `AppSecurityConfig`
Configures the overall security rules.

```java
http
    .csrf(customizer -> customizer.disable())         // disable CSRF for stateless JWT
    .authorizeHttpRequests(request -> request
        .requestMatchers("/register", "/login")
        .permitAll()
        .anyRequest().authenticated())
    .sessionManagement(session ->
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // no server sessions
    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
```

### `JWTFilter`
Runs on every request. Reads JWT from header, validates it, and sets authentication in `SecurityContext`.

### `JWTService`
Handles all JWT operations:

| Method | What it does |
|---|---|
| `generateToken(user)` | Creates a signed JWT with username + expiry |
| `extractUsername(token)` | Pulls username from token payload |
| `validateToken(token, userDetails)` | Checks username match + expiry |
| `extractAllClaims(token)` | Cryptographically verifies + decodes token |
| `isTokenExpired(token)` | Checks expiration date inside token |

### `UserPrincipal`
A **wrapper** that bridges your `User` entity and Spring Security's `UserDetails` interface.

```
User (DB entity)  →  UserPrincipal (bridge)  →  Spring Security
```

| Method | Purpose |
|---|---|
| `getUsername()` | Identity, used for token matching |
| `getPassword()` | Used during login for BCrypt verification |
| `getAuthorities()` | Roles like "USER", "ADMIN" |
| `isEnabled()` | Is account active? |
| `isAccountNonLocked()` | Is account locked? |
| `isCredentialsNonExpired()` | Has password expired? |

### `RiteshUserDetailsService`
Fetches user from DB and wraps in `UserPrincipal`.

```java
public UserDetails loadUserByUsername(String username) {
    User user = userRepository.findByUsername(username);
    return new UserPrincipal(user);  // wraps DB entity
}
```

### `DaoAuthenticationProvider`
Spring's built-in provider that:
1. Calls `UserDetailsService` to fetch user
2. Uses `BCryptPasswordEncoder` to verify password
3. Checks account status flags

### `AuthenticationManager` (ProviderManager)
Coordinator — receives authentication requests and delegates to the right `AuthenticationProvider`.

> Note: `AuthenticationManager` is the interface. `ProviderManager` is the actual implementation Spring uses internally.

---

## Security Context

### What gets stored after JWT validation

```
SecurityContextHolder
      └── SecurityContext
              └── UsernamePasswordAuthenticationToken
                      ├── principal (UserPrincipal)
                      │       └── User
                      │           ├── id = 1
                      │           ├── username = "ayush"
                      │           └── password = "$2a$12$..."
                      ├── credentials = null (cleared)
                      ├── authorities = ["USER"]
                      ├── authenticated = true
                      └── details
                              ├── remoteAddress = "192.168.1.1"
                              └── sessionId = "abc123"
```

### Accessing it anywhere in your app

```java
Authentication auth = SecurityContextHolder.getContext().getAuthentication();

String username        = auth.getName();
UserPrincipal principal = (UserPrincipal) auth.getPrincipal();
Collection<?> roles    = auth.getAuthorities();
```

### Important — Context lives only for one request

```
Request arrives  → JWT validated → stored in SecurityContext
       │
       ▼
Request processed → response sent
       │
       ▼
SecurityContext CLEARED automatically

Next request → JWT validated again → stored again
```

This is what **STATELESS** means — nothing persists between requests.

---

## Common Questions

**Q: Is the password checked again on every request?**
No. JWT validation (signature + expiry) is the only check after login. The 3-arg constructor on `UsernamePasswordAuthenticationToken` sets `authenticated = true` and all subsequent filters trust it.

**Q: Why hit the DB on every JWT request?**
The token only contains username and expiry. Fresh DB fetch ensures you get current roles and account status (e.g., if account was locked after token was issued).

**Q: Why does `AuthenticationManager` need to be a `@Bean`?**
Spring creates it internally but doesn't expose it. Your login controller needs to `@Autowired` it to call `authenticate()` manually, which requires it to be registered as a Bean.

**Q: Why `UserPrincipal` instead of making `User` implement `UserDetails`?**
Single Responsibility Principle — `User` manages DB data, `UserPrincipal` manages security representation. Mixing `@Entity` with `UserDetails` causes JPA/serialization issues and tight coupling.

**Q: What's the difference between the 2-arg and 3-arg `UsernamePasswordAuthenticationToken`?**

| | 2-arg constructor | 3-arg constructor |
|---|---|---|
| Used at | Login (unverified claim) | After JWT validation (verified) |
| `authenticated` | `false` | `true` |
| `authorities` | `null` | populated |