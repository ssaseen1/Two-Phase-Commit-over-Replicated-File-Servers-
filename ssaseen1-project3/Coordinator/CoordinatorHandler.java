import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class CoordinatorHandler implements FileServiceCoordinator.Iface {

	public static volatile CopyOnWriteArrayList<ParticipantInfo> list = new CopyOnWriteArrayList<ParticipantInfo>();
	public static volatile AtomicInteger transaction_id = new AtomicInteger(0);
	public static volatile ConcurrentHashMap<Integer, CopyOnWriteArrayList<TwoPhaseCommitProtocol>> map = new ConcurrentHashMap<>();
	public static volatile int testCaseNumber = 0;

	@Override
	public String writeFile(RFile rFile) throws SystemException, TException {
		// TODO Auto-generated method stub
		int t_id = 0;
		int flag = 0;
		Integer trans_id = null;
		int choice = 0;
		String response = null;
		
		
		
		if(testCaseNumber == 1 || testCaseNumber == 2)
		{
			TwoPhaseCommitProtocol twoPhaseCommitProtocol = new TwoPhaseCommitProtocol();
			twoPhaseCommitProtocol.setMessage("vote_request");
			twoPhaseCommitProtocol.setMessageIsSet(true);
			
			synchronized(transaction_id)
			{
				t_id = transaction_id.incrementAndGet();

				TwoPhaseCommitProtocol tp = new TwoPhaseCommitProtocol();
				tp.setMessage("commit");
				CopyOnWriteArrayList<TwoPhaseCommitProtocol> l = new CopyOnWriteArrayList<>();
				l.add(tp);
				trans_id = new Integer(t_id);
				map.put(trans_id, l);
			}
			
			
			twoPhaseCommitProtocol.setTransactionId(t_id);
			twoPhaseCommitProtocol.setTransactionIdIsSet(true);

			
			LogClassCoordinator.insert(rFile, twoPhaseCommitProtocol, "write");

			Thread[] threads = new Thread[list.size()];
			for(int i=0;i<list.size();i++) 
			{

				threads[i]= new Thread(new VoteSender(list.get(i).getHostname(), list.get(i).getPortno(), rFile, twoPhaseCommitProtocol, "write"));
				threads[i].start();
			}
			for(int i=0;i<list.size();i++) 
			{
				try 
				{

					threads[i].join();

				} 
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					System.err.println(e.getMessage());
				}
			}
			
			CopyOnWriteArrayList<TwoPhaseCommitProtocol> responses = map.get(trans_id);
			for(int j=0;j<responses.size();j++) 
			{
				if(responses.get(j).getMessage().equals("abort")) {
					flag = 1;
					break;
				}
			}
			
			if(flag == 0) 
			{

				twoPhaseCommitProtocol.setMessage("global_commit");
				response = "File Write Successful";
			}
			else 
			{

				twoPhaseCommitProtocol.setMessage("global_abort");
				response = "File Write Unsuccessful";
			}

			LogClassCoordinator.update(twoPhaseCommitProtocol);

			for(int i=0;i<list.size();i++) 
			{
				threads[i]= new Thread(new VoteSender(list.get(i).getHostname(), list.get(i).getPortno(), rFile, twoPhaseCommitProtocol, "write"));
				threads[i].start();
				try 
				{

					threads[i].join();

				} 
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					System.err.println(e.getMessage());
				}
			}
			
			
		}
		
		else if (testCaseNumber == 3){
			TwoPhaseCommitProtocol twoPhaseCommitProtocol = new TwoPhaseCommitProtocol();
			twoPhaseCommitProtocol.setMessage("vote_request");
			twoPhaseCommitProtocol.setMessageIsSet(true);
			
			
			synchronized(transaction_id)
			{
				t_id = transaction_id.incrementAndGet();

				TwoPhaseCommitProtocol tp = new TwoPhaseCommitProtocol();
				tp.setMessage("commit");
				CopyOnWriteArrayList<TwoPhaseCommitProtocol> l = new CopyOnWriteArrayList<>();
				l.add(tp);
				trans_id = new Integer(t_id);
				map.put(trans_id, l);
			}
			
			
			twoPhaseCommitProtocol.setTransactionId(t_id);
			twoPhaseCommitProtocol.setTransactionIdIsSet(true);

			
			LogClassCoordinator.insert(rFile, twoPhaseCommitProtocol, "write");

			System.exit(0);
		}
		
		else if(testCaseNumber == 41 || testCaseNumber == 42 || testCaseNumber == 43)
		{
			TwoPhaseCommitProtocol twoPhaseCommitProtocol = new TwoPhaseCommitProtocol();
			twoPhaseCommitProtocol.setMessage("vote_request");
			twoPhaseCommitProtocol.setMessageIsSet(true);
			
			synchronized(transaction_id) {
				t_id = transaction_id.incrementAndGet();
				TwoPhaseCommitProtocol tp = new TwoPhaseCommitProtocol();
				tp.setMessage("commit");
				CopyOnWriteArrayList<TwoPhaseCommitProtocol> l = new CopyOnWriteArrayList<>();
				l.add(tp);
				trans_id = new Integer(t_id);
				map.put(trans_id, l);
			}
			
			
			twoPhaseCommitProtocol.setTransactionId(t_id);
			twoPhaseCommitProtocol.setTransactionIdIsSet(true);
			
			LogClassCoordinator.insert(rFile, twoPhaseCommitProtocol, "write");
			
			Thread[] threads = new Thread[list.size()];
			for(int i=0;i<list.size();i++) {
				threads[i]= new Thread(new VoteSender(list.get(i).getHostname(), list.get(i).getPortno(), rFile, twoPhaseCommitProtocol, "write"));
				threads[i].start();
			}
			for(int i=0;i<list.size();i++) {
				try {
					threads[i].join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.err.println(e.getMessage());
				}
			}
			
			System.exit(0);
		

		}
		else if(testCaseNumber == 5)
		{

			TwoPhaseCommitProtocol twoPhaseCommitProtocol = new TwoPhaseCommitProtocol();
			twoPhaseCommitProtocol.setMessage("vote_request");
			twoPhaseCommitProtocol.setMessageIsSet(true);
			
			synchronized(transaction_id)
			{
				t_id = transaction_id.incrementAndGet();

				TwoPhaseCommitProtocol tp = new TwoPhaseCommitProtocol();
				tp.setMessage("commit");
				CopyOnWriteArrayList<TwoPhaseCommitProtocol> l = new CopyOnWriteArrayList<>();
				l.add(tp);
				trans_id = new Integer(t_id);
				map.put(trans_id, l);
			}
			
			
			twoPhaseCommitProtocol.setTransactionId(t_id);
			twoPhaseCommitProtocol.setTransactionIdIsSet(true);

			
			LogClassCoordinator.insert(rFile, twoPhaseCommitProtocol, "write");

			Thread[] threads = new Thread[list.size()];
			for(int i=0;i<list.size();i++) 
			{

				threads[i]= new Thread(new VoteSender(list.get(i).getHostname(), list.get(i).getPortno(), rFile, twoPhaseCommitProtocol, "write"));
				threads[i].start();
			}
			for(int i=0;i<list.size();i++) 
			{
				try 
				{

					threads[i].join();

				} 
				catch (InterruptedException e) 
				{
					// TODO Auto-generated catch block
					System.err.println(e.getMessage());
				}
			}
			
			CopyOnWriteArrayList<TwoPhaseCommitProtocol> responses = map.get(trans_id);
			for(int j=0;j<responses.size();j++) 
			{
				if(responses.get(j).getMessage().equals("abort")) {
					flag = 1;
					break;
				}
			}
			
			if(flag == 0) 
			{

				twoPhaseCommitProtocol.setMessage("global_commit");
				response = "File Write Successful";
			}
			else 
			{

				twoPhaseCommitProtocol.setMessage("global_abort");
				response = "File Write Unsuccessful";
			}

			LogClassCoordinator.update(twoPhaseCommitProtocol);

			for(int i=0;i<list.size();i++) 
			{
				threads[i]= new Thread(new VoteSender(list.get(i).getHostname(), list.get(i).getPortno(), rFile, twoPhaseCommitProtocol, "write"));
				threads[i].start();
				try 
				{

					threads[i].join();

				} 
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					System.err.println(e.getMessage());
				}
			}
			
			
		
		}
		
		
		
		return response;
	}

	@Override
	public RFile readFile(String filename) throws SystemException {
		// TODO Auto-generated method stub
		Random random = new Random();
		int index = random.nextInt(list.size());
		RFile rFile = new RFile();
		
		TTransport transport = null;
		transport = new TSocket(list.get(index).getHostname(), list.get(index).getPortno());
		
		try {
			transport.open();
			TProtocol protocol = new TBinaryProtocol(transport);
		    FileServiceParticipant.Client client = new FileServiceParticipant.Client(protocol);
		    
		    rFile = client.readFile(filename);
		    System.out.println(rFile.getContent()+" from "+list.get(index).getHostname());
		    return rFile;
		} catch (TTransportException e) {
			// TODO Auto-generated catch block
			System.err.println("Participant "+list.get(index).getName()+" "+list.get(index).getHostname()+" "+list.get(index).getPortno()+" is down");
			rFile = readFile(filename);
		}catch (SystemException e){ 
			throw e;
		}catch (TException e) {
			System.err.println("Participant "+list.get(index).getName()+" "+list.get(index).getHostname()+" "+list.get(index).getPortno()+" is down");
			rFile = readFile(filename);
		}
		return rFile;
	}

	@Override
	public String lastAction(TwoPhaseCommitProtocol twoPhaseCommmit) throws SystemException, TException {
		// TODO Auto-generated method stub
		String last_action = null;
		last_action = LogClassCoordinator.lastAction(twoPhaseCommmit);
		return last_action;
	}

	@Override
	public void setChoice(int choice) throws SystemException, TException 
	{
		testCaseNumber = choice;
		// TODO Auto-generated method stub
		
	}
}