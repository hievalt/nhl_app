package nhl_main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;

/**
 * "Käyttöliittymä" toimintojen testaamiselle
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
		while (true) {
			System.out.print("\nToiminnot: aikavali, pelaajat, joukkueet");
			System.out.print("\n:");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String toiminto = br.readLine();

		// Toimintojen tunnistus ja toteuttaminen
			switch (toiminto) {
				case ("aikavali"):
					PelitAikavalilla.main(args);
					break;
				case ("pelaajat"):
					HaePelaajat pelaajat = new HaePelaajat();
					pelaajat.syote();
					break;
				case ("joukkueet"):
					HaeJoukkueet joukkueet = new HaeJoukkueet();
					joukkueet.Tulosta();
					break;
			}
		}
	}
}
