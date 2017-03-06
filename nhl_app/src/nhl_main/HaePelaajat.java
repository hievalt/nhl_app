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
 * 
 * @author v,j
 *
 */
public class HaePelaajat {

	/**
	 * Tiedonsyöttö
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		System.out.print("Joukkue: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String joukkue = br.readLine();
		System.out.println("");
		Tulosta(LuoLista(joukkue));

	}

	/**
	 * Tulostaa listan pelaajista
	 * @param pelaajalista 		Lista pelaajista
	 */
	private static void Tulosta(List<String> pelaajalista) {
		for (String pelaaja : pelaajalista){
			System.out.println(pelaaja);
		}
		
	}

	/**
	 * Hakee joukkueen pelaajat nhl.comista
	 * @param joukkue 		Joukkueen nimi
	 * @return pelaajat		Elementtilista pelaajista
	 * @throws IOException
	 */
	private static Elements HaeSivulta(String joukkue) throws IOException {
		Document sivu = Jsoup.connect("https://www.nhl.com/"+joukkue+"/roster").get();
		Elements pelaajat = sivu.getElementsByClass("name-col");
		return pelaajat;
	}

	/**
	 * 
	 * @param joukkue 			Joukkueen nimi
	 * @return pelaajalista 	String-lista pelaajista
	 * @throws IOException
	 */
	private static List<String> LuoLista(String joukkue) throws IOException {
		Elements pelaajat = HaeSivulta(joukkue);
		List<String> pelaajalista = new ArrayList<String>();
		int rooliIndex = 0;
		String[] roolit = {"Forwards", "Defense", "Goalies"};
		for (Element pelaaja : pelaajat){
			if (pelaaja.text().equals("Player") && rooliIndex < 3){
				pelaajalista.add(roolit[rooliIndex]);
				rooliIndex++;
			} else pelaajalista.add(pelaaja.text());
		}
		return pelaajalista;
		
	}

}
