package com.boraandege.carrental;
import com.boraandege.carrental.dto.LocationDTO;
import com.boraandege.carrental.mapper.LocationMapper;
import com.boraandege.carrental.model.Location;
import com.boraandege.carrental.repository.LocationRepository;
import com.boraandege.carrental.service.impl.LocationServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LocationServiceImplTest {

    @Autowired
    private LocationServiceImpl locationService;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationMapper locationMapper;


    @Test
    void testAddLocation() {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setCode("LOC1");
        locationDTO.setName("Downtown Office");
        locationDTO.setAddress("123 Main St");

        LocationDTO result = locationService.addLocation(locationDTO);

        assertNotNull(result.getId());
        assertEquals("LOC1", result.getCode());
        assertEquals("Downtown Office", result.getName());
        assertEquals("123 Main St", result.getAddress());

        Optional<Location> savedLocation = locationRepository.findById(result.getId());
        assertTrue(savedLocation.isPresent());
        assertEquals("LOC1", savedLocation.get().getCode());
    }

    @Test
    void testGetLocationById() {
        Location location = new Location();
        location.setCode("LOC1");
        location.setName("Downtown Office");
        location.setAddress("123 Main St");
        location = locationRepository.save(location);

        LocationDTO result = locationService.getLocationById(location.getId());

        assertNotNull(result);
        assertEquals("LOC1", result.getCode());
    }

    @Test
    void testGetLocationByCode() {
        Location location = new Location();
        location.setCode("LOC1");
        location.setName("Downtown Office");
        location.setAddress("123 Main St");
        location = locationRepository.save(location);

        LocationDTO result = locationService.getLocationByCode("LOC1");

        assertNotNull(result);
        assertEquals(location.getId(), result.getId());
    }

    @Test
    void testGetAllLocations() {
        Location location1 = new Location();
        location1.setCode("LOC1");
        location1.setName("Downtown Office");
        location1.setAddress("123 Main St");
        locationRepository.save(location1);

        Location location2 = new Location();
        location2.setCode("LOC2");
        location2.setName("Airport Office");
        location2.setAddress("456 Airport Rd");
        locationRepository.save(location2);

        List<LocationDTO> result = locationService.getAllLocations();

        assertEquals(4, result.size());
        assertTrue(result.stream().anyMatch(l -> "LOC1".equals(l.getCode())));
        assertTrue(result.stream().anyMatch(l -> "LOC2".equals(l.getCode())));
    }

    @Test
    void testUpdateLocation() {
        Location location = new Location();
        location.setCode("LOC1");
        location.setName("Downtown Office");
        location.setAddress("123 Main St");
        location = locationRepository.save(location);

        LocationDTO updateDTO = new LocationDTO();
        updateDTO.setCode("LOC1");
        updateDTO.setName("Updated Office");
        updateDTO.setAddress("456 Main St");

        LocationDTO result = locationService.updateLocation(location.getId(), updateDTO);

        assertNotNull(result);
        assertEquals("Updated Office", result.getName());

        Optional<Location> updated = locationRepository.findById(location.getId());
        assertTrue(updated.isPresent());
        assertEquals("Updated Office", updated.get().getName());
        assertEquals("456 Main St", updated.get().getAddress());
    }

    @Test
    void testDeleteLocation() {
        Location location = new Location();
        location.setCode("LOC1");
        location.setName("Downtown Office");
        location.setAddress("123 Main St");
        location = locationRepository.save(location);

        locationService.deleteLocation(location.getId());

        Optional<Location> deleted = locationRepository.findById(location.getId());
        assertTrue(deleted.isEmpty());
    }
}
