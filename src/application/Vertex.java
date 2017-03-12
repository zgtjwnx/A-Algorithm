package application;
import java.util.HashMap;



public class Vertex{
	public int x;
	public int y;
	
	public Vertex parent;
	public double g;
	public double h;
	public double f;
	
	public Vertex(int x, int y){
		
		this.x = x;
		this.y = y;
		this.g = 0;
		this.h=0;
		this.f=0;
	    this.parent = null;
	}
    public Vertex(int x, int y,Vertex parent){
		
		this.x = x;
		this.y = y;
		this.g = 0;
		this.h=0;
		this.f=0;
	    this.parent = parent;
	}
    
    public Vertex(Vertex s){
    	this.x = s.x;
    	this.y = s.y;
    	this.g = s.g;
    	this.h = s.h;
    	this.f = s.f;
    	this.parent = s.parent;
    }
	
    public String toString(){
		return this.x+","+this.y;
	}
	public void set_parent(Vertex tmp){
		parent=tmp;
	}

	public Vertex get_neighbor(int i) {
		if(i==0)
			return new Vertex(this.x+1,this.y);
		if(i==1)
			return new Vertex(this.x-1,this.y);
		if(i==2)
			return new Vertex(this.x+1,this.y+1);
		if(i==3)
			return new Vertex(this.x-1,this.y+1);
		if(i==4)
			return new Vertex(this.x+1,this.y-1);
		if(i==5)
			return new Vertex(this.x-1,this.y-1);
		if(i==6)
			return new Vertex(this.x,this.y+1);
		if(i==7)
			return new Vertex(this.x,this.y-1);
		else
			return null;		
	}
}