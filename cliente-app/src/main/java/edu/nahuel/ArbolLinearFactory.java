package edu.nahuel;
import edu.nahuel.soporte.*;
import java.util.Iterator;
/**
 * Posiblemente sea una perdida de tiempo
 * tanta clase. Vemos como avanzar
 *No hace falta un arbol lineal,Tan solo uso un arbol, que usa un solo nivel, cuyos arboles estan vacios pero son necesarios para los directorios
 */
public class ArbolLinearFactory
{
	public ArbolLinearFactory(){
	}
	public Arbol<String> crearArbolLinear(Arbol<String> arbol){
		Arbol<String> linear=new Arbol<String>(arbol.getNombre());
		String dirName=arbol.getNombre();
		agregarArbol(linear,arbol,dirName);
		return linear;
	}
	private void agregarArbol(Arbol<String> linear,Arbol<String> arbol,String dirName){
		
		Iterator<Nodo<String>> itNodos=arbol.nodosIterator();		
		while(itNodos.hasNext()){
			Nodo<String> n = itNodos.next();
			linear.addNodo(dirName+"\\"+n.getValor());
				
		}
		Iterator<Arbol<String>> itArboles=arbol.arbolIterator();
		while(itArboles.hasNext()){
			Arbol<String>  a=itArboles.next();
			linear.addArbol(dirName+"\\"+a.getNombre());
			agregarArbol(linear,a,dirName+"\\"+a.getNombre());
		}
	}
	
	
}
