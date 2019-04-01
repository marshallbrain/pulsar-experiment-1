package ui.engine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import pulsar.Main;

public class VectorGraphics {
	
	private Graphics2D graphics;
	private Point screenPosition;

	public VectorGraphics(Graphics g) {
		graphics = (Graphics2D) g;
		screenPosition = new Point(0, 0);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	public Graphics getGraphics() {
		return graphics;
	}

	public void translationSet(ScreenPosition pos) {
		switch(pos) {
		case CENTER:
			Point newPos = new Point(Main.WIDTH/2, Main.HEIGHT/2);
			graphics.translate(newPos.getX()-screenPosition.getX(), newPos.getY()-screenPosition.getY());
			screenPosition = newPos;
			break;
		default:
			break;
		}
	}

	public void draw(Vector v) {
		
		graphics.setColor(v.getFillColor());
		
		switch(v.getType()) {
		case "circle":
			drawCircle((Circle) v);
			break;
		}
		
	}
	
	public void drawCircle(Circle c) {
		int cx = c.getCenterX();
		int cy = c.getCenterY();
		int r = Math.toIntExact(c.getRadius());
//		Arc2D circle = drawVisibleArc(cx, cy, r, -Main.WIDTH/2, -Main.HEIGHT/2, Main.WIDTH/2, Main.HEIGHT/2);
		List<Arc2D> arcs = drawVisibleArc(cx, cy, 120, -Main.WIDTH/2, -Main.HEIGHT/2, Main.WIDTH/2, Main.HEIGHT/2);
		
		Path2D cutCircle = new Path2D.Double();
		for(Arc2D a: arcs) {
			cutCircle.append(a, true);
		}
		cutCircle.closePath();
		graphics.fill(cutCircle);
		
	}
	
	private List<Arc2D> drawVisibleArc(int cx, int cy, int r, int minX, int minY, int maxX, int maxY) {
		
		int[] lines = new int[] {maxX, maxY, minX, minY};
		List<Double> angles = new ArrayList<Double>();
		List<Arc2D> arcs = new ArrayList<Arc2D>();
		
		for(int i = 0; i < lines.length; i++) {
			
			int v = lines[i];
			double z = Math.sqrt(Math.pow(r, 2)-Math.pow(Math.abs(v), 2));
			
			if(!Double.isNaN(z)) {
				
				double startAngle = 0;
				double endAngle = 0;
				
				if(i%2 == 0) {
					startAngle = Math.toDegrees(Math.atan2(z, v));
					endAngle = Math.toDegrees(Math.atan2(-z, v));
				} else {
					startAngle = Math.toDegrees(Math.atan2(v, z));
					endAngle = Math.toDegrees(Math.atan2(v, -z));
				}
				
				angles.add(Math.min(startAngle, endAngle));
				angles.add(Math.max(startAngle, endAngle));
				
			}
			
		}
		
		for(int i = 1; i < angles.size(); i+=2) {
			
			double startAngle = 0;
			double endAngle = 0;
			
			startAngle = angles.get(i);
			if(i+1 >= angles.size()) {
				endAngle = angles.get(0);
			} else {
				endAngle = angles.get(i+1);
			}
			
			if(endAngle < 0) {
				endAngle += 360;
			}
			
			endAngle -= startAngle;
			
			Arc2D arc = new Arc2D.Double(cx-r, cy-r, r*2, r*2, startAngle, endAngle, Arc2D.OPEN);
			arcs.add(arc);
			
		}
		
		if(arcs.isEmpty()) {
			Arc2D arc = new Arc2D.Double(cx-r, cy-r, r*2, r*2, 0, 360, Arc2D.OPEN);
			arcs.add(arc);
		}
		
		return arcs;
		
	}

}
