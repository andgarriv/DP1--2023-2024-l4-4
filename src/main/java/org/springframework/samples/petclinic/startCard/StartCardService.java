package org.springframework.samples.petclinic.startCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StartCardService {
    private StartCardRepository startCardRepository;
    @Autowired
    public StartCardService(StartCardRepository startCardRepository){
        this.startCardRepository = startCardRepository;
    }
    
}
