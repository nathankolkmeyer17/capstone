package capstone;
/**
 * 
 * @author nkolk
 * class to create and utilize edge throughout
 */
public class Edge {
	private Point p1;
	private Point p2;
	//edge constructor
	public Edge(Point p1, Point p2) {
		this.p1 = p1;
		this.p2= p2;
	}
	//edge length method
	public double length() {
		double dx = p1.getX() - p2.getX();
		double dy = p1.getY() - p2.getY();
		return Math.sqrt(dx * dx + dy * dy);
	}
	//return point 1 of edge
	public Point getPoint1()
	{
		return p1;
	}
	//returns point 2 of edge
	public Point getPoint2() {
		return p2;
	}
	//used to check line intersection, utilizing area around it
    public Point[] getArea() {
        Point[] result = new Point[2];
        result[0] = new Point(Math.min(p1.getX(), p2.getX()), Math.min(p1.getY(),
                p2.getY()));
        result[1] = new Point(Math.max(p1.getX(), p2.getX()), Math.max(p1.getY(),
                p2.getY()));
        return result;
    }
    //equals comparison function between two edges
	public boolean equals(Object aEdge) {
		Edge other = (Edge)aEdge;
		return (this.p1.equals(other.p1)&& this.p2.equals(other.p2)) 
				|| (this.p1.equals(other.p2)&& this.p2.equals(other.p1));
	}
	//string method to print edge
	public String toString() {
		return p1.toString() + "---" + p2.toString();
	}
}
