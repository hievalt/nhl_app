package nhl_main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
* Hakee joukkueet
* @author Valtteri Hietala
* @author Juuso Eskelinen
* Version 7.3.2017 // Version changes: method and function naming corrected(starting with lower case), Made class more object oriented:
* constructors ja get-method
*/
public class HaeJoukkueet{
	
	private List<String> joukkuelista = new ArrayList<String>();
	
	
	/**
	 * oletus constructori, haetaan nhl sivuilta.
	 */
	HaeJoukkueet(){
		this.luoLista("https://www.nhl.com/info/teams");
	}
	
	/**
	 * vaihtoehtoinen constructori.
	 * @param sivu sivusto josta haetaan
	 */
	HaeJoukkueet(String sivu){
		this.luoLista(sivu);
	}
	
	public List<String> getLista(){
		return joukkuelista;
	}
	
	
	/**
	 * Luo listan joukkueiden nimistä
	 * @return joukkuelista		String lista joukkueista
	 * @throws IOException
	 */
	private List<String> luoLista(String sivusto) {
		Document sivu = null;
		try {
			sivu = Jsoup.connect(sivusto).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements joukkueCity = haeSivulta(sivu, "team-city");
		for (int i = 0; i < joukkueCity.size(); i++) joukkuelista.add(joukkueCity.eq(i).text()); 
		return joukkuelista;
	}
	
	/**
	 * Hakee joukkueiden tiedot (kaupunki, nimi) sivulta
	 * @param sivu 				(HTML)lähdekoodi
	 * @param selector 			Elementin tunniste, jota haetaan
	 * @return joukkueInfo		Elementtilista, joukkueiden nimet ja kaupungit
	 * @throws IOException
	 */
	private Elements haeSivulta(Document sivu, String selector) {
		Elements joukkueInfo = sivu.select("a."+selector);
		return joukkueInfo;
	}
	
	/**
	 * Tulostaa listan joukkueista
	 * @throws IOException
	 */
	void tulosta() throws IOException{
		List<String> joukkueet = new ArrayList<String>();
		joukkueet = getLista();
		for (String joukkue : joukkueet) System.out.println(joukkue); 
	}
	
}
