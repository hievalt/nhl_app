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
 * @author Valtteri Hietala
 * @author Juuso Eskelinen
 * Version 7.3.2017 // Version changes: method and function naming corrected(starting with lower case), Made class more object oriented:
 * constructors ja get-method
 */
public class HaePelaajat{
	private List<String> pelaajalista = new ArrayList<String>();
	
	/**
	 * Empty constructor
	 */
	HaePelaajat(){
		
	}
	
	
	/**
	 * Constructor with team attribute
	 * @param joukkue team which the list of players will be created
	 * @throws IOException
	 */
	HaePelaajat(String joukkue){
		this.luoLista(joukkue);
	}
	
	
	
	public List<String> getLista() throws IOException {
		return pelaajalista;
	}


	public void syote() throws IOException{
		System.out.println("Joukkue: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String joukkue = br.readLine();
		tulosta(joukkue);
	}
	/**
	 * Tulostaa listan pelaajista
	 * @param pelaajalista 		Lista pelaajista
	 * @throws IOException 
	 */
	private void tulosta(String joukkue) throws IOException {
		List<String> pelaajalista = new ArrayList<String>();
		pelaajalista = getLista();
		for (String pelaaja : pelaajalista) System.out.println(pelaaja);
		
	}

	/**
	 * Hakee joukkueen pelaajat nhl.comista
	 * @param joukkue 		Joukkueen nimi
	 * @return pelaajat		Elementtilista pelaajista
	 * @throws IOException
	 */
	private Elements haeSivulta(String joukkue) throws IOException {
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
	private void luoLista(String joukkue) {
		
		HaePelaajat olio = new HaePelaajat();
		Elements pelaajat = null;
		try {
			pelaajat = olio.haeSivulta(joukkue);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int rooliIndex = 0;
		String[] roolit = {"FORWARDS", "DEFENSE", "GOALIES"};
		
		for (Element pelaaja : pelaajat){
			if (pelaaja.text().equals("Player") && rooliIndex < 3){
				pelaajalista.add(roolit[rooliIndex]);
				rooliIndex++;
			} else pelaajalista.add(pelaaja.text());
		}
		
		
	}
}
