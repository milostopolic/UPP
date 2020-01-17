package root.demo.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
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

import root.demo.dto.ProfileDTO;
import root.demo.dto.UserDTO;
import root.demo.exception.InvalidJWTokenException;
import root.demo.jwt.JwtBlackList;
import root.demo.jwt.JwtTokenProvider;
import root.demo.model.Korisnik;
import root.demo.model.RoleName;
import root.demo.repository.KorisnikRepository;
import root.demo.repository.RoleRepository;
import root.demo.request.LoginRequest;
import root.demo.request.SignUpRequest;
import root.demo.response.JwtAuthenticationResponse;
import root.demo.service.CheckTokenAndPermissions;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    KorisnikRepository korisnikRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;
    
    @Autowired
    CheckTokenAndPermissions permissions;

    @Autowired
    JwtTokenProvider jwtProvider;
    
    public static RoleName roleName;
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    @RequestMapping("/secured")
	public String secured(){
		return "Pozdrav " + new Date();
	}

    
    @GetMapping("/getAll") //permisije
    public List<UserDTO> getAllUseres(){
    	List<UserDTO> users =  new ArrayList<UserDTO>();
    	for(Korisnik u : korisnikRepository.findAll()) {
    		users.add(new UserDTO(u));
    	}
    	return users;
    }
    
 
    int attemps = 0;
    String myEmail = "";
    
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    	InetAddress localhost = null;
		try {
			localhost = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ip = (localhost.getHostAddress()).trim();
    	try {
    		/*if(loginRequest.getEmail().equals(myEmail)) {
    			
    		} else {
    			this.myEmail = loginRequest.getEmail();
    			this.attemps = 0;
    		}*/

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
    		Korisnik us = korisnikRepository.findByEmail(loginRequest.getEmail()).get();
    		logger.info("ID: {} | PRN4SI | success", us.getId() );
	        return ResponseEntity.ok(new JwtAuthenticationResponse(profile, jwt));
		} catch (AuthenticationException e) {

			logger.error("userIP: {} | PRN4SI | failed", ip);

			/*attemps++;
			if(attemps == 3) {
				User r = korisnikRepository.findByEmail(loginRequest.getEmail()).get();
				r.setNonLocked(false);
				korisnikRepository.save(r);
				logger.error("ID: {} | LCKD | error", r.getId() );
			}*/
			logger.error("PRN4SI | fail");

			return new ResponseEntity<String>("Not logged! " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
    }
    
    @PostMapping("/checkEmail")
    public ResponseEntity<?> checkEmail(@RequestBody String email) {
    	System.out.println(email);
    	Korisnik loginUser = korisnikRepository.findByEmail(email).get();
    	System.out.println(loginUser.getEmail() + " dsssssss");
    	if(loginUser == null) {
            return new ResponseEntity<>("Fail -> No email found. Register first",
                     HttpStatus.BAD_REQUEST);
       } 
    	   return new ResponseEntity<UserDTO>(new UserDTO(loginUser), HttpStatus.OK);	
    }
    
    @GetMapping("/validEmail/{email}")
    public ResponseEntity<?> validEmail(@PathVariable String email) {
    	InetAddress localhost = null;
		try {
			localhost = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String ip = (localhost.getHostAddress()).trim();
        System.out.println("System IP Address : " + 
                      (localhost.getHostAddress()).trim());
    	if(korisnikRepository.findByEmail(email).isPresent()) {
    		logger.error("userIP: {} | R3USER | failed", ip);
    		return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);	
    	} else {
    		return new ResponseEntity<>(true,
                    HttpStatus.OK);
    	}  	   
    }
    
    @GetMapping("/getLogged")
    public ResponseEntity<?> getLogged() {
    	System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
    	if(korisnikRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).isPresent()) {
    		Korisnik logged = korisnikRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).get();
    		return new ResponseEntity<UserDTO>(new UserDTO(logged), HttpStatus.OK);
    	} else {
    		 return new ResponseEntity<>("Fail ->No logged user",
                     HttpStatus.BAD_REQUEST);
    	}   	
    	
    }
    
  
    
    @GetMapping("/check/{token}") 
    public ResponseEntity<?> checkToken(@PathVariable String token) throws InvalidJWTokenException{
    	List<String> permisije = permissions.getPermissions(token);
    	String permissije = "";
    	for(String perm : permisije) {
    		permissije += perm+"|";
    	}
    	return new ResponseEntity<String>(permissije, HttpStatus.OK);
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
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) throws MessagingException {
       
       if(korisnikRepository.existsByEmail(signUpRequest.getEmail())) {
          return new ResponseEntity<>("Fail -> Email is already in use!",
                   HttpStatus.BAD_REQUEST);
        }

       if(signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword()))
				try {
					
					
				       Korisnik user = new Korisnik(signUpRequest.getEmail(), 
				    		   				encoder.encode(signUpRequest.getPassword()),
				    		   				Collections.singleton(roleRepository.findByName(RoleName.USER)));
				        
				        user.setEnabled(true); //false za mail
				        user.setNonLocked(true);
				        
				        				    
				        //emailService.sendNotification(user, confirmationToken, "Welcome to Megatravel.com! Confirm your registration.");
				        korisnikRepository.save(user);
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