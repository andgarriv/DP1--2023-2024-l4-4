package us.l4_4.dp1.end_of_line.achievement;

import org.springframework.beans.BeanUtils;
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
import us.l4_4.dp1.end_of_line.exceptions.BadRequestException;

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
	public Iterable<Achievement> getAllAchievements() {
		return achievementService.findAllAchievements();
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Achievement findAchievement(@PathVariable("id") int id){
		return achievementService.findAchiviementById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Achievement createAchievement(@RequestBody @Valid Achievement achievement){ 
		return achievementService.saveAchievement(achievement);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Achievement updateAchievement(@PathVariable("id") Integer id, @RequestBody @Valid Achievement achievement) {
		Achievement achievementToUpdate = achievementService.findAchiviementById(id);
		if(achievementToUpdate == null)
			throw new BadRequestException("Achievement not found");
		BeanUtils.copyProperties(achievement, achievementToUpdate, "id");
		return achievementService.saveAchievement(achievementToUpdate);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteAchievement(@PathVariable("id") Integer id){
		achievementService.deleteAchievementById(id);
	}
}