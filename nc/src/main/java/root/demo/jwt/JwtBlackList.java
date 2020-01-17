package root.demo.jwt;

import java.util.ArrayList;
import java.util.List;

public class JwtBlackList {

	public static List<String> lista = new ArrayList<String>();

	public void init() {
		lista.add("Test");
	}
	
	public void add(String string) {
		lista.add(string);
	}
	
}
