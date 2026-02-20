import java.util.LinkedList;

public class Workshop<V extends Vehicles>
{
    private int capacitymax;
    protected LinkedList<V> cars;

    public Workshop(){
        this.capacitymax = 2 + (int)(Math.random() * 7);
        this.cars = new LinkedList<>();
    }

    protected void checkin(V other)
    {
        if(cars.size() < capacitymax)
        {
            cars.addLast(other);
        }
        else
        {
            throw new IllegalArgumentException("Capacity at max");
        }
    }
    protected void checkOut()
    {
        if(cars.isEmpty())
        {
            throw new IllegalArgumentException("No cars to checkout");
        }
        cars.removeFirst();
    }
    protected int checkCap()
    {
        return cars.size();
    }
    protected int checkMaxCap()
    {
        return capacitymax;
    }

}
