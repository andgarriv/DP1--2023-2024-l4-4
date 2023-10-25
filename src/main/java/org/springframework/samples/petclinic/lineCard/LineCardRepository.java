package org.springframework.samples.petclinic.lineCard;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineCardRepository extends CrudRepository<LineCard,Integer> {

}
