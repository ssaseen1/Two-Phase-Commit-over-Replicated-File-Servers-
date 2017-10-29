import org.apache.thrift.TException;

public class CordinatorInvokeHandler implements InvokeCoordinator.Iface{

	@Override
	public void wakeUPCoordinator() throws SystemException, TException {
		// TODO Auto-generated method stub
		String p = Integer.toString(CoordinatorInvoker.coordinatorPort_no);
		String[] args = {CoordinatorInvoker.hostname, p};
		Coordinator.main(args);
		System.out.println("Turning on Coordinator");
	}

}