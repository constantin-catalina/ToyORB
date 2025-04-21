package ByteSendReceive;

import java.net.*;
import java.io.*;
import Commons.Address;

public class ByteReceiver
{
	private ServerSocket srvS;
	private Socket s;
	private InputStream iStr;

	private String myName;
	private Address myAddr;

	public ByteReceiver(String theName, Address theAddr)
	{
		myName = theName;
		myAddr = theAddr;
		try
		{
			srvS = new ServerSocket(myAddr.port(), 1000);
			System.out.println("Receiver Serversocket:" + srvS);
		}
		catch (Exception e)
		{
			System.out.println("Error opening server socket");
		}
	}


	public byte[] receive()
	{
		int val;
		byte buffer[] = null;
		try
		{
			s = srvS.accept();
			System.out.println("Receiver accept: Socket" + s);
			iStr = s.getInputStream();
			val = iStr.read();
			buffer = new byte[val];
			iStr.read(buffer);


			iStr.close();
			s.close();

		}
		catch (IOException e)
		{
			System.out.println("IOException in receive_transform_and_feedback");
		}
		return buffer;
	}

	protected void finalize() throws Throwable
	{
		super.finalize();
		srvS.close();
	}
}

