package com.boraandege.carrental;

import com.boraandege.carrental.dto.CarDTO;
import com.boraandege.carrental.enums.CarStatus;
import com.boraandege.carrental.enums.CarType;
import com.boraandege.carrental.enums.TransmissionType;
import com.boraandege.carrental.exception.ResourceNotFoundException;
import com.boraandege.carrental.mapper.CarMapper;
import com.boraandege.carrental.model.Car;
import com.boraandege.carrental.repository.CarRepository;
import com.boraandege.carrental.repository.ReservationRepository;
import com.boraandege.carrental.service.impl.CarServiceImpl;
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
class CarServiceImplTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private CarServiceImpl carService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void testAddCar() {
        CarDTO carDTO = new CarDTO();
        carDTO.setBarcodeNumber("CAR01");
        carDTO.setBrand("Audi");
        carDTO.setModel("A5");
        carDTO.setDailyPrice(BigDecimal.valueOf(50));
        carDTO.setTransmissionType(TransmissionType.MANUAL);
        carDTO.setCarType(CarType.LUXURY);

        CarDTO result = carService.addCar(carDTO);

        assertNotNull(result);
        assertEquals("CAR01", result.getBarcodeNumber());

        Optional<Car> savedCar = carRepository.findByBarcodeNumber("CAR01");
        assertTrue(savedCar.isPresent());
    }

    @Test
    void testGetCarById() {
        Car car = new Car();
        car.setBarcodeNumber("CAR_UNIQUE_456");
        car.setBrand("Honda");
        car.setModel("Accord");
        car.setDailyPrice(BigDecimal.valueOf(70));
        car.setTransmissionType(TransmissionType.AUTOMATIC);
        car.setCarType(CarType.STANDARD);
        car.setPassengerCapacity(5);
        car.setMileage(20000);
        car.setStatus(CarStatus.AVAILABLE);
        car = carRepository.saveAndFlush(car);
        CarDTO result = carService.getCarById(car.getId());


        assertNotNull(result);
        assertEquals(car.getId(), result.getId());
        assertEquals("CAR_UNIQUE_456", result.getBarcodeNumber());
        assertEquals("Honda", result.getBrand());
        assertEquals("Accord", result.getModel());
    }

    @Test
    void testGetCarById_NotFound() {
        assertThrows(ResourceNotFoundException.class, () -> carService.getCarById(999L));
    }

    @Test
    void testGetAllCars() {
        Car car1 = new Car();
        car1.setBarcodeNumber("CAR02");
        carRepository.save(car1);

        Car car2 = new Car();
        car2.setBarcodeNumber("CAR456");
        carRepository.save(car2);

        List<CarDTO> result = carService.getAllCars();

        assertEquals(3, result.size());
    }

    @Test
    void testUpdateCar() {
        Car car = new Car();
        car.setBarcodeNumber("CAR03");
        car.setBrand("Audi");
        car.setModel("A1");
        car = carRepository.save(car);

        CarDTO carDTO = new CarDTO();
        carDTO.setBarcodeNumber("CAR03");
        carDTO.setBrand("Audi");
        carDTO.setModel("A2");

        CarDTO result = carService.updateCar(car.getId(), carDTO);

        assertNotNull(result);
        assertEquals("A2", result.getModel());
        assertEquals("Audi", result.getBrand());
        assertEquals("CAR03", result.getBarcodeNumber());
    }

    @Test
    void testDeleteCar() {
        Car car = new Car();
        car.setBarcodeNumber("CAR04");
        car = carRepository.save(car);

        carService.deleteCar(car.getId());

        Optional<Car> deletedCar = carRepository.findById(car.getId());
        assertTrue(deletedCar.isEmpty());
    }

    @Test
    void testSearchAvailableCars() {
        Car car = new Car();
        car.setCarType(CarType.STANDARD);
        car.setTransmissionType(TransmissionType.AUTOMATIC);
        car.setStatus(CarStatus.AVAILABLE);
        carRepository.save(car);

        List<CarDTO> result = carService.searchAvailableCars(CarType.STANDARD, TransmissionType.AUTOMATIC);

        assertEquals(1, result.size());
    }

    @Test
    void testDeleteCarByBarcode_Success() {
        Car car = new Car();
        car.setBarcodeNumber("CAR05");
        car.setStatus(CarStatus.RESERVED);
        carRepository.save(car);

        boolean result = carService.deleteCarByBarcode("CAR05");

        assertFalse(result);
        assertFalse(carRepository.findByBarcodeNumber("CAR05").isEmpty());
    }

    @Test
    void testDeleteCarByBarcode_Failure() {
        Car car = new Car();
        car.setBarcodeNumber("CAR06");
        car.setStatus(CarStatus.RESERVED);
        carRepository.save(car);

        boolean result = carService.deleteCarByBarcode("CAR06");

        assertFalse(result);
        assertTrue(carRepository.findByBarcodeNumber("CAR06").isPresent());
    }
}
