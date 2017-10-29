import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

public class Participant {

	public static FileServiceParticipant.Processor<FileServiceParticipant.Iface> processor;
	public static ParticipantHandler handler;
	static int port_no = 0;
	static String name = null;
	static int coordinator_port = 0;
	static String coordinator_host = null;
	
	
	public static void main(String[] args) 
	{
		
		handler = new ParticipantHandler();
		processor = new FileServiceParticipant.Processor<FileServiceParticipant.Iface>(handler);
		
		try 
		{
			String str = null;
			FileReader file = new FileReader("coordinator_info.txt");
			BufferedReader br = new BufferedReader(file);
			
			while((str = br.readLine())!= null) 
			{
				String values[] = str.split("\\s+");
				coordinator_host = values[0];
				coordinator_port = Integer.parseInt(values[1]);
			}
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
		

		LogClassParticipant.create();

		File file = new File("Data");
		if(!file.exists()) 
		{

			file.mkdir();
		}
		
		List<LogObject> list = LogClassParticipant.incompleteStatus();
		for(int i=0;i<list.size();i++)
		{
			TTransport transport = null;
			transport = new TSocket(coordinator_host, coordinator_port);
			try 
			{
				transport.open();
			} 
			catch (TTransportException e) 
			{
				// TODO Auto-generated catch block
				System.err.println("Coordinator down");
			}
			 
			TProtocol protocol = new TBinaryProtocol(transport);
		    FileServiceCoordinator.Client client = new FileServiceCoordinator.Client(protocol);
		    TwoPhaseCommitProtocol tpc = new TwoPhaseCommitProtocol();

		    tpc.setTransactionId(list.get(i).getTransactionId());
		    try 
		    {
				String action = client.lastAction(tpc);

				tpc.setMessage(action);
				LogClassParticipant.update(tpc);
				if(action.equals("global_commit") && list.get(i).getOperation().equals("write")) 
				{

					PrintWriter print;
					try 
					{
						print = new PrintWriter("Data"+"/"+list.get(i).getFilename());
						print.write(list.get(i).getContent());
						print.flush();
						print.close();
					} 
					catch (FileNotFoundException e) 
					{
						// TODO Auto-generated catch block
						System.err.println(e.getMessage());
					}
				}
			} 
		    catch (TException e)
		    {
				// TODO Auto-generated catch block
				System.err.println("Coordinator down");
			}
		}
		
		
		//Load the Hashmap
		File folder = new File("Data");
		if(folder.listFiles() != null) 
		{
			for(File file_load : folder.listFiles())
			{
				
				try 
				{

					BufferedReader br = new BufferedReader(new FileReader(file_load.getAbsolutePath()));
					StringBuilder sb = new StringBuilder();
					String line = br.readLine();
					while(line!=null)
					{
						sb.append(line);
						sb.append("\n");
						line = br.readLine();
					}
					String contents = sb.toString();
					ParticipantHandler.file_contents.put(file_load.getName(), contents);

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
			}
		}
		
		
		
		if(args.length == 0) 
		{
			System.err.println("Enter port number");
		}
		else
		{

			name = args[0];
			port_no = Integer.parseInt(args[1]);
			

			TServerTransport transport=null;
			TServer server =null ;
			
			try 
			{
				transport = new TServerSocket(port_no);
				server = new TThreadPoolServer(new TThreadPoolServer.Args(transport).processor(processor));
				System.out.println("Participant has been started");
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
