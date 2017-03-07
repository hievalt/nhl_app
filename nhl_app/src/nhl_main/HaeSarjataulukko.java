/**
 * 
 */
package nhl_main;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Luokka hakee tämänhetkisen sarjataulukon kaikki joukkueet
 * @author Juuso Eskelinen
 * @author Valtteri Hietala
 */
public class HaeSarjataulukko {
	
	private String[][] sarjataulukko = new String[30][3];

	/**
	 * konstruktori
	 */
	public HaeSarjataulukko() {
		this.luoLista("http://www.sportsnet.ca/hockey/nhl/standings");
	}
	
	
	public String[][] getLista(){
		return sarjataulukko;
	}

	/**
	 * luodaan sarjataulukko
	 * @param sivusto
	 * @return
	 */
	private void luoLista(String sivusto) {
		Document sivu = null;
		try {
			sivu = Jsoup.connect(sivusto).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements joukkue = haeSivulta(sivu, "#fullleaguestandings > div.table-responsive > table > tbody > tr > td.team");
		Elements pts = haeSivulta(sivu, "#fullleaguestandings > div.table-responsive > table > tbody > tr > td.pts");
		Elements gp = haeSivulta(sivu, "#fullleaguestandings > div.table-responsive > table > tbody > tr > td.gp");
		for (int i = 0; i < joukkue.size(); i++) {
			 sarjataulukko[i][0] = joukkue.eq(i).text(); 
			 sarjataulukko[i][1] = pts.eq(i).text(); 
			 sarjataulukko[i][2] = gp.eq(i).text(); 
		}
		                                             
	}
	
	/**
	 * Haetaan sivulta sarjataulukon joukkueet
	 * @param sivu josta haetaan
	 * @param selector haettava elementti
	 * @return listan elementeistä
	 */
	private Elements haeSivulta(Document sivu, String selector) {
		Elements joukkue = sivu.select(selector);
		return joukkue;
	}
	
	/**
	 * Tulostaa listan joukkueista
	 * @throws IOException
	 */
	void tulosta() throws IOException{
		System.out.printf("%-10s %-12s %-11s %-3s %n","Sijoitus:", "Joukkue:", "Pisteet:", "GP:"); 
		for (int i = 0; i < sarjataulukko.length; i++){
			System.out.printf("%-10s %-12s %-11s %-3s %n",i + 1,  sarjataulukko[i][0], sarjataulukko[i][1], sarjataulukko[i][2]); 
		}
	}
	
	
	/**
	 * @param args ei käytössä
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		HaeSarjataulukko s = new HaeSarjataulukko();
		s.tulosta();
	}

}
