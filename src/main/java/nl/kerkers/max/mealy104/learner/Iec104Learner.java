package nl.kerkers.max.mealy104.learner;

import de.learnlib.mapper.ContextExecutableInputSUL;
import de.learnlib.mapper.Mappers;
import de.learnlib.mapper.api.ContextExecutableInput;
import nl.kerkers.max.mealy104.mapper.Iec104Mapper;
import nl.kerkers.max.mealy104.teacher.Iec104ContextHandler;
import nl.kerkers.max.mealy104.teacher.Iec104Teacher;
import org.openmuc.j60870.APdu;

import java.util.List;

/**
 * @author Max Kerkers
 */
public class Iec104Learner extends Learner<Iec104Words> {

	public Iec104Learner() {
		final ContextExecutableInputSUL<ContextExecutableInput<List<APdu>, Iec104Teacher>, List<APdu>, Iec104Teacher> ceiSUL;
		ceiSUL = new ContextExecutableInputSUL<>(new Iec104ContextHandler());
		this.sul = Mappers.apply(new Iec104Mapper(), ceiSUL);
	}

}
