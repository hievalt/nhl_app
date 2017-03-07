package nhl_main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Hakee joukkueiden nimet, kaupungit
 * @author v,j
 *
 */
public class HaeJoukkueet {
	
	// Constructor
	HaeJoukkueet(){}
	
	/**
	 * Luo listan joukkueiden nimistä
	 * @return joukkuelista		String lista joukkueista
	 * @throws IOException
	 */
	private List<String> LuoLista() throws IOException{
		Document sivu = Jsoup.connect("https://www.nhl.com/info/teams").get();
		Elements joukkueCity = HaeSivulta(sivu, "team-city");
		List<String> joukkuelista = new ArrayList<String>();
		for (int i = 0; i < joukkueCity.size(); i++){
			joukkuelista.add(joukkueCity.eq(i).text());
		}
		return joukkuelista;
	}
	
	/**
	 * Hakee joukkueiden tiedot (kaupunki, nimi) sivulta
	 * @param sivu 				(HTML)lähdekoodi
	 * @param selector 			Elementin tunniste, jota haetaan
	 * @return joukkueInfo		Elementtilista, joukkueiden nimet ja kaupungit
	 * @throws IOException
	 */
	private Elements HaeSivulta(Document sivu, String selector) throws IOException {
		Elements joukkueInfo = sivu.select("a."+selector);
		return joukkueInfo;
	}
	
	/**
	 * Tulostaa listan joukkueista
	 * @throws IOException
	 */
	void Tulosta() throws IOException{
		List<String> joukkueet = new ArrayList<String>();
		joukkueet = LuoLista();
		for (String joukkue : joukkueet){
			System.out.println(joukkue);
		}
	}
}
