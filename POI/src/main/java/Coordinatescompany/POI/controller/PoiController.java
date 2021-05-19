package Coordinatescompany.POI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Coordinatescompany.POI.entities.Poi;
import Coordinatescompany.POI.services.PoiServiceImpl;

@RestController
@RequestMapping("/pois")
public class PoiController {
	
	@Autowired
	private PoiServiceImpl poiServiceImpl;

	@PostMapping("/cadastrar")
	public Poi novoPoi(Poi poi) {
		return poiServiceImpl.salvar(poi);
	}
}
