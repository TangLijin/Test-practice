package parking;

import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Calendar;

@RunWith(JMockit.class)
public class VipParkingStrategyJMockitTest {

    @Test
    public void testCalculateHourlyPrice_givenSunday_thenPriceIsDoubleOfSundayPrice(){

        /* Exercise 6: Write test case for VipParkingStrategy calculateHourlyPrice
        * by using JMockit to mock static method */
//        new MockUp<ParkingLot>() {
//            @Mock
//            public int getBasicHourlyPrice() {
//                return 25 * 2;
//            }
//        }
//        VipParkingStrategy vipParkingStrategy = new VipParkingStrategy();
//        Assert.assertEquals(vipParkingStrategy.calculateHourlyPrice().intValue(), 100);

    }

    @Test
    public void testCalculateHourlyPrice_givenNotSunday_thenPriceIsDoubleOfNonSundayPrice(){

        /* Exercise 6: Write test case for VipParkingStrategy calculateHourlyPrice
         * by using JMockit to mock static method */


    }
}
