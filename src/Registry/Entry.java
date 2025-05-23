package Registry;

import java.util.*;
import Commons.Address;

public class Entry implements Address
{
	private String destinationId;
	private int portNr;
	public Entry(String theDest, int thePort)
	{
		destinationId = theDest;
		portNr = thePort;
	}

	@Override
	public String dest()
	{
		return destinationId;
	}

	@Override
	public int port()
	{
		return portNr;
	}
}