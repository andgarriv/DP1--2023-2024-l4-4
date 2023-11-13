package us.l4_4.dp1.end_of_line.statistic;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Integer>{

    @Query("SELECT a FROM Achievement a")
    List<Achievement> findAll();

    //@Query("SELECT o.achievements FROM Owner o WHERE o.id=:ownerid")
    //public List<Achievement> findPlayerAchievements(@Param("ownerid") int ownerid);

    public Achievement findByName(String name);
}