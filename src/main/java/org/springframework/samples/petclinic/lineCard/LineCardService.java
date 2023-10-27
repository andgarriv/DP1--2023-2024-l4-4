package org.springframework.samples.petclinic.lineCard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LineCardService {
    private LineCardRepository lineCardRepository;
    @Autowired
    public LineCardService(LineCardRepository lineCardRepository){
        this.lineCardRepository = lineCardRepository;
    }
    
}
