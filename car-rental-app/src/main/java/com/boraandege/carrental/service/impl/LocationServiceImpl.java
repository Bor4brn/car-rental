package com.boraandege.carrental.service.impl;

import com.boraandege.carrental.dto.LocationDTO;
import com.boraandege.carrental.model.Location;
import com.boraandege.carrental.exception.ResourceNotFoundException;
import com.boraandege.carrental.mapper.LocationMapper;
import com.boraandege.carrental.repository.LocationRepository;
import com.boraandege.carrental.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    @Override
    public LocationDTO addLocation(LocationDTO locationDTO) {
        Location location = locationMapper.toEntity(locationDTO);
        Location savedLocation = locationRepository.save(location);
        return locationMapper.toDTO(savedLocation);
    }

    @Override
    public LocationDTO getLocationById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + id));
        return locationMapper.toDTO(location);
    }

    @Override
    public LocationDTO getLocationByCode(String code) {
        Location location = locationRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with code: " + code));
        return locationMapper.toDTO(location);
    }

    @Override
    public List<LocationDTO> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return locations.stream()
                .map(locationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LocationDTO updateLocation(Long id, LocationDTO locationDTO) {
        Location existingLocation = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + id));

        existingLocation.setCode(locationDTO.getCode());
        existingLocation.setName(locationDTO.getName());
        existingLocation.setAddress(locationDTO.getAddress());

        Location updatedLocation = locationRepository.save(existingLocation);
        return locationMapper.toDTO(updatedLocation);
    }

    @Override
    public void deleteLocation(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found with id: " + id));
        locationRepository.delete(location);
    }
}
