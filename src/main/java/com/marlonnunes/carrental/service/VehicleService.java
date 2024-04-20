package com.marlonnunes.carrental.service;

import com.marlonnunes.carrental.dto.commons.PageDTO;
import com.marlonnunes.carrental.dto.vehicle.CreateVehicleDTO;
import com.marlonnunes.carrental.dto.vehicle.VehicleDTO;
import com.marlonnunes.carrental.model.User;
import com.marlonnunes.carrental.model.Vehicle;
import com.marlonnunes.carrental.model.enums.Color;
import com.marlonnunes.carrental.repository.VehicleRepository;
import com.marlonnunes.carrental.repository.VehicleSpecification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository repository;

    @Autowired
    private UserService userService;


    public ResponseEntity<VehicleDTO> create(CreateVehicleDTO vehicleDTO, User authUser) {

        if(this.repository.findByNumberPlate(vehicleDTO.numberPlate()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "service.vehicle-service.create.number-plate-already-exists");
        }

        Vehicle vehicle = new Vehicle();
        BeanUtils.copyProperties(vehicleDTO, vehicle);

        vehicle.setCreatedBy(authUser.getId());
        vehicle.setUpdatedBy(authUser.getId());
        vehicle.setCreatedAt(LocalDateTime.now());
        vehicle.setUpdatedAt(LocalDateTime.now());

        vehicle = this.repository.save(vehicle);

        return new ResponseEntity<>(VehicleDTO.fromVehicleWithUsers(vehicle, authUser, authUser), HttpStatus.CREATED);
    }

    public ResponseEntity<VehicleDTO> getVehicleById(Long id) {
        Vehicle vehicle = this.getById(id);

        Set<Long> userIds = new HashSet<>(List.of(vehicle.getUpdatedBy(), vehicle.getCreatedBy()));
        Map<Long, User> userMap = this.userService.getByIds(userIds).stream().collect(Collectors.toMap(User::getId, Function.identity(), (current, replace) -> replace));

        return ResponseEntity.ok(VehicleDTO.fromVehicleWithUsers(vehicle, userMap.get(vehicle.getCreatedBy()), userMap.get(vehicle.getUpdatedBy())));
    }

    public Vehicle getById(Long id){
        return this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "service.vehicle-service.get-by-id.not-found"));
    }

    public ResponseEntity<PageDTO<VehicleDTO>> search(List<Long> ids, List<String> numberPlates, List<Color> colors, List<String> makes, List<String> models,
                                                        int page, int pageSize) {

        Pageable pageable = PageRequest.of(page -1, pageSize, Sort.by("createdAt").ascending());


        Page<Vehicle> vehiclePage = this.repository.findAll(VehicleSpecification.byCriteria(ids, numberPlates, colors, makes, models), pageable);

        return ResponseEntity.ok(PageDTO.buildFromPage(vehiclePage, VehicleDTO::fromVehicle));
    }
}
