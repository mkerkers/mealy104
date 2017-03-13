package nl.kerkers.max.mealy104;

import de.learnlib.algorithms.features.observationtable.ObservationTable;
import de.learnlib.algorithms.features.observationtable.writer.ObservationTableASCIIWriter;
import net.automatalib.graphs.Graph;
import net.automatalib.util.graphs.dot.GraphDOT;
import net.automatalib.visualization.Visualization;
import net.automatalib.words.Alphabet;
import net.automatalib.words.Word;
import nl.kerkers.max.mealy104.checker.Checker;
import nl.kerkers.max.mealy104.learner.Iec104Learner;
import nl.kerkers.max.mealy104.learner.Iec104Words;
import nl.kerkers.max.mealy104.learner.Learner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static java.lang.System.exit;

/**
 * @author Max Kerkers
 */
public class Application<T> {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	static {
		Configuration.configLearnLogger();
	}

	public void execute(Learner<T> learner, Alphabet<T> alphabet) throws Exception {
		execute(learner, null, alphabet);
	}

	public void execute(Learner<T> learner, Checker<T> checker, Alphabet<T> alphabet) throws Exception {
		learner.learn(alphabet);

		Graph<?, ?> graph = learner.getGraph(alphabet);
		ObservationTable<T, Word<String>> observationTable = learner.getObservationTable();
		Boolean equivalentToStandard = learner.testEquivalence(checker);

		logGraph(graph);
		logObservationTable(observationTable);
		logEquivalence(equivalentToStandard);

//		OTUtils.displayHTMLInBrowser(observationTable);
		Visualization.visualizeGraph(graph, true);
	}

	public static void main(String[] args) {
		try {
			if (args.length == 0) {
				Configuration.load();
			} else {
				Configuration.load(args[0]);
			}
		} catch (IOException e) {
			logger.error("Unable to load configuration correctly from file", e);
			exit(2);
		}

		try {
			new Application<Iec104Words>().execute(new Iec104Learner(), Configuration.CHECKER, Configuration.ALPHABET);
		} catch (RuntimeException e) {
			logger.error("Application crashed", e);
			exit(-1);
		} catch (Exception e) {
			logger.error("Application crashed", e);
			exit(1);
		}
		exit(0);
	}

	private void logGraph(Graph<?, ?> graph) {
		StringBuilder output = new StringBuilder();

		try {
			output.append("State Machine in GraphVIZ DOT format:");
			output.append(System.lineSeparator());
			GraphDOT.write(graph, output);

			logger.info(output.toString());
		} catch (IOException e) {
			logger.warn("Printing Graph in GraphVIZ DOT format failed", e);
		}
	}

	private void logObservationTable(ObservationTable<T, Word<String>> observationTable) {
		StringBuilder output = new StringBuilder();
		ObservationTableASCIIWriter<T, Word<String>> observationTableASCIIWriter = new ObservationTableASCIIWriter<>();

		output.append("Observation Table:");
		output.append(System.lineSeparator());
		observationTableASCIIWriter.write(observationTable, output);

		logger.info(output.toString());
	}

	private void logEquivalence(Boolean equivalentToStandard) {
		if (equivalentToStandard != null) {
			logger.info("Equivalent to standard: " + equivalentToStandard);
		}
	}
}
