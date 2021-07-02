package com;
public	class Tupla{
		String id;
		String name;
		public Tupla(int _id,String _name){
			id=""+_id;
			name=_name;
		}
		public boolean esId(String _id){
			return id.compareTo(_id)==0;
		}
		public String getId(){
			return id;
		}
		public String getName(){
			return name;
		}
		public String toString(){
			return "{id: "+id+" , "+"name: "+name+"}";  
		}
	}