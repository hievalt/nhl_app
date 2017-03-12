package nhl_en;

import java.io.IOException;

/**
 * Class for Player
 * @author v
 *
 */
public class Player {
	
	private String player_name, player_url;
	private static int next_id = 1;
	private int player_id, team_id;
	private StatTable player_stats = new StatTable();
	
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
	
	public StatTable getStats() throws IOException{
		if (this.player_stats.getSize() < 1) this.player_stats = new StatTable(this);
		return this.player_stats;
	}

	public String print() {
		return this.getName() + " (id: " + this.getTeamId() + ")";
	}
		
}
