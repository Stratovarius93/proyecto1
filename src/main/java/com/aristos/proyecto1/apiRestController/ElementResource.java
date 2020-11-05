package com.aristos.proyecto1.apiRestController;

import com.aristos.proyecto1.businessController.ElementController;
import com.aristos.proyecto1.documents.Element;
import com.aristos.proyecto1.dtos.ElementDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ElementResource.ELEMENTS)
public class ElementResource {
    public  static final String ELEMENTS="/elements";
    public static final String ID = "/{id}";
    private ElementController elementController;

    @Autowired
    public ElementResource(ElementController elementController){
        this.elementController = elementController;
    }

    @PostMapping(produces = {"application/json"})
    public Mono<ResponseEntity> create(@RequestBody ElementDto elementDto){
        return this.elementController.createElement(elementDto);
    }

    @GetMapping(value = ID)
    public Mono<ElementDto> readElement(@PathVariable Long id){
        return this.elementController.readById(id);
    }

    @GetMapping
    public Flux<ElementDto> read() {
        return this.elementController.read();
    }

    @PutMapping(value = ID)
    public Mono<ResponseEntity> update(@PathVariable Long id, @RequestBody ElementDto elementDto){
        return  this.elementController.update(id,elementDto);
    }
    @DeleteMapping(value = ID)
    public Mono<ResponseEntity> deleteUser(@PathVariable Long id) {
        return this.elementController.delete(id);
    }
}
