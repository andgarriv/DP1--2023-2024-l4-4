package us.l4_4.dp1.end_of_line.card;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import us.l4_4.dp1.end_of_line.enums.Color;
import us.l4_4.dp1.end_of_line.enums.Exit;
import us.l4_4.dp1.end_of_line.enums.Orientation;


@SpringBootTest
public class cardServiceTest {
    @Autowired
    CardService cs;
    
    
    @Test
    public void shouldFindCardByID(){
        Card c = this.cs.findById(1);
        assertEquals(1,c.getId());
    }

    @Test
    @Transactional
    public void shouldCreateCard(){
        Card c = new Card();
        c.setInitiative(2);
        c.setColor(Color.GREEN);
        c.setExit(Exit.EXIT_011_A);
        c.setOrientation(Orientation.S);

        cs.save(c);
        Card valiCard = this.cs.findById(c.getId());

        assertNotNull(valiCard);
    }


    @Test
    @Transactional
    public void shouldEditCard(){
         Card c = new Card();
        c.setInitiative(2);
        c.setExit(Exit.EXIT_011_A);
        c.setColor(Color.MAGENTA);
        c.setOrientation(Orientation.S);

        Card created = cs.save(c);

        created.setInitiative(4);
        created.setColor(Color.GREEN);
        created.setExit(Exit.EXIT_001_A);
        created.setOrientation(Orientation.E);

        Card updated = cs.save(created);

        assertEquals(updated.getInitiative(),4);
        assertEquals(updated.getColor(), Color.GREEN);
        assertEquals(updated.getExit(), Exit.EXIT_001_A);
        assertEquals(updated.getOrientation(), Orientation.E);
    }


    /* @Test
    @Transactional
    public void shouldDeleteCard(){

        Card c = new Card();
        c.setInitiative(2);
        c.setExit(Exit.EXIT_011_A);
        c.setColor(Color.MAGENTA);
        c.setOrientation(Orientation.S);

        cs.save(c);

        cs.delete(c.getId());
        Card resut = cs.findById(c.getId());
        assertNull(resut);







    } */





    


}
