package nl.kerkers.max.mealy104;

import de.learnlib.logging.LearnLogger;
import de.learnlib.logging.PlottableLogRecord;
import net.automatalib.automata.transout.impl.compact.CompactMealy;
import net.automatalib.util.graphs.dot.GraphDOT;
import net.automatalib.words.Alphabet;
import nl.kerkers.max.mealy104.checker.Iec104Standard;
import nl.kerkers.max.mealy104.learner.Iec104Words;
import nl.kerkers.max.mealy104.teacher.implementations.StartStoppable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.util.Properties;
import java.util.logging.LogRecord;

import static nl.kerkers.max.mealy104.learner.Iec104Words.*;

/**
 * @author Max Kerkers
 */
public class Configuration {

	public static InetAddress IPADDRESS = InetAddress.getLoopbackAddress();
	public static int PORT = 2404;
	public static int COMMONADDRESS = 65535;
	public static long RESPONSETIME = 1000;
//	public static Alphabet<Iec104Words> ALPHABET = Iec104Words.toAlphabet();
	public static Alphabet<Iec104Words> ALPHABET = Iec104Words.toAlphabet(
			U_STARTDT,
			U_STOPDT,
			U_TESTFR,
			S,
			I_C_SC_NA_1
	);
	public static Iec104Standard CHECKER = new Iec104Standard(ALPHABET);
	public static boolean FLUSH = true;
	public static StartStoppable SIMULATOR = new StartStoppable.Default();

	private static final String DEFAULTFILE = "mealy104.properties";

	static void load() throws IOException {
		InputStream input = Configuration.class.getClassLoader().getResourceAsStream(DEFAULTFILE);

		if (input != null) {
			load(input);
		}
	}

	static void load(String configFile) throws IOException {
		InputStream input = Configuration.class.getClassLoader().getResourceAsStream(configFile);

		if (input == null) {
			throw new IOException("Configuration file (" + configFile + ") not found in classpath.");
		}

		load(input);
	}

	private static void load(InputStream input) throws IOException {
		Properties prop = new Properties();

		prop.load(input);

		if (prop.containsKey("ipaddress")) {
			IPADDRESS = InetAddress.getByName(prop.getProperty("ipaddress"));
		}

		if (prop.containsKey("port")) {
			PORT = Integer.parseInt(prop.getProperty("port"));
		}

		if (prop.containsKey("commonaddress")) {
			COMMONADDRESS = Integer.parseInt(prop.getProperty("commonaddress"));
		}

		if (prop.containsKey("responsetime")) {
			RESPONSETIME = Long.parseLong(prop.getProperty("responsetime"));
		}

		if (prop.containsKey("alphabet")) {
			String[] wordString = prop.getProperty("alphabet").replaceAll("\\s+","").toUpperCase().split(",");
			Iec104Words[] words = new Iec104Words[wordString.length];

			for (int i = 0; i < wordString.length; i++) {
				words[i] = Iec104Words.valueOf(wordString[i]);
			}

			ALPHABET = Iec104Words.toAlphabet(words);
		}

		if (prop.containsKey("flushbuffer")) {
			FLUSH = Boolean.parseBoolean(prop.getProperty("flushbuffer"));
		}

		if (prop.containsKey("simulator")) {
			String[] simulatorProperty = prop.getProperty("simulator").split("\\s+");

			if (simulatorProperty.length > 0) {
				try {
					Class<?> clazz = Class.forName(simulatorProperty[0]);

					if (simulatorProperty.length == 1) {
						SIMULATOR = (StartStoppable) clazz.newInstance();
					} else {
						Constructor<?> constructor;
						Integer[] simulatorPropertyArgs = new Integer[simulatorProperty.length - 1];

						for (int i = 1; i < simulatorProperty.length; i++) {
							simulatorPropertyArgs[i - 1]  = Integer.parseInt(simulatorProperty[i]);
						}

						constructor = clazz.getConstructor(simulatorPropertyArgs.getClass());
						SIMULATOR = (StartStoppable) constructor.newInstance((Object) simulatorPropertyArgs);
					}
				} catch (ReflectiveOperationException e) {
					throw new IOException("Unable to select simulator", e);
				}
			}
		}
	}

	static void configLearnLogger() {
		LearnLogger.setGlobalFilter((LogRecord record) -> {
			Logger learnLogger = LoggerFactory.getLogger(LearnLogger.class);
			if (record instanceof PlottableLogRecord) {
				Object data = ((PlottableLogRecord) record).getData();
				if (data instanceof CompactMealy<?, ?>) {
					StringBuilder output = new StringBuilder();
					try {
						output.append("Intermediate State Machine in GraphVIZ DOT format:");
						output.append(System.lineSeparator());
						GraphDOT.write(((CompactMealy<?, ?>) data).graphView(), output);

						learnLogger.info(output.toString());
					} catch (IOException e) {
						learnLogger.warn("Printing Graph in GraphVIZ DOT format failed", e);
					}
				}
			} else {
				learnLogger.info(record.getMessage());
			}
			return false;
		});
	}

}
