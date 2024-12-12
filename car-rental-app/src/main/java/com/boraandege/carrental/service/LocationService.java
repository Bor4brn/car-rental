package com.boraandege.carrental.service;

import com.boraandege.carrental.dto.LocationDTO;
import java.util.List;

public interface LocationService {

    LocationDTO addLocation(LocationDTO locationDTO);

    LocationDTO getLocationById(Long id);

    LocationDTO getLocationByCode(String code);

    List<LocationDTO> getAllLocations();

    LocationDTO updateLocation(Long id, LocationDTO locationDTO);

    void deleteLocation(Long id);
}
