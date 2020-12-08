package dad.javafx.micv.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LeerFichero {

	public static ArrayList<String> leer(String string) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<String> resultado = new ArrayList<String>();
		FileReader fr = new FileReader(LeerFichero.class.getResource(string).getFile());
		BufferedReader br = new BufferedReader(fr);
		String linea;
		
		while ((linea = br.readLine()) != null) {
			resultado.add(linea);
		}
		
		br.close();
		fr.close();
		
		return resultado;
	}

}
