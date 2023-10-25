package org.springframework.samples.petclinic.startCard;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface StartCardRepository extends CrudRepository<StartCard,Integer>{

}
