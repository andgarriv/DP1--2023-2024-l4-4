package org.springframework.samples.petclinic.lineCard;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("api/v1/card/line")
@Tag(name="LineCard", description = "API for the management of LineCards")
@SecurityRequirement(name = "bearerAuth")
public class LineCardController {

    private LineCardService lineCardService;

    @Autowired
    public LineCardController(LineCardService lineCardService){
        this.lineCardService = lineCardService;
    }
     
    
}