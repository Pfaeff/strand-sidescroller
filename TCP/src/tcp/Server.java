package tcp;

import java.io.*; 
import java.net.*; 
import java.util.StringTokenizer;

class Server{ 
	public static void main(String args[]) throws Exception { 
		String clientSentence; 
		Integer answer = null; 
		ServerSocket welcomeSocket= new ServerSocket(6789); 
		while(true) { 
			Socket connectionSocket= welcomeSocket.accept(); 
			BufferedReader inFromClient= new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			DataOutputStream outToClient= new DataOutputStream(connectionSocket.getOutputStream());
			clientSentence= inFromClient.readLine(); 
			
			StringTokenizer tokenizer;
			tokenizer = new StringTokenizer( clientSentence );
			
			int x=Integer.parseInt(tokenizer.nextToken());
			String operator=tokenizer.nextToken();
			int y=Integer.parseInt(tokenizer.nextToken());
			
			if (operator.equals("+")){
				answer=x+y;
			} else if (operator.equals("+")){
				answer=x*y;
			} else if (operator.equals("-")){
				answer=x-y;
			} else if (operator.equals("/")){
				answer=x/y;
			}
			
			outToClient.writeBytes(answer.toString()); 
		} 
	} 
}
