package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.lang.Math;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Vector;
import java.util.HashMap;
import java.util.Map;
/*
 *  class MazeMap is for storing the map data
 *  int[][] node
 *   0 ----  no blocked
 *   1 ----  hard to traverse
 *   2 ----  obstacle
 *   3 ----  highway in no blocked area
 *   4 ----  highway in hard traverse area
 */
public class MazeMap{
	
	public char[][] node;
	public char[][] node_forprint;
    public int width;
	public int height;
	public int x1, x2, y1, y2;
	private int x_block[],y_block[];
	private boolean is_loaded = false;
	private int start_goal_index;
	/*
	 *  constructor 
	 *  @param    width ----- number of cells horizontally
	 *             height ---- number of cells vertically  
	 */
	public MazeMap(int width, int height){          
		this.width = width;
		this.height = height;
		node = new char[height][width];
		node_forprint= new char[height][width];
		for(int i = 0; i < height; i++)
			for(int j = 0;j < width; j++){
				node[i][j] = '1';
				node_forprint[i][j]='1';
			}
		x_block=new int[8];
		y_block=new int[8];
	}
	/*
	 *   default constructor
	 */
	public MazeMap(){}       
	
	public boolean is_loaded(){
		return is_loaded;
	}
	
	

	private void my_print() {
		for (int i = 0; i < 120; i++) {
			for (int j = 0; j < 160; j++) {
//				System.out.print(node[i][j]);
			}
//			System.out.println();
		}
	}

	private void eight_block() {
		for (int i = 0; i < 120; i++) {
			for (int j = 0; j < 160; j++) {
				node[i][j] = '1';
				node_forprint[i][j] = '1';
			}
		}

		for (int i = 0; i < 8; i++) {
			Random random = new Random();
			int y = random.nextInt(144) % (144 - 15 + 1) + 15;
			int x = random.nextInt(104) % (104 - 15 + 1) + 15;
			x_block[i]=x;
			y_block[i]=y;
			for (int m = -15; m <= 15; m++) {
				for (int n = -15; n <= 15; n++) {
					if (random.nextBoolean() == false){
						node[x + m][y + n] = '2';
					node_forprint[x + m][y + n] = '2';}
				}
			}
		}
	}

	private boolean generate_points(int[] a) {
		Random random = new Random();

		int x = random.nextInt(39) % (39 - 0 + 1) + 0;
		int y = random.nextInt(39) % (39 - 0 + 1) + 0;
		if (y > 19) {
			y += 120;
		}
		if (x > 19) {
			x += 80;
		}
		if((x==0&&y==0||x==119&&y==0||x==0&&y==159||x==119&&y==159))
				return false;
		if (node[x][y] != '0') {
			a[0] = x;
			a[1] = y;
			return true;
		}
		return false;

	}

	public void generate_sng() {
		int[] tmp = new int[2];
		while (generate_points(tmp) == false) {
		}
		x1 = tmp[0];
		y1 = tmp[1];
//		System.out.println("the start is " + x1 + " " + y1);
		while (true) {
			if (generate_points(tmp) == true) {
				x2 = tmp[0];
				y2 = tmp[1];
				if (((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)) >= 10000)
					break;
			}

		}
//		System.out.println("the goal is " + x2 + " " + y2);
		

	}
	
	public void print_start_goal_point(){
		try {		 
			
			   File file = new File("start_goal_" + start_goal_index + ".txt");	
			   if (!file.exists()) {
			    file.createNewFile();
			   }			 
			   FileWriter fw = new FileWriter(file.getAbsoluteFile());
			   BufferedWriter bw = new BufferedWriter(fw);
		
			   bw.write(""+x1+","+y1);
			   bw.newLine();
			   bw.write(""+x2+","+y2);
			   bw.newLine();
			  
			 
			   bw.close();			 
			   start_goal_index ++;
//			   System.out.println("Done");			 
			  } catch (IOException e) {
			   e.printStackTrace();
			  }
			 

	}
	
