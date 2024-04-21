package com.marlonnunes.carrental.service;

import com.marlonnunes.carrental.dto.store.CreateStoreDTO;
import com.marlonnunes.carrental.dto.store.StoreDTO;
import com.marlonnunes.carrental.dto.store.UpdateStoreDTO;
import com.marlonnunes.carrental.model.Store;
import com.marlonnunes.carrental.model.User;
import com.marlonnunes.carrental.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class StoreManagementService {

    @Autowired
    private StoreService service;

    @Autowired
    private UserService userService;

    public ResponseEntity<StoreDTO> createStore(CreateStoreDTO createStore, User authUser) {
        Store store = new Store();
        String normalizedCnpj = StringUtils.normalizeCpfOrCnpj(createStore.cnpj());

        this.checkIfStoreAlreadyExist(normalizedCnpj);

        BeanUtils.copyProperties(createStore, store);

        store.setCnpj(normalizedCnpj);
        store.setCreatedAt(LocalDateTime.now());
        store.setUpdatedAt(LocalDateTime.now());
        store.setCreatedBy(authUser);
        store.setUpdatedBy(authUser);

        store = this.service.save(store);

        return new ResponseEntity<>(StoreDTO.fromStore(store), HttpStatus.CREATED);
    }

    public ResponseEntity<StoreDTO> updateStore(UpdateStoreDTO updateStore, User authUser) {
        Store store = this.service.getById(updateStore.id());
        String normalizedCnpj = StringUtils.normalizeCpfOrCnpj(updateStore.cnpj());
        if(!normalizedCnpj.equals(store.getCnpj())){
            this.checkIfStoreAlreadyExist(normalizedCnpj);
        }

        BeanUtils.copyProperties(updateStore, store);

        store.setCnpj(normalizedCnpj);
        store.setUpdatedBy(authUser);
        store.setUpdatedAt(LocalDateTime.now());

        store = this.service.save(store);

        return ResponseEntity.ok(StoreDTO.fromStore(store));
    }

    private void checkIfStoreAlreadyExist(String normalizedCnpj){
        if(this.service.findByCnpj(normalizedCnpj).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "service.store-management-service.update-store.cnpj-already-exist");
        }
    }

    public ResponseEntity<StoreDTO> getStoreDTO(Long id) {
        Store store = this.service.getById(id);


        return ResponseEntity.ok(StoreDTO.fromStore(store));
    }
}
