package MazeGenerator;

public class Vector2I
{
    public int start;
    public int end;

    public Vector2I(int start, int end)
    {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString()
    {
        return "( " + start + ", " + end + ")";
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof Vector2I))
            return false;

        Vector2I vec = (Vector2I) obj;
        return vec.start == start && vec.end == end;
    }

}