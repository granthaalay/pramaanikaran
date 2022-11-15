package com.pramaanikaran.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class JwtResponse {
  private String jwtToken;
  private String type;
  private Long id;
  private String username;
  private String email;
  private List<String> roles;
}
