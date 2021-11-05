package it.mattiaciraldo.spring.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.mattiaciraldo.spring.model.Role;
import it.mattiaciraldo.spring.model.RoleType;
import it.mattiaciraldo.spring.model.User;
import it.mattiaciraldo.spring.model.UserDetailsImpl;
import it.mattiaciraldo.spring.repository.RoleRepository;
import it.mattiaciraldo.spring.repository.UserRepository;
import it.mattiaciraldo.spring.security.JwtUtils;
import it.mattiaciraldo.spring.security.LoginRequest;
import it.mattiaciraldo.spring.security.LoginResponse;

@RestController
@RequestMapping("/api")
public class AuthController {

	@Autowired
	PasswordEncoder encoder;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserRepository userRepository;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	RoleRepository roleRepository;

	@PostMapping("/login")
	public String authenticateUser(@RequestParam String username, String password) {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setPassword(password);
		loginRequest.setUsername(username);
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		authentication.getAuthorities();

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		// ResponseEntity<?>
		ResponseEntity<?> response = ResponseEntity.ok(new LoginResponse(jwt, userDetails.getId(),
				userDetails.getUsername(), userDetails.getEmail(), roles, userDetails.getExpirationTime()));
		if (response.getStatusCode().is4xxClientError()) {
			return "ERROR";
		} else {
			return "Connesso!";
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestParam String username, String email, String password, String nome,
			String cognome) {
		SignupRequest signUpRequest = new SignupRequest();
		signUpRequest.setUsername(username);
		signUpRequest.setEmail(email);
		signUpRequest.setPassword(password);
		signUpRequest.setNome(nome);
		signUpRequest.setCognome(cognome);

		// Verifica l'esistenza di Username e Email già registrate
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new SignupResponse("Errore: Username già in uso!"));
		}
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new SignupResponse("Errore: Email già in uso!"));
		}
		// Crea un nuovo user codificando la password
		User user = new User(null, signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()), signUpRequest.getNome(), signUpRequest.getCognome());
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		// Verifica l'esistenza dei Role
		if (strRoles == null) {
			Role userRole = roleRepository.findByRoleType(RoleType.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Errore: Role non trovato!"));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByRoleType(RoleType.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Errore: Role non trovato!"));
					roles.add(adminRole);
					break;
				default:
					Role userRole = roleRepository.findByRoleType(RoleType.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Errore: Role non trovato!"));
					roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok(new SignupResponse("User registrato con successo!"));
	}
}
