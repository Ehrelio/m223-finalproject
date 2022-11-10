package ch.zli.eb.m223.finalproject.service;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import ch.zli.eb.m223.finalproject.model.Credential;
import ch.zli.eb.m223.finalproject.model.CwSUser;

import io.smallrye.jwt.build.Jwt;

@ApplicationScoped
public class SessionService {
  @Inject
  UserService userService;

  public Response authenticate(Credential credential) {
    Optional<CwSUser> principal = userService.findByEmail(credential.getEmail());

    try {
      if (principal.isPresent() && principal.get().getHashedPassword().equals(credential.getPassword())) {
        String token = Jwt
            .issuer("https://zli.example.com/")
            .upn(credential.getEmail())
            .groups(new HashSet<>(Arrays.asList("User", "Admin")))
            .expiresIn(Duration.ofHours(12))
            .sign();
        return Response
            .ok(principal.get())
            .cookie(new NewCookie("finalproject", token))
            .header("Authorization", "Bearer " + token)
            .build();
      }
    } catch (Exception e) {
      System.err.println("Couldn't validate password.");
    }

    return Response.status(Response.Status.FORBIDDEN).build();
  }
}
