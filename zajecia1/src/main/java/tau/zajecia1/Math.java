package tau.zajecia1;

import java.util.List;

public class Math{

    public double add(List<Double> nums)
    {
        double sum = 0.0;

        for(double num : nums)
            sum += num;

        return sum;
    }
}