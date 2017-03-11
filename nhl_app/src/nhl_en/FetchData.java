package nhl_en;

import java.io.IOException;

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
		//
		// Jos samasta joukkueesta halutaan toinen pelaaja myöhemmin ni ei tarvii hakee netistä
		// vaan ne on listassa tallessa
		Player fromPitts = teams.getTeam("penguins").getPlayer("crosby"); 
		System.out.println(fromPitts.getName() + " (id: " + fromPitts.getId() + ")\n");
		
		// One player from same team, roster loaded from list
		Player fromWild = teams.getTeam("wild").getPlayer("Granlund");
		System.out.println(fromWild.getName() + " (id: " + fromWild.getId() + ")"); 
		System.out.println(fromWild.getName() + " has " + fromWild.getStats().getGoals() + " goals this season");
		
		/* All players from team
		for (Player p : teams.getTeam("washington").getRoster()){
			System.out.println(p.getName() + " (id: " + p.getId() + ")");
		}
		*/
		
		// Get player, id, url, goals
		Player fromWPG = teams.getTeam("jets").getPlayer("laine"); 
		System.out.println("\n" + fromWPG.getName() + " (id: " + fromWPG.getId() + ", url: "+ fromWPG.getUrl() + ")");
		System.out.println(fromWPG.getName() + " has " + fromWPG.getStats().getAssists() + " assists this season");
		
		// Get player, player id, team, team id, player points
		Player fromCHI = teams.getTeam("chicago").getPlayer("panarin");
		System.out.println("\n" + fromCHI.getName() + " (Player id: "+ fromCHI.getId() + ") - " + teams.getTeam(fromCHI.getTeamId()).getName() +
				" (Team id: " + fromCHI.getTeamId() + ")");
		System.out.println(fromCHI.getName() + " has " + fromCHI.getStats().getPoints() + " points this season");
	}
		
}









