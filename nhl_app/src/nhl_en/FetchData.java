package nhl_en;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
		System.out.println("Syntax:");
		System.out.println("[Team], [Name], [print, roster]");
		System.out.println("[Player], [Name], [print, goals, assists, points]");
		System.out.println("e.g. player, Patrik Laine, goals\n");
		
		String[] types = {"team", "player"};
		String[] team_actions = {"roster", "print"};
		String[] player_actions = {"print", "goals", "assists", "points"};
		
		while (true){
			// Input
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print(":");
			String name = br.readLine();
			String input = name.toLowerCase();
			
			// Type is team or player
			for (String type : types){
				if (input.split(", ").length > 1 && input.split(", ")[0].equals(type)) {
					if (input.split(", ")[0].equals("team")) {
						Team team = teams.getTeam(name.split(", ")[1]);
						// Team actions
						for (String action : team_actions) {
							if (input.split(", ").length > 2 && input.split(", ")[2].equals(action)) {
								switch (action) {
									case ("print"):
										System.out.println(team.print());
										break;
									case "roster":
										System.out.println(
												"\nRoster of " + team.print() + "\n*************************************");
										for (Player p : team.getRoster()) {
											System.out.println(p.getName() + " (id: " + p.getId() + ")");
										}
										System.out.println("");
										break;
									}
									break;
							}
						}
						// Player actions
					} else if (input.split(", ")[0].equals("player")) {
						Player player = teams.findPlayer(name.split(", ")[1]);
						for (String action : player_actions) {
							if (input.split(", ").length > 2 && input.split(", ")[2].equals(action)) {
								switch (action) {
									case ("print"):
										System.out.println(player.print());
										break;
									case "goals":
										System.out.println(player.getName() + " has " + player.getStats().getGoals() + " goals");
										break;
									case "assists":
										System.out.println(player.getName() + ": " + player.getStats().getAssists() + " assists");
										break;
									case "points":
										System.out.println(player.getName() + ": " + player.getStats().getPoints() + " points");
										break;
									}
									break;
							}
						}
					}
						break;
				}
			}
		}
		/*
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
		
		All players from team
		for (Player p : teams.getTeam("washington").getRoster()){
			System.out.println(p.getName() + " (id: " + p.getId() + ")");
		}
		
		
		// Get player, id, url, goals
		Player fromWPG = teams.getTeam("jets").getPlayer("laine"); 
		System.out.println("\n" + fromWPG.getName() + " (id: " + fromWPG.getId() + ", url: "+ fromWPG.getUrl() + ")");
		System.out.println(fromWPG.getName() + " has " + fromWPG.getStats().getAssists() + " assists this season");
		
		// Get player, player id, team, team id, player points
		Player fromCHI = teams.getTeam("chicago").getPlayer("panarin");
		System.out.println("\n" + fromCHI.getName() + " (Player id: "+ fromCHI.getId() + ") - " + teams.getTeam(fromCHI.getTeamId()).getName() +
				" (Team id: " + fromCHI.getTeamId() + ")");
		System.out.println(fromCHI.getName() + " has " + fromCHI.getStats().getPoints() + " points this season");
		*/
	}
		
}









