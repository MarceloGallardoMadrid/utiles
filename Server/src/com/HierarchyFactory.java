package com;
import com.soporte.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * Se encarga de construir una estructura de directorios apartir de un arbol
 * Por ahora todo string, mas adelante vemos que onda
 *
 */
public class HierarchyFactory 
{
	//Si quiero tener varios directorios en memoria?Veo
	Arbol<String> jerarquia;
	String outDir;
	public HierarchyFactory(String _outDir){
		outDir=_outDir;
	}
	public void setJerarquia(Arbol<String> root){
		jerarquia=root;
	}
	public void crearDirectorios(){
		try{
			File f=new File(outDir);
			if(f.isDirectory()){
				crearEstructura(f,jerarquia);
			}
			
		}
		catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	private void crearEstructura(File parent,Arbol<String> dir) throws IOException{
		File carpeta=new File(parent,dir.getNombre());
		carpeta.mkdir();
		Iterator<Nodo<String>> itNodos= dir.nodosIterator();
		while(itNodos.hasNext()){
			Nodo<String> n= itNodos.next();
			
			File arch=new File(carpeta,n.getValor());
			arch.createNewFile();
		}
		Iterator<Arbol<String>> itArboles= dir.arbolIterator();
		while(itArboles.hasNext()){
			Arbol<String> a= itArboles.next();
			crearEstructura(carpeta,a);
		}
	}
	
		
}
