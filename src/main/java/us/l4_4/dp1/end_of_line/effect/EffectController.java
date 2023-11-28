package us.l4_4.dp1.end_of_line.effect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/games/effects")
@Tag(name = "Effect", description = "API for the management of Effect")
@SecurityRequirement(name = "bearerAuth")
public class EffectController {
     
    public EffectService effectService;

    @Autowired
    public EffectController(EffectService effectService) {
        this.effectService = effectService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Effect> getAllEffects() {
        return effectService.getEffects();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Effect getEffectById(@PathVariable int id) {
        return effectService.findEffectById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Effect createEffect(Effect effect) {
        effectService.save(effect);
        return effect;
    }

    @PutMapping("/{id}")
    public Effect updateEffect(@PathVariable int id, @RequestBody Effect effect) {
        Effect effectToUpdate = effectService.findEffectById(id);
        effectToUpdate.setColor(effect.getColor());
        effectToUpdate.setHability(effect.getHability());
        effectService.save(effectToUpdate);
        return effectToUpdate;
    }

    @DeleteMapping("/{id}")
    public void deleteEffect(@PathVariable int id) {
        effectService.deleteById(id);
    }
}
