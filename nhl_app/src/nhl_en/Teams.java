package nhl_en;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Class for Teams
 * Manages invidual teams as a list
 */
public class Teams{
	
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

	public Player findPlayer(String player) throws IOException {
		for (Team team : this.team_list){
			if (team.getPlayer(player).getName() != null){
				return team.getPlayer(player);
			}
		}
		return new Player();
	}
	
}
