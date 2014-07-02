package pharaohLayoutGa;

import java.lang.reflect.Field;
import java.util.Arrays;

import pharaohLayoutModel.Map;
import pharaohLayoutModel.renderer.WindowRenderMap;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class Main {
	private static void displayHelpAndExit() {
		System.out.println("plga " + Main.getVersion());
		System.out.println();

		for (Field f : Args.class.getFields()) {
			Parameter p = f.getAnnotation(Parameter.class);

			if (p != null) {
				System.out.print(Arrays.toString(p.names()));

				if (!p.description().equals("")) {
					System.out.print(" - " + p.description());
				}

				System.out.println();
			}
		}

		System.exit(0);
	}

	private static String getVersion() {
		return Main.class.getPackage().getImplementationVersion();
	}

	public static void main(String[] argv) {
		new JCommander(new Args(), argv);

		if (Args.help) {
			displayHelpAndExit();
		}

		Population.mapHeight = Args.mapHeight;
		Population.mapWidth = Args.mapWidth;
		Population pop = new Population(Args.populationSize, true);

		Map fittest = new Map(Population.mapHeight, Population.mapWidth);
		fittest.fillRandom(3);

		int generation = 0;

		WindowRenderMap wnd = null;

		if (!Args.headless) {
			wnd = new WindowRenderMap(fittest);
			wnd.setVisible(true);
			wnd.setLocation(100, 1000);
			wnd.getRenderer().shouldFakeIsomorphic = false;
		}

		while (true) {
			generation++;
			Population nextEvolution = Algorithm.evolve(pop);
			Map nextEvolutionFittest = nextEvolution.getFittest();

			int nextEvolutionFittestRating = Algorithm.getFitnessRating(nextEvolutionFittest);

			if (nextEvolutionFittestRating > Algorithm.getFitnessRating(fittest)) {
				fittest = nextEvolutionFittest.copy();
				System.out.println("geneartion:" + generation);
				System.out.println("rank: " + nextEvolutionFittestRating);

				if (!Args.headless) {
					wnd.getRenderer().setMap(fittest);
					wnd.setTitle("rating: " + nextEvolutionFittestRating);
					wnd.getRenderer().repaint();
				}
			}
		}
	}
}
