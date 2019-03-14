package ui.universe.view.pane.list;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import files.type.Type;
import files.type.TypeDistrict;
import species.colony.ResourceManager;

public class ListDistrictType {
	
	private ArrayList<TypeDistrict> districts;
	
	private BufferedImage info;
	private BufferedImage border;
	
	private ResourceManager resource;
	
	public ListDistrictType(ResourceManager r) {
		
		districts = new ArrayList<TypeDistrict>();
		
		try {
			info = ImageIO.read(new File("gfx\\ui\\view\\colony\\district\\info.png"));
			border = ImageIO.read(new File("gfx\\ui\\view\\colony\\boarder.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		resource = r;
		
	}

	public void set(TypeDistrict d) {
		districts.clear();
		districts.add(d);
	}

	public void set(ArrayList<Type> districtTypes) {
		
		districts.clear();
		
		for(Type t: districtTypes) {
			TypeDistrict d = (TypeDistrict) t;
			districts.add(d);
		}
	}
	
	public TypeDistrict clicked(int x, int y) {
		
		for(int i = 0; i < districts.size(); i++) {
			if(0 < x && x < info.getWidth()) {
				if(info.getHeight()*i < y && y < info.getHeight()*(i+1)) {
					return districts.get(i);
				}
			}
		}
		
		return null;
		
	}

	public boolean render(Graphics g, int x, int y) {
		
		Graphics2D g2d = (Graphics2D)g;
		
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
		
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		FontMetrics metrics = g.getFontMetrics(font);
		
		for(int i = 0; i < districts.size(); i++) {
			if(districts.get(i) != null) {
				
				g.drawImage(info, x, y, null);
				g.drawImage(border, x+5, y+25, null);
				g.drawImage(districts.get(i).getIcon(), x+6, y+26, null);
				
				g2d.drawString(districts.get(i).getName(), x+5, y+15);
				
				String dis = districts.get(i).getName() + "_description";
				int y1 = y+20;
				for (String line : dis.split("\n"))
					g2d.drawString(line, x+80, y1 += metrics.getHeight());
				
				y += (info.getHeight()+5);
				
			}
		}
		
		return true;
		
	}
	
}
