package application;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Fringe<T extends Vertex>{
	private My_datastructure<T> Astar_list;
	public void toArray(){		
		System.out.println(Astar_list.peek().f);
	}
	public Fringe(){
		Astar_list = new My_datastructure<T>();

	}
	
	
	private void fringe_f(T s, double key){
		s.f=key;
	}

	public void fring_peek(){
		System.out.println(Astar_list.peek());
	}
	
	public T peek_node(){
		return Astar_list.peek();
	}
	
	public double minKey(){
		return Astar_list.peek().f;
	}
	public T pop(){
		return Astar_list.pop();
		
	}
	
	public boolean check(T tmp){
		return Astar_list.check(tmp);
	}
	
	public boolean isEmpty(){
		return Astar_list.isEmpty();
	}
	
	public void insert(T s, double key){
		fringe_f(s,key);
		
		Astar_list.insert(s);	
	}

	
	public void remove(T s){
		Astar_list.remove(s);
	}
	
}