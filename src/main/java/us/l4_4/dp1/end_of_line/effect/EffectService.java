package us.l4_4.dp1.end_of_line.effect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import us.l4_4.dp1.end_of_line.exceptions.ResourceNotFoundException;

@Service
public class EffectService {

    private final EffectRepository effectRepository;

    @Autowired
    public EffectService(EffectRepository effectRepository) {
        this.effectRepository = effectRepository;
    }

    @Transactional(readOnly = true)
    public Iterable<Effect> findAll() {
        return effectRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Effect findById(Integer id) throws DataAccessException{
        return effectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Effect","id", id));
    }

    @Transactional
    public Effect save(Effect effect) {
        return effectRepository.save(effect);
    }

    @Transactional
    public void delete(Integer id) {
        effectRepository.deleteById(id);
    }
}
