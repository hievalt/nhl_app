package nhl_main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

	public static void main(String[] args) throws IOException {
		System.out.print("Joukkue: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String joukkue = br.readLine();
		System.out.println("");
		HaeSivu(joukkue);

	}

	private static void HaeSivu(String joukkue) throws IOException {
		Document sivu = Jsoup.connect("https://www.nhl.com/"+joukkue+"/roster").get();
		Elements pelaajat = sivu.getElementsByClass("name-col");
		int rooliIndex = 0;
		String[] roolit = {"Forwards", "Defense", "Goalies"};
		for (Element pelaaja : pelaajat){
			if (pelaaja.text().equals("Player") && rooliIndex < 3){
				System.out.println(roolit[rooliIndex]);
				rooliIndex++;
			} else System.out.println(pelaaja.text());
		}
		
	}

}
