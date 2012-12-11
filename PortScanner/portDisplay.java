import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;


public class portDisplay{

    AtomicIntegerArray opennedPorts = new AtomicIntegerArray(65535);
    AtomicIntegerArray opennedPorts2 = new AtomicIntegerArray(65535);
    AtomicIntegerArray portchecker = new AtomicIntegerArray(65535);
    AtomicInteger totalPorts = new AtomicInteger(0);
    AtomicInteger closedPorts = new AtomicInteger(0);
    AtomicInteger filteredPorts = new AtomicInteger(0);
    AtomicInteger openPortscounter = new AtomicInteger(0);
    AtomicInteger openPortscounter2 = new AtomicInteger(0);
    AtomicInteger checkerPortscounter = new AtomicInteger(0);
    AtomicInteger percentage = new AtomicInteger(0);
    

    
    
    public portDisplay(){
        AtomicIntegerArray opennedPorts = new AtomicIntegerArray(65535);
        AtomicIntegerArray opennedPorts2 = new AtomicIntegerArray(65535);
        AtomicIntegerArray portchecker = new AtomicIntegerArray(65535);
        AtomicInteger totalPorts = new AtomicInteger(0);
        AtomicInteger closedPorts = new AtomicInteger(0);
        AtomicInteger filteredPorts = new AtomicInteger(0);
        AtomicInteger openPortscounter = new AtomicInteger(0);
        AtomicInteger openPortscounter2 = new AtomicInteger(0);
        AtomicInteger checkerPortscounter = new AtomicInteger(0);
        AtomicInteger percentage = new AtomicInteger(0);
    }
   
    public void incrementTotalPorts(){
        totalPorts.incrementAndGet();
    }
    
    public int incrementClosed(){
        return closedPorts.incrementAndGet();
    }
    
    public int incrementFiltered(){
        return filteredPorts.incrementAndGet();
    }

    public void setPercentage(int i){
        percentage.set(i);
    }     
    
    public int getPercentage(){
        return percentage.get();
    }
    
    public int getTotalPorts(){
        return totalPorts.get();
    }
    
    public int getChecked(int counter){
        return portchecker.get(counter);
    }
    
    public int getcheckedPorts(){
        return checkerPortscounter.get();
    }
    
    public int getClosed(){
        return closedPorts.get();
    }
    
    public int getFiltered(){
        return filteredPorts.get();
    }
    
    public void insertPort(int port){
        opennedPorts.addAndGet(openPortscounter.get(),port);
        openPortscounter.incrementAndGet();
    
    }
    
    public void insertPort2(int port){
        opennedPorts2.addAndGet(openPortscounter2.get(),port);
        openPortscounter2.incrementAndGet();
    
    }
    
    public void insertChecker(int port){
        portchecker.addAndGet(checkerPortscounter.get(),port);
        checkerPortscounter.incrementAndGet();
    }
    

    public String toString(){
        String res = "Succesful connections: \n";
        for(int i=0; opennedPorts.get(i) != 0 ;i++){
            res+= "Port " + opennedPorts.get(i) + " connected in first try \n";
        }
        for(int j=0; opennedPorts2.get(j) != 0 ;j++){
            res+= "Port " + opennedPorts2.get(j) + " connected in second try \n";
        }
        res+= "Ports closed " + closedPorts.get() + "\n";
        res+= "Ports filtered " + filteredPorts.get() + "\n";
        return res;
    }
}
    
    
    

