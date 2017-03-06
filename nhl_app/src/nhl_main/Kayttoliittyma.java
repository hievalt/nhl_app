package nhl_main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

/**
 * "Kayttoliittyma toimintojen testaamiselle
 * 
 * @author V, J
 *
 */
public class Kayttoliittyma {

	/**
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws IOException, ParseException {
<<<<<<< HEAD
		while (true) {
			System.out.print("Toiminto: ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String toiminto = br.readLine();
=======
		System.out.print("Toiminto: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String toiminto = br.readLine();
		
		// Toimintojen tunnistus ja toteuttaminen
		switch (toiminto) {
			case ("aikavali"):
				PelitAikavalilla.main(args);
				break;
		}
>>>>>>> branch 'master' of https://github.com/hievalt/nhl_app.git

			// Toimintojen tunnistus ja toteuttaminen
			switch (toiminto) {
				case ("aikaväli"):
					PelitAikavalilla.main(args);
					break;
				case ("pelaajat"):
					HaePelaajat.main(args);
				break;
				}
		}
	}

}
