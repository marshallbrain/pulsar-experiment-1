package species.colony;

import java.util.ArrayList;

import bodys.Body;
import files.type.Type;
import files.type.TypeBuilding;
import files.type.TypeDistrict;
import files.type.TypeLoader;
import species.Species;
import species.colony.build.Building;
import species.colony.build.District;
import ui.universe.view.pane.BuildingUI;
import ui.universe.view.pane.DistrictUI;

public class Colony {
	
	private District[] districts; //array for all posible district slots
	private Building[] buildings;
	private DistrictUI[] districtsUI; //the visual aspect of each district
	private BuildingUI[] buildingsUI;
	
	private ResourceManager resource;
	
	/**
	 * initalizes  colony
	 * 
	 * @param planet the planet that the colony is on
	 * @param species the species that the colony is under controle by, relevent for species boneses
	 * @param gamefile the gamefile containing district info
	 */
	public Colony(Body b, Species s, TypeLoader colonyLoader) {
		
		resource = new ResourceManager(s, colonyLoader, s.getResourceManagerMaster().getResourceTotal());
		
		//get type array contaning typedistricts with information from the district folder
		ArrayList<Type> dt = colonyLoader.getTypes("district");
		ArrayList<TypeDistrict> districtTypes = new ArrayList<TypeDistrict>();
		districts = new District[8];
		districtsUI = new DistrictUI[districts.length];
		
		resource.set(districts); //adds the district array to the resorcemanager
		
		for(int i = 0; i < districts.length; i++) {
			districts[i] = new District(resource);
		}
		
		//loops through all district
		for(int i = 0; i < dt.size(); i++) {
			
			if(((TypeDistrict) dt.get(i)).isPotential(b)) {
				districtTypes.add((TypeDistrict) dt.get(i));
			}
			
		}
		
		resource.setDistricts(districtTypes);
		
		for(int i = 0; i < districtsUI.length; i++) {
			districtsUI[i] = new DistrictUI(districts[i]);
		}

		ArrayList<Type> bt = colonyLoader.getTypes("building");
		ArrayList<TypeBuilding> buildingTypes = new ArrayList<TypeBuilding>();
		buildings = new Building[44];
		buildingsUI = new BuildingUI[buildings.length];
		
		resource.set(buildings);
		
		for(int i = 0; i < buildings.length; i++) {
			buildings[i] = new Building(resource);
		}
		
		for(int i = 0; i < bt.size(); i++) {
			if(((TypeBuilding) bt.get(i)).isPotential(b)) {
				buildingTypes.add((TypeBuilding) bt.get(i));
			}
		}
		
		resource.setBuildings(buildingTypes);
		
		for(int i = 0; i < buildingsUI.length; i++) {
			buildingsUI[i] = new BuildingUI(buildings[i]);
		}
		
	}

	public DistrictUI[] getDistricts() {
		return districtsUI;
	}

	public BuildingUI[] getBuildings() {
		return buildingsUI;
	}

	public ResourceManager getResourceManager() {
		return resource;
	}

}
