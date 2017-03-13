package nl.kerkers.max.mealy104.teacher.messages;

import org.openmuc.j60870.APdu;
import org.openmuc.j60870.IeFixedTestBitPattern;
import org.openmuc.j60870.IeTime56;
import org.openmuc.j60870.TypeId;

import java.util.Date;
import java.util.List;

/**
 * @author Max Kerkers
 */
public class MessageITimedTest extends MessageI {

	public MessageITimedTest() {
		super(TypeId.C_TS_TA_1);
	}

	@Override
	public List<APdu> execute() throws Exception {
		//return send(new IeTestSequenceCounter(5), new IeTime56(new Date().getTime()));
		return send(new IeFixedTestBitPattern(), new IeTime56(new Date().getTime()));
	}

}
