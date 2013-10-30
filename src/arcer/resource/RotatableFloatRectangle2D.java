package arcer.resource;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;
import java.awt.Polygon;
import java.lang.StrictMath;

public class RotatableFloatRectangle2D extends Polygon {
	//WARNING: UNTESTED. REMOVE THIS WHEN IT IS TESTED
	private  Point2D.Float tl, tr, bl, br;
	private float angle=0; //angle between x axis and (center point of rect and middle of right side)
	public RotatableFloatRectangle2D(java.awt.geom.Point2D.Float tl,
			java.awt.geom.Point2D.Float tr, java.awt.geom.Point2D.Float br,
			java.awt.geom.Point2D.Float bl) {
		super();
		this.tl = tl;
		this.tr = tr;
		this.bl = bl;
		this.br = br;
	}
	public RotatableFloatRectangle2D(java.awt.geom.Point2D.Float tl,
			java.awt.geom.Point2D.Float tr, java.awt.geom.Point2D.Float bl,
			java.awt.geom.Point2D.Float br, float angle) {
		super();
		this.tl = tl;
		this.tr = tr;
		this.bl = bl;
		this.br = br;
		this.angle = angle;
	}
	private Point2D.Float rotatePoint(Point2D.Float p, float angle){
		float x=p.x, y=p.y;
		float x1= (float) (x*StrictMath.cos(angle)-y*StrictMath.sin(angle));
		float y1= (float) (x*StrictMath.sin(angle)+y*StrictMath.cos(angle));
		return new Point2D.Float(x1, y1);	
	}
	public void rotate(float angle_counterclockwise){
		tl=rotatePoint(tl, angle_counterclockwise);
		tr=rotatePoint(tr, angle_counterclockwise);
		bl=rotatePoint(bl, angle_counterclockwise);
		br=rotatePoint(br, angle_counterclockwise);
		this.angle= this.angle+angle_counterclockwise;
	}
	public Point2D.Float[] getPoints(){
		//returns an array of points, starting from the original top-left corner, and going clockwise
		Point2D.Float[] arr={this.tl, this.tr, this.br, this.bl};
		return arr;
	}
	
	

		


}
