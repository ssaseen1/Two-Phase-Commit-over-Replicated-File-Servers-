import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class VoteSender implements Runnable {

	public String hostname = null;
	int port_no = 0;
	RFile rFile = null;
	TwoPhaseCommitProtocol twoPhaseCommit = null;
	String file_operation = null;
	
	
	public VoteSender(String host,int port, RFile rf, TwoPhaseCommitProtocol tpc, String operation) {
		// TODO Auto-generated constructor stub
		this.hostname = host;
		this.port_no = port;
		this.rFile = rf;
		this.twoPhaseCommit = tpc;
		this.file_operation = operation;
	}
	
	@Override
	public void run() 
	{

		// TODO Auto-generated method stub
		TTransport transport = null;
		transport = new TSocket(hostname, port_no);
		try 
		{
			transport.open();
		} 
		catch (TTransportException e) 
		{
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}
		 
		TProtocol protocol = new TBinaryProtocol(transport);
	    FileServiceParticipant.Client client = new FileServiceParticipant.Client(protocol);
	    
	    if(file_operation.equals("write")) 
	    {

	    	TwoPhaseCommitProtocol tpc = null;
	    	CopyOnWriteArrayList<TwoPhaseCommitProtocol> list = new CopyOnWriteArrayList<>();
	    	try 
	    	{

				tpc = client.writeFile(rFile, twoPhaseCommit);

				if(tpc.getMessage().equals("commit fail"))
				{

					Thread.sleep(1500);
					tpc.setMessage("commit");

				}
					synchronized(CoordinatorHandler.map.get(tpc.getTransactionId())) 
					{
						list.addAll(CoordinatorHandler.map.get(tpc.getTransactionId()));
						list.add(tpc);
						Integer i = new Integer(tpc.getTransactionId());
						CoordinatorHandler.map.put(i, list);
					}
					
			} 
	    	catch (TException e) 
	    	{

				// TODO Auto-generated catch block
				System.err.println(e.getMessage());
				
				if(tpc == null && twoPhaseCommit.getMessage().equals("vote_request")) 
				{
				
					synchronized(CoordinatorHandler.map.get(twoPhaseCommit.getTransactionId())) {
						list.addAll(CoordinatorHandler.map.get(twoPhaseCommit.getTransactionId()));
						tpc = new TwoPhaseCommitProtocol();
						tpc.setTransactionId(twoPhaseCommit.getTransactionId());
						tpc.setMessage("abort");
						list.add(tpc);
						Integer i = new Integer(tpc.getTransactionId());
						CoordinatorHandler.map.put(i, list);
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.err.println(e.getMessage());
			}
	    }
	    
	}

}
