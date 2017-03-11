package nhl_en;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Class for team
 * @author v
 *
 */
public class Team {
	
	private String team_name;
	private static int next_id = 0;
	private int team_id;
	private List<Player> roster = new ArrayList<Player>();
	
	/**
	 * Empty constructor
	 */
	public Team() {}

	public List<Player> getRoster() throws IOException {
		checkRoster();
		return this.roster;
	}
	
	/**
	 * Checks if roster has been fetched and creates one if needed
	 * @throws IOException
	 */
	private void checkRoster() throws IOException {
		if (this.roster.size() < 1) this.createRoster();
	}

	/**
	 * Constructor
	 * @param name team_name
	 */
	public Team(String name){
		this.team_name = name;
		this.team_id = ++next_id;
	}
	
	/**
	 * Creates roster for the team
	 * @throws IOException
	 */
	private void createRoster() throws IOException {
		
		List<Player> roster = new ArrayList<Player>();
		String team_url = this.getTeamUrl();
		Document roster_page = Jsoup.connect("https://www.nhl.com/"+team_url+"/roster").get();
		Elements players = roster_page.select(".name-col");
		for (Element player : players){
			if (!player.text().equals("Player")){
				String playerName = player.text().split(" ", 2)[1]; // Remove first initial
				Player new_player = new Player(playerName, this.team_id, player.select("a").attr("href"));
				roster.add(new_player);
				}
			}
		this.roster = roster;
	}
	
	/**
	 * Get url of a team
	 * @return url (string)
	 */
	private String getTeamUrl() {
		String url = this.team_name.split(" ")[this.team_name.split(" ").length-1].toLowerCase();
		if (url.equals("jackets") || url.equals("wings") || url.equals("leafs")){
			url = this.team_name.split(" ")[this.team_name.split(" ").length-2].toLowerCase() +
					this.team_name.split(" ")[this.team_name.split(" ").length-1].toLowerCase();
		}
		return url;
	}
	
	/**
	 * Get team name
	 * @return team_name
	 */
	public String getName(){
		return this.team_name;
	}
	
	/**
	 * Get team_id
	 * @return team_id
	 */
	public int getTeamId(){
		return this.team_id;
	}
	
	/**
	 * Get player from team with name
	 * Null if player is not found
	 * @param name (name of the player)
	 * @return Player
	 * @throws IOException
	 */
	public Player getPlayer(String name) throws IOException{
		
		checkRoster();
		
		for (Player player : this.roster){
			if (player.getName().toLowerCase().contains(name.toLowerCase())) return player;
		}
		return new Player();
	}
}