	private void updata(char tmp,int index,int i1,int j1){
		if(index==0){
			if(tmp=='a')
				node_forprint[i1][j1]='c';
			else if(tmp=='b')
				node_forprint[i1][j1]='d';
		}
		else if(index==1){
			if(tmp=='a'&&node_forprint[i1][j1]!='c'){
				node_forprint[i1][j1]='e';
			}
			else if(tmp=='b'&&node_forprint[i1][j1]!='d'){
				node_forprint[i1][j1]='f';
			}
		}
		else if(index==2){
			if(tmp=='a'&&node_forprint[i1][j1]!='c'&&node_forprint[i1][j1]!='e'){
				node_forprint[i1][j1]='g';
			}
			else if(tmp=='b'&&node_forprint[i1][j1]!='d'&&node_forprint[i1][j1]!='f'){
				node_forprint[i1][j1]='h';
			}
		}
		else {
			if(tmp=='a'&&node_forprint[i1][j1]!='c'&&node_forprint[i1][j1]!='e'&&node_forprint[i1][j1]!='g'){
				node_forprint[i1][j1]='i';
			}
			else if(tmp=='b'&&node_forprint[i1][j1]!='d'&&node_forprint[i1][j1]!='f'&&node_forprint[i1][j1]!='h'){
				node_forprint[i1][j1]='j';
			}
		}
	}
		
	
	private boolean four_highway(int index) {
//		System.out.println(index);
		char[][] node_tmp = new char[120][160];

		for (int i = 0; i < 120; i++) {
			for (int j = 0; j < 160; j++) {
				node_tmp[i][j] = node[i][j];
			}
		}

		for (int i = 0; i < 4; i++) {
			Random random = new Random();
			int m = random.nextInt(4) % (4 - 1 + 1) + 1;

			if (m == 1) {
				int count = 0;
				int x = random.nextInt(119) % (119 - 0 + 1) + 0;

				int y = 0;
				for (; y < 20; y++) {
					if (x > 119 || x < 0 || y > 159 || y < 0 || node_tmp[x][y] == 'a' || node_tmp[x][y] == 'b'
							|| x > 119 || x < 0 || y > 159 || y < 0)
						return false;
					if (node_tmp[x][y] == '1'){
						node_tmp[x][y] = 'a';
						
					}
					if (node_tmp[x][y] == '2'){
						node_tmp[x][y] = 'b';
					}
				}
				while (true) {
					double n = Math.random();
					if (n < 0.6) {
						for (int p = 0; p < 20; p++, y++) {
							if ((x > 119 || x < 0 || y > 159 || y < 0) && count >= 5) {
								for (int i1 = 0; i1 < 120; i1++) {
									for (int j1 = 0; j1 < 160; j1++) {
										node[i1][j1] = node_tmp[i1][j1];
										updata(node[i1][j1],index,i1,j1);
										
								}
								
							}return true;}

							if (x > 119 || x < 0 || y > 159 || y < 0 || node_tmp[x][y] == 'a' || node_tmp[x][y] == 'b')
								return false;
							if (node_tmp[x][y] == '1')
								node_tmp[x][y] = 'a';
							if (node_tmp[x][y] == '2')
								node_tmp[x][y] = 'b';
							}
					} else if (n < 0.8 && n > 0.6) {
						for (int p = 0; p < 20; p++, x++) {
							if ((x > 119 || x < 0 || y > 159 || y < 0) && count >= 5) {
								for (int i1 = 0; i1 < 120; i1++) {
									for (int j1 = 0; j1 < 160; j1++) {
										node[i1][j1] = node_tmp[i1][j1];
										updata(node[i1][j1],index,i1,j1);
									}
								}
								return true;
							}
							if (x > 119 || x < 0 || y > 159 || y < 0 || node_tmp[x][y] == 'a' || node_tmp[x][y] == 'b')
								return false;
							if (node_tmp[x][y] == '1')
								node_tmp[x][y] = 'a';
							if (node_tmp[x][y] == '2')
								node_tmp[x][y] = 'b';
						}
					} else {
						for (int p = 0; p < 20; p++, x--) {
							if ((x > 119 || x < 0 || y > 159 || y < 0) && count >= 5) {
								for (int i1 = 0; i1 < 120; i1++) {
									for (int j1 = 0; j1 < 160; j1++) {
										node[i1][j1] = node_tmp[i1][j1];
										updata(node[i1][j1],index,i1,j1);
									
									}
								}
								return true;
							}
							if (x > 119 || x < 0 || y > 159 || y < 0 || node_tmp[x][y] == 'a' || node_tmp[x][y] == 'b')
								return false;
							if (node_tmp[x][y] == '1')
								node_tmp[x][y] = 'a';
							if (node_tmp[x][y] == '2')
								node_tmp[x][y] = 'b';
						}
					}
					count++;
				}
			}
			if (m == 2) {
				int x = random.nextInt(119) % (119 - 0 + 1) + 0;
				int count = 0;

				int y = 159;
				for (; y > 139; y--) {
					if (x > 119 || x < 0 || y > 159 || y < 0 || node_tmp[x][y] == 'a' || node_tmp[x][y] == 'b'
							|| x > 119 || x < 0 || y > 159 || y < 0)
						return false;
					if (node_tmp[x][y] == '1')
						node_tmp[x][y] = 'a';
					if (node_tmp[x][y] == '2')
						node_tmp[x][y] = 'b';
				}
				while (true) {
					double n = Math.random();
					if (n < 0.6) {
						for (int p = 0; p < 20; p++, y--) {
							if ((x > 119 || x < 0 || y > 159 || y < 0) && count >= 5) {
								for (int i1 = 0; i1 < 120; i1++) {
									for (int j1 = 0; j1 < 160; j1++) {
										node[i1][j1] = node_tmp[i1][j1];
										updata(node[i1][j1],index,i1,j1);
									}
								}
								return true;
							}
							if (x > 119 || x < 0 || y > 159 || y < 0 || node_tmp[x][y] == 'a' || node_tmp[x][y] == 'b')
								return false;
							if (node_tmp[x][y] == '1')
								node_tmp[x][y] = 'a';
							if (node_tmp[x][y] == '2')
								node_tmp[x][y] = 'b';
						}
					} else if (n < 0.8 && n > 0.6) {
						for (int p = 0; p < 20; p++, x++) {
							if ((x > 119 || x < 0 || y > 159 || y < 0) && count >= 5) {
								for (int i1 = 0; i1 < 120; i1++) {
									for (int j1 = 0; j1 < 160; j1++) {
										node[i1][j1] = node_tmp[i1][j1];
										updata(node[i1][j1],index,i1,j1);
									}
								}
								return true;
							}
							if (x > 119 || x < 0 || y > 159 || y < 0 || node_tmp[x][y] == 'a' || node_tmp[x][y] == 'b')
								return false;
							if (node_tmp[x][y] == '1')
								node_tmp[x][y] = 'a';
							if (node_tmp[x][y] == '2')
								node_tmp[x][y] = 'b';
						}
					} else {
						for (int p = 0; p < 20; p++, x--) {
							if ((x > 119 || x < 0 || y > 159 || y < 0) && count >= 5) {
								for (int i1 = 0; i1 < 120; i1++) {
									for (int j1 = 0; j1 < 160; j1++) {
										node[i1][j1] = node_tmp[i1][j1];
										updata(node[i1][j1],index,i1,j1);
									}
								}
								return true;
							}
							if (x > 119 || x < 0 || y > 159 || y < 0 || node_tmp[x][y] == 'a' || node_tmp[x][y] == 'b')
								return false;
							if (node_tmp[x][y] == '1')
								node_tmp[x][y] = 'a';
							if (node_tmp[x][y] == '2')
								node_tmp[x][y] = 'b';
						}
					}
					count++;
				}
			}
			if (m == 3) {
				int y = random.nextInt(159) % (159 - 0 + 1) + 0;

				int count = 0;
				int x = 0;
				for (; x < 20; x++) {
					if (x > 119 || x < 0 || y > 159 || y < 0 || node_tmp[x][y] == 'a' || node_tmp[x][y] == 'b'
							|| x > 119 || x < 0 || y > 159 || y < 0)
						return false;
					if (node_tmp[x][y] == '1')
						node_tmp[x][y] = 'a';
					if (node_tmp[x][y] == '2')
						node_tmp[x][y] = 'b';
				}
				while (true) {
					double n = Math.random();
					if (n < 0.6) {
						for (int p = 0; p < 20; p++, x++) {
							if ((x > 119 || x < 0 || y > 159 || y < 0) && count >= 5) {
								for (int i1 = 0; i1 < 120; i1++) {
									for (int j1 = 0; j1 < 160; j1++) {
										node[i1][j1] = node_tmp[i1][j1];
										updata(node[i1][j1],index,i1,j1);
									}
								}
								return true;
							}
							if (x > 119 || x < 0 || y > 159 || y < 0 || node_tmp[x][y] == 'a' || node_tmp[x][y] == 'b')
								return false;
							if (node_tmp[x][y] == '1')
								node_tmp[x][y] = 'a';
							if (node_tmp[x][y] == '2')
								node_tmp[x][y] = 'b';
						}
					} else if (n < 0.8 && n > 0.6) {
						for (int p = 0; p < 20; p++, y++) {
							if ((x > 119 || x < 0 || y > 159 || y < 0) && count >= 5) {
								for (int i1 = 0; i1 < 120; i1++) {
									for (int j1 = 0; j1 < 160; j1++) {
										node[i1][j1] = node_tmp[i1][j1];
										updata(node[i1][j1],index,i1,j1);
									}
								}
								return true;
							}
							if (x > 119 || x < 0 || y > 159 || y < 0 || node_tmp[x][y] == 'a' || node_tmp[x][y] == 'b')
								return false;
							if (node_tmp[x][y] == '1')
								node_tmp[x][y] = 'a';
							if (node_tmp[x][y] == '2')
								node_tmp[x][y] = 'b';
						}
					} else {
						for (int p = 0; p < 20; p++, y--) {
							if ((x > 119 || x < 0 || y > 159 || y < 0) && count >= 5) {
								for (int i1 = 0; i1 < 120; i1++) {
									for (int j1 = 0; j1 < 160; j1++) {
										node[i1][j1] = node_tmp[i1][j1];
										updata(node[i1][j1],index,i1,j1);
									}
								}
								return true;
							}
							if (x > 119 || x < 0 || y > 159 || y < 0 || node_tmp[x][y] == 'a' || node_tmp[x][y] == 'b')
								return false;
							if (node_tmp[x][y] == '1')
								node_tmp[x][y] = 'a';
							if (node_tmp[x][y] == '2')
								node_tmp[x][y] = 'b';
						}
					}
					count++;
				}
			}
			if (m == 4) {
				int y = random.nextInt(159) % (159 - 0 + 1) + 0;

				int count = 0;
				int x = 119;
				for (; x > 99; x--) {
					if (x > 119 || x < 0 || y > 159 || y < 0 || node_tmp[x][y] == 'a' || node_tmp[x][y] == 'b'
							|| x > 119 || x < 0 || y > 159 || y < 0)
						return false;
					if (node_tmp[x][y] == '1')
						node_tmp[x][y] = 'a';
					if (node_tmp[x][y] == '2')
						node_tmp[x][y] = 'b';
				}
				while (true) {
					double n = Math.random();
					if (n < 0.6) {
						for (int p = 0; p < 20; p++, x--) {
							if ((x > 119 || x < 0 || y > 159 || y < 0) && count >= 5) {
								for (int i1 = 0; i1 < 120; i1++) {
									for (int j1 = 0; j1 < 160; j1++) {
										node[i1][j1] = node_tmp[i1][j1];
										updata(node[i1][j1],index,i1,j1);
									}
								}
								return true;
							}
							if (x > 119 || x < 0 || y > 159 || y < 0 || node_tmp[x][y] == 'a' || node_tmp[x][y] == 'b')
								return false;
							if (node_tmp[x][y] == '1')
								node_tmp[x][y] = 'a';
							if (node_tmp[x][y] == '2')
								node_tmp[x][y] = 'b';
						}
					} else if (n < 0.8 && n > 0.6) {
						for (int p = 0; p < 20; p++, y++) {
							if ((x > 119 || x < 0 || y > 159 || y < 0) && count >= 5) {
								for (int i1 = 0; i1 < 120; i1++) {
									for (int j1 = 0; j1 < 160; j1++){
										node[i1][j1] = node_tmp[i1][j1];
										updata(node[i1][j1],index,i1,j1);
									}
								}
								return true;
							}
							if (x > 119 || x < 0 || y > 159 || y < 0 || node_tmp[x][y] == 'a' || node_tmp[x][y] == 'b')
								return false;
							if (node_tmp[x][y] == '1')
								node_tmp[x][y] = 'a';
							if (node_tmp[x][y] == '2')
								node_tmp[x][y] = 'b';
						}
					} else {
						for (int p = 0; p < 20; p++, y--) {
							if ((x > 119 || x < 0 || y > 159 || y < 0) && count >= 5) {
								for (int i1 = 0; i1 < 120; i1++) {
									for (int j1 = 0; j1 < 160; j1++) {
										node[i1][j1] = node_tmp[i1][j1];
										updata(node[i1][j1],index,i1,j1);
									}
								}
								return true;
							}
							if (x > 119 || x < 0 || y > 159 || y < 0 || node_tmp[x][y] == 'a' || node_tmp[x][y] == 'b')
								return false;
							if (node_tmp[x][y] == '1')
								node_tmp[x][y] = 'a';
							if (node_tmp[x][y] == '2')
								node_tmp[x][y] = 'b';
						}
					}
					count++;
				}
			}
		}
		return false;
	}

