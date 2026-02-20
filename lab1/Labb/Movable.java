interface Movable
{
    void move();
    void turnLeft();
    void turnRight();
    enum direction
    {
        North, South, East, West;
    }
}
