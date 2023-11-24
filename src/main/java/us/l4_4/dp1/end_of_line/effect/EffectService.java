package us.l4_4.dp1.end_of_line.effect;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EffectService {

    private final EffectRepository effectRepository;

    @Autowired
    public EffectService(EffectRepository effectRepository) {
        this.effectRepository = effectRepository;
    }

    @Transactional(readOnly = true)
    public List<Effect> getEffects() {
        return effectRepository.findAll();
    }
    @Transactional
    public Effect save(Effect effect) {
        return effectRepository.save(effect);
    }

    @Transactional
    public void deleteById(int id) {
        effectRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Effect findEffectById(int id) {
        return effectRepository.findEffectById(id);
    }
    
}
