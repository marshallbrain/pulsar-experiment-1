package ui.engine.vectors;

import java.awt.Shape;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.namespace.QName;

import ui.engine.Point;

@XmlRootElement(name = "text")
public class Text implements Vector {

	@XmlAttribute(name = "pading_x")
	private int padingX;
	@XmlAttribute(name = "pading_y")
	private int padingY;

	@XmlAttribute(name = "style")
	private String styleString;
	@XmlValue
	private String text;
	
	private Map<QName, Object> parameters;

	@Override
	public String getType() {
		return "text";
	}

	@Override
	public String getStyleString() {
		return null;
	}

	@Override
	public Shape getShape() {
		
		text = text.replaceAll("\\s+","");
		
		String t = text;
		
		if(t.startsWith("@")) {
			t = parameters.get(new QName(t.substring(1))).toString();
		}
		
		System.out.println(t);
		
		return null;
	}
	
	@XmlTransient
	@Override
	public Map<String, String> getStyle() {
		return null;
	}

	@Override
	public void move(Point offset) {
	}

	@Override
	public void transform(double offset) {
	}

	@Override
	public void normalize(long screenSize, int screenWidth, int minSize) {
	}

	@Override
	public void setStyle(Map<String, String> s) {
	}

	@Override
	public void assingParamerters(Map<QName, Object> p) {
		parameters = p;
	}
	
	@Override
	public Object clone() {
		
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
}
