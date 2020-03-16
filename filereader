import java.io.*;
import java.util.*;

class filereader {

    public static void main(String[] args) {
        
        Scanner in = new Scanner(System.in);
        System.out.println("File path: ");

        String path = in.nextLine();

        Scanner input;

        ArrayList<String> data = new ArrayList<String>();

        try {
            File file = new File(path);
            input= new Scanner(file);

            while (input.hasNextLine()) {
                data.add(input.nextLine());
            }


        }
        catch (FileNotFoundException e) {
            System.out.println(e);
            return;
        }

        for (int line = 0; line < data.size(); line++) {
            String[] parts = data.get(line).split(" = ");

            String names = parts[0];
            String values = parts[1];
            
            List<String> list1 = Arrays.asList(names);

            if (list1.contains("g")) {
                Double g = Double.parseDouble(values);
             
                System.out.println(g);
            }

            if (list1.contains("m")) {
                Double m = Double.parseDouble(values);

                System.out.println(m);
            }

            if(list1.contains("mu")) {
                Double mu = Double.parseDouble(values);

                System.out.println(mu);
            }

            if(list1.contains("vmax")) {
                Double vmax = Double.parseDouble(values);

                System.out.println(vmax);
            }

            if(list1.contains("tol")) {
                Double tol = Double.parseDouble(values);

                System.out.println(tol);
            }

            if(list1.contains("start")) {
                //String[] split = list.get(values).split(",");   //needs adjusting
            }

            if(list1.contains("goal")) {    //needs adjusting

            }

            if(list1.contains("height")) {      //needs adjusting

            }

            //System.out.println(names);
            //System.out.println(values);
        }

    }
}
