package com.marlonnunes.carrental.controller;

import com.marlonnunes.carrental.dto.commons.PageDTO;
import com.marlonnunes.carrental.dto.store.CreateStoreDTO;
import com.marlonnunes.carrental.dto.store.StoreDTO;
import com.marlonnunes.carrental.dto.store.UpdateStoreDTO;
import com.marlonnunes.carrental.model.User;
import com.marlonnunes.carrental.service.StoreManagementService;
import com.marlonnunes.carrental.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin/store-management")
public class StoreManagementRestController {

    @Autowired
    private StoreManagementService service;

    @Autowired
    private StoreService storeService;

    @PostMapping("/create")
    public ResponseEntity<StoreDTO> createStore(@Valid @RequestBody CreateStoreDTO createStore, @AuthenticationPrincipal User authUser){
        return this.service.createStore(createStore, authUser);
    }

    @PutMapping("/update")
    public ResponseEntity<StoreDTO> updateStore(@Valid @RequestBody UpdateStoreDTO updateStore, @AuthenticationPrincipal User authUser){
        return this.service.updateStore(updateStore, authUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreDTO> getById(@PathVariable Long id){
        return this.service.getStoreDTO(id);
    }

    @GetMapping("/search")
    public ResponseEntity<PageDTO<StoreDTO>> getStores(
            @RequestParam(name = "id", required = false) List<Long> ids,
            @RequestParam(name = "legalName", required = false) List<String> names,
            @RequestParam(name = "cnpj", required = false) List<String> cnpjs,
            @RequestParam(name = "createdAtIni", required = false) LocalDate createdAtIni,
            @RequestParam(name = "createdAtEnd", required = false) LocalDate createdAtEnd,
            @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
            @AuthenticationPrincipal User authUser
            ){
        return this.storeService.search(ids, names, cnpjs, createdAtIni, createdAtEnd, page, pageSize, authUser);
    }


}
