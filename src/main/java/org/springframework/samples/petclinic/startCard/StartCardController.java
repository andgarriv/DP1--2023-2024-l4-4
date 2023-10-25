package org.springframework.samples.petclinic.startCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/card/start")
@Tag(name="StartCard", description = "API for the management of StartCards")
@SecurityRequirement(name = "bearerAuth")
public class StartCardController {

    private StartCardService startCardService;

    @Autowired
    public StartCardController(StartCardService startCardService){
        this.startCardService = startCardService;
    }
     
    
}