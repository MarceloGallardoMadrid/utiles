package com.soporte;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * Estructura que resuelve facilmente la jerarquia
 *
 */
public class Arbol<T> 
{
	
    //Un arbol esta compuesto de nodos, que pueden ser arboles o nodos comunes
	//Combiene tener una lista para cada cosa?
	//Por el uso que le voy a dar si
	//Una lista de nodos y una lista arboles
	private ArrayList<Arbol<T>> arboles;
	private ArrayList<Nodo<T>> nodos;
	private String nombre;
	public Arbol(String name){
		arboles=new ArrayList<>();
		nodos=new ArrayList<>();
		nombre=name;
	}
	public Arbol(String name,ArrayList<Arbol<T>> as,ArrayList<Nodo<T>> ns){
		arboles=as;
		nodos=ns;
		nombre=name;
	}
	public void setNombre(String name){
		nombre=name;
	}
	public String getNombre(){
		return nombre;
	}
	public void reset(){
		for(Arbol<T> a:arboles){
			a.reset();
		}
		arboles=new ArrayList<>();
		nodos=new ArrayList<>();
	}
	public void addArbol(Arbol<T> a){
		arboles.add(a);
	}
	public void addNodo(T n){
		nodos.add(new Nodo<T>(n));
	}
	public Iterator<Arbol<T>> arbolIterator(){
		return arboles.iterator();
	}
	public Iterator<Nodo<T>> nodosIterator(){
		return nodos.iterator();
	}
	public String toString(int nivel){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<nivel;i++){
			sb.append("\t");
		}
		sb.append("[ ").append(nombre).append("\n");
		for(int i=0;i<nivel;i++){
			sb.append("\t");
		}
		sb.append("\t");
		for(Nodo<T> n: nodos){
			sb.append("(").append(" ").append(n.toString()).append(" ").append("),");
		}
		for(Arbol<T> a:arboles){
			sb.append("\n").append(a.toString(nivel+1));
		}
		sb.append("\n");
		for(int i=0;i<nivel;i++){
			sb.append("\t");
		}
		sb.append("]");
		return sb.toString();
	}
	public boolean equals(Object o){
		Arbol<T> otro=(Arbol<T>)o;
		return otro.nombre.equals(this.nombre);
	}
	public Arbol<T> getArbol(String nombre){
		//Dos directorios se pueden llamar igual, vemos como resolverlo
		Arbol<T> obj=new Arbol<T>(nombre);
		Arbol<T> finded=null;
		for(Arbol<T> a:arboles){
			if(a.equals(obj)){
				return a;
			}
			
		}
		return null;
	}
	
}
