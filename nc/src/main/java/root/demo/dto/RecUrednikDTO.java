package root.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import root.demo.model.Korisnik;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecUrednikDTO {

	private String ime;
	
	private String prezime;
	
	private Long id;
	
	public RecUrednikDTO(Korisnik k) {
		this.ime = k.getIme();
		this.prezime = k.getPrezime();
		this.id = k.getId();
	}
	
}