	public void generate_file() {
		eight_block();
		

		int count = 0;
		while (count < 4) {
			if (four_highway(count) == true) {
				count++;
			}
		}

		entire_block();
        is_loaded = true;
        print_file();
//		my_print();

	}

	public void print_file() {
		try {		 
		
			   File file = new File("mapinformation.txt");	
			   if (!file.exists()) {
			    file.createNewFile();
			   }			 
			   FileWriter fw = new FileWriter(file.getAbsoluteFile());
			   BufferedWriter bw = new BufferedWriter(fw);
		
			   bw.write(""+x1+","+y1);
			   bw.newLine();
			   bw.write(""+x2+","+y2);
			   bw.newLine();
			   for(int i=1;i<9;i++){
				   bw.write(""+x_block[i-1]+","+y_block[i-1]);
				   bw.newLine();
			   }
			   for(int i=0;i<120;i++){
				   for(int j=0;j<160;j++){
					   if(node_forprint[i][j]=='c')
					   bw.write(""+"a1"+",");
					   else if(node_forprint[i][j]=='d')
						   bw.write(""+"b1"+",");
					   else if(node_forprint[i][j]=='e')
						   bw.write(""+"a2"+",");
					   else if(node_forprint[i][j]=='f')
						   bw.write(""+"b2"+",");
					   else if(node_forprint[i][j]=='g')
						   bw.write(""+"a3"+",");
					   else if(node_forprint[i][j]=='h')
						   bw.write(""+"b3"+",");
					   else if(node_forprint[i][j]=='i')
						   bw.write(""+"a4"+",");
					   else if(node_forprint[i][j]=='j')
						   bw.write(""+"b4"+",");
					   else
						   bw.write(""+node_forprint[i][j]+",");
				   }
				   bw.newLine();
			   }
			   bw.close();			 
//			   System.out.println("Done");			 
			  } catch (IOException e) {
			   e.printStackTrace();
			  }
			 

	}

