package MazeGenerator;
import MazeGenerator.Cell;
import physics.Tree;
import physics.Vector2d;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MazeForSale {


        public static void mazeCreation() {
            MazeForSale maze = new MazeForSale(40,40);
            int line = 0;
            ArrayList<Tree> d = new ArrayList<>();


            // Create a stream to hold the output
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
// IMPORTANT: Save the old System.out!
            PrintStream old = System.out;
// Tell Java to use your special stream
            System.setOut(ps);
// Print some output: goes to your special stream
            System.out.println(maze);
// Put things back
            System.out.flush();
            System.setOut(old);
// Show what happened
            Scanner scanner = new Scanner(baos.toString());
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                for (int i = 0; i < s.length(); i++){
                    char c = s.charAt(i);
                    if(c == 1){
                        d.add(new Tree(new Vector2d(line,i), 0.5));
                    }
                    line++;
                }

            } scanner.close();

        }

        Cell maze[][];


    public MazeForSale() {
            maze = new Cell[0][0];
        }

        public MazeForSale( int length , int height ) {
            maze = new Cell[height][length];
            for ( int i = 0 ; i < height ; ++i ) {
                for ( int k = 0 ; k < length ; ++k ) {
                    maze[i][k] = new Cell(i,k);
                    maze[i][k].setSet(i*length+k);
                }
            }
            generate();
        }


        private void generate() {
            int di[] = { -1 , 1 , 0 , 0 };
            int dk[] = {  0 , 0 , -1 , 1 };
            ArrayList<Integer> queue = new ArrayList<Integer>();
            for ( int i = 0 ; i < maze.length*maze[0].length ; ++i ) {
                queue.add(i);
            }
            for ( int q = 0 ; q < maze.length*maze[0].length-1 ; ++q ) {
                int pos = (int) (Math.random()*queue.size());
                int set = queue.get(pos);
                queue.remove(pos);
                ArrayList<Cell> stack = new ArrayList<Cell>();
                for ( int i = 0 ; i < maze.length ; ++i ) {
                    for ( int k = 0 ; k < maze[0].length ; ++k ) {
                        if ( maze[i][k].getSet() != set && check(maze[i][k],set,di,dk) ) {
                            stack.add(maze[i][k]);
                        }
                    }
                }
                pos = (int) (Math.random()*stack.size());
                Cell cell = stack.get(pos);
                for ( int w = 0 ; w < di.length ; ++w ) {
                    int ti = di[w]+cell.i;
                    int tk = dk[w]+cell.k;
                    try {
                        if ( maze[ti][tk].getSet() == set ) {
                            merge(cell,maze[ti][tk]);
                            cell.connect(w, maze[ti][tk]);
                            break;
                        }
                    }
                    catch ( Exception e ) {}
                }

            }
        }

        //merges two cels together
        private void merge(Cell cell, Cell cell2) {
            int set = cell2.getSet();
            for ( int i = 0 ; i < maze.length ; ++i ) {
                for ( int k = 0 ; k < maze[0].length ; ++k ) {
                    if ( maze[i][k].getSet() == set  ) {
                        maze[i][k].setSet(cell.getSet());
                    }
                }
            }
        }

        //checks to see if the new location is valid
        private boolean check ( Cell cell , int set, int[] di, int[] dk ) {
            for ( int w = 0 ; w < di.length ; ++w ) {
                int ti = di[w]+cell.i;
                int tk = dk[w]+cell.k;
                try {
                    if ( maze[ti][tk].getSet() == set ) {
                        return true;
                    }
                }
                catch ( Exception e ) {}
            }
            return false;
        }

        @Override
        public String toString() {
            StringBuilder res = new StringBuilder();
            for ( int k = 0 ; k < maze[0].length ; ++k ) {
                res.append("11");
            }
            res.append("1");
            res.append("\n");
            for ( int i = 0 ; i < maze.length ; ++i ) {
                res.append("1");
                for ( int k = 0 ; k < maze[0].length ; ++k ) {
                    if ( i == 0 && k == 0  )
                        res.append(maze[i][k].toString(0).replace('O', 'S'));
                    else if ( i == maze.length-1 && k == maze[0].length-1  )
                        res.append("E1");
                    else
                        res.append(maze[i][k].toString(0));
                }
                res.append("\n");
                res.append("1");
                for ( int k = 0 ; k < maze[0].length ; ++k ) {
                    res.append(maze[i][k].toString(1));
                }
                res.append("\n");
            }
            return res.toString();
        }


}
