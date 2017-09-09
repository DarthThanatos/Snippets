package pFactorial;

import java.io.IOException;
import java.util.Date;

import com.ericsson.otp.erlang.OtpAuthException;
import com.ericsson.otp.erlang.OtpConnection;
import com.ericsson.otp.erlang.OtpErlangDecodeException;
import com.ericsson.otp.erlang.OtpErlangExit;
import com.ericsson.otp.erlang.OtpErlangInt;
import com.ericsson.otp.erlang.OtpErlangObject;
import com.ericsson.otp.erlang.OtpPeer;
import com.ericsson.otp.erlang.OtpSelf;

public class Main {
    private OtpSelf client;
    private OtpPeer server;
    private OtpConnection connection;
    
    public void init() throws IOException, OtpAuthException {
        client = new OtpSelf("client", "batman");
        server = new OtpPeer("server@Lenovo-PC");
        connection = client.connect(server);
        System.out.println(connection);
    }
    
    public void shouldInvokeErlangTranslateFunction(String module,String fun,int N) throws IOException,
    OtpAuthException, OtpErlangExit, OtpErlangDecodeException {
    		init();
    		//connection.sendRPC("translator", "translate", withArgs("friend", "Spanish"));
    		//OtpErlangObject response = connection.receiveMsg().getMsg();
    		Date d0 = new Date();
    		long start = d0.getTime();
    		connection.sendRPC(module, fun, withArgs(N));
    		OtpErlangObject response = connection.receiveMsg().getMsg();
    		Date d1 = new Date();
    		long end = d1.getTime();
    		System.out.println("Erlang time" + response + " Java time" + (end - start));
    }
    private OtpErlangObject[] withArgs(int N) {
        return new OtpErlangObject[] { 
                new OtpErlangInt(N)
        };
    }
    
	public static void main(String [] args){
		int N = 1000;
		//Fac1.fac1(N);	
		//Fac2.fac2(N);
		Fac3.fac3(N);
		String fun,mod = "pFactorial";
		try{
			//erl -sname server -setcookie batman
			fun = "fac3";
			new Main().shouldInvokeErlangTranslateFunction(mod,fun,N);
		}catch(Exception e){e.printStackTrace();}
	}
}
