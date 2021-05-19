package Coordinatescompany.POI.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import Coordinatescompany.POI.entities.Poi;

public interface PoiRepository extends PagingAndSortingRepository<Poi, Integer> {


}