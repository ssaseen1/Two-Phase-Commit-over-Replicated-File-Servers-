import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.thrift.TException;

public class ParticipantHandler implements FileServiceParticipant.Iface {
	
	public static volatile ConcurrentHashMap<String, String> file_contents = new ConcurrentHashMap<>();
	public static volatile int testCaseNumber = 0;
	public static volatile int failureFlag = 0;

	@Override
	public TwoPhaseCommitProtocol writeFile(RFile rFile, TwoPhaseCommitProtocol twoPhaseCommmit) throws SystemException, TException {
		// TODO Auto-generated method stub
		int choice = 0;
		String status = null;

		if(twoPhaseCommmit.getMessage().equals("vote_request")) 
		{

			if(testCaseNumber != 2)
			{
				synchronized(rFile.getMeta().getFilename())
				{
					status =LogClassParticipant.currentStatus(rFile);
					if(status == null) 
					{

						LogClassParticipant.insert(rFile, twoPhaseCommmit, "write");

					}
				}
				
				if(status != null) 
				{

					if(status.equals("commit") || status.equals("abort") || status.equals("vote_request"))
					{

						twoPhaseCommmit.setMessage("abort");
						LogClassParticipant.insert(rFile, twoPhaseCommmit, "write");
					}
				}
				
				else 
				{

					

					if(testCaseNumber == 41) {
						twoPhaseCommmit.setMessage("commit");
						LogClassParticipant.update(twoPhaseCommmit);
					}
					
					else if(testCaseNumber == 42) {
						twoPhaseCommmit.setMessage("abort");
						LogClassParticipant.update(twoPhaseCommmit);
					}
					
					else if(testCaseNumber == 43)
					{
						twoPhaseCommmit.setMessage("abort");
						LogClassParticipant.update(twoPhaseCommmit);
					
						
					}
					
					if(testCaseNumber == 1) 
					{

						twoPhaseCommmit.setMessage("commit");

						LogClassParticipant.update(twoPhaseCommmit);


						return twoPhaseCommmit;
					}
					if(testCaseNumber == 5)
					{

						if(ParticipantHandler.failureFlag == 1)
						{
							twoPhaseCommmit.setMessage("commit");
							LogClassParticipant.update(twoPhaseCommmit);
							twoPhaseCommmit.setMessage("commit fail");
							Thread thread = new Thread(new Runnable() 
							{
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										System.err.println(e.getMessage());
									}
									System.exit(0);
								}
							});
							thread.start();
						}
						else
						{
							twoPhaseCommmit.setMessage("commit");
							LogClassParticipant.update(twoPhaseCommmit);
						}

						return twoPhaseCommmit;
					}
				}
			}
			else
			{

				synchronized(rFile.getMeta().getFilename())
				{
					status =LogClassParticipant.currentStatus(rFile);
					if(status == null) 
					{

						LogClassParticipant.insert(rFile, twoPhaseCommmit, "write");
						
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if(status != null) 
				{

					if(status.equals("commit") || status.equals("abort") || status.equals("vote_request"))
					{

						twoPhaseCommmit.setMessage("abort");
						LogClassParticipant.insert(rFile, twoPhaseCommmit, "write");
					}
				}
				
				else 
				{

					
						twoPhaseCommmit.setMessage("commit");
						LogClassParticipant.update(twoPhaseCommmit);
				
				}
			}
		}
		else if(twoPhaseCommmit.getMessage().equals("global_commit") && testCaseNumber == 1)
		{

			LogClassParticipant.update(twoPhaseCommmit);

			String content = LogClassParticipant.getContent(twoPhaseCommmit.getTransactionId());

			file_contents.put(rFile.getMeta().getFilename(), content);
			PrintWriter print;
			try 
			{

				print = new PrintWriter("Data"+"/"+rFile.getMeta().getFilename());
				print.write(content);
				print.flush();
				print.close();
			} 
			catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				System.err.println(e.getMessage());
			}
			System.exit(0);
		
		}
		else if(twoPhaseCommmit.getMessage().equals("global_commit"))
		{
			System.out.println("Insire Paricipant global commit message: ");

			LogClassParticipant.update(twoPhaseCommmit);
			System.out.println("Insire Paricipant global commit after update: ");

			String content = LogClassParticipant.getContent(twoPhaseCommmit.getTransactionId());
			System.out.println("Insire Paricipant global commit file and content : "+rFile.getMeta().getFilename()+content);

			file_contents.put(rFile.getMeta().getFilename(), content);
			PrintWriter print;
			try 
			{
				System.out.println("Insire Paricipant global commit insie data print  ");

				print = new PrintWriter("Data"+"/"+rFile.getMeta().getFilename());
				print.write(content);
				print.flush();
				print.close();
			} 
			catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				System.err.println(e.getMessage());
			}
			
			
		}
		
		else if(twoPhaseCommmit.getMessage().equals("global_abort"))
		{

			LogClassParticipant.update(twoPhaseCommmit);
		}
		
		return twoPhaseCommmit;
	}

	@Override
	public RFile readFile(String filename) throws SystemException, TException {
		// TODO Auto-generated method stub
		
		String content = null;
		if(file_contents.containsKey(filename)) {
			System.out.println(file_contents.get(filename));
			content = file_contents.get(filename);
		}
		else {
			SystemException exception = new SystemException();
			exception.setMessage("File does not exist");
			throw exception;
		}
		RFileMetadata rm = new RFileMetadata();
		rm.setFilename(filename);
		RFile rFile = new RFile();
		rFile.setContent(content);
		rFile.setMeta(rm);
		return rFile;
	}

	@Override
	public String lastDecision(int transaction_id) throws SystemException, TException {
		// TODO Auto-generated method stub
		String action = null;
		action = LogClassParticipant.lastAction(transaction_id);
		return action;
	}

	@Override
	public void setChoice(int choice) throws SystemException, TException {
		// TODO Auto-generated method stub
		testCaseNumber = choice;
		if(testCaseNumber == 3) 
			System.exit(1);
	}

	@Override
	public void setFailureParticipant(int failureValue) throws SystemException, TException {
		// TODO Auto-generated method stub
		failureFlag = failureValue;
	}
}
