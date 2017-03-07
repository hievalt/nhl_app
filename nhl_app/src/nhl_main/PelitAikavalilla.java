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
 * Ohjelmalla lasketaan tietyn tai kaikkien joukkueiden pelim��ri� tietyll� aikav�lill�
 * @author Valtteri Hietala
 * @author Juuso Eskelinen
 * version 6�7.3.2017  //TODO: kolmas konstruktori tietyn joukkuuen pelim��rien luomiselle ja metodi "haeTietty"
 */
public class PelitAikavalilla {
	
	private static List<String> pelimaarat = new ArrayList<String>();
	
	
	public PelitAikavalilla() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * Constructiori luokalle
	 */
	public PelitAikavalilla(String pvm, String pvm2){
		this.HaeKaikki(pvm, pvm2);
	}
	


	public List<String> getPelimaarat(){
		return pelimaarat;
	}
	/**
	 * Tietojen kysyminen k�ytt�j�lt� ja tulosten tulostus
	 * 
	 * @param args
	 * @throws IOException
	 * @throws ParseException
	 */
	public static void main(String[] args) throws IOException, ParseException {
		// Tiedonsyöttö
		PelitAikavalilla  p = new PelitAikavalilla();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("\nInsert team (empty = get all): ");
		String joukkue = br.readLine();
		System.out.print("Give starting date(dd.mm.yyyy): ");
		String alkupvm = br.readLine();
		System.out.print("Give ending date(dd.mm.yyyy): ");
		String loppupvm = br.readLine();
		p.HaeKaikki(alkupvm, loppupvm);
		p.Tulosta(joukkue, alkupvm, loppupvm);
	}
	
	private void Tulosta(String joukkue, String alkupvm, String loppupvm){
		String format = "%-15s%s%n"; // Tyhjän välin pituus joukkueen ja pelimäärän välissä tulosteessa
		String yksiJoukkue = ""; // Jos haetaan tiettyä joukkuetta
		
		System.out.print("\nGames played during: " + alkupvm + " - " + loppupvm + ":\n");	
		if (joukkue.equals("")) {
		
			// Tulostetaan pelimäärät joukkueittain
			for (String peli : pelimaarat) {
				System.out.printf(format, peli.split("-")[1].trim(),
						peli.split("-")[0].trim());
			}
		} else {
			// Haetaan vain tietyn joukkueen pelimäärä
			try {
				yksiJoukkue = LueTiedosto(joukkue, alkupvm, loppupvm);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.printf(format, yksiJoukkue.split("-")[1].trim(),
					yksiJoukkue.split("-")[0].trim());
		}
	}


	/**
	 * Hakee kaikkien joukkueiden pelimäärät
	 * 
	 * @param pelimaarat 	Tulostettava lista
	 * @param alkupvm		Käyttäjän asettama pvm alaraja
	 * @param loppupvm 		Käyttäjän asettama pvm yläraja
	 * @return 
	 * @return List<String> pelimaarat
	 * @throws IOException
	 * @throws ParseException
	 */
	private void HaeKaikki(String alkupvm, String loppupvm) {
		List<String> joukkueet = null;
		try {
			joukkueet = luoJoukkueLista("resources/teamlist.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Käy joukkuelistan läpi ja hakee jokaisen joukkueen pelimäärät
		for (int i = 0; i < joukkueet.size(); i++) {
			try {
				pelimaarat.add(LueTiedosto(joukkueet.get(i), alkupvm, loppupvm));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Järjestetään pelimäärän mukaan
		Collections.sort(pelimaarat, Collections.reverseOrder());
		
	}
	
	
	/**
	 * Haetaan tiedostosta joukkueet listaan
	 * @param tiedostonnimi tiedosto, josta haetaan
	 * @return lista joukkueista
	 * @throws IOException 
	 */
	public List<String> luoJoukkueLista(String tiedostonnimi) throws IOException {
		List<String> l = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(tiedostonnimi));
		try {
		    String line = br.readLine();

		    while (line != null) {
		        line = br.readLine();
		        l.add(line);
		    }		    
		} finally {
		    br.close();
		}
		//poistetaan listan lopusta ylim. nullit
		for(int i = 0; i < l.size(); i ++){
			if(l.get(i) == null) l.remove(i);
		}
		return l;
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
	private String LueTiedosto(String joukkue, String alkupvm, String loppupvm) throws IOException, ParseException {
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

		return lkm + " games - " + joukkue;
	}

	/**
	 * Formatoidaan merkkijonot päivämääriksi
	 * 
	 * @param pvmrivi		Päivämäärä tiedostosta
	 * @param pvmSyote		Käyttäjän syöttämä päivämääräraja
	 * @return int 			-1 jos käyttäjän syöte tiedostosta luetun päivämäärän ennen, 0 jos sama, 1 jos jälkeen
	 * @throws ParseException
	 */
	private int TarkistaPvm(String pvmrivi, String pvmSyote)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		if (pvmrivi != "" && pvmSyote != "") {
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
