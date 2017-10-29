import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

public class CoordinatorInvoker {

	public static InvokeCoordinator.Processor<InvokeCoordinator.Iface> processor;
	public static CordinatorInvokeHandler handler;
	static int port_no = 0;
	static String hostname = null;
	static int  coordinatorPort_no= 0;
	
	public static void main(String[] args) 
	{
		
		handler = new CordinatorInvokeHandler();
		processor = new InvokeCoordinator.Processor<InvokeCoordinator.Iface>(handler);
		
		if(args.length == 0) 
		{
			System.err.println("Inside CoordinatorInvoker Enter port number");
		}
		else
		{

			hostname = args[0];
			port_no = Integer.parseInt(args[1]);
			coordinatorPort_no = Integer.parseInt(args[2]);
			

			TServerTransport transport=null;
			TServer server =null ;
			
			try 
			{
				transport = new TServerSocket(port_no);
				server = new TThreadPoolServer(new TThreadPoolServer.Args(transport).processor(processor));
				System.out.println("ParticipantInvoker has been started");
				server.serve();
			} 
			catch (TTransportException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
