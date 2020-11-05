package com.aristos.proyecto1.apiRestController;

import com.aristos.proyecto1.documents.Element;
import com.aristos.proyecto1.dtos.ElementDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;

import static org.junit.jupiter.api.Assertions.*;

@ApiTestConfig
class ElementResourceTest {
    @Autowired
    private RestService restService;
    @Test
    void create() {
        int num[] = {4,5,6};
        ElementDto elementDto = new ElementDto(3L, "nombre3", "tipo2","usuario2", true, num);
        String get = this.restService.restbuildert().post()
                .uri(ElementResource.ELEMENTS).body(BodyInserters.fromObject(elementDto))
                .exchange().expectStatus().isCreated().expectBody(String.class).returnResult().getResponseBody();
        assertNotNull(get);
        assertEquals("\"element created \"", get);
    }

    @Test
    void readById(){
        ElementDto elementDto = this.restService.restbuildert().get()
                .uri(ElementResource.ELEMENTS+ElementResource.ID,"id")
                .exchange().expectStatus().isOk().expectBody(ElementDto.class)
                .returnResult().getResponseBody();
        assertNotNull(elementDto);
        assertEquals("id",elementDto.getId());
    }
    //@Test
    //void read(){
    //    this.restService.restbuildert().get().uri(ElementResource.ELEMENTS).exchange().expectStatus().isOk();
    //}
}