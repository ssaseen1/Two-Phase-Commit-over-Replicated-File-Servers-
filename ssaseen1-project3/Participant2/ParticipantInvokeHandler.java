import org.apache.thrift.TException;

public class ParticipantInvokeHandler implements InvokeParticipant.Iface{

	@Override
	public void wakeUPParticipant() throws SystemException, TException {
		// TODO Auto-generated method stub
		String p = Integer.toString(ParticipantInvoker.participantPort_no);
		String[] args = {ParticipantInvoker.invokerName, p};
		System.out.println("Turning on participant Handler"+ParticipantInvoker.participantPort_no +ParticipantInvoker.invokerName);

		Participant.main(args);
	}

}
