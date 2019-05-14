package species.colony;

import java.util.List;
import java.util.Map;
import java.util.Set;

import bodys.Body;
import species.Species;
import species.colony.build.District;

public class Colony {
	
	private Body body;
	
	private District[] districts;
	
	public Colony(Body b, Species s) {
		
		body = b;
		
		body.setColony(this); 
		
		init();
		
	}
	
	private void init() {
		
		initDistricts();
		
	}

	private void initDistricts() {
		
		districts = new District[4];
		
		List<String> potential = District.getPotentialDistricts(body.getTags());
		
		for(int i = 0; i < districts.length; i++) {
			if(i < potential.size()) {
				districts[i] = new District(potential.get(i), body.getTags());
			} else {
				districts[i] = new District(body.getTags());
			}
		}
		
	}

}
