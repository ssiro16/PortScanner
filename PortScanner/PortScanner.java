import java.util.*;
import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;

public class PortScanner extends Thread
{

    InetAddress mydirIP;
    int myport;
    int time;  
    int myflag;
    portDisplay display;
    int numberPorts;
    
    public PortScanner(InetAddress dirIP, int port, int T1, int flag, portDisplay display, int totalPorts){
    this.mydirIP = dirIP;
    this.myport = port;
    this.time = T1;
    this.myflag = flag;
    this.display = display;
    this.numberPorts = totalPorts;
    }

    public void run(){
        String comparator = "";
        String response = "java.net.ConnectException: Connection refused";
        
        if(myflag == 0){   
            try{      
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(mydirIP,myport), time);
                socket.close();
                display.incrementTotalPorts();
                display.insertPort(myport);
                if((display.getTotalPorts()*100/numberPorts) > display.getPercentage()){
                    display.setPercentage(display.getTotalPorts()*100/numberPorts);
                    System.out.println(display.getPercentage()+"%");
                }
                
            } 
            catch(IOException e) {
                comparator = ""+e;
                if(comparator.equals(response)){
                    display.incrementTotalPorts();
                    display.incrementClosed();
                    if((display.getTotalPorts()*100/numberPorts) > display.getPercentage()){
                    display.setPercentage(display.getTotalPorts()*100/numberPorts);
                    System.out.println(display.getPercentage()+"%");
                }
                }
                else display.insertChecker(myport);
                
                }
            
            }   
        else if(myflag == 1) {
            try{      
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(mydirIP,myport), time);
                socket.close();
                display.incrementTotalPorts();
                display.insertPort2(myport);
                if((display.getTotalPorts()*100/numberPorts) > display.getPercentage()){
                    display.setPercentage(display.getTotalPorts()*100/numberPorts);
                    System.out.println(display.getPercentage()+"%");
                }
                
            } 
            catch(IOException e) {
                display.incrementTotalPorts();
                display.incrementFiltered();
                if((display.getTotalPorts()*100/numberPorts) > display.getPercentage()){
                    display.setPercentage(display.getTotalPorts()*100/numberPorts);
                    System.out.println( display.getTotalPorts()*100/numberPorts + "%");
                }
            } 
        
        
        
        }
    }
    
    
    
    public static void main(String[] args) throws InterruptedException{

        int PI = Integer.parseInt(args[1]);
        int PF = Integer.parseInt(args[2]);
        int T1 = Integer.parseInt(args[3]);
        int Max = Integer.parseInt(args[4]);
        int port = PI;
        PortScanner scanner = null; 
        InetAddress dirIP = null;
        portDisplay display = new portDisplay();
        int baseThreads = activeCount();
        int totalPorts = PF-PI;
        
        try{      
            dirIP = InetAddress.getByName(args[0]);
            System.out.println("\nPlease wait, hosts with most of the ports filtered may take a while to start running");
            
            while(port <= PF){
                if(activeCount() <= Max){
                    scanner = new PortScanner(dirIP, port, T1, 0,display, totalPorts);
                    scanner.start();
                    port++;
                    }
            } while(activeCount() > baseThreads){}
            
            int counter = 0;
            while(counter < display.getcheckedPorts()){
                if(activeCount() <= Max){
                    scanner = new PortScanner(dirIP, display.getChecked(counter), T1, 1,display, totalPorts);
                    scanner.start();
                    counter++;
                    }
            } while(activeCount() > baseThreads){}
            
        } 
        catch(UnknownHostException e) {
            System.out.println( "Couldn't resolve host, try to execute again and try another one");
            System.exit(1);
        }
        
        
        //Print the results
        String res = "";
        res+="Host analyzed :" + dirIP + " (" + args[0] + ") \n";
        res+="Port range :" + PI + "-" + PF + "\n";
        res+="Number of threads: " + Max + "\n";
        res+=""+ display.toString();
        System.out.println(res);
        
        
        Scanner kbd = new Scanner(System.in);
        System.out.println("Do you want to store the results in a document?");
        System.out.println("/y/ or /n/");
        String store = kbd.nextLine();
        if(store.equals("y"))
        {
            try{
            System.out.println("Insert a name for the file");
            String filename = kbd.nextLine();
            PrintWriter out = new PrintWriter(filename);
            out.println(res);
            out.close();
            
            } catch(FileNotFoundException e) {System.out.println(e);
            }
        }
    }
}
