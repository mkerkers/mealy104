package nl.kerkers.max.mealy104.mapper;

import de.learnlib.api.SULException;
import de.learnlib.mapper.api.ContextExecutableInput;
import de.learnlib.mapper.api.Mapper;
import nl.kerkers.max.mealy104.learner.Iec104Words;
import nl.kerkers.max.mealy104.teacher.Iec104Teacher;
import org.openmuc.j60870.APdu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.StringJoiner;

import static de.learnlib.mapper.api.Mapper.MappedException.ignoreAndContinue;

/**
 * @author Max Kerkers
 */
public class Iec104Mapper implements Mapper<Iec104Words, String, ContextExecutableInput<List<APdu>, Iec104Teacher>, List<APdu>> {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void pre() {
		logger.trace("Iteration started");
	}

	@Override
	public void post() {
		logger.trace("Iteration ended");
	}

	@Override
	public ContextExecutableInput<List<APdu>, Iec104Teacher> mapInput(Iec104Words abstractInput) {
		logger.trace("Abstract input to mapper: " + abstractInput);
		return abstractInput.getMapping();
	}

	@Override
	public String mapOutput(List<APdu> concreteOutput) {
		StringJoiner abstractOutput = new StringJoiner("|").setEmptyValue("-");

		for (APdu aPdu : concreteOutput) {
			if (aPdu.getApciType() == APdu.APCI_TYPE.I_FORMAT) {
//				abstractOutput.add("I[" + aPdu.getASdu().getTypeIdentification().toString().replace("_1", "") + "]");
				abstractOutput.add("I");
			} else if (aPdu.getApciType() == APdu.APCI_TYPE.S_FORMAT) {
//				abstractOutput.add("S(" + aPdu.getReceiveSeqNumber() + ")");
				abstractOutput.add("S");
			} else {
				abstractOutput.add("U[" + aPdu.getApciType().toString().replace("_CON", "") + "]");
			}
		}

		logger.trace("Abstract output from mapper: " + abstractOutput.toString());

		return abstractOutput.toString();
	}

	@Override
	public MappedException<? extends String> mapWrappedException(SULException e) throws SULException {
		logger.trace("Abstract output from mapper: ERROR - (" + e.getMessage() + ")");
		return ignoreAndContinue("ERROR");
	}

	@Override
	public MappedException<? extends String> mapUnwrappedException(RuntimeException e) throws RuntimeException {
		throw e;
	}
}
