package capstone;
/**
 * 
 * @author nkolk
 * Class to test intersection between lines
 */
public class doLinesIntersect {
	
	public static final double EPSILON = 0.000001;

    //calculate the cross product of two points.
    public static double crossProduct(Point p1, Point p2) {
        return p1.getX() * p2.getY() - p2.getX() * p1.getY();
    }

    //check if area around line intersect
    public static boolean check(Point[] pt1, Point[] pt2) {
       if(pt1[0].getX() <= pt2[1].getX() && pt1[1].getX() >= pt2[0].getX() && pt1[0].getY() <= pt2[1].getY() && pt1[1].getY() >= pt2[0].getY()) {
    	   return true;
       }
       return false;
    }

    // checks if a Point is on a line
    public static boolean onLine(Edge e1, Point e2) {
        Edge newEdge = new Edge(new Point(0, 0), new Point(e1.getPoint2().getX() - e1.getPoint1().getX(), e1.getPoint2().getY() - e1.getPoint1().getY()));
        Point newPoint = new Point(e2.getX() - e1.getPoint1().getX(), e2.getY() - e1.getPoint1().getY());
        double crossRes = crossProduct(newEdge.getPoint2(), newPoint);
        if(e2.equals(e1.getPoint1())||e2.equals(e1.getPoint2())) {
        	return false;
        }
       if(Math.abs(crossRes) < EPSILON) {
    	   return true;
       }
       return false;
    }

    //check if given point is right of line, uses crossProduct
    public static boolean isPointRight(Edge e1, Point e2) {
        Edge newEdge = new Edge(new Point(0, 0), new Point(e1.getPoint2().getX() - e1.getPoint1().getX(), e1.getPoint2().getY() - e1.getPoint1().getY()));
        Point newPoint = new Point(e2.getX() - e1.getPoint1().getX(), e2.getY() - e1.getPoint1().getY());
        if(crossProduct(newEdge.getPoint2(), newPoint) < 0) {
        	return true;
        }
        return false;
    }
    
    // check if first line crosses second
    public static boolean lineCrossesLine(Edge e1, Edge e2) {
        if(onLine(e1, e2.getPoint1()) || onLine(e1, e2.getPoint2()) || (isPointRight(e1, e2.getPoint1()) ^ isPointRight(e1,e2.getPoint2()))) {
        	return true;
        }
        return false;
    }
    //check if lines intersect
    public static boolean intersect(Edge e1, Edge e2) {
        Point[] a1 = e1.getArea();
        Point[] a2 = e2.getArea();
        if(check(a1, a2) && lineCrossesLine(e1, e2) && lineCrossesLine(e2, e1)) {
        	return true;
        }
        return false;
    }
}
