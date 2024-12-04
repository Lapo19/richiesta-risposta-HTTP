package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MioThread extends Thread{

    Socket s0;
    String metodo = "";
    String ricevuta="";
    int volte= 0;


    public MioThread(Socket s0){
        this.s0=s0;
    }
    
    @Override
    public void run(){
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(s0.getInputStream()))) {
            DataOutputStream out = new DataOutputStream(s0.getOutputStream());
            ricevuta = in.readLine();
            if(ricevuta.split(" ")[0].equals("GET")){
                while(true){
                    String line = in.readLine();
                    if(line.equals("")){
                    String bodyRisposta ="<html><body>Pagina non trovata</body></html>";
                    out.writeBytes("HTTP/1.1 404 Not found \r\n");
                    out.writeBytes("Content-Lenght: "+ bodyRisposta +"\r\n");
                    out.writeBytes("Content-Type: text/html \r\n");
                    out.writeBytes("\r\n");
                    out.writeBytes(bodyRisposta);
                    break;
                }else{
                    System.out.println(line);
                }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
