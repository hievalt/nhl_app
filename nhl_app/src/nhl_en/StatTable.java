package nhl_en;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Creates list which includes player goals, assists and points from season 2016-2017
 * 
 * TODO: All seasons, all stats
 * 
 * @author v
 *
 */
public class StatTable {
	// Lista listoista, tarkotuksena saada kaikki kaudet talteen
	// Nyt statsit vaan nykyiseltä kaudelta
	private List<List<String>> seasons = new ArrayList<List<String>>(); // [season], [goals], [assists], [points]
	
	/**
	 * Empty constructor
	 */
	public StatTable() {}
	
	/**
	 * Create stats table
	 * @param player (Player whose stats will be loaded)
	 * @throws IOException
	 */
	public StatTable(Player player) throws IOException{
		this.seasons = createSeasons(player);
	}

	
	/**
	 * Creates each season
	 * Currently only season 2016-2017
	 * @param player (Player whose stats will be loaded)
	 * @return seasons_data
	 * @throws IOException
	 */
	private List<List<String>> createSeasons(Player player) throws IOException {
		List<List<String>> seasons_data = new ArrayList<List<String>>();
		if (player.getUrl() != null){
			Document player_page = Jsoup.connect("https://www.nhl.com" + player.getUrl()).get();
			Element seasonStats = player_page.select(".player-overview__stats").first(); // Pitäis saada #careerTable, mutta ei toiminu millää
			List<String> new_season = new ArrayList<String>();
			new_season.add(seasonStats.select("td[data-col='0']").first().text()); // Season
			new_season.add(seasonStats.select("td[data-col='2']").first().text()); // Goals
			new_season.add(seasonStats.select("td[data-col='3']").first().text()); // Assists	
			new_season.add(seasonStats.select("td[data-col='4']").first().text()); // Points
			seasons_data.add(new_season);
		}
		return seasons_data;
	}
	
	public String getGoals(){
		return this.seasons.get(0).get(1).toString();
	}
	
	public String getAssists(){
		return this.seasons.get(0).get(2).toString();
	}
	
	public String getPoints(){
		return this.seasons.get(0).get(3).toString();
	}

	/**
	 * Season list size
	 * @return int
	 */
	public int getSize() {
		return this.seasons.size();
	}

}
