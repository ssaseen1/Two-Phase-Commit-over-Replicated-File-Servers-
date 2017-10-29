import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class Client implements Runnable 
{
	String operationName = null;
	int coordinatorPortNumber = 0;
	String coordinatorHostName = null;
	String fileName = null;
	
	
	public Client(String operationName, int coordinatorPortNumber, String coordinatorHostName,String fileName) 
	{
		super();
		this.operationName = operationName;
		this.coordinatorPortNumber = coordinatorPortNumber;
		this.coordinatorHostName = coordinatorHostName;
		this.fileName = fileName;
	}

	public void run()
	{
		TTransport transport = null;

		transport = new TSocket(coordinatorHostName, coordinatorPortNumber);

		try {
			transport.open();
		} catch (TTransportException e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}
		
		TProtocol protocol = new  TBinaryProtocol(transport);
	    FileServiceCoordinator.Client client = new FileServiceCoordinator.Client(protocol);

//	    perform(client, operation, file_name);
	    

	    String content = null;
		StringBuilder file_content = new StringBuilder();
		String info = null;
		String operationType = operationName.toLowerCase();
		RFile readFile = new RFile();
		RFileMetadata metaData = new RFileMetadata();
		TIOStreamTransport tios = new TIOStreamTransport(System.out);
		TProtocol tp = new TJSONProtocol.Factory().getProtocol(tios);
		FileReader fileReader = null;
		String response = null;
		System.out.println("operationType : "+operationType);

		
	    if(operationType.equals("write"))
		{
			try 
			{

				fileReader = new FileReader(fileName);
			} 
			catch (FileNotFoundException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			try 
			{
				while ((content = bufferedReader.readLine()) != null)
				{
					file_content = file_content.append(content);
					file_content.append(System.getProperty("line.separator"));
				}
			} 
			

			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(file_content.toString().isEmpty()) 
			{
				
			}
			else 
			{
				file_content.deleteCharAt(file_content.length()-1);
			}
			
			info = file_content.toString();
			metaData.setFilename(fileName);
			metaData.setFilenameIsSet(true);
			readFile.setContent(info);
			readFile.setContentIsSet(true);
			readFile.setMeta(metaData);
			readFile.setMetaIsSet(true);
			try 
			{
				response = client.writeFile(readFile);
			}
			catch (TException e) 
			{
				// TODO Auto-generated catch block
				System.err.println("Participants have aborted");
			}	
			System.out.println(response);
		
		}
	    else if(operationType.equals("read"))
	    {
	    	try 
	    	{
				readFile = client.readFile(fileName);
				System.out.println("File Contents : "+readFile.getContent());
			}
	    	catch (SystemException e) 
	    	{
				// TODO Auto-generated catch block
				System.err.println(e.getMessage());
			} 
	    	catch (TException e)
	    	{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    transport.close();
	}	

}