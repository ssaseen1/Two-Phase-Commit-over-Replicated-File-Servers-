import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class TwoPhaseCommitInvoker 
{

	public static void main(String[] args) 
	{

		List<InvokerInfo> invokerList = new ArrayList<>();
		int testCaseNumber = 0;
		String coordinatorHostName = null;
		int coordinatorPortNumber = 0;
		int cordinatorInvokerPort = 0;
		
		if(args.length == 0)
		{
			System.err.println("Test case number is not provided : <test case number>");
			System.exit(0);
		}
		
		testCaseNumber =  Integer.parseInt(args[0]);
				
		try 
		{
			String str = null;
			FileReader file = new FileReader("invoker_info.txt");
			BufferedReader br = new BufferedReader(file);
			
			while((str = br.readLine())!= null) 
			{
				String values[] = str.split("\\s+");
				InvokerInfo iInfo = new InvokerInfo();
				iInfo.setName(values[0]);
				iInfo.setHostname(values[1]);
				iInfo.setPortno(Integer.parseInt(values[2]));
				invokerList.add(iInfo);
			}
			br.close();
			file.close();
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
		
		List<ClientInfo> clientInfoList = new ArrayList<ClientInfo>();
		try 
		{
			String str = null;
			FileReader fileReader = new FileReader("client_info.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			
			
			while((str = bufferedReader.readLine())!= null) 
			{
				String values[] = str.split("\\s+");
				
				ClientInfo clientInfo = new ClientInfo();
				clientInfo.setCoHostName(values[0]);
				clientInfo.setCoPortNumber(Integer.parseInt(values[1]));
				//clientInfo.setCoPortNumber(Integer.parseInt(values[2]));
				clientInfo.setOperationName(values[3]);
				clientInfo.setFileName(values[4]);
				clientInfoList.add(clientInfo);
				
				coordinatorHostName = values[0];
				coordinatorPortNumber =  Integer.parseInt(values[1]);
				cordinatorInvokerPort = Integer.parseInt(values[2]);
			}
			bufferedReader.close();
			fileReader.close();
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
		
		
		try 
		{
			String strPart = null;
			FileReader filePart = new FileReader("participant_info.txt");
			BufferedReader br = new BufferedReader(filePart);
			
			while((strPart = br.readLine())!= null) 
			{
				String values[] = strPart.split("\\s+");
				ParticipantInfo pInfo = new ParticipantInfo();
				pInfo.setName(values[0]);
				pInfo.setHostname(values[1]);
				pInfo.setPortno(Integer.parseInt(values[2]));
				CoordinatorHandler.list.add(pInfo);
			}
			br.close();
			filePart.close();
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
		
		for(int j=0;j<invokerList.size();j++)
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
				System.err.println("Participant : "+CoordinatorHandler.list.get(j).getHostname()+" "+CoordinatorHandler.list.get(j).getPortno()+" is down");
			}
			 
			TProtocol protocol = new TBinaryProtocol(transport);
		    FileServiceParticipant.Client client = new FileServiceParticipant.Client(protocol);
		    try 
		    {
				client.setChoice(testCaseNumber);
			} 
		    catch (TException e)
		    {
				// TODO Auto-generated catch block
				System.err.println("Participant: "+invokerList.get(j).getName()+" "+invokerList.get(j).getHostname()+" "+invokerList.get(j).getPortno()+" is down");
			}
		}
		

		TTransport transport1 = null;
		transport1 = new TSocket(coordinatorHostName, coordinatorPortNumber);
		try 
		{
			transport1.open();
		} 
		catch (TTransportException e)
		{
			// TODO Auto-generated catch block
			System.err.println("Coordinator: "+coordinatorHostName+" "+coordinatorPortNumber+" is down");
		}
		 
		TProtocol protocol1 = new TBinaryProtocol(transport1);
	    FileServiceCoordinator.Client coClient = new FileServiceCoordinator.Client(protocol1);
	    try 
	    {
	    	coClient.setChoice(testCaseNumber);
		} 
	    catch (TException e)
	    {
			// TODO Auto-generated catch block
			System.err.println("Coordinator: "+coordinatorHostName+" "+coordinatorPortNumber+" is down");
		}
	    /* Invoking Client methods based on client_info.txt*/
	    
	    if(testCaseNumber == 1)
	    {
	    	 Thread thread1 = new Thread();

	 	    thread1 = new Thread(new Client(clientInfoList.get(0).getOperationName(), clientInfoList.get(0).getCoPortNumber(), clientInfoList.get(0).getCoHostName(),clientInfoList.get(0).getFileName()));
	 	    thread1.start();

	 	    try 
	 	    {		

	 			thread1.join();

	 		} 
	 	    catch (InterruptedException e) 
	 	    {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
	 	    
	 	    Thread[] newPartThread = new Thread[invokerList.size()];
	 	    for(int i = 0; i < invokerList.size(); i++){
	 	    	
	 			newPartThread[i] = new Thread(new ParticipantWakeUp(invokerList.get(i).getPortno(), invokerList.get(i).getHostname()));

	 	    	newPartThread[i].start();
	 	    	try {
	 				Thread.sleep(1000);
	 			} catch (InterruptedException e) {
	 				// TODO Auto-generated catch block
	 				e.printStackTrace();
	 			}
	 	    }
	 	
	 	    try {
	 			Thread.sleep(500);
	 		} catch (InterruptedException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
	 	    
	 	    Thread thread2 = new Thread();
	 	    

	 	    thread2 = new Thread(new Client(clientInfoList.get(1).getOperationName(), clientInfoList.get(1).getCoPortNumber(), clientInfoList.get(1).getCoHostName(),clientInfoList.get(1).getFileName()));
	 	    thread2.start();
	    }
	    if(testCaseNumber == 2)
	    {
	 	    Thread[] newPartThreadCase2 = new Thread[clientInfoList.size()];

	    	for(int i = 0; i < clientInfoList.size(); i++)
	    	{
	 	    	
	    		newPartThreadCase2[i] = new Thread(new Client(clientInfoList.get(i).getOperationName(), clientInfoList.get(i).getCoPortNumber(), clientInfoList.get(i).getCoHostName(),clientInfoList.get(i).getFileName()));

	    		newPartThreadCase2[i].start();
	 	    	/*try {
	 				Thread.sleep(1000);
	 			} catch (InterruptedException e) {
	 				// TODO Auto-generated catch block
	 				e.printStackTrace();
	 			}*/
	 	    }
	 	    
	    }
	    if(testCaseNumber == 3)
	    {
	    	TTransport transport = null;
			transport = new TSocket(coordinatorHostName, coordinatorPortNumber);
			try 
			{
				transport.open();
			} 
			catch (TTransportException e)
			{
				// TODO Auto-generated catch block
				System.err.println("Coordinator : "+coordinatorHostName+" "+coordinatorHostName+" is down");
			}
			 
			TProtocol protocol = new TBinaryProtocol(transport);
		    FileServiceCoordinator.Client client = new FileServiceCoordinator.Client(protocol);
		    try 
		    {
				client.setChoice(testCaseNumber);
				
			} 
		    catch (TException e)
		    {
				// TODO Auto-generated catch block
				System.err.println("Coordinator: "+coordinatorHostName+" "+coordinatorHostName+" is down");
			}
	    	
		    
	    	for(int j=0;j<CoordinatorHandler.list.size();j++)
			{
				TTransport transportP = null;
				transportP = new TSocket(CoordinatorHandler.list.get(j).getHostname(), CoordinatorHandler.list.get(j).getPortno());
				try 
				{
					transportP.open();
				} 
				catch (TTransportException e)
				{
					// TODO Auto-generated catch block
					System.err.println("Participant : "+CoordinatorHandler.list.get(j).getHostname()+" "+CoordinatorHandler.list.get(j).getPortno()+" is down");
				}
				 
				TProtocol protocolP = new TBinaryProtocol(transportP);
			    FileServiceParticipant.Client clientP = new FileServiceParticipant.Client(protocolP);
			    try 
			    {
					clientP.setChoice(testCaseNumber);
				} 
			    catch (TException e)
			    {
					// TODO Auto-generated catch block
					System.err.println("Participant: "+invokerList.get(j).getName()+" "+invokerList.get(j).getHostname()+" "+invokerList.get(j).getPortno()+" is down");
				}
			}
	    	
	    	
	    	Thread thread1 = new Thread();

	 	    thread1 = new Thread(new Client(clientInfoList.get(0).getOperationName(), clientInfoList.get(0).getCoPortNumber(), clientInfoList.get(0).getCoHostName(),clientInfoList.get(0).getFileName()));
	 	    thread1.start();

	    	
	 	    
	 	    //Coordinator UP again
	 	    
	 	   Thread CoordThread = new Thread(new CoordinatorWakeUp(cordinatorInvokerPort, coordinatorHostName));

	 	  CoordThread.start();
	 	  
	 	  
	 	 try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   
	    try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    }
	    if(testCaseNumber == 41 || testCaseNumber == 42 || testCaseNumber == 43)
	    {
	    	Thread thread1 = new Thread();

	 	    thread1 = new Thread(new Client(clientInfoList.get(0).getOperationName(), clientInfoList.get(0).getCoPortNumber(), clientInfoList.get(0).getCoHostName(),clientInfoList.get(0).getFileName()));
	 	    thread1.start();

	 	    try 
	 	    {		

	 			thread1.join();

	 		} 
	 	    catch (InterruptedException e) 
	 	    {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
	 	    
	 	    
	 	    //Coordinator UP again
	 	    
	 	   Thread CoordThread = new Thread(new CoordinatorWakeUp(cordinatorInvokerPort, coordinatorHostName));

	 	  CoordThread.start();
	 	  
	 	  
	 	 try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   
	    try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    Thread thread2 = new Thread();
	    

	    thread2 = new Thread(new Client(clientInfoList.get(1).getOperationName(), clientInfoList.get(1).getCoPortNumber(), clientInfoList.get(1).getCoHostName(),clientInfoList.get(1).getFileName()));
	    thread2.start();
	 	    
	    }
	    if(testCaseNumber == 5)
	    {
	    	
	    	
	    		Random random = new Random();
	    		int index = random.nextInt(CoordinatorHandler.list.size());
	    		String randomHostName = CoordinatorHandler.list.get(index).getHostname();
	    		int randomPortNumber = CoordinatorHandler.list.get(index).getPortno();

		 		
		 		TTransport transport = null;
				transport = new TSocket(randomHostName, randomPortNumber);
				try 
				{
					transport.open();
				} 
				catch (TTransportException e)
				{
					// TODO Auto-generated catch block
					System.err.println("Participant : "+randomHostName+" "+randomPortNumber+" is down");
				}
				 
				TProtocol protocol = new TBinaryProtocol(transport);
			    FileServiceParticipant.Client client = new FileServiceParticipant.Client(protocol);
			    try 
			    {
					client.setFailureParticipant(1);
				} 
			    catch (TException e)
			    {
					// TODO Auto-generated catch block
					System.err.println("Participant : "+randomHostName+" "+randomPortNumber+" is down");
				}
		 		
		 		
		 		
		 		
		 		
		 		
		 		
			    Thread thread1 = new Thread();

		 	    thread1 = new Thread(new Client(clientInfoList.get(0).getOperationName(), clientInfoList.get(0).getCoPortNumber(), clientInfoList.get(0).getCoHostName(),clientInfoList.get(0).getFileName()));
		 	    thread1.start();

		 	    try 
		 	    {		

		 			thread1.join();

		 		} 
		 	    catch (InterruptedException e) 
		 	    {
		 			// TODO Auto-generated catch block
		 			e.printStackTrace();
		 		}
		 	    
		 	   String randomParticipantInvokerHostName = invokerList.get(index).getHostname();
	    		int randomParticipantInvokerPortNumber = invokerList.get(index).getPortno();
		 	    
		 	   
		 	    
		 	    
		 	    
		 	    Thread newPartThread = new Thread();		 	    	
		 		newPartThread = new Thread(new ParticipantWakeUp(randomParticipantInvokerPortNumber, randomParticipantInvokerHostName));

		 	    
		 	
		 	    try {
		 			Thread.sleep(500);
		 		} catch (InterruptedException e) {
		 			// TODO Auto-generated catch block
		 			e.printStackTrace();
		 		}
		 	    
		 	    Thread thread2 = new Thread();
		 	    

		 	    thread2 = new Thread(new Client(clientInfoList.get(1).getOperationName(), clientInfoList.get(1).getCoPortNumber(), clientInfoList.get(1).getCoHostName(),clientInfoList.get(1).getFileName()));
		 	    thread2.start();
	    
	    	
	    }
	        
	}
}
