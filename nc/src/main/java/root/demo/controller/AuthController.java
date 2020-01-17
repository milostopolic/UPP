package root.demo.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import root.demo.dto.LoginRequestDTO;
import root.demo.dto.LoginResponseDTO;
import root.demo.dto.ProfileDTO;
import root.demo.dto.SignupRequestDTO;
import root.demo.dto.UserDTO;
import root.demo.exception.InvalidJWTokenException;
import root.demo.jwt.JwtBlackList;
import root.demo.jwt.JwtTokenProvider;
import root.demo.model.Korisnik;
import root.demo.model.RoleName;
import root.demo.repository.KorisnikRepository;
import root.demo.repository.RoleRepository;
import root.demo.service.CheckTokenAndPermissions;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    KorisnikRepository userRepository;

    
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtTokenProvider jwtProvider;
    
    @Autowired
    CheckTokenAndPermissions permissions;
    
    
    public static RoleName roleName;
   
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    @RequestMapping("/secured")
	public String secured(){
		return "Pozdrav " + new Date();
	}
/*
    @PreAuthorize("hasAuthority('AddUsers')")
    @PostMapping("/activateUser") //dodati permisije
    public ResponseEntity<?> activateUser(@RequestBody ActivateUserDTO acu){
    	Korisnik u = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
    	InetAddress localhost = null;
		try {
			localhost = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        System.out.println("System IP Address : " + 
                      (localhost.getHostAddress()).trim());
    	Korisnik usr = userRepository.getOne(acu.getId());
    	if(acu.getStatus().equals(UserStatus.ACTIVATE)) {
    		usr.setEnabled(acu.isFlag());
    		logger.info("user: {}, id: {} | AKN0U5 | success", u.getId(), usr.getId());
    		userRepository.save(usr);
    	}else if(acu.getStatus().equals(UserStatus.BLOCK)){
    		usr.setNonLocked(!acu.isFlag());
    		logger.info("user: {}, id: {} | BLN0U5 | success", u.getId(), usr.getId());
    		userRepository.save(usr);
    	}

    	return new ResponseEntity<UserDTO>(new UserDTO(usr), HttpStatus.OK);
    }
 */   
   
    
    int attemps = 0;
    String myEmail = "";
    
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
    	InetAddress localhost = null;
		try {
			localhost = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ip = (localhost.getHostAddress()).trim();
    	try {

    		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
	                loginRequest.getEmail(),
	                loginRequest.getPassword()
	         );
    		
	         
    		Authentication authentication = authenticationManager.authenticate(
	            token
	        );
    		
    		String email = authentication.getName();
    		List<String> authorities = authentication.getAuthorities().stream()
    				.map(GrantedAuthority::getAuthority)
    				.collect(Collectors.toList());
    		
    		String jwt = jwtProvider.generateToken(authentication);
    		ProfileDTO profile = new ProfileDTO(email, authorities, true);
    		Korisnik us = userRepository.findByEmail(loginRequest.getEmail()).get();
    		logger.info("ID: {} | PRN4SI | success", us.getId() );
	        return ResponseEntity.ok(new LoginResponseDTO(profile, jwt));
		} catch (AuthenticationException e) {

			logger.error("userIP: {} | PRN4SI | failed", ip);

			logger.error("PRN4SI | fail");

			return new ResponseEntity<String>("Not logged! " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
    }
    
    @GetMapping("/getLogged")
    public ResponseEntity<?> getLogged() {
    	
    	if(userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).isPresent()) {
    		Korisnik logged = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
    		return new ResponseEntity<UserDTO>(new UserDTO(logged), HttpStatus.OK);
    	} else {
    		 return new ResponseEntity<>("Fail ->No logged user",
                     HttpStatus.BAD_REQUEST);
    	}   	
    	
    }
    
    @GetMapping("/check/{token}") 
    public ResponseEntity<?> checkToken(@PathVariable String token) throws InvalidJWTokenException{
    	if(permissions.validateJwtToken(token)) {
    		return new ResponseEntity<>(HttpStatus.OK);
    	} return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    
    @GetMapping("/check/{token}/username")
    public ResponseEntity<?> getUsername(@PathVariable String token) throws InvalidJWTokenException{
    	if(permissions.validateJwtToken(token)) {
    		return new ResponseEntity<String>(jwtProvider.getUserPrincipal(token).getUsername(), HttpStatus.OK);
    	}
    	return null;
    }
    
    @GetMapping("/signout") 
    public ResponseEntity<?> signout(HttpServletRequest request) {
    	System.out.println("LOGOUT");
    	SecurityContextHolder.getContext().setAuthentication(null);
    	SecurityContextHolder.clearContext();
    	JwtBlackList.lista.add(request.getHeader("Authorization").substring(7, request.getHeader("Authorization").length()));
		return null;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequestDTO signUpRequest) throws MessagingException {
       
       if(userRepository.existsByEmail(signUpRequest.getEmail())) {
          return new ResponseEntity<>("Fail -> Email is already in use!",
                   HttpStatus.BAD_REQUEST);
        }

       if(signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword()))
				try {
					
					
				       Korisnik user = new Korisnik(signUpRequest.getEmail(), 
				    		   				encoder.encode(signUpRequest.getPassword()),
				    		   				Collections.singleton(roleRepository.findByName(RoleName.USER)));
				        
				        user.setEnabled(false);
				        
				        
				       
				        //emailService.sendNotification(user, confirmationToken, "Welcome to Megatravel.com! Confirm your registration.");
				        userRepository.save(user);
						logger.info("user: {} | R3USER | success", user.getId());
				        return new ResponseEntity<Korisnik>(user, HttpStatus.CREATED);
						   
					   
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		else {
	    	   return new ResponseEntity<>("Fail -> Passwords don't match!",
	                   HttpStatus.BAD_REQUEST);
	       }
	return null;
    }
}