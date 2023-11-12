package org.springframework.samples.petclinic.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/player")
@Tag(name = "Player", description = "API for the management of Player")
//@SecurityRequirement(name = "bearerAuth")
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService){
        this.playerService = playerService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Player create(@RequestBody @Valid Player player){
        return playerService.createPlayer(player);        
    }


    
}


