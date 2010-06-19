package tcp;

import java.io.*;
import java.net.*; 

class Client{ 
	public static void main(String args[]) throws Exception { 
		System.out.println("Eingabeschema:  x  [operation] y");
		String sentence; 
		String modifiedSentence; 
		
		BufferedReader inFromUser= new BufferedReader(new InputStreamReader(System.in)); 
		
		Socket clientSocket= new Socket("127.0.0.1", 6789); 
		
		DataOutputStream outToServer= new DataOutputStream(clientSocket.getOutputStream());
		
		BufferedReader inFromServer= new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); 
		
		sentence = inFromUser.readLine(); 
		outToServer.writeBytes(sentence+ '\n'); 
		modifiedSentence= inFromServer.readLine(); 
		
		System.out.println("FROMSERVER: " + modifiedSentence); 
		clientSocket.close(); 
	} 
}
