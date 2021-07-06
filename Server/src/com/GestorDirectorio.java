package com;
import com.soporte.*;
import java.io.*;
import java.util.ArrayList;
/**
 * Se encarga de devolver una estructura limpita de jerarquia de archivos
 * Por ahora todo string, mas adelante vemos que onda
 *
 */
public class GestorDirectorio 
{
	//Si quiero tener varios directorios en memoria?Veo
	Arbol<String> raiz;
	public GestorDirectorio(){
		
	}
	public void reset(){
		raiz.reset();
	}
	private void inicializarArbol(String  nombre){
		raiz= new Arbol(nombre);
	}
	public void addFile(String file){
		raiz.addNodo(file);
	}
	public void addDir(String dir){
		raiz.addArbol(new Arbol(dir));
	}
	public void addFileInTree(String dir,String file){
		Arbol<String> ar=raiz.getArbol(dir);
		ar.addNodo(file);
	}
	public void addDirInTree(String dir,String file){
		Arbol<String> ar=raiz.getArbol(dir);
		ar.addNodo(file);
	}
	public Arbol<String> getRaiz(){
		return raiz;
	}
	//El chiste es leer toda la estructura de archivos e ir guardandolo en el arbol
	public Arbol<String> leerDirectorio(String directorio){
		raiz= new Arbol(directorio);
		getFilesNames(raiz,directorio);
		return raiz;
	}
	private void  getFilesNames(Arbol<String> arbol,String directorio){
		File dir = new File(directorio);
		if(dir.isDirectory()){
			
			File[] files= dir.listFiles();
			for(File f: files){
				if(f.isFile()&& !f.isHidden()){
					arbol.addNodo(f.getName());					
				}
				if(f.isDirectory()){
					Arbol<String> arbolNuevo=new Arbol<>(f.getName());
					getFilesNames(arbolNuevo,f.getName());
					arbol.addArbol(arbolNuevo);
				}
			}
		}
	}
		
}
