package application;




import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.String;
import java.util.HashSet;
import java.util.LinkedList;



public class Astar{
	public char[][] data;
	public Vertex start_v;
	public Vertex goal_v;
	public int width;
	public int height;
	public Fringe fringe;
	public HashSet<String> closed_list;
    public LinkedList<Vertex> result_path;
	public double total_cost;
	public int expanded_num = 0;
	public double weight;
	public HashSet<String> expanded_list;
	public double optimum_cost;
	public Astar(MazeMap map, double weight){
		this.weight = weight;
		fringe=new Fringe();
		closed_list=new HashSet<String>();
		expanded_list = new HashSet<String>();
		this.start_v = new Vertex(map.x1, map.y1);
		this.goal_v = new Vertex(map.x2, map.y2);
		this.width = map.width;
		this.height = map.height;
		this.data = map.node.clone();
		this.start_v.g = 0;
		this.start_v.parent = this.start_v;
		start_v.h = weight*calculate_heuristic(start_v, goal_v);
		fringe.insert(start_v, start_v.g + start_v.h);
		expanded_list.add(start_v.x + "," + start_v.y);
		expanded_num =1;
		result_path=new LinkedList<Vertex>();
//		fringe.toArray();
	}
	
	public long solve(){
		while(!fringe.isEmpty()){
//			fringe.fring_peek();
//			fringe.fring_size();
			Vertex s = fringe.pop();
			String test_s=""+s.x+"000"+s.y;
			if((s.x== goal_v.x)&&(s.y== goal_v.y)){
				total_cost=s.g;
				Vertex tmp=s;
				while(!((tmp.x==start_v.x)&&(tmp.y==start_v.y))){
					result_path.addFirst(tmp);
					tmp=tmp.parent;
					
				}
				result_path.addFirst(tmp);
				output_result();
				output_file();
                long used_memory = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
//				System.out.println(this.expanded_num);
				return used_memory;
			}
			if(closed_list.contains(test_s)){
				continue;
			}
			if(!closed_list.contains(test_s)){
				closed_list.add(test_s);
				
			}
			for(int i = 0; i < 8;i ++){
				Vertex s_prime;	
				s_prime = s.get_neighbor(i);
				String test_sprime=""+s_prime.x+"000"+s_prime.y;
//				System.out.println(s_prime);
//				fringe.fring_peek();
//				System.out.println("0");
				if(s_prime.x < height && s_prime.x >= 0 && s_prime.y >= 0 && s_prime.y < width && data[s_prime.x][s_prime.y] != '0'){
					if(!closed_list.contains(test_sprime)){                  
//						System.out.println("1");
						if(!fringe.check(s_prime)){
//							System.out.println("2");
							s_prime.g = Double.POSITIVE_INFINITY;
							
							s_prime.parent = null;
							
//							System.out.println(s_prime.g);
						}
						update_vertex(s, s_prime);
					}
				}
				
			}
//			System.out.println("number");
		}
		return 0;
		//System.out.println("number");
	}
	
	public void update_vertex(Vertex s, Vertex s_prime){
		double cost = step_cost(s, s_prime);
		if((s.g + cost) < s_prime.g){
			s_prime.g = s.g + cost;
			if(fringe.check(s_prime)){
				fringe.remove(s_prime);
				expanded_num = expanded_num-1;
			}
			
			s_prime.set_parent(s);
			fringe.insert(s_prime, (s_prime.g + weight*calculate_heuristic(s_prime, goal_v)));
			expanded_num = expanded_num+1;
//			System.out.println(s_prime.x + "," + s_prime.y);
			if(!expanded_list.contains(s_prime.x +","+s_prime.y)){
				expanded_list.add(s_prime.x + ","+s_prime.y);
			}
		}
	}
	
