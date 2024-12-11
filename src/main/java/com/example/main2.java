package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class main2 {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8080);
        
        do {
            Socket s0 = ss.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(s0.getInputStream()));
            DataOutputStream out = new DataOutputStream(s0.getOutputStream());

            String primaStringa = in.readLine();
            System.out.println(primaStringa);
            String [] request = primaStringa.split(" ");
            String metodo = request[0];
            String risorsa = request[1];
            String versione = request[2];

            String header;

            do {
                header = in.readLine();
                System.out.println(header);
            } while (!header.isEmpty());

            if(risorsa.equals("/")){
                risorsa= "/index.html";
            }
            File file = new File("htdocs"+risorsa); // prendo il file nella cartella htdocs
            
            if(file.exists()){
                //String msg = "Benvenuto nella <b>home</b>";
                
                out.writeBytes("HTTP/1.1 200 OK \r\n");
                //out.writeBytes("Content-Lenght: "+ msg.length() +"\r\n");
                out.writeBytes("Content-Lenght: "+ file.length() +"\r\n");
                out.writeBytes("Content-Type:" + getTipo(file) + "\r\n");
                out.writeBytes("\r\n");
                //out.writeBytes(msg);
                InputStream input = new FileInputStream(file);    // gestione del file
                byte[] buf = new byte[8192];
                int n;
                while ((n=input.read(buf)) != -1) {
                    out.write(buf, 0, n);
                }
            }
            else{
                String msg = "File non trovato";
                out.writeBytes("HTTP/1.1 404 Not found \r\n");
                out.writeBytes("Content-Lenght: "+ msg.length() +"\r\n");
                out.writeBytes("Content-Type: text/html \r\n");
                out.writeBytes("\r\n");
                out.writeBytes(msg);
            }
            s0.close();
        } while (true);
    }

    public static String getTipo(File f){
        String[] s = f.getName().split("\\.");
        String ext = s[s.length-1];

        switch (ext) {
            case "html":
            case "htm":
                return "text/html";

            case "png":
                return "image/png";

            case "jpeg":
            case "jpg":
                return "image/jpeg";

            case "css":
                return "text/css";

            case "js":
                return "application/javascript";

            default :
                return "";
        }
    } 
}
