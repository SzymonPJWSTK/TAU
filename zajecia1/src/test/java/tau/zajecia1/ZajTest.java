package tau.zajecia1;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ZajTest{
    Math math;

    @Before
    public void init(){
        math = new Math();
    }
    @Test
    public void mathExistsCheck(){
        assertNotNull(math);
    }
    @Test
    public void addingCheck(){
        List<Double> nums = new ArrayList<Double>();
        nums.add(2.0);
        nums.add(2.0);

        double sum = math.add(nums);

        assertEquals(4.0,sum, 0.0001);
    }
    @Test
    public void secondAddingCheck(){
        List<Double> nums = new ArrayList<Double>();

        for(int i = 0; i < 10 ;i++)
            nums.add(0.1);

        double sum = math.add(nums);
        assertEquals(1.0,sum, 0.0001);
    }
}