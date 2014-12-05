import java.util.ArrayList;



public class Testing {
	public static void main(String[] args) {
		
		
		String test1 = "<section><title>Input interpretation</title><sectioncontents>today</sectioncontents></section><section><title>Result</title><sectioncontents>Thursday, December 4, 2014</sectioncontents></section><section><title>Date formats</title><sectioncontents>04/12/2014  (day/month/year)</sectioncontents></section><section><title>Time in 2014</title><sectioncontents>338th day49th week</sectioncontents></section><section><title>Observances for December 4 (Ireland)</title><sectioncontents>(no official holidays or major observances)</sectioncontents></section><section><title>Anniversaries for December 4, 2014</title><sectioncontents>birth of Marisa Tomei (1964- ): 50th anniversary\n"
				+ "birth of Chelsea Noble (1964- ): 50th anniversary\n"
				+ "Charlemagne becomes sole ruler of the Franklish Kingdom (771): 1243rd anniversary\n"
				+ "Works Progress Administration is liquidated (1942): 72nd anniversary\n"
				+ "English colonists celebrate the first Thanksgiving in Americas (1619): 395th anniversary</sectioncontents></section><section><title>Daylight information for December 4 in Dublin, Ireland</title><sectioncontents>sunrise | 8:22 am GMT  (5 minutes  19 seconds ago)\n"
				+ "sunset | 4:10 pm GMT  (7 hours  42 minutes from now)\n"
				+ "duration of daylight | 7 hours 48 minutes</sectioncontents></section><section><title>Phase of the Moon</title><sectioncontents>waxing gibbous moon</sectioncontents></section>";
	
		String test2 = "<noresults></noresults>";
		
		String test3 = "<section><title>Input interpretation</title><sectioncontents>The Dark Knight  (movie)</sectioncontents></section><section><title>Basic movie information</title><sectioncontents>title | The Dark Knight\n"
				+ "director | Christopher Nolan\n"
				+ "release date | 18/07/2008  (6 years 5 months ago)\n"
				+ "runtime | 152 minutes  (2 hours 32 minutes)\n"
				+ "writers | Christopher Nolan  |  Jonathan Nolan  |  David S. Goyer  |  Bob Kane\n"
				+ "genres | action  |  crime  |  thriller\n"
				+ "MPAA rating | PG-13\n"
				+ "production budget | $185 million  (US dollars)  (current equivalent: $204.4 million)</sectioncontents></section><section><title>Image</title><sectioncontents></sectioncontents></section><section><title>Box office performance</title><sectioncontents>total receipts | $533.3 million  (US dollars)  (all-time rank: 4th)\n"
				+ "highest receipts | $158.4 million  (US dollars)  (weekend ending July 20, 2008)\n"
				+ "highest rank | 1st  (weekend ending July 20, 2008)\n"
				+ "maximum number of screens | 4366  (weekend ending July 20, 2008)\n"
				+ "highest average receipts per screen | $36283  (US dollars)  (weekend ending July 20, 2008)\n"
				+ "(unadjusted box office receipts)\n"
				+ "(US data only)</sectioncontents></section><section><title>Cast</title><sectioncontents>actor | character(s)\n"
				+ "Michael Caine | Alfred J. Pennyworth\n"
				+ "Gary Oldman | James Gordon\n"
				+ "Aaron Eckhart | Two-Face\n"
				+ "Christian Bale | Batman\n"
				+ "Maggie Gyllenhaal | Rachel Dawes</sectioncontents></section><section><title>Academy Awards and nominations</title><sectioncontents>category | recipient\n"
				+ "actor in a supporting role  (winner) | Heath Ledger\n"
				+ "art direction  (nominee) | Nathan Crowley  |  Peter Lando\n"
				+ "cinematography  (nominee) | Wally Pfister\n"
				+ "film editing  (nominee) | Lee Smith\n"
				+ "makeup  (nominee) | John Caglione, Jr.  |  Conor O'Sullivan\n"
				+ "sound editing  (winner) | Richard King\n"
				+ "sound mixing  (nominee) | Lora Hirschberg  |  Gary A. Rizzo  |  Ed Novick\n"
				+ "visual effects  (nominee) | Nick Davis  |  Chris Corbould  |  Tim Webber  |  Paul Franklin</sectioncontents></section><section><title>Wikipedia summary</title><sectioncontents></sectioncontents></section><section><title>Wikipedia page hits history</title><sectioncontents></sectioncontents></section>";
		
		
		String test4 = "<section><title>Input interpretation</title><sectioncontents>What is it?</sectioncontents></section><section><title>Response</title><sectioncontents>That depends on what \"it\" is.</sectioncontents></section>";
		
//		ArrayList<String[]> A = Assignment.decodeXML(test1);
//		
//		for (String[] S: A) {
//			for (String B: S) {
//				System.out.printf("%18s", B + "  ");
//			}
//			System.out.println();
//	}	
		// System.out.print(System.currentTimeMillis());
		
		long day = 24 * 3600 * 1000;
		long year = day * 365;
		System.out.println(year / day);
		
		long a = System.currentTimeMillis() - 3601000;
		System.out.println(year / (24 * 3600 * 1000));
		System.out.println(Assignment.getTimeDifference(a));
		
		
	
	}
}
