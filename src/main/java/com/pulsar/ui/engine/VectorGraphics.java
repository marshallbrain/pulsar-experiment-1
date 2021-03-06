package ui.engine;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import math.Other;
import pulsar.Main;
import ui.engine.Point;
import ui.engine.vectors.Vector;

public class VectorGraphics {
	
	private Graphics2D graphics;
	private Graphics2D graphicsOriginal;
	
	private AffineTransform screenTransform;
	
	private Map<String, Area> areaLog;
	
	private AffineTransform affineTransform;

	public VectorGraphics(Graphics g) {
		
		graphicsOriginal = (Graphics2D) g;
		graphics = (Graphics2D) graphicsOriginal.create();
		screenTransform = new AffineTransform();
		
		init(graphics);
		
	}
	
	private void init(Graphics2D g) {
		
		g.clipRect(0, 0, Main.WIDTH, Main.HEIGHT);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	public Graphics2D getGraphics() {
		return graphics;
	}
	
	public void saveTransform() {
		affineTransform = graphics.getTransform();
	}
	
	public void revertTransform() {
		graphics.setTransform(affineTransform);
	}

	public void translationSet(Point o) {
		translationSet(ScreenPosition.ZERO);
		translationMove(o);
	}

	public void translationSet(ScreenPosition pos) {
		
		Point p;
		
		switch(pos) {
			case CENTER:
				p = new Point(Main.WIDTH/2, Main.HEIGHT/2);
				break;
			case ZERO:
				p = new Point(0, 0);
				break;
			default:
				return;
		}
		
		screenTransform = new AffineTransform();
		screenTransform.translate(p.getX(), p.getY());
		
	}

	public void translationMove(Point o) {
		screenTransform.translate(o.getX(), o.getY());
	}

	public void draw(String id, Shape s, Map<String, String> m) {
		
//		Area a = getArea(s, Main.WIDTH, Main.HEIGHT);
		
		if(s == null) {
			return;
		}
		
		s = screenTransform.createTransformedShape(s);
		
		if(areaLog != null) {
			Area a = new Area(s);
			Area p = areaLog.putIfAbsent(id, a);
			if(p != null) {
				a.add(p);
				areaLog.put(id, a);
			}
		}
		
		if(m != null) {
			
			String fill = m.get("fill");
			if(!(fill == null || fill.equals("none"))) {
				
				String alpha = m.get("fill-opacity");
				if(alpha == null)
					alpha = "1";
				
				graphics.setColor(Other.getColor(fill, alpha));
				graphics.fill(s);
				
			}
			
			String stroke = m.get("stroke");
			if(!(stroke == null || stroke.equals("none"))) {
				
				String alpha = m.get("stroke-opacity");
				if(alpha == null)
					alpha = "1";
				
				String width = m.get("stroke-width");
				if(width == null)
					width = "1";
				BasicStroke bs = new BasicStroke(Integer.parseInt(width));
				
				graphics.setColor(Other.getColor(stroke, alpha));
				graphics.setStroke(bs);
				graphics.draw(s);
				
			}
			
		}
		
	}

	public Area getArea(Shape s, int width, int height) {
		
		if(s == null) {
			return null;
		}
		
		Area a = new Area(s);
		Area b = new Area(new Rectangle2D.Double(0, 0, width, height));
		try {
			b.transform(graphics.getTransform().createInverse());
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}
		
		a.intersect(b);
		
		return a;
		
	}
	
	public void startLogArea() {
		areaLog = new HashMap<String, Area>();
	}
	
	public Map<String, Area> stopLogArea() {
		Map<String, Area> a = new HashMap<String, Area>(areaLog);
		areaLog.clear();
		if(!a.isEmpty()) {
			return a;
		}
		return null;
	}

}
