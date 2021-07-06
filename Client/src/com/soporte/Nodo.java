package com.soporte;
/**
 * Parte basica de cualquier estructura
 *
 */
public class Nodo<T> 
{
	T valor;
	//Se encargar de almacenar un valor nomas
	public Nodo(T v){
		valor=v;
	}
	public T getValor(){
		return valor;
	}
	public void setValor(T v){
		valor=v;
	}
	public String toString(){
		return valor.toString();
	}
}
