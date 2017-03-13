package nl.kerkers.max.mealy104.learner;

import de.learnlib.algorithms.features.observationtable.ObservationTable;
import de.learnlib.algorithms.lstargeneric.mealy.ExtensibleLStarMealy;
import de.learnlib.algorithms.lstargeneric.mealy.ExtensibleLStarMealyBuilder;
import de.learnlib.api.SUL;
import de.learnlib.cache.mealy.MealyCacheConsistencyTest;
import de.learnlib.cache.mealy.MealyCacheOracle;
import de.learnlib.cache.mealy.MealyCaches;
import de.learnlib.eqtests.basic.EQOracleChain;
import de.learnlib.eqtests.basic.mealy.RandomWalkEQOracle;
import de.learnlib.experiments.Experiment.MealyExperiment;
import de.learnlib.oracles.SULOracle;
import net.automatalib.automata.transout.MealyMachine;
import net.automatalib.graphs.Graph;
import net.automatalib.words.Alphabet;
import net.automatalib.words.Word;
import nl.kerkers.max.mealy104.checker.Checker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @author Max Kerkers
 */
public class Learner<T> {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected SUL<T, String> sul;
	private ExtensibleLStarMealy<T, String> learner;

	protected Learner() {}

	public Learner(SUL<T, String> sul) {
		this.sul = sul;
	}

	public MealyMachine<?, T, ?, String> learn(Alphabet<T> alphabet) {
		SULOracle<T, String> oracle = new SULOracle<>(sul);
		MealyCacheOracle<T, String> mqOracle = MealyCaches.createCache(alphabet, oracle);

		RandomWalkEQOracle<T, String> eqOracleRandomWalk = new RandomWalkEQOracle<>(0.01, 1000, new Random(42), sul);
//		MealyRandomWordsEQOracle<T, String> eqOracleRandom = new MealyRandomWordsEQOracle<>(mqOracle, 5, 10, 100, new Random(42));
		MealyCacheConsistencyTest<T, String> eqOracleConsistency = mqOracle.createCacheConsistencyTest();
//		CompleteExplorationEQOracle<T, Word<String>> eqOracleComplete = new CompleteExplorationEQOracle<>(mqOracle, 4);
//		WMethodEQOracle.MealyWMethodEQOracle<T, String> eqOracleW = new WMethodEQOracle.MealyWMethodEQOracle<>(5, mqOracle);
//		WpMethodEQOracle.MealyWpMethodEQOracle<T, String> eqOracleWp = new WpMethodEQOracle.MealyWpMethodEQOracle<>(5, mqOracle);
		EQOracleChain.MealyEQOracleChain<T, String> eqOracle = new EQOracleChain.MealyEQOracleChain<>(eqOracleRandomWalk, eqOracleConsistency);

		learner = new ExtensibleLStarMealyBuilder<T, String>()
				.withAlphabet(alphabet)
				.withOracle(mqOracle)
				.create();

//		learner = new RivestSchapireMealy<>(alphabet, mqOracle);

		MealyExperiment<T, String> experiment = new MealyExperiment<>(learner, eqOracle, alphabet);
		experiment.setLogModels(true);

		logger.info("Start running experiment with alphabet " + alphabet.toString());
		return experiment.run();
	}

	public Graph<?, ?> getGraph(Alphabet<T> alphabet) {
		return learner.getHypothesisModel().transitionGraphView(alphabet);
	}

	public ObservationTable<T, Word<String>> getObservationTable() {
		if (learner == null) {
			return null;
		}
		return learner.getObservationTable();
	}

	public Boolean testEquivalence(Checker<T> checker) {
		if (checker == null) {
			return null;
		}
		return checker.testEquivalence(learner.getHypothesisModel());
	}
}
