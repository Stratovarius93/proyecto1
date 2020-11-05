package com.aristos.proyecto1.businessController;

import com.aristos.proyecto1.documents.Element;
import com.aristos.proyecto1.dtos.ElementDto;
import com.aristos.proyecto1.repositories.ElementRepository;
import javassist.NotFoundException;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class ElementController {
    private ElementRepository elementRepository;

    @Autowired
    public ElementController(ElementRepository elementRepository) {
        this.elementRepository = elementRepository;
    }
    //create
    public Mono<ResponseEntity> createElement(ElementDto elementDto){
        Element element = new Element();
        element.setId(elementDto.getId());
        element.setName(elementDto.getName());
        element.setType(elementDto.getType());
        element.setUser(elementDto.getUser());
        element.setActive(elementDto.getActive());
        element.setData(elementDto.getData());
        return this.elementRepository.save(element).map( callback ->{
            return new ResponseEntity("\"element created \"", HttpStatus.CREATED);
        }).onErrorReturn(new ResponseEntity("\"element not created \"", HttpStatus.NOT_ACCEPTABLE));
    }
    //read
    public Flux<ElementDto> read(){
        return this.elementRepository.findAll().map(ElementDto::new);
    }

    //read by id
    public Mono<ElementDto> readById(Long id){
        return this.elementRepository.findById(id).switchIfEmpty(Mono.error(new NotFoundException(" element " + id)))
                .map(ElementDto::new);
    }
    //update
    public Mono<ResponseEntity> update(Long id, ElementDto elementDto){
        Mono<Element> element= this.elementRepository.findById(id).switchIfEmpty(Mono.error(new NotFoundException(" element " + id)))
                .map(elementDb ->{
                    elementDb.setName(elementDto.getName());
                    elementDb.setType(elementDto.getType());
                    elementDb.setUser(elementDto.getUser());
                    elementDb.setActive(elementDto.getActive());
                    elementDb.setData(elementDto.getData());
                    return elementDb;
                });
        return Mono.when(element)
                .then(this.elementRepository.save(element.block()))
                .map( callback -> {
                    return new ResponseEntity("\"element updated\"",HttpStatus.ACCEPTED);
                });
    }
}
