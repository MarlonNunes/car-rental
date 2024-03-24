package com.marlonnunes.carrental.controller;

import com.marlonnunes.carrental.dto.commons.PageDTO;
import com.marlonnunes.carrental.dto.vehicle.CreateVehicleDTO;
import com.marlonnunes.carrental.dto.vehicle.VehicleDTO;
import com.marlonnunes.carrental.model.User;
import com.marlonnunes.carrental.model.enums.Color;
import com.marlonnunes.carrental.service.VehicleService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    private VehicleService service;
    @PostMapping("/create")
    public ResponseEntity<VehicleDTO> create(@Valid @RequestBody CreateVehicleDTO vehicleDTO, @AuthenticationPrincipal User user){
        return this.service.create(vehicleDTO, user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleDTO> getVehicle(@PathVariable Long id){
        return this.service.getVehicleById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<PageDTO<VehicleDTO>> searchVehicles(@RequestParam(required = false) List<String> models, @RequestParam(required = false) List<String> makes, @RequestParam(required = false) List<Long> ids,
                                                              @RequestParam(required = false) List<Color> colors, @RequestParam(required = false) List<String> numberPlates,
                                                              @RequestParam(defaultValue = "1") int page,  @RequestParam(defaultValue = "10") int pageSize){
        return this.service.search(ids, numberPlates, colors, makes, models, page, pageSize);
    }
}
