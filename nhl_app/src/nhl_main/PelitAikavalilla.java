package nhl_main;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Valtteri Hietala
 * @author Juuso Eskelinen
 * version 6.3.2017
 */
public class PelitAikavalilla {

	/**
	 * Tietojen kysyminen k�ytt�j�lt� ja tulosten tulostus
	 * 
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws IOException, ParseException {
		// Tiedonsyöttö
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("\nsy�t� joukkue (tyhj� = haetaan kaikki): ");
		String joukkue = br.readLine();
		System.out.print("Sy�t� alkupvm: ");
		String alkupvm = br.readLine();
		System.out.print("Sy�t� loppupvm: ");
		String loppupvm = br.readLine();
		String format = "%-15s%s%n";
		String yksiJoukkue = ""; // Jos haetaan tiettyä joukkuetta
		
		System.out.print("\nPelimäärät aikavälillä " + alkupvm + " - "
				+ loppupvm + ":\n");
		
		if (joukkue.equals("")) {
			// Haetaan kaikkien joukkueiden pelimäärät
			List<String> pelimaarat = new ArrayList<String>();
			pelimaarat = HaeKaikki(pelimaarat, alkupvm, loppupvm);
			
			// Tulostetaan pelimäärät joukkueittain
			for (String peli : pelimaarat) {
				System.out.printf(format, peli.split("-")[1].trim(),
						peli.split("-")[0].trim());
			}
		} else {
			// Haetaan vain tietyn joukkueen pelimäärä
			yksiJoukkue = LueTiedosto(joukkue, alkupvm, loppupvm);
			System.out.printf(format, yksiJoukkue.split("-")[1].trim(),
					yksiJoukkue.split("-")[0].trim());
		}
	}

	//TODO: tiedostosta joukkueet?
	/**
	 * Hakee kaikkien joukkueiden pelimäärät
	 * 
	 * @param pelimaarat 	Tulostettava lista
	 * @param alkupvm		Käyttäjän asettama pvm alaraja
	 * @param loppupvm 		Käyttäjän asettama pvm yläraja
	 * @return List<String> pelimaarat
	 * @throws IOException
	 * @throws ParseException
	 */
	private static List<String> HaeKaikki(List<String> pelimaarat, String alkupvm, String loppupvm) throws IOException, ParseException {
		String[] joukkueet = { "Anaheim", "Arizona", "Boston", "Buffalo",
				"Calgary", "Carolina", "Chicago", "Colorado", "Columbus",
				"Dallas", "Detroit", "Edmonton", "Florida", "LA Kings",
				"Minnesota", "Montreal", "Nashville", "NJ Devils",
				"NY Rangers", "NY Islanders", "Philadelphia", "Pittsburgh",
				"Ottawa", "San Jose", "St Louis", "Tampa Bay", "Toronto",
				"Vancouver",
				// "Vegas Golden",
				"Washington", "Winnipeg" };
		
		// Käy joukkuelistan läpi ja hakee jokaisen joukkueen pelimäärät
		for (int i = 0; i < joukkueet.length; i++) {
			pelimaarat.add(LueTiedosto(joukkueet[i], alkupvm, loppupvm));
		}
		// Järjestetään pelimäärän mukaan
		Collections.sort(pelimaarat, Collections.reverseOrder());
		return pelimaarat;
		
	}

	/**
	 * Lukee tiedoston rivi riviltä ja laskee joukkueen pelimäärän aikavälillä alkupvm - loppupvm
	 * 
	 * @param joukkue		Joukkueen nimi
	 * @param alkupvm		Käyttäjän asettama pvm alaraja
	 * @param loppupvm		Käyttäjän asettama pvm yläraja
	 * @return String		Yhden joukkueen pelimäärä annetulla aikavälillä
	 * @throws IOException
	 * @throws ParseException
	 */
	private static String LueTiedosto(String joukkue, String alkupvm,
			String loppupvm) throws IOException, ParseException {
		// System.out.println("Sy�tetty joukkue oli: " + joukkue);
		String rivi = "";
		boolean vali = false;
		int lkm = 0;

		try {
			BufferedReader lukija = new BufferedReader(
					new FileReader(
							"resources/nhl.txt"));

			while ((rivi = lukija.readLine()) != null) {
				// jos vali = true, ni tiedostoa luetaan sellasesta kohasta joka
				// on ylittäny pvm alarajan
				if (vali) {
					if (rivi.contains(joukkue)) {
						// Tarkistetaan onko pvm rivi ylärajan sisällä
						if (rivi.contains("2017") || rivi.contains("2016")) {
							if (TarkistaPvm(rivi.split(" ")[0].trim(), loppupvm) < 0)
								break;
							else
								lkm++;
						}
					}
				} else {
					// Etsitään ensimmäinen alarajan sisällä oleva pvm rivi
					// tiedostosta
					if (rivi.contains("2017") || rivi.contains("2016"))
						if (TarkistaPvm(rivi.split(" ")[0].trim(), alkupvm) <= 0) {
							vali = true;
						}

				}
			}

			lukija.close();
		} catch (FileNotFoundException e) {
			// Tiedostoa ei löydy
			e.printStackTrace();
		}

		return lkm + " peli� - " + joukkue; // + "aikavälillä " + alkupvm +
											// " - " + loppupvm ;
	}

	/**
	 * Formatoidaan merkkijonot päivämääriksi
	 * 
	 * @param pvmrivi		Päivämäärä tiedostosta
	 * @param pvmSyote		Käyttäjän syöttämä päivämääräraja
	 * @return int 			-1 jos käyttäjän syöte tiedostosta luetun päivämäärän ennen, 0 jos sama, 1 jos jälkeen
	 * @throws ParseException
	 */
	private static int TarkistaPvm(String pvmrivi, String pvmSyote)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		if (pvmrivi != "" && pvmSyote != "") {
			// pvmrivi = pvmrivi.split("/")[2] + "/" + pvmrivi.split("/")[1] +
			// "/"
			// + pvmrivi.split("/")[0];
			Date tiedostosta = sdf.parse(pvmrivi);
			Date annettu = sdf.parse(pvmSyote);
			return VertailePvm(tiedostosta, annettu);
		}
		return -1;
	}

	/**
	 * Verrataan tiedoston päivämäärää annettuihin rajapäivämääriin
	 * 
	 * @param tiedostosta	Tiedostosta luettu pvm
	 * @param annettu		Käyttäjän syöttämä pvm
	 * @return int 			-1 jos käyttäjän syöte tiedostosta luetun päivämäärän ennen, 0 jos sama, 1 jos jälkeen
	 */
	private static int VertailePvm(Date tiedostosta, Date annettu) {
		if (annettu.before(tiedostosta)) {
			return -1;
		} else if (annettu.equals(tiedostosta)) {
			return 0;
		} else {
			return 1;
		}

	}

}
