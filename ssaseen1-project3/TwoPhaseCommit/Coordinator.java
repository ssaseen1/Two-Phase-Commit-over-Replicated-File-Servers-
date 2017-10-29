import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.net.ssl.HostnameVerifier;

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

public class Coordinator {

	public static FileServiceCoordinator.Processor<FileServiceCoordinator.Iface> processor;
	public static CoordinatorHandler handler;
	static String hostname = null;
	static int port_no = 0;
	
	
	public static void main(String[] args) 
	{
		
		handler = new CoordinatorHandler();
		processor = new FileServiceCoordinator.Processor<FileServiceCoordinator.Iface>(handler);
		
		LogClassCoordinator.create();
		
		CoordinatorHandler.transaction_id = LogClassCoordinator.maxTransaction_id();
		
		try 
		{
			String str = null;
			FileReader file = new FileReader("participant_info.txt");
			BufferedReader br = new BufferedReader(file);
			
			while((str = br.readLine())!= null) 
			{
				String values[] = str.split("\\s+");
				ParticipantInfo pinfo = new ParticipantInfo();
				pinfo.setName(values[0]);
				pinfo.setHostname(values[1]);
				pinfo.setPortno(Integer.parseInt(values[2]));
				CoordinatorHandler.list.add(pinfo);
			}
			br.close();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}
		
		List<LogObject> list = LogClassCoordinator.select();
		for(int i=0;i<list.size();i++) 
		{
			ArrayList<String> responses = new ArrayList<>();
			for(int j=0;j<CoordinatorHandler.list.size();j++) 
			{
				TTransport transport = null;
				transport = new TSocket(CoordinatorHandler.list.get(j).getHostname(), CoordinatorHandler.list.get(j).getPortno());
				try 
				{
					transport.open();
				}
				catch (TTransportException e)
				{
					// TODO Auto-generated catch block
					System.err.println("Participant: "+CoordinatorHandler.list.get(j).getName()+" "+CoordinatorHandler.list.get(j).getHostname()+" "+CoordinatorHandler.list.get(j).getPortno()+" is down");
				}
				 
				TProtocol protocol = new TBinaryProtocol(transport);
			    FileServiceParticipant.Client client = new FileServiceParticipant.Client(protocol);
			    try 
			    {
					String action = client.lastDecision(list.get(i).getTransactionId());

					responses.add(action);
				} 
			    catch (TException e) 
			    {
					// TODO Auto-generated catch block
					System.err.println("Participant: "+CoordinatorHandler.list.get(j).getName()+" "+CoordinatorHandler.list.get(j).getHostname()+" "+CoordinatorHandler.list.get(j).getPortno()+" is down");
				}
			}
			if(responses.size() == CoordinatorHandler.list.size())
			{
				int flag = 0;
				TwoPhaseCommitProtocol two = new TwoPhaseCommitProtocol();
				RFileMetadata rm = new RFileMetadata();
				RFile rFile = new RFile();
				two.setTransactionId(list.get(i).getTransactionId());
				rm.setFilename(list.get(i).getFilename());
				rFile.setMeta(rm);
				
				for(int k=0;k<responses.size();k++) 
				{
					if(responses.get(k).equals("abort"))
					{
						flag = 1;
					}
				}
				if(flag == 0) 
				{
					two.setMessage("global_commit");
				}
				else 
				{
					two.setMessage("global_abort");
				}
				for(int j=0;j<CoordinatorHandler.list.size();j++) 
				{
					TTransport transport = null;
					transport = new TSocket(CoordinatorHandler.list.get(j).getHostname(), CoordinatorHandler.list.get(j).getPortno());
					try 
					{
						transport.open();
					} 
					catch (TTransportException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
					TProtocol protocol = new TBinaryProtocol(transport);
				    FileServiceParticipant.Client client = new FileServiceParticipant.Client(protocol);
				    try 
				    {
				    	LogClassCoordinator.update(two);
				    	if(list.get(i).operation.equals("write")) 
				    	{				    	

				    		TwoPhaseCommitProtocol tc = client.writeFile(rFile, two);

				    	}
								
					} 
				    catch (TException e) 
				    {
						// TODO Auto-generated catch block
						System.err.println(e.getMessage());
					}
				}
			}
			if(responses.size() < CoordinatorHandler.list.size())
			{
				
			}
		}
		
		

		if(args.length == 0) 
		{
			System.err.println("Enter hostname and port number");
		}
		else 
		{

			try
			{
				hostname = args[0];
				port_no = Integer.parseInt(args[1]);

				
			}
			catch(NumberFormatException n)
			{
				n.printStackTrace();
			}
			
			TServerTransport transport=null;
			TServer server =null ;
			
			try 
			{
				transport = new TServerSocket(port_no);
				server = new TThreadPoolServer(new TThreadPoolServer.Args(transport).processor(processor));
				
				System.out.println("Coordinator has been started");
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
