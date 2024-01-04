package us.l4_4.dp1.end_of_line.achievement;

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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/achievements")
@Tag(name = "Achievements", description = "The Achievements management API")
@SecurityRequirement(name = "bearerAuth")
public class AchievementController {

    private final AchievementService achievementService;

    @Autowired
	public AchievementController(AchievementService achievementService) {
		this.achievementService = achievementService;
	}

    @GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Achievement> findAll() {
		return achievementService.findAll();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Achievement findById(@PathVariable("id") int id){
		return achievementService.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Achievement create(@RequestBody @Valid Achievement achievement){ 
		return achievementService.save(achievement);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Achievement update(@PathVariable("id") Integer id, @RequestBody @Valid Achievement achievement) {
		return achievementService.update(id, achievement);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable("id") Integer id){
		achievementService.delete(id);
	}
}