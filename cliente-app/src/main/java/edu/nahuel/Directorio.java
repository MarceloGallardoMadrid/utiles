package edu.nahuel;
import java.util.*;
public class Directorio<T> implements Iterable<T>{
	String nombre;
	ArrayList<T> archivos;
	public Directorio(String n){
		nombre=n;
		archivos=new ArrayList<>();
	}
	public Directorio(String n,ArrayList<T> files){
		nombre=n;
		archivos=files;
	}
	public void setNombre(String n){
		nombre=n;
		
	}
	public void setArchivos(ArrayList<T> files){
		archivos=files;
	}
	public ArrayList<T> getArchivos(){
		return archivos;
	}
	public String getNombre(){
		return nombre;
	}
	public void reset(){
		archivos=new ArrayList<>();
	}
	public void addFile(T file){
		archivos.add(file);
	}
	public Iterator<T> iterator(){
		return archivos.iterator();
	}
	
}