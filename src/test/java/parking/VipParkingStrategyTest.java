package parking;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class VipParkingStrategyTest {

	@Test
    public void testPark_givenAVipCarAndAFullParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    /* Exercise 4, Write a test case on VipParkingStrategy.park()
	    * With using Mockito spy, verify and doReturn */
	    //
        ParkingLot parkingLot = Mockito.mock(ParkingLot.class);
        Mockito.when(parkingLot.isFull()).thenReturn(true);
        List<ParkingLot> parkingLotList = new ArrayList<>();
        parkingLotList.add(parkingLot);
        Car car = new Car("Audi");
        VipParkingStrategy vipParkingStrategy = Mockito.spy(new VipParkingStrategy());
        Mockito.doReturn(true).when(vipParkingStrategy).isAllowOverPark(car);

        vipParkingStrategy.park(parkingLotList, car);

        Mockito.verify(vipParkingStrategy, Mockito.times(1)).createReceipt(parkingLot, car);

    }

    @Test
    public void testPark_givenCarIsNotVipAndAFullParkingLog_thenGiveNoSpaceReceipt() {

        /* Exercise 4, Write a test case on VipParkingStrategy.park()
         * With using Mockito spy, verify and doReturn */
        ParkingLot parkingLot = Mockito.mock(ParkingLot.class);
        Mockito.when(parkingLot.isFull()).thenReturn(true);
        List<ParkingLot> parkingLotList = new ArrayList<>();
        parkingLotList.add(parkingLot);
        Car car = new Car("Audi");
        VipParkingStrategy vipParkingStrategy = Mockito.spy(new VipParkingStrategy());
        Mockito.doReturn(false).when(vipParkingStrategy).isAllowOverPark(car);
        vipParkingStrategy.park(parkingLotList, car);
        Mockito.verify(vipParkingStrategy, Mockito.times(0)).createReceipt(parkingLot, car);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsVipCar_thenReturnTrue(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        Car car = Mockito.mock(Car.class);
        CarDao carDao = Mockito.mock(CarDao.class);
        Mockito.when(carDao.isVip("AUDI")).thenReturn(true);
        ParkingLot parkingLot = Mockito.mock(ParkingLot.class);
        Mockito.when(parkingLot.isFull()).thenReturn(true);
        List<ParkingLot> parkingLotList = new ArrayList<>();
        parkingLotList.add(parkingLot);
        Mockito.when(car.getName()).thenReturn("AUDI");
        VipParkingStrategy vipParkingStrategy = Mockito.spy(new VipParkingStrategy());
        vipParkingStrategy.park(parkingLotList, car);

    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsVipCar_thenReturnFalse(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsNotVipCar_thenReturnFalse(){
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
    }

    private Car createMockCar(String carName) {
        Car car = mock(Car.class);
        when(car.getName()).thenReturn(carName);
        return car;
    }
}
