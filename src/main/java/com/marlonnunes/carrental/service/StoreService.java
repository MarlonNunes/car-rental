package com.marlonnunes.carrental.service;

import com.marlonnunes.carrental.dto.commons.PageDTO;
import com.marlonnunes.carrental.dto.store.StoreDTO;
import com.marlonnunes.carrental.model.Store;
import com.marlonnunes.carrental.model.User;
import com.marlonnunes.carrental.repository.StoreRepository;
import com.marlonnunes.carrental.repository.StoreSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.http.HttpStatusCode;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Service
public class StoreService {

    @Autowired
    private StoreRepository repository;

    public Store getById(Long id){
        return this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "service.store-service.get-by-id.not-found"));
    }

    public Store save(Store store){
        if(Objects.isNull(store)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "service.store-service.save.null");
        return this.repository.save(store);
    }

    public Optional<Store> findByCnpj(String cnpj){
        return this.repository.findByCnpj(cnpj);
    }

    public ResponseEntity<PageDTO<StoreDTO>> search(Collection<Long> ids, Collection<String> names, Collection<String> cnpjs,
                                                    LocalDate createdAtIni, LocalDate createdAtEnd, Integer page,
                                                    Integer pageSize, User authUser) {

        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("createdAt").ascending());

        Page<Store> storePage = this.repository.findAll(StoreSpecification.byCriteria(ids, names, cnpjs, createdAtIni, createdAtEnd, authUser), pageable);

        return ResponseEntity.ok(PageDTO.buildFromPage(storePage, StoreDTO::fromStore));
    }
}
