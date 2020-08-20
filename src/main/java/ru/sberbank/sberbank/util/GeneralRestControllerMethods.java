package ru.sberbank.sberbank.util;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

public interface GeneralRestControllerMethods<T_entity> {

    @GetMapping
    @Operation(summary = "This API allows to retrieve a list of entities from the system")
    ResponseEntity<CollectionModel<T_entity>> getAll(HttpServletRequest request);

    @GetMapping("/{id:[+]?[\\d]+}")
    @Operation(summary = "This API allows to retrieve an entity based on ID from the system")
    ResponseEntity<T_entity> getById(@PathVariable(name = "id")  Integer id, HttpServletRequest request);

    @PostMapping
    @Operation(summary = "This API allows to create a new entity in the system")
    ResponseEntity<T_entity> create(@RequestBody T_entity dto, HttpServletRequest request);

    @PatchMapping(value = "/{id:[+]?[\\d]+}")
    @Operation(summary = "This API allows to partially update a new entity in the system")
    ResponseEntity<Object> doPatch(@PathVariable(name = "id")  Integer id, @RequestBody T_entity new_entity,HttpServletRequest request);

    @PutMapping(value = "/{id:[+]?[\\d]+}")
    @Operation(summary = "This API allows to entirely update of a new entity with replacement of all information")
    ResponseEntity<Object> doPut (@PathVariable(name = "id")  Integer id, @RequestBody T_entity new_entity,HttpServletRequest request);

    @DeleteMapping(value = "/{id:[+]?[\\d]+}")
    @Operation(summary = "This API allows to delete a new entity by ID in the system")
    ResponseEntity<Object> doDelete(@PathVariable(name = "id")  Integer id,HttpServletRequest request);
}
