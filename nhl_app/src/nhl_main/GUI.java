package nhl_main;


import java.awt.EventQueue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Font;
import javax.swing.border.EmptyBorder;

/**
 * TODO: Skaalautuvat listat, "Pelaajaprofiili" kun valitsee pelaajan, Kalenterikomponentin lisäys -> PelitAikavalilla
 * 
 * Ohjelman GUI
 * @author v,j
 *
 */
public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public GUI() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 635, 409);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		// Listamodelit, jotka on "back-end" noissa listoissa?
		final DefaultListModel<String> joukkueetModel = new DefaultListModel<String>();
		final DefaultListModel<String> pelaajatModel = new DefaultListModel<String>();
		
		// Joukkueiden haku
		HaeJoukkueet joukkueLista = new HaeJoukkueet();
		List<String> joukkueet = new ArrayList<String>();
		joukkueet = joukkueLista.getLista();
		for (String joukkue : joukkueet){
			joukkueetModel.addElement(joukkue);
		}
	    
	    // Joukkuelista
	    final JList<String> joukkueBox = new JList<String>(joukkueetModel);
	    joukkueBox.setBorder(new EmptyBorder(2, 2, 2, 2));
	    joukkueBox.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
	    
		JScrollPane joukkueBoxScroll = new JScrollPane();
		joukkueBoxScroll.setViewportView(joukkueBox);
		
		getContentPane().add(joukkueBoxScroll, BorderLayout.WEST);
		
		// Pelaajalista
		final JList<String> pelaajaBox = new JList<String>(pelaajatModel);
		pelaajaBox.setBorder(new EmptyBorder(2, 2, 2, 2));
		pelaajaBox.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
		
		JScrollPane pelaajaBoxScroll = new JScrollPane();
		pelaajaBoxScroll.setViewportView(pelaajaBox);
		
		getContentPane().add(pelaajaBoxScroll, BorderLayout.CENTER);
		
		// Joukkuetta klikkaamalla haetaan pelaajaboxiin sen joukkueen pelaajat
	    joukkueBox.addListSelectionListener(new ListSelectionListener() {
	    	public void valueChanged(ListSelectionEvent arg0) {
	    		
	    		// Tyhjennetään lista mahd. aikaisemmista pelaajista
	    		pelaajatModel.clear();
	    		
	    		// Otetaan joukkueen nimen loppuosa valitusta joukkueesta
	    		String[] joukkueNimi = joukkueBox.getSelectedValue().toString().split(" ");
	    		String valittuJoukkue = joukkueNimi[joukkueNimi.length-1].toLowerCase();
	    		
	    		// Jos joukkue on esim. Detroit Red Wings niin url ei ole /wings vaan /redwings
	    		// Otetaan joukkueen nimestä viimeisen sanan lisäksi myös viimeistä edeltävä -> "red" + "wings"
	    		if (valittuJoukkue.equals("jackets") || valittuJoukkue.equals("wings") || valittuJoukkue.equals("leafs")){
	    			valittuJoukkue = joukkueNimi[joukkueNimi.length-2].toLowerCase() + valittuJoukkue;
	    		}
	    		
	    		// Haetaan lista joukkueen pelaajista
	    		HaePelaajat pelaajaLista = new HaePelaajat(valittuJoukkue); // luodaan HaePelaajat-olio
	    		List<String> pelaajat = new ArrayList<String>(); //alustetaan pelaajalista
	    		try {
					pelaajat = pelaajaLista.getLista(); //pyydet��n oliolta pelaajalistaa
				} catch (IOException e) {
					e.printStackTrace();
				}
	    		
	    		// Lisätään pelaajat GUIn listaan
	    		for (String pelaaja : pelaajat) {
	    			pelaajatModel.addElement(pelaaja);
	    		}
	    	}
	    });
		
	}
}
