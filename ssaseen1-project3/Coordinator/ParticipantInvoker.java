import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

public class ParticipantInvoker {

	public static InvokeParticipant.Processor<InvokeParticipant.Iface> processor;
	public static ParticipantInvokeHandler handler;
	static int  invokerPort_no= 0;
	static String invokerName = null;
	static String  participantPort_no= null;
	
	public static void main(String[] args) 
	{
		
		handler = new ParticipantInvokeHandler();
		processor = new InvokeParticipant.Processor<InvokeParticipant.Iface>(handler);
		
		if(args.length == 0) 
		{
			System.err.println("Inside ParticipantInvoker Enter port number");
		}
		else
		{

			invokerName = args[0];
			invokerPort_no = Integer.parseInt(args[1]);
			
			participantPort_no = args[2];
			

			TServerTransport transport=null;
			TServer server =null ;
			
			try 
			{
				transport = new TServerSocket(invokerPort_no);
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
