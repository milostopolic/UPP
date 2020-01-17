package root.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import root.demo.dto.SignupRequestDTO;
import root.demo.jwt.UserPrincipal;
import root.demo.model.Korisnik;
import root.demo.model.Permission;
import root.demo.model.Role;
import root.demo.model.RoleName;
import root.demo.repository.KorisnikRepository;
import root.demo.repository.RoleRepository;



@Service
public class DomainUserDetailsService implements UserDetailsService {

	@Autowired
	private KorisnikRepository korisnikRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private static final Logger logger = LoggerFactory.getLogger(DomainUserDetailsService.class);

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println(email);
		Korisnik user = korisnikRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Korisnik not found with email: " + email));

		return getUserPrincipal(user);
	}

	public void changePassword(Korisnik user, String newPassword) {
		user.setPassword(passwordEncoder.encode(newPassword));
		korisnikRepository.save(user);
		logger.info("Korisnik {} has changed their password.", user.getEmail());
	}

	public Korisnik findByEmail(String email) {
		return korisnikRepository.findByEmail(email).orElse(null);
	}

	public void changeUserNonLockStatusTrue(Korisnik user) {
		user.setNonLocked(true);
		korisnikRepository.save(user);
	}

	public void changeUserNonLockStatusFalse(Korisnik user) {
		user.setNonLocked(false);
		korisnikRepository.save(user);
	}

	public List<Korisnik> findAllUsers() {
		return korisnikRepository.findUsersExceptSelf();
	}

	public Korisnik findCurrentUser() {
		return korisnikRepository.findCurrentUser();
	}

	@Transactional
	public Korisnik registerUser(SignupRequestDTO signUpRequest) {
		if (korisnikRepository.existsByEmail(signUpRequest.getEmail())) {
			return null;
		}

		Korisnik user = new Korisnik(signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()),
				Collections.singleton(roleRepository.findByName(RoleName.USER)));

		korisnikRepository.save(user);

		return user;
	}

	private UserPrincipal getUserPrincipal(Korisnik user) {
		Stream<String> roles = user.getRoles().stream()
				.map(Role::getName)
				.map(Enum::name);

		Stream<String> permissions = user.getRoles().stream()
				.map(Role::getPermissions)
				.flatMap(Collection::stream)
				.map(Permission::getName);

		List<GrantedAuthority> authorities = Stream.concat(roles, permissions)
				.distinct()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		return new UserPrincipal(user.getId(), user.getPassword(), user.getEmail(), user.isEnabled(), authorities,
				user.isNonLocked());
	}

	public void activateUser(Korisnik user) {
		user.setEnabled(true);
		korisnikRepository.save(user);
	}

	public List<String> getUserAuthorities(Korisnik user) {
		Stream<String> roles = user.getRoles().stream()
				.map(Role::getName)
				.map(Enum::name);

		Stream<String> permissions = user.getRoles().stream()
				.map(Role::getPermissions)
				.flatMap(Collection::stream)
				.map(Permission::getName);

		List<GrantedAuthority> authorities = Stream.concat(roles, permissions)
				.distinct()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		List<String> auts = new ArrayList<String>();
		for (GrantedAuthority nesto : authorities) {
			auts.add(nesto.getAuthority());
		}

		return auts;
	}
}
