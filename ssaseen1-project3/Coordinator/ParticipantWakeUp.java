import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class ParticipantWakeUp implements Runnable{
	public static FileServiceParticipant.Processor<FileServiceParticipant.Iface> processor;
	public static ParticipantHandler handler;
	int portNo = 0;
	String hostName = null;
	static int coordinatorPort = 0;
	static String coordinatorHost = null;
	
	public ParticipantWakeUp(int portNo, String hostName){
		super();
		this.portNo = portNo;
		this.hostName = hostName;
	}

	@Override
	public void run() 
	{
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
	    InvokeParticipant.Client client = new InvokeParticipant.Client(protocol);
	    
	    try {
			client.wakeUPParticipant();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		

			
		}
}
