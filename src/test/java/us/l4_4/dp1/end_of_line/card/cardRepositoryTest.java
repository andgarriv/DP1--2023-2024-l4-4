package us.l4_4.dp1.end_of_line.card;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import us.l4_4.dp1.end_of_line.enums.Color;

@DataJpaTest
public class cardRepositoryTest {

        @Autowired
        CardRepository cr;

        @Test
        public void findCardsByColor(){
            List<Card> cds = cr.findCardsByColor(Color.RED);

            assertTrue(cds.size() > 0 );

        }




    


}
