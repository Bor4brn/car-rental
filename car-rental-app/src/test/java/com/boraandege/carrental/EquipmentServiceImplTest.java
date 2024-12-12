package com.boraandege.carrental;
import com.boraandege.carrental.dto.EquipmentDTO;
import com.boraandege.carrental.model.Equipment;
import com.boraandege.carrental.exception.ResourceNotFoundException;
import com.boraandege.carrental.mapper.EquipmentMapper;
import com.boraandege.carrental.repository.EquipmentRepository;
import com.boraandege.carrental.service.impl.EquipmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class EquipmentServiceImplTest {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentMapper equipmentMapper;

    @Autowired
    private EquipmentServiceImpl equipmentService;


    @Test
    void testAddEquipment() {
        equipmentRepository.deleteAll();

        EquipmentDTO equipmentDTO = new EquipmentDTO();
        equipmentDTO.setName("NAVIGATION_SYSTEM"); // distinct from data.sql equipment names
        equipmentDTO.setPrice(BigDecimal.valueOf(100));

        EquipmentDTO result = equipmentService.addEquipment(equipmentDTO);

        assertNotNull(result);
        assertEquals("NAVIGATION_SYSTEM", result.getName());
        assertEquals(BigDecimal.valueOf(100), result.getPrice());

        // Verify the entity is saved in the database
        Optional<Equipment> savedEquipment = equipmentRepository.findById(result.getId());
        assertTrue(savedEquipment.isPresent());
        assertEquals("NAVIGATION_SYSTEM", savedEquipment.get().getName());
    }

    @Test
    void testGetEquipmentById() {
        equipmentRepository.deleteAll();

        Equipment equipment = new Equipment();
        equipment.setName("ROOF_BOX");
        equipment.setPrice(BigDecimal.valueOf(10));
        equipment = equipmentRepository.save(equipment);

        EquipmentDTO result = equipmentService.getEquipmentById(equipment.getId());

        assertNotNull(result);
        assertEquals("ROOF_BOX", result.getName());
        assertEquals(BigDecimal.valueOf(10), result.getPrice());
    }

    @Test
    void testGetEquipmentById_NotFound() {
        equipmentRepository.deleteAll();
        assertThrows(ResourceNotFoundException.class, () -> equipmentService.getEquipmentById(999L));
    }

    @Test
    void testGetAllEquipments() {
        equipmentRepository.deleteAll();

        Equipment equipment1 = new Equipment();
        equipment1.setName("ROOF_BOX_TEST");
        equipment1.setPrice(BigDecimal.valueOf(10));
        equipmentRepository.save(equipment1);

        Equipment equipment2 = new Equipment();
        equipment2.setName("BABY_SEAT_TEST");
        equipment2.setPrice(BigDecimal.valueOf(15));
        equipmentRepository.save(equipment2);

        List<EquipmentDTO> result = equipmentService.getAllEquipments();

        assertEquals(2, result.size());
    }

    @Test
    void testUpdateEquipment() {
        equipmentRepository.deleteAll();

        Equipment equipment = new Equipment();
        equipment.setName("SNOW_TYRES");
        equipment.setPrice(BigDecimal.valueOf(10));
        equipment = equipmentRepository.save(equipment);

        EquipmentDTO equipmentDTO = new EquipmentDTO();
        equipmentDTO.setName("SNOW_TYRES_UPDATED");
        equipmentDTO.setPrice(BigDecimal.valueOf(12));

        EquipmentDTO result = equipmentService.updateEquipment(equipment.getId(), equipmentDTO);

        assertNotNull(result);
        assertEquals("SNOW_TYRES_UPDATED", result.getName());
        assertEquals(BigDecimal.valueOf(12), result.getPrice());
    }

    @Test
    void testDeleteEquipment() {
        equipmentRepository.deleteAll();

        Equipment equipment = new Equipment();
        equipment.setName("WIFI_HOTSPOT");
        equipment = equipmentRepository.save(equipment);

        equipmentService.deleteEquipment(equipment.getId());

        Optional<Equipment> deletedEquipment = equipmentRepository.findById(equipment.getId());
        assertTrue(deletedEquipment.isEmpty());
    }
}
