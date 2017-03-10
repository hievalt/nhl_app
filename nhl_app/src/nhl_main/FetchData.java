package nhl_main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @author v
 * 
 * TODO: Pelaajia ei voi hakee suoraan vaan täytyy tietää pelaajan joukkue, joku ratkasu tähän?
 */
public class FetchData {
	
	/**
	 * For testing
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// Create teams list
		Teams teams = new Teams();
		
		// Get Team with string
		System.out.println(teams.getTeam("Minnesota").getName()); 
		
		// Get WPG team_id with string
		int wpg = teams.getTeam("Winnipeg").getTeamId(); 
		
		// Get team with team_id
		System.out.println(teams.getTeam(wpg).getName()+"\n"); 
		
		// One player from team, roster loaded from internet
		Player fromWild = teams.getTeam("Minnesota").getPlayer("Suter"); 
		System.out.println(fromWild.getName());
		
		// One player from same team, roster loaded from list
		fromWild = teams.getTeam("wild").getPlayer("Granlund");
		System.out.println(fromWild.getName());
		
		/* All players from team
		for (Player p : teams.getTeam("washington").getRoster()){
			System.out.println(p.getName() + " (id: " + p.getId() + ")");
		}
		*/
		
		Player fromWPG = teams.getTeam("jets").getPlayer("laine"); 
		System.out.println("\n" + fromWPG.getName() + " (id: " + fromWPG.getId() + ")");
		System.out.println(fromWPG.getUrl());
		
		Player fromCHI = teams.getTeam("chicago").getPlayer("panarin");
		System.out.println("\n" + fromCHI.getName() + " (Player id: "+ fromCHI.getId() + ") - " + teams.getTeam(fromCHI.getTeamId()).getName() +
				" (Team id: " + fromCHI.getTeamId() + ")");
	}
		
}

/**
 * Class for team
 * @author v
 *
 */
class Team {
	
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

/**
 * Class for Teams
 * Manages invidual teams as a list
 */
class Teams {
	
	private List<Team> team_list = new ArrayList<Team>();
	
	/**
	 * Constructor
	 * @throws IOException
	 */
	public Teams() throws IOException {
		this.team_list = this.createTeams();
	}
	
	/**
	 * Creates list of teams
	 * @return team_list
	 * @throws IOException
	 */
	private List<Team> createTeams() throws IOException {
		Document html = Jsoup.connect("https://www.nhl.com/info/teams").get();
		Elements teams = html.select("a.team-city");
		List<Team> team_list = new ArrayList<Team>();
		
		for (Element team : teams) {
			Team new_team = new Team(team.text());
			team_list.add(new_team);
		}
		
		return team_list;
	}
	
	/**
	 * Returns list of teams
	 * @return team_list
	 */
	public List<Team> getTeamList(){
		return this.team_list;
	}
	
	/**
	 * Returns one team that contains specific string in its name
	 * Null if team is not found
	 * @param name (name or part of the name of wanted team)
	 * @return Team
	 */
	public Team getTeam(String name){
		for (Team team : this.team_list){
			if (team.getName().toLowerCase().contains(name.toLowerCase())) return team;
		}
		return new Team();
	}
	
	/**
	 * Returns one team via team_id
	 * Null if team is not found
	 * @param team_id (team_id of wanted team)
	 * @return Team
	 */
	public Team getTeam(int team_id){
		for (Team team : this.team_list) if(team.getTeamId() == team_id) return team;
		return new Team();
	}
	
}

/**
 * Class for Player
 * @author v
 *
 */
class Player {
	
	private String player_name, player_url;
	private static int next_id = 0;
	private int player_id, team_id;
	
	/**
	 * Empty constructor
	 */
	public Player() {}
	
	/**
	 * 
	 * @param name (player name)
	 * @param team_id 	(team id)
	 */
	public Player(String name, int team_id, String player_url){
		this.player_name = name;
		this.team_id = team_id;
		this.player_url = player_url;
		this.player_id = ++next_id;
	}
	
	
	/**
	 * Returns name of the player (string)
	 * @return player_name
	 */
	public String getName(){
		return this.player_name;
	}
	
	/**
	 * Returns id of the player (int)
	 * @return player_id
	 */
	public int getId() {
		return this.player_id;
	}
	
	/**
	 * Returns id of the team
	 * @return team_id
	 */
	public int getTeamId(){
		return this.team_id;
	}
	
	public String getUrl(){
		return this.player_url;
	}
	
	
}



