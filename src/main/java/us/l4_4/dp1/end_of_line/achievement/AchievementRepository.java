package us.l4_4.dp1.end_of_line.achievement;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Integer>{

    public Achievement findByName(String name);
}