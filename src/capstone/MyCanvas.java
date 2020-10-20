package capstone;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.util.Vector;
import java.util.Set;
import java.util.HashSet;

/**
 * 
 * @author nkolk
 *  main class to take point input data from fileIO, and using Point, Edge, doLinesIntersect
 *  calculate the convex hull and triangulation of the polygon it generates
 */
public class MyCanvas
{
	int nlength = 0;
    JLabel view;
    BufferedImage surface;
    static Random random = new Random();

    //point set and array to hold generated points
    static Set<Point> pSet = new HashSet<Point>();
    static Point[] p;
    //vector to hold hull points
    static Vector<Point> hull = new Vector<Point>();
    //vector to hold hull edges
    static Vector<Edge> hullEdges = new Vector<Edge>();
    static FileIO fileIO = new FileIO();
    static Vector<Edge> AllPotentialEdges = new Vector<Edge>();
    static Vector<Edge> triangEdge = new Vector<Edge>();

    public static void main(String[] args) throws IOException
    {
        MyCanvas canvas = new MyCanvas();
        JFrame frame = new JFrame(); 
        frame.setSize(1000,1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(canvas.view);
        frame.pack();
        frame.setVisible(true);
        frame.repaint();
    }
   
    public MyCanvas()
    {
    	FileIO.readFile("file.txt", pSet);
    	p=new Point[pSet.size()];
        pSet.toArray(p);
        if(pSet.size()==2) {
        	System.out.println("Need at least 3 points to run program.");
        }
        else {
        System.out.println("Given points in file: "+pSet);
        surface = new BufferedImage(1000,1000,BufferedImage.TYPE_INT_RGB);
        view = new JLabel(new ImageIcon(surface));
        Graphics g = surface.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0,0,1000,1000);
        for(Point temp : pSet) {
        	int x = temp.getX();
        	int y = temp.getY();
            g = surface.getGraphics();
            //add point to image
            drawPoint(x,y,g);	 
        }
        
        // calculate vector of hull points and construct edges of convex hull
        int n = p.length;
        convexHull(p,n);
    	g.setColor(Color.RED);
    	//start at first point
    	Point p1 = hull.get(0);
        for(int j = 1; j < hull.size();j++) {
        	//second point starts at second point in convex hull
        	Point p2 = hull.get(j);
        	//create temp edge from two points and add to vector, change point
        	//values
        	Edge temp = new Edge(p1,p2);
        	hullEdges.add(temp);
        	//change point values
        	p1 = p2;
        }
        hullEdges.add(new Edge(new Point(hull.get(hull.size()-1).getX(), hull.get(hull.size()-1).getY()),
        		new Point( hull.get(0).getX(),hull.get(0).getY())));
        //check if hull edges array filled
        System.out.println("Convex Hull Edges: ");
        for(int e = 0; e < hullEdges.size();e++) {
        	Edge aTemp = hullEdges.get(e);
        	System.out.println("Edge " + e + ": "+ aTemp.toString());
        }
    	System.out.println("Number of all potential edges before adding edges: "+AllPotentialEdges.size());
    	
    	for(int i1  =0; i1 < p.length;i1++) {	
    		for(int j = i1+1; j < p.length;j++) {
    			Edge tempedge = new Edge(p[i1],p[j]);   			
    			AllPotentialEdges.add(tempedge);
    			
    		}
    	}
    	System.out.println("Number of all potential edges after adding edges: "+ AllPotentialEdges.size());
 	
    	//develop triangulation solution
    	System.out.println("Number of triangulation edges before adding edges: " +triangEdge.size());
    	for(Edge wEdge : AllPotentialEdges) {
    		if(triangulation(triangEdge, wEdge)==false) {
    			triangEdge.add(wEdge);
    			System.out.println("Number of triangulation edges after adding edge: " +triangEdge.size()+ " Edge Added: "+ wEdge);
    		}
    			
    	}
    	//print triangulation to the screen
    	for(Edge temp : triangEdge) {
    		Point p4= temp.getPoint1();
    		Point p5 = temp.getPoint2();
			g.drawLine(p4.getX(),p4.getY(),p5.getX(),p5.getY());			
    	}    	
        }
    }
    
    
	//boolean to test next edge for triangulation against other edges within triangulation
    public static boolean triangulation(Vector<Edge> vecAll, Edge vea) {
    	if(hullEdges.contains(vea)) {
    		return false;
    	}
    	for(Edge edg : vecAll) {    				
    				if(doLinesIntersect.intersect(vea,edg)) {
    					System.out.println("Cannot add edge:" +vea +" \nEdges: " +vea + " and " + edg +" cross");
    					return true;
    				}    				
    		}    	
    	return false;	
    }
   
//    //draws points
    public void drawPoint(int x, int y, Graphics g)
    {
    		//x and y location for point
            int xLoc = x;
            int yLoc = y;
            //set point color and image
            g.setColor(Color.blue);
            g.fillOval(xLoc, yLoc, 8, 8);
            g.drawOval(xLoc, yLoc, 8, 8);
            
    }
 
    //direction to start 
    public static int direction(Point p, Point q, Point r) 
    { 
        int val = (q.getY() - p.getY()) * (r.getX() - q.getX()) - 
                  (q.getX() - p.getX()) * (r.getY() - q.getY()); 
        if (val == 0) {
        	return 0; 
        }
        if (val > 0) {
        	return 1;  
        }
        return 2;
    } 
    
    // convex hull looping 
    public static void convexHull(Point points[], int n) 
    {    	
        //three points to calculate
        if (n < 3) {
        	return; 
        }
        //leftmost point 
        int leftmost = 0; 
        for (int i = 1; i < n; i++) {
            if (points[i].getX() < points[leftmost].getX()) 
                leftmost = i; 
        }  
        int p = leftmost, q; 
        //loop through until reached first point again
        do
        { 
            hull.add(points[p]);            
            q = (p + 1) % n;               
            for (int i = 0; i < n; i++) 
            { 
               if (direction(points[p], points[i], points[q]) == 2) 
                   q = i; 
            }        
            p = q;        
        } while (p != leftmost);      
        //prints points that are apart of convex hull
    } 
	
}