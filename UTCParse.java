package com.att.research.HiveUDF;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;

public class UTCParse extends UDF {
    public double evaluate(Text date) {
	try {
	    String comp[] = date.toString().split("[^0-9]+");
	    int n = comp.length;
	    int y = (n > 0) ? Integer.parseInt(comp[0]) : 0;
	    int m = (n > 1) ? Integer.parseInt(comp[1]) : 0;
	    int d = (n > 2) ? Integer.parseInt(comp[2]) : 0;
	    int h = (n > 3) ? Integer.parseInt(comp[3]) : 0;
	    int mi = (n > 4) ? Integer.parseInt(comp[4]) : 0;
	    double s = (n > 5) ? Double.parseDouble(comp[5]) : 0.0;
	    if (y > 1900) y -= 1900;
	    double ts = (double) java.util.Date.UTC(y, m - 1, d, h, mi, 0);
	    ts = ts / 1000 + s;
	    return ts;
	} catch (NumberFormatException ex) {
	    return -1.0;
	}
    }

    /*    
    public static void main(String arg[]) {
	double lat = java.lang.Double.parseDouble(arg[1]);
	double lon = java.lang.Double.parseDouble(arg[2]);
	int zoom = java.lang.Integer.parseInt(arg[3]);
	double xy[] = ll2xy(lat, lon, zoom);
	System.out.println("("+lat+", "+lon+", "+zoom+") -> ("+xy[0]+", "+xy[1]+")");
    }*/
}
