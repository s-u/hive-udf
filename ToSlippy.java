package com.att.research.HiveUDF;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;

public class ToSlippy extends UDF {
    public static double [] ll2xy(final double lat, final double lon, final int zoom) {
	double pzoom = 1 << zoom;
	double xtile = (lon + 180.0) / 360.0 * pzoom;
	double ytile = (1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 / Math.cos(Math.toRadians(lat))) / Math.PI) / 2.0 * pzoom;
	if (xtile < 0)
	    xtile = 0;
	if (xtile >= pzoom)
	    xtile = (pzoom - 1);
	if (ytile < 0)
	    ytile=0;
	if (ytile >= pzoom)
	    ytile=(pzoom - 1);
	double res[] = { xtile, ytile };
	return res;
    }

    public Text evaluate(double lat, double lon, IntWritable zoom) {
	//if (lat == null || lon == null) return null;
	int zoom_ = (zoom == null) ? 19 : zoom.get();
	double pzoom = 1 << zoom_;
	double xy[] = ll2xy(lat, lon, zoom_);
	return new Text(((int)Math.floor(xy[0]))+ "," +
			((int)Math.floor(xy[1])));
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
