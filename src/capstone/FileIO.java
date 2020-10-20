package capstone;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Set;
/**
 * 
 * @author nkolk
 * class to get file input and perform functions on
 */
public class FileIO {
	public static Set<Point> readFile(String filename, Set<Point> setp) {
		try {
	        BufferedReader in = new BufferedReader(
	                               new FileReader(filename));
	        String str;
	       
	        while ((str = in.readLine())!= null) {
	            String[] ar=str.split(",");
	            int xint = Integer.parseInt(ar[0]);
	            int yint = Integer.parseInt(ar[1]);
	            Point thisPoint = new Point(xint, yint);
				setp.add(thisPoint);
			
	        }
	        in.close();
	    } catch (Exception e) {
	        System.out.println("File Read Error, in put must be in format: Line 1:230,50 Line 2:40,621 with no spacing between"
	        		+ " and so on");
	    }
		return setp;
	}
	

}
