package ui.engine.vectors;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;

import ui.engine.Point;
import ui.engine.VectorGraphics;

@XmlRootElement(name = "tab_layout")
public class TabLayout implements Vector {
	
	@XmlAttribute(name = "x")
	private int y;
	@XmlAttribute(name = "y")
	private int x;
	private int currentTab;
	private int renderedTab;
	
	@XmlAttribute(name = "id")
	private String id;
	
	@XmlAnyElement(lax = true)
	private List<TabItem> tabs;
	
	public TabLayout() {
		tabs = new ArrayList<TabItem>();
		renderedTab = -1;
	}
	
	public static void setVectors(Map<String, VectorGroup> vectorList) {
		
		VectorGroup s = vectorList.get("view.tab.static");
		TabItem.setVectors(s);
		
	}
	
	public List<TabItem> getTabs() {
		return tabs;
	}
	
	public void add(TabItem t) {
		tabs.add(t);
	}
	
	public void setActive(Integer i) {
		currentTab = i;
		renderedTab = -1;
	}
	
	@Override
	public String getType() {
		return "tab_layout";
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getAction(String id, String action) {
		
		if(currentTab != renderedTab) {
			
			renderedTab = currentTab;
			TabItem t = tabs.get(renderedTab);
			return "key setView " + t.getView();
			
		}
		
		switch(action) {
			case "right click":
				currentTab = Integer.parseInt(id.split(" ")[1]);
				renderedTab = currentTab;
				TabItem t = tabs.get(renderedTab);
				return "key setView " + t.getView();
			default:
				return null;
		}
		
	}

	@Override
	public Shape getShape() {
		return null;
	}

	@Override
	public Map<String, String> getStyle() {
		return null;
	}

	@Override
	public void inherit(Vector v) {
	}

	@Override
	public void draw(VectorGraphics vg) {
		int pos = 0;
		for(int i = 0; i < tabs.size(); i++) {
			pos = tabs.get(i).draw(id + " " + String.valueOf(i), pos, vg);
		}
	}

	@Override
	public void move(Point offset) {
	}

	@Override
	public void transform(Point offset) {
	}

	@Override
	public void normalize() {
	}

	@Override
	public void normalize(long screenSize, int screenWidth, int minSize) {
	}

	@Override
	public void setStyle() {
	}

	@Override
	public void assingParamerters(Map<QName, Object> p) {
	}
	
	@Override
	public Vector clone() {
		
		try {
			TabLayout clone = (TabLayout) super.clone();
			clone.tabs.clear();
			for(TabItem t: tabs) {
				clone.tabs.add(t);
			}
			return clone;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
}
