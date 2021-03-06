package ui.engine.vectors;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import ui.engine.Point;
import ui.engine.ScreenPosition;
import ui.engine.VectorGraphics;

@XmlRootElement(name = "tab_item")
public class TabItem {
	
	@XmlAttribute(name = "text")
	private String text;
	private String id;
	private String view;
	
	private static VectorGroup staticVector;
	private VectorGroup vector;

	private List<Integer> offset;
	private Map<String, String> style;
	
	public static void setVectors(VectorGroup v) {
		staticVector = v;
	}
	
	public String getId() {
		return id;
	}
	
	public void setName(String t) {
		text = t;
	}

	public int draw(String i, int pos, VectorGraphics vg) {
		
		if(vector == null) {
			vector = staticVector.clone();
		}
		
		id = i;
		vg.translationMove(new Point(pos, 0));
		for(Vector v: vector.getVectors()) {

			if(v instanceof TextRegion) {
				
				TextRegion tr = (TextRegion) v;
				tr.setText(text);
				//TODO change the draw method to have id parameter and remove setCurrentGraphics
				tr.setCurrentGraphics(vg.getGraphics());
				Shape b = tr.getBound().getShape();
				vg.draw(id, b, tr.getBound().getStyle());
				pos = (int) Math.floor(b.getBounds().getWidth());
				
			}
			
			vg.draw(id, v.getShape(), v.getStyle());
			
		}
		
		return pos;
		
	}
	
	public Shape getShape() {
		return null;
	}
	
	public Map<String, String> getStyle() {
		return style;
	}
	
	private Map<String, String> convertStyle(String s) {
		
		if(s == null) {
			return null;
		}
		
		Map<String, String> style = new HashMap<String, String>();
		
		for(String e: s.split(";")) {
			
			String key = e.split(":")[0];
			String value = e.split(":")[1];
			
			style.put(key, value);
			
		}
		
		return style;
		
	}

	@XmlAttribute(name = "view")
	public String getView() {
		return view;
	}

	public void setView(String v) {
		view = v;
	}
	
}