	private void entire_block() {
		for (int i = 0; i < 120; i++) {
			for (int j = 0; j < 160; j++) {
				if (Math.random() < 0.2) {
					if (node[i][j] != 'a' && node[i][j] != 'b'){
						node[i][j] = '0';
					node_forprint[i][j] = '0';
					}
				}
			}
		}
	}

	public void import_start_goal(File file){
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 0;
            tempString = reader.readLine();
            String[] start_cor=tempString.split(",");
            x1=Integer.parseInt(start_cor[0]);
            y1=Integer.parseInt(start_cor[1]);
            tempString = reader.readLine();
            String[]goal_cor=tempString.split(",");
            x2=Integer.parseInt(goal_cor[0]);
            y2=Integer.parseInt(goal_cor[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void import_file(File file) {
		//File file = new File("mapinformation.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 0;
            tempString = reader.readLine();
            String[] start_cor=tempString.split(",");
            x1=Integer.parseInt(start_cor[0]);
            y1=Integer.parseInt(start_cor[1]);
            tempString = reader.readLine();
            String[]goal_cor=tempString.split(",");
            x2=Integer.parseInt(goal_cor[0]);
            y2=Integer.parseInt(goal_cor[1]);
            for(int i=0;i<8;i++){
            	tempString = reader.readLine();
            }
            while ((tempString = reader.readLine()) != null) {
                for(int j=0;j<160;j++){
                	String[] tmp_col=tempString.split(",");
                	if(tmp_col[j]=="a1"||tmp_col[j]=="a2"||tmp_col[j]=="a3"||tmp_col[j]=="a4")
                		node[line][j]='a';
                	else if(tmp_col[j]=="b1"||tmp_col[j]=="b2"||tmp_col[j]=="b3"||tmp_col[j]=="b4")
                		node[line][j]='b';
                	else
                		node[line][j]=tmp_col[j].charAt(0);
//                	System.out.print(node[line][j]);
                }
                line++;
//                System.out.println();
                
            }
            is_loaded = true;
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

	}
	
	
	
	
	
	
	
	
	
	
	/*
	 *  random generate the maze map
	 */
//	public void generateMap(){
//		int[] random_x = new int[8];
//		int[] random_y = new int[8];
//		
//		// set the 8 centers for the hard traverse cells
//		for(int i = 0;i < 8;i++){
//			random_x[i] = (int) Math.round(Math.random() * width);
//			random_y[i] = (int) Math.round(Math.random() * height);
//		}
//		// set the hard traverse cells
//		for(int i = 0;i < 8;i++){
//			for(int j = -15; j < 15; j++){
//				for(int k = -15;k< 15;k++){
//					if(random_x[i] + j < width && random_x[i] + j >= 0 && random_y[i] + k < height && random_y[i] + k >= 0){   // make sure the selected cell is within the map
//						if(Math.random() > 0.5){
//							node[random_x[i]+j][random_y[i] + k] = 1; 
//						}
//					}
//				}
//				
//			}
//		}
//		// generate highway path
//		for(int i = 0;i < 4;i ++)
//			hPath.add(new HashSet());
//		for(int i = 0; i < 4; i++){
//			int start = (int) Math.floor(Math.random()*(width + width + height + height - 8));
//			// start from the upper bound of the map
//			if(start < width - 2){
//				extendHighway(new Point(start+1, 0));
//				
//				boolean goStraight = (Math.random() < 0.6)? true:false;
//				if(goStraight){
//					
//				}
//				
//		    // start from the right bound of the map
//			}else if(start >= width - 2 && start < width + height - 4){
//			
//			// start from the bottom bound of the map
//			}else if(start >= width + height - 4 && start < width + width + height - 6){
//			
//			// start from the left bound of the map	
//			}else if(start >= width*2 + height - 6 && start < width * 2 + height * 2 - 8){
//				
//			}
//		}
//		
//		
//		
//		
//	}
//	
//
//	public void importMap(){
//		
//	}
//	/*
//	 *  detects existing highway and map boundary
//	 *  @param start_point  ---- start point of the next 20 cells of highway
//	 *         direction    ---- 0  move downward
//	 *                           1  move upperward
//	 *                           2  move rightward
//	 *                           3  move leftward
//	 */
//	private int extendHighway(Point start_point, int direction, int path_index){
//		
//		HashSet h = (HashSet)hPath.get(path_index);
//		int start_x = (int) start_point.getX();
//		int start_y = (int) start_point.getY();
//		if(direction == 0){
//			for(int i = 1;i <= 20;i ++){
//				if(start_y + i >= height){
//					if(h.size()>=100){
//						
//					}else{
//						h.clear();
//						return 1; 
//						
//					}
//				}
//				if(!highwaydCells.contains(new Point(start_x, start_y + i))){
//					h.add(new Point(start_x, start_y + i));
//					
//				}else{
//					h.clear();
//				}
//			}
//			
//		}else if(direction == 1){
//			for(int i = 1;i <= 20;i ++){
//				if(!highwayCells.contains(new Point(start_x, start_y - i))){
//					h.add(new Point(start_x, start_y - i));
//				}else{
//					h.clear();
//				}
//			}
//		}else if(direction == 2){
//			for(int i = 1;i <= 20;i ++){
//				if(!highwayCells.contains(new Point(start_x+i, start_y))){
//					h.add(new Point(start_x+i, start_y));
//				}else{
//					h.clear();
//				}
//			}
//		}else if(direction == 3){
//			for(int i = 1;i <= 20;i ++){
//				if(!highwayCells.contains(new Point(start_x-i, start_y))){
//					h.add(new Point(start_x-i, start_y));
//				}else{
//					h.clear();
//				}
//			}
//		}
//	
//		
//		return ;
//	}
}