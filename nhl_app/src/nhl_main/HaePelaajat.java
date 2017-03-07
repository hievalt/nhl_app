package nhl_main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Hakee tietyn joukkueen pelaajat
 * @author v,j
 *
 */
public class HaePelaajat{
	
	// Constructor
	HaePelaajat(){}

	public void Syote() throws IOException{
		System.out.println("Joukkue: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String joukkue = br.readLine();
		Tulosta(joukkue);
	}
	/**
	 * Tulostaa listan pelaajista
	 * @param pelaajalista 		Lista pelaajista
	 * @throws IOException 
	 */
	private void Tulosta(String joukkue) throws IOException {
		List<String> pelaajalista = new ArrayList<String>();
		pelaajalista = LuoLista(joukkue);
		for (String pelaaja : pelaajalista) System.out.println(pelaaja);
		
	}

	/**
	 * Hakee joukkueen pelaajat nhl.comista
	 * @param joukkue 		Joukkueen nimi
	 * @return pelaajat		Elementtilista pelaajista
	 * @throws IOException
	 */
	private Elements HaeSivulta(String joukkue) throws IOException {
		Document sivu = Jsoup.connect("https://www.nhl.com/"+joukkue+"/roster").get();
		Elements pelaajat = sivu.select(".name-col");
		return pelaajat;
	}

	/**
	 * Luo listan pelaajista
	 * @param joukkue 			Joukkueen nimi
	 * @return pelaajalista 	String-lista pelaajista
	 * @throws IOException
	 */
	private List<String> LuoLista(String joukkue) throws IOException {
		
		HaePelaajat olio = new HaePelaajat();
		Elements pelaajat = olio.HaeSivulta(joukkue);
		
		List<String> pelaajalista = new ArrayList<String>();
		int rooliIndex = 0;
		String[] roolit = {"FORWARDS", "DEFENSE", "GOALIES"};
		
		for (Element pelaaja : pelaajat){
			if (pelaaja.text().equals("Player") && rooliIndex < 3){
				pelaajalista.add(roolit[rooliIndex]);
				rooliIndex++;
			} else pelaajalista.add(pelaaja.text());
		}
		
		return pelaajalista;
		
	}

	public List<String> LataaLista(String joukkue) throws IOException {
		return LuoLista(joukkue);
	}

}
