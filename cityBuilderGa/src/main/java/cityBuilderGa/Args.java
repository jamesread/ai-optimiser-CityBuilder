package cityBuilderGa;

import com.beust.jcommander.Parameter;

public class Args {
	@Parameter(names = "--tourneySize")
	public static int tournamentSize = 10;

	@Parameter(names = "--mapHeight")
	public static int mapHeight = 5;

	@Parameter(names = "--mapWidth")
	public static int mapWidth = 5;

	@Parameter(names = "--popSize")
	public static int populationSize = 100;

	@Parameter(names = "--headless", description = "By default a GUI showing the map is shown. Using this argument allows the program to run without a GUI.")
	public static boolean headless = false;

	@Parameter(names = "--help", help = true, description = "Show this help message.")
	public static boolean help = false;
}
