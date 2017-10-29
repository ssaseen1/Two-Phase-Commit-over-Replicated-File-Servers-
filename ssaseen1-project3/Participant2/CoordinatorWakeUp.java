import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class CoordinatorWakeUp implements Runnable{
	public static FileServiceCoordinator.Processor<FileServiceCoordinator.Iface> processor;
	public static ParticipantHandler handler;
	int portNo = 0;
	String hostName = null;
	static int coordinatorPort = 0;
	static String coordinatorHost = null;
	
	public CoordinatorWakeUp(int portNo, String hostName){
		super();
		this.portNo = portNo;
		this.hostName = hostName;
	}
           
	
		@Override
		public void run() {
			// TODO Auto-generated method stub

			
			
			// TODO Auto-generated method stub
			TTransport transport = null;
			transport = new TSocket(hostName, portNo);
			try 
			{
				transport.open();
			} 
			catch (TTransportException e) 
			{
				// TODO Auto-generated catch block
				System.err.println("ParticipantInvoker down");
			}
			 
			TProtocol protocol = new TBinaryProtocol(transport);
		    InvokeCoordinator.Client client = new InvokeCoordinator.Client(protocol);
		    
		    try {
				client.wakeUPCoordinator();
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}

