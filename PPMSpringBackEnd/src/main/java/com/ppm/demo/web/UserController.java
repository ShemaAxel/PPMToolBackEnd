package com.ppm.demo.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ppm.demo.domain.User;
import com.ppm.demo.payload.JWTLoginSuccessResponse;
import com.ppm.demo.payload.LoginRequest;
import com.ppm.demo.security.JWTTokenProvider;
import com.ppm.demo.services.MapValidationErrorService;
import com.ppm.demo.services.UserService;
import com.ppm.demo.validator.UserValidator;
import static com.ppm.demo.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private MapValidationErrorService mapValidationErrorService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private JWTTokenProvider tokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);

		return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
	}

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {

		userValidator.validate(user, result);
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;
		User newUser = userService.saveUserOrUpdate(user);
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);

	}

	@PostMapping("/update")
	public ResponseEntity<?> updateUser(@Valid @RequestBody User user, BindingResult result) {

		userValidator.validate(user, result);
		ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
		if (errorMap != null)
			return errorMap;
		User newUser = userService.saveUserOrUpdate(user);
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);

	}

	@GetMapping("/all")
	public Iterable<User> getAllUser() {
		return userService.findAll();
	}

	@GetMapping("/find/{username}")
	public ResponseEntity<?> findByUsername(@PathVariable String username) {
		User user = userService.findUserByUsername(username);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return new ResponseEntity<String>("User with: '" + id + "' is Deleted.", HttpStatus.OK);
	}

}