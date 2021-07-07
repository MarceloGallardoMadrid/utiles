package edu.nahuel;
//"%JAVA_HOME%/bin/javac" -d out -cp src src/com/ServerMain.java
//"%JAVA_HOME%/bin/java" -cp out com.ServerMain
import java.io.*;
import java.nio.file.Files;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
public class ServerMain
{
	static String FilesPath="Files/";
	static String FilesPathTree="Files";
	static String XMLPath="xml\\files.xml";
	
	public static void main(String[] args) 
	{
		
		mostrarIP();
		//enviarArchivoDeep();
		uploadFiles();
		
	}
	public static void imprimirArbolFile(){
		GestorDirectorio gd = new GestorDirectorio();
		gd.imprimirArbolFile(FilesPathTree,"xml");
		
	}
	public static void crearArbolLinear(){
		GestorDirectorio gd = new GestorDirectorio();
		gd.crearArbolLinear(FilesPathTree);
	}
	public static void leerArbol(){
		GestorDirectorio gd = new GestorDirectorio();
		gd.leerArbol();

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
	}
	private static ArrayList<String> getFilesNames(){
		ArrayList<String> filesName=new ArrayList<String>();
		File dir = new File("Files");
		if(dir.isDirectory()){
			File[] files= dir.listFiles();
			for(File f: files){
				if(f.isFile()&& !f.isHidden()){
					filesName.add(f.getName());
					
				}
			}
		}
		return filesName;
	}
	private static byte[] getFileBytes(String name){
	byte[] fileC=null;	
			try{
				File f =new File(name);
				fileC=Files.readAllBytes(f.toPath());
		}
		catch(IOException ioe){
				System.out.println(ioe.getMessage());
		}
		
		return fileC;
	}	
	public BufferedReader getReader(String name){
		BufferedReader in =null;
		try{
			File file= new File(name);
			in= new BufferedReader(new FileReader(file));
		}
		catch(FileNotFoundException fe){
			System.out.println(fe.getMessage());
		}
		return in;
	}
	public static void uploadFiles(){
		imprimirArbolFile();
		System.out.println("Soy un servidor");
		ServerSocket servidor=null;
		Socket sc=null;
		final int PORT=5000;
		DataInputStream in;
		DataOutputStream out;
		byte[] buff;
		try
		{
			servidor=new ServerSocket(PORT);
			
			System.out.println("Servidor iniciad");
			
			while(true)
			{
				sc=servidor.accept();
				System.out.println("Cliente conectado");
				in = new DataInputStream(sc.getInputStream());
				out = new DataOutputStream(sc.getOutputStream());
				String mensaje=in.readUTF();
				System.out.println(mensaje);
				out.writeUTF("Hola desde servidor");
				//Como voy a usar los numeros para elegir los archivos, es un par id, archivo
				//un mensaje que tenga 1,nombre
				//Le digo que existen estos archivos y le pregunto si lo desea descargar a todos

				//le digo que se lo mando
				out.writeUTF("Enviando bytes");
				String fname=XMLPath;
				enviarArchivo(fname,out);
				//no necesita mas datas creo
				out.writeUTF("Archivo enviado");
				//Hasta aca el xml
				mensaje=in.readUTF();
				int size= Integer.parseInt(mensaje);
				System.out.println("Necesita "+mensaje+" archivos");
				//El numero de archivos
				enviarArchivos(out,in);
				//Archivos envias
				sc.close();
				System.out.println("Cliente desconectado");
			}
		}
		catch(IOException ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	public static void showFiles(){
		ArrayList<String> filesName=getFilesNames();
		//El chiste es voy creando paquetitos de 1024 para enviarselo
		// byte[] fileContent=getFileBytes(filesName.get(0));
		// for(byte b:fileContent){
			// System.out.println(b);
		// }
		System.out.println("Soy un servidor");
		ServerSocket servidor=null;
		Socket sc=null;
		final int PORT=5000;
		DataInputStream in;
		DataOutputStream out;
		byte[] buff;
		try
		{
			servidor=new ServerSocket(PORT);
			
			System.out.println("Servidor iniciad");
			
			while(true)
			{
				sc=servidor.accept();
				System.out.println("Cliente conectado");
				in = new DataInputStream(sc.getInputStream());
				out = new DataOutputStream(sc.getOutputStream());
				String mensaje=in.readUTF();
				System.out.println(mensaje);
				out.writeUTF("Hola desde servidor");
				//Como voy a usar los numeros para elegir los archivos, es un par id, archivo
				//un mensaje que tenga 1,nombre
				int n=1;
				String listName="";
				ArrayList<Tupla> tuplas= new  ArrayList<Tupla>();
				for(String name:filesName){
					Tupla t= new Tupla(n,name);
					tuplas.add(t);
					listName+=t.toString()+"\n";
					
					n++;
				}
				out.writeUTF(listName);
				//Mostre los archivos
				out.writeUTF("Seleccione con el numero al archivo");
				//Leo su opcion
				String op=in.readUTF();
				String fname="not found";
				System.out.println(op);
				for(Tupla t:tuplas){
					if(t.esId(op)){
						fname=t.getName();
					}
				}
				//le digo el nombre del archivo
				System.out.println(fname);
				out.writeUTF(fname);
				//le digo que se lo mando
				out.writeUTF("Enviando bytes");
				
				//Archivo's bytes
				byte[] fileContent=getFileBytes(FilesPath+fname);
				int ln=fileContent.length;
				out.writeUTF(ln+"");
				System.out.println(fileContent.length+" bytes sending");
				int datasent=0;
				while(datasent<ln){
					int bsend=bufflen(ln-datasent);
					buff =new byte[bsend];
					System.arraycopy(fileContent,datasent,buff,0,bsend);
					out.write(buff);
					out.flush();
					datasent+=bsend;
				}
				//out.write(fileContent);
				//no necesita mas datas creo
				out.writeUTF("Archivo enviado");
				sc.close();
				System.out.println("Cliente desconectado");
				filesName=getFilesNames();
			}
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
		System.out.println("Soy un servidor");
		ServerSocket servidor=null;
		Socket sc=null;
		final int PORT=5000;
		DataInputStream in;
		DataOutputStream out;
		try
		{
			servidor=new ServerSocket(PORT);
			
			System.out.println("Servidor iniciad");
			
			while(true)
			{
				sc=servidor.accept();
				System.out.println("Cliente conectado");
				in = new DataInputStream(sc.getInputStream());
				out = new DataOutputStream(sc.getOutputStream());
				String mensaje=in.readUTF();
				System.out.println(mensaje);
				out.writeUTF("Hola desde servidor");
				sc.close();
				System.out.println("Cliente desconectado");
			}
		}
		catch(IOException ex)
		{
			System.out.println(ex.getMessage());
		}
	}
	public static void udpmain()
	{
		final int PORT=5000;
		byte[] buffer= new byte[1024];
		while(true)
		{
		try
		{
			System.out.println("Iniciando servidor UDP");
			DatagramSocket socketUDP = new DatagramSocket(PORT);
			
			DatagramPacket peticion= new DatagramPacket(buffer,buffer.length);
			
			socketUDP.receive(peticion);
			System.out.println("Recibo info del cliente");
			String mensaje = new String(peticion.getData());
			System.out.println(mensaje);
			int puertoCliente=peticion.getPort();
			InetAddress direccion= peticion.getAddress();
			
			mensaje="hola soy el servidor";
			
			buffer=mensaje.getBytes();
			DatagramPacket respuesta = new DatagramPacket(buffer,buffer.length,direccion,puertoCliente);
			
			socketUDP.send(respuesta);
			System.out.println("Envio info a cliente");

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
	}
	private static void enviarArchivo(String fname,DataOutputStream cliente) throws IOException{
		
				byte[] buff;
				byte[] fileContent=getFileBytes(fname);
				int ln=fileContent.length;
				cliente.writeUTF(ln+"");
				System.out.println(fileContent.length+" bytes sending");
				int datasent=0;
				while(datasent<ln){
					int bsend=bufflen(ln-datasent);
					buff =new byte[bsend];
					System.arraycopy(fileContent,datasent,buff,0,bsend);
					cliente.write(buff);
					cliente.flush();
					datasent+=bsend;
				}
	}
	private static void enviarArchivoDeep(){
		System.out.println("Soy un servidor");
		ServerSocket servidor=null;
		Socket sc=null;
		final int PORT=5000;
		DataInputStream in;
		DataOutputStream out;
		byte[] buff;
		try
		{
			servidor=new ServerSocket(PORT);
			
			System.out.println("Servidor iniciad");
			
			while(true)
			{
				sc=servidor.accept();
				System.out.println("Cliente conectado");
				in = new DataInputStream(sc.getInputStream());
				out = new DataOutputStream(sc.getOutputStream());
				String mensaje=in.readUTF();
				System.out.println(mensaje);
				out.writeUTF("Hola desde servidor");
				//Hasta aca los hola
				String fname="Files\\dir\\t1.txt";
				//le digo el nombre del archivo
				System.out.println(fname);
				out.writeUTF(fname);
				//le digo que se lo mando
				out.writeUTF("Enviando bytes");
				
				enviarArchivo(fname,out);
				//no necesita mas datas creo
				out.writeUTF("Archivo enviado");
				sc.close();
				System.out.println("Cliente desconectado");
			}
		}
		catch(IOException ex)
		{
			System.out.println(ex.getMessage());
		}		
	}
	private static void enviarArchivos(DataOutputStream out, DataInputStream in)throws IOException{
		int iter=0;
		String fname="vacio";
		while(!(fname.equals("nada"))&&iter<50){
			fname=in.readUTF();
			if(fname.equals("nada")||fname.equals("vacio")){
			}
			else{
				enviarArchivo(fname,out);
				
			}
			iter++;

		}
	}
}