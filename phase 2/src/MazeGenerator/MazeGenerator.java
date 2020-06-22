package MazeGenerator;

import org.jetbrains.annotations.NotNull;
import physics.Tree;
import physics.Vector2d;

import java.util.*;
import java.util.List;

public class MazeGenerator
{
    private static Random rand = new Random();
    private static int width;
    private static int height;
    private static int block_width = 2; //2m eind, begin en midden boom
    private static int block_height = 2;
    public static int count = 0;
    public static double i1 = 0.5;

    private static final List<Vector2I> maze = new ArrayList<>();

    private static final List<Vector2d> start_horizontal = new ArrayList<>();       //stores starting coordinates lines
    private static final List<Vector2d> end_horizontal = new ArrayList<>();         //stores ending coordinates lines
    private static final List<Vector2d> start_vertical = new ArrayList<>();
    private static final List<Vector2d> end_vertical = new ArrayList<>();
    private static final List<Vector2d> midpoint_horizontal = new ArrayList<>();
    private static final List<Vector2d> midpoint_vertical = new ArrayList<>();
    private static final List<Vector2d> all_coordinates = new ArrayList<>();


    public static void generate()
    {
        //this is gonna store the indexes of the visited coordinates
        List<Integer> visited = new ArrayList<>();
        //stores the path of the maze that's currently building
        List<Vector2I> toVisit = new ArrayList<>();

        visited.add(0); //start upper left
        toVisit.add(new Vector2I(0, 1));
        toVisit.add(new Vector2I(0, width));

        while(toVisit.size() > 0)
        {
            //gives a random index
            int randomIndex = rand.nextInt(toVisit.size());
            //next position
            Vector2I nextPath = toVisit.remove(randomIndex);

            if(visited.contains(nextPath.end))
                continue;

            //adds the next position/path to the map
            if(nextPath.start > nextPath.end)
                maze.add(new Vector2I(nextPath.end, nextPath.start));
            else
                maze.add(nextPath);

            //adds the position into the visited list, so you dont go twice over it
            visited.add(nextPath.end);


            //finding the adjacent tiles
            //so they can be added to the cache
            int upper = nextPath.end - width;
            if(upper > 0 && !visited.contains(upper))
                toVisit.add(new Vector2I(nextPath.end, upper));

            int left = nextPath.end - 1;
            if(left % width != width - 1 && !visited.contains(left))
                toVisit.add(new Vector2I(nextPath.end, left));

            int right = nextPath.end + 1;
            if(right % width != 0 && !visited.contains(right))
                toVisit.add(new Vector2I(nextPath.end, right));

            int under = nextPath.end + width;
            if(under < width * height && !visited.contains(under))
                toVisit.add(new Vector2I(nextPath.end, under));
        }

    }

        public static void coordinates()
        {

            for(int y = 0; y < height; y++)
            {
                for(int x = 0; x < width; x++)
                {
                    int current = (y * width) + x;
                    int lower = ((y + 1) * width) + x;
                    //WALLS HAVE TO BE REPLACED BY TREES AND WATER
                    //HORIZONTAL WALLS
                    if(!maze.contains(new Vector2I(current, lower)))
                    start_horizontal.add(new Vector2d(x ,(y + 1)));
                    end_horizontal.add(new Vector2d((x + 1), (y + 1)));

                    //calculating the middle of the horizontal lines
                    //so only need to adjust the x coordinate
                    midpoint_horizontal.add(new Vector2d((1.5 * x), (y+1)));

                    //VERTICAL WALLS
                    if(!maze.contains(new Vector2I(current, current + 1)))
                    start_vertical.add(new Vector2d((x + 1), y));
                    end_vertical.add(new Vector2d((x + 1), (y + 1)));

                    //calculating the middle of the vertical lines
                    //so only need to adjust the y coordinate
                    midpoint_vertical.add(new Vector2d((x+1), (1.5*(y + 1))));
                }
            }

        /*
        System.out.println(start_horizontal);
        System.out.println();
        System.out.println(end_horizontal);
        System.out.println();
        System.out.println(start_vertical);
        System.out.println();
        System.out.println(end_vertical);
        System.out.println();
        System.out.println(midpoint_horizontal);
        System.out.println();
        System.out.println(midpoint_vertical);
         */

        all_coordinates.addAll(start_horizontal);
        all_coordinates.addAll(midpoint_horizontal);
        all_coordinates.addAll(end_horizontal);
        all_coordinates.addAll(start_vertical);
        all_coordinates.addAll(midpoint_vertical);
        all_coordinates.addAll(end_vertical);

       // System.out.println(all_coordinates);
        //final_list = removeDuplicates(all_coordinates);

    }

    /**
     *
     * @return
     */

    public static @NotNull ArrayList<Tree> removeDuplicates() {

        ArrayList<Tree> treeList = new ArrayList<>();
        List<Vector2d> newList = new ArrayList<Vector2d>();

        for(Vector2d v : all_coordinates){
            boolean check = true;
            if (newList.size() < 1) {
                newList.add(v);
            }
            else {
                for(Vector2d w : newList) {
                    if((v.get_x()==w.get_x() && v.get_y()==w.get_y())){
                        check = false;
                        break;
                    }
                }
            }
            if(check) {
                newList.add(v);
                treeList.add(new Tree(v, 0.5));
            }

        }

        for(int i=0;i<treeList.size()-1;i++) {
            System.out.print(treeList.get(i).getP() + " || ");
            count++;
        }
        System.out.println(count);
        return treeList;
    }

    public static @NotNull ArrayList<Tree> createMaze(){

       width = 10;
       height = 10;

        generate();
        coordinates();
        return removeDuplicates();

    }
}