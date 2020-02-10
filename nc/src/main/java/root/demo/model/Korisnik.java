package root.demo.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedEntityGraph(name = "Korisnik.Roles.Permissions", 
	attributeNodes = @NamedAttributeNode(value = "roles", subgraph = "permissions"), 
	subgraphs = @NamedSubgraph(name = "permissions", attributeNodes = @NamedAttributeNode("permissions")))
@Inheritance(
	    strategy = InheritanceType.JOINED
	)
public class Korisnik {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String ime;

	private String prezime;

	private String grad;

	private String drzava;

	private String email;

	private boolean recenzent;

	private String titula;

	private String username;

	private String password;
	
	@Column(name = "enabled", nullable = false, columnDefinition = "boolean default false") 
	private boolean enabled;
	
	@Column(name = "non_locked", nullable = false, columnDefinition = "boolean default true") 
	private boolean nonLocked;
	
	@Column(name = "aktiviran", nullable = false, columnDefinition = "boolean default false") 
	private boolean aktiviran;

	@ManyToMany
	@JoinTable(name = "Korisnik_NaucnaOblast", joinColumns = {
			@JoinColumn(name = "korisnik_id") }, inverseJoinColumns = { @JoinColumn(name = "naucna_oblast_id") })
	private List<NaucnaOblast> naucneOblasti;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "korisnik_roles", joinColumns = @JoinColumn(name = "korisnik_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	@ManyToMany(mappedBy="recenzenti")
	private List<Casopis> casopisiRec; 
	
	@ManyToMany(mappedBy="urednici")
	private List<Casopis> casopisiUred; 
	
	@OneToMany(mappedBy = "glavniUrednik")
	private List<Casopis> casopisiGlUred;
	
	@OneToMany(mappedBy="autor")
	private List<NaucniRad> naucniRadovi;
	
	public Korisnik(String s, String ss, Set<Role> set) {
		this.email = s;
		this.password = ss;
		this.roles = set;
	}

}