	public double step_cost(Vertex cen, Vertex nei){
		int x1 = cen.x;
		int x2 = nei.x;
		int y1 = cen.y;
		int y2 = nei.y;
		
		if((x1 == x2 - 1 && y1 == y2) || (x1 == x2 + 1 && y1 == y2) || (x1 == x2 && y1 == y2 + 1) || (x1 == x2 && y1 == y2 - 1)){
			if(data[x1][y1]=='1' && data[x2][y2]=='1'){
				return 1;
			}else if((data[x1][y1]=='1' && data[x2][y2]=='2')||(data[x1][y1]=='2' && data[x2][y2]=='1')){
				return 1.5;
			}else if((data[x1][y1]=='1' && data[x2][y2]=='a')||(data[x1][y1]=='a' && data[x2][y2]=='1')){
				return 0.625;
			}else if((data[x1][y1]=='1' && data[x2][y2]=='b')||(data[x1][y1]=='b' && data[x2][y2]=='1')){
				return 0.75;
			}else if(data[x1][y1]=='2' && data[x2][y2]=='2'){
				return 2;
			}else if((data[x1][y1]=='2' && data[x2][y2]=='a')||(data[x1][y1]=='a' && data[x2][y2]=='2')){
				return 1.125;
			}else if((data[x1][y1]=='2' && data[x2][y2]=='b')||(data[x1][y1]=='b' && data[x2][y2]=='2')){
				return 1.25;
			}else if((data[x1][y1]=='a' && data[x2][y2]=='a')){
				return 0.25;
			}else if((data[x1][y1]=='a' && data[x2][y2]=='b')||(data[x1][y1]=='b' && data[x2][y2]=='a')){
				return 0.375;
			}else if(data[x1][y1]=='b' && data[x2][y2]=='b'){
				return 0.5;
			}
			
		}else{
			if(data[x1][y1]=='1' && data[x2][y2]=='1'){
				return 1.414;
			}else if((data[x1][y1]=='1' && data[x2][y2]=='2')||(data[x1][y1]=='2' && data[x2][y2]=='1')){
				return 2.121;
			}else if((data[x1][y1]=='1' && data[x2][y2]=='a')||(data[x1][y1]=='a' && data[x2][y2]=='1')){
				return 0.884;
			}else if((data[x1][y1]=='1' && data[x2][y2]=='b')||(data[x1][y1]=='b' && data[x2][y2]=='1')){
				return 1.061;
			}else if(data[x1][y1]=='2' && data[x2][y2]=='2'){
				return 2.828;
			}else if((data[x1][y1]=='2' && data[x2][y2]=='a')||(data[x1][y1]=='a' && data[x2][y2]=='2')){
				return 1.591;
			}else if((data[x1][y1]=='2' && data[x2][y2]=='b')||(data[x1][y1]=='b' && data[x2][y2]=='2')){
				return 1.768;
			}else if(data[x1][y1]=='a' && data[x2][y2]=='a'){
				return 0.354;
			}else if((data[x1][y1]=='a' && data[x2][y2]=='b')||(data[x1][y1]=='b' && data[x2][y2]=='a')){
				return 0.53;
			}else if(data[x1][y1]=='b' && data[x2][y2]=='b'){
				return 0.707;
			}
		}
		return 0;
		
	}
	
	public void output_result(){
		for(Vertex  my_iterator: result_path){
		//	System.out.println(my_iterator);
		}
		
	}
	public void output_file(){
		try {		 			
			   File file = new File("Astar.txt");	
			   if (!file.exists()) {
			    file.createNewFile();
			   }			 
			   FileWriter fw = new FileWriter(file.getAbsoluteFile());
			   BufferedWriter bw = new BufferedWriter(fw);
			   bw.write(""+total_cost);
			   bw.newLine();
			   for(Vertex  my_iterator: result_path){
				   bw.write("("+my_iterator+")");
				   bw.newLine();
				}
			   bw.close();			 			 
			  } catch (IOException e) {
			   e.printStackTrace();
			  }
	}
	
	public double calculate_heuristic(Vertex v1, Vertex v2){
		//return Math.sqrt((v1.x - v2.x)*(v1.x - v2.x)+(v1.y - v2.y)*(v1.y - v2.y));
//		return 0.25*Math.sqrt((v1.x - v2.x)*(v1.x - v2.x)+0.25*(v1.y - v2.y)*(v1.y - v2.y));
		return 0.25*Math.abs(v1.x - v2.x)+0.25*Math.abs(v1.y - v2.y);
//		return Math.abs(v1.x - v2.x)+Math.abs(v1.y - v2.y);
//		return 5*Math.abs(v1.x - v2.x)+5*Math.abs(v1.y - v2.y);
		
	}
	
	//public static void main(String[] args){
//		MazeMap a=new MazeMap(160,120);	
//		a.generate_file();
//		a.generate_sng();
//		a.print_file();
//		a.import_file();
//		Astar b=new Astar(a);
//		b.solve();	
//	}
}