package edu.nahuel;
//"%JAVA_HOME%/bin/javac" -d out -cp src src/com/ClientMain.java
//"%JAVA_HOME%/bin/java" -cp out com.ClientMain
import java.io.*;
import java.net.*;
// import java.net.UnknownHostException;
// import java.net.ServerSocket;
// import java.net.Socket;
// import java.net.SocketException;
// import java.net.DatagramSocket;
// import java.net.DatagramPacket;
// import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
import inet.ipaddr.*;
public class ClientMain
{
	static String FilesPath="Files/";
	public static void main(String[] args)
	{

		System.out.println("Hola mundo");
		
		//Ya sale falta poder escribir la ip del servidor
		//mostrarIP();
		downloadFolder(true);
		//probarInet();
	}
	public static void probarInet(){
		System.out.println("Hola inet");
		String ipv6Str = "::/64";
		String ipv4Str = "1.2.255.4/255.255.0.0";
		try {
			System.out.println("Hola try");
			IPAddress ipv6Address = new IPAddressString(ipv6Str).toAddress();
			IPAddress ipv4Address = new IPAddressString(ipv4Str).toAddress();
				// use addresses
		} catch (Exception e) {
			String msg = e.getMessage();//detailed message indicating improper string format
			System.out.println(msg);
			System.out.println("error");
			// handle improperly formatted address string
		}
		System.out.println("chau inet");
	}
	public static void mostrarIP(){
		        // Aqui obtenemos la ip local de la maquina

		System.out.println("Get ip");
		try{
			InetAddress address = InetAddress.getLocalHost();

			System.out.println("IP Local :"+address.getHostAddress());

	 

			// Aqui obtenemos la ip de la web del programador

			String domain="www.lawebdelprogramador.com";

			InetAddress address2 = InetAddress.getByName(domain);

			byte IP[] = address2.getAddress();

			System.out.print("IP del dominio "+domain+" :");

			for (int index = 0; index < IP.length; index++)

			{

			   if (index > 0)

					 System.out.print(".");

			   System.out.print(((int)IP[index])& 0xff);

			}
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
		try {
			IPAddress address = new IPAddressString("192.101.0.1").toAddress();
		} catch(Exception ea) {
        System.out.println(ea.getMessage());// provides description of validation failure
		}
	}
	public static void downloadFolder(boolean userIp){
		//El chiste es voy creando paquetitos de 1024 y se lo voy escribiendo al archivo
		System.out.println("Soy un cliente");
		String HOST="127.0.0.1";
		if(userIp){
			Scanner scn=new Scanner(System.in);
			System.out.println("Escriba la direccion del servidor");
			HOST=scn.nextLine();
		}
		
		final int PORT=5000;
		DataInputStream in;
		DataOutputStream out;
		byte[] buff;
		try
		{
			Socket sc= new Socket(HOST,PORT);
			in=new DataInputStream(sc.getInputStream());
			out= new DataOutputStream(sc.getOutputStream());
			
			out.writeUTF("Hola desde el cliente");
			String mensaje=in.readUTF();
			System.out.println(mensaje);
			//Hasta aca son los holas
			//Ver los archivos
			mensaje=in.readUTF();
			System.out.println(mensaje);
			mensaje=in.readUTF();
			System.out.println(mensaje);
			// //Escribir el archivo que queremos
			Scanner scn = new Scanner(System.in);
			String fid=scn.next();
			out.writeUTF(fid);
			//le aviso el archivo o si no existe
			String fname=in.readUTF();
			System.out.println(fname);
			//recibiendo
			mensaje=in.readUTF();

			recibirArchivo(mensaje,fname,in);
			//ACK del servidor
			mensaje=in.readUTF();
			System.out.println(mensaje);

			sc.close();
		}
		catch(IOException ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	public static void watchingFiles(boolean userIp){
		//El chiste es voy creando paquetitos de 1024 y se lo voy escribiendo al archivo
		System.out.println("Soy un cliente");
		String HOST="127.0.0.1";
		if(userIp){
			Scanner scn=new Scanner(System.in);
			System.out.println("Escriba la direccion del servidor");
			HOST=scn.nextLine();
		}
		
		final int PORT=5000;
		DataInputStream in;
		DataOutputStream out;
		byte[] buff;
		try
		{
			Socket sc= new Socket(HOST,PORT);
			in=new DataInputStream(sc.getInputStream());
			out= new DataOutputStream(sc.getOutputStream());
			
			out.writeUTF("Hola desde el cliente");
			String mensaje=in.readUTF();
			System.out.println(mensaje);
			//Hasta aca son los holas
			//Ver los archivos
			mensaje=in.readUTF();
			System.out.println(mensaje);
			mensaje=in.readUTF();
			System.out.println(mensaje);
			//Escribir el archivo que queremos
			Scanner scn = new Scanner(System.in);
			String fid=scn.next();
			out.writeUTF(fid);
			//le aviso el archivo o si no existe
			String fname=in.readUTF();
			System.out.println(fname);
			//recibiendo
			mensaje=in.readUTF();
			String length=in.readUTF();
			int ln=Integer.parseInt(length);
			System.out.println(mensaje+" "+length+" bytes");
			//Crear archivo
			File f=new File(FilesPath+fname);
			BufferedOutputStream bos= new BufferedOutputStream(new FileOutputStream(f));
			//Recibir bytes
			int recivedbytes=0;
			
			while(recivedbytes<ln){
				int brec=bufflen(ln-recivedbytes);
				buff=new byte[brec];
				in.read(buff);
				bos.write(buff);
				recivedbytes+=brec;
			}

			//ACK del servidor
			mensaje=in.readUTF();
			System.out.println(mensaje);
			bos.close();
			sc.close();
		}
		catch(IOException ex)
		{
			System.out.println(ex.getMessage());
		}
	
	}
	public static int bufflen(int ln){
		if(ln>=1024){
			return 1024;
		}
		else{
			return ln;
		}
	}
	public static void tcpmain()
	{
		System.out.println("Soy un cliente");
		
		final String HOST="127.0.0.1";
		final int PORT=5000;
		DataInputStream in;
		DataOutputStream out;
		try
		{
			Socket sc= new Socket(HOST,PORT);
			in=new DataInputStream(sc.getInputStream());
			out= new DataOutputStream(sc.getOutputStream());
			
			out.writeUTF("Hola desde el cliente");
			String mensaje=in.readUTF();
			System.out.println(mensaje);
			
			
			sc.close();
			
		}
		catch(IOException ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	public static void udpmain()
	{
		final int PORT_SERVER=5000;
		byte[] buffer= new byte[1024];

		try
		{
			InetAddress direccion_servidor=  InetAddress.getByName("localhost");
			DatagramSocket socketUDP = new DatagramSocket();
			
			String mensaje = "Hola soy el cliente";
			buffer=mensaje.getBytes();

			DatagramPacket pregunta= new DatagramPacket(buffer,buffer.length,direccion_servidor,PORT_SERVER);
			System.out.println("Envio datagrama");

			socketUDP.send(pregunta);
			
			DatagramPacket peticion = new DatagramPacket(buffer,buffer.length);
 
			socketUDP.receive(peticion);
			mensaje=new String(peticion.getData());
			System.out.println(mensaje);
			socketUDP.close();

		}
		catch(UnknownHostException uoex)
		{
			System.out.println(uoex.getMessage());
		}
		catch(SocketException ex)
		{
			System.out.println(ex.getMessage());
		}
		catch(IOException ioex)
		{
			System.out.println(ioex.getMessage());
			
		}
	}
	private static void recibirArchivo(String mensaje,String fname,DataInputStream in) throws IOException{
		String length=in.readUTF();
		int ln=Integer.parseInt(length);
		System.out.println(mensaje+" "+length+" bytes");
		byte[] buff; 
		File f=new File(FilesPath+fname);
		BufferedOutputStream bos= new BufferedOutputStream(new FileOutputStream(f));
		//Recibir bytes
		int recivedbytes=0;
		while(recivedbytes<ln){
			int brec=bufflen(ln-recivedbytes);
			buff=new byte[brec];
			in.read(buff);
			bos.write(buff);
			recivedbytes+=brec;
		}
		bos.close();
	}
	private static void recibirArchivoDeep(boolean userIp){
		System.out.println("Soy un cliente");
		String HOST="127.0.0.1";
		if(userIp){
			Scanner scn=new Scanner(System.in);
			System.out.println("Escriba la direccion del servidor");
			HOST=scn.nextLine();
		}
		
		final int PORT=5000;
		DataInputStream in;
		DataOutputStream out;
		byte[] buff;
		try
		{
			Socket sc= new Socket(HOST,PORT);
			in=new DataInputStream(sc.getInputStream());
			out= new DataOutputStream(sc.getOutputStream());
			
			out.writeUTF("Hola desde el cliente");
			String mensaje=in.readUTF();
			System.out.println(mensaje);
			//Hasta aca son los holas

			//le aviso el archivo o si no existe
			String fname=in.readUTF();
			System.out.println(fname);
			//recibiendo
			mensaje=in.readUTF();

			recibirArchivo(mensaje,fname,in);
			//ACK del servidor
			mensaje=in.readUTF();
			System.out.println(mensaje);

			sc.close();
		}
		catch(IOException ex)
		{
			System.out.println(ex.getMessage());
		}
	}
}