package physics;


public class TestSolvers {

    public static void main(String[]args){

      //=============================================================================================
        System.out.println("=====90, 10=====");

        long totalTimeValues = 0;

        for(int i = 0; i<1000;i++) {

            long startT = System.nanoTime();
            SimulateMain.beginning(9.81,45,0.131, 500, 0.2, new Vector2d(8.75,10), new Vector2d(250,250), "1"  );
            SimulateMain.simulator.take_shot(Tools.velFromAngle(90, 10), true);
            long endT = System.nanoTime();
            double totalTime = SimulateMain.simulator.get_engine().get_t();
            double dt = 0.01;
            double steps = totalTime/dt;
            long time = endT - startT;
            totalTimeValues += (long) (time/steps);
        }

        System.out.println("Sorted the list in " + (totalTimeValues)/1000 + " nanoseconds ");

//=============================================================================================
        System.out.println("=====90, 30=====");

        totalTimeValues = 0;

        for(int i = 0; i<1000;i++) {

            long startT = System.nanoTime();
            SimulateMain.beginning(9.81,45,0.131, 500, 0.2, new Vector2d(8.75,10), new Vector2d(250,250), "0.6 * cos x + 2"  );
            SimulateMain.simulator.take_shot(Tools.velFromAngle(90, 30), true);
            long endT = System.nanoTime();
            double totalTime = SimulateMain.simulator.get_engine().get_t();
            double dt = 0.01;
            double steps = totalTime/dt;
            long time = endT - startT;
            totalTimeValues += (long) (time/steps);
        }

        System.out.println("Sorted the list in " + (totalTimeValues)/1000 + " nanoseconds ");

        //=============================================================================================
        System.out.println("=====90, 50=====");

        totalTimeValues = 0;

        for(int i = 0; i<1000;i++) {

            long startT = System.nanoTime();
            SimulateMain.beginning(9.81,45,0.131, 500, 0.2, new Vector2d(8.75,10), new Vector2d(250,250), "0.6 * cos x + 2"  );
            SimulateMain.simulator.take_shot(Tools.velFromAngle(90, 50), true);
            long endT = System.nanoTime();
            double totalTime = SimulateMain.simulator.get_engine().get_t();
            double dt = 0.01;
            double steps = totalTime/dt;
            long time = endT - startT;
            totalTimeValues += (long) (time/steps);
        }

        System.out.println("Sorted the list in " + (totalTimeValues)/1000 + " nanoseconds ");


        //=============================================================================================
        System.out.println("=====90, 500=====");

        totalTimeValues = 0;

        for(int i = 0; i<1000;i++) {

            long startT = System.nanoTime();
            SimulateMain.beginning(9.81,45,0.131, 500, 0.2, new Vector2d(8.75,10), new Vector2d(250,250), "0.6 * cos x + 2"  );
            SimulateMain.simulator.take_shot(Tools.velFromAngle(90, 500), true);
            long endT = System.nanoTime();
            double totalTime = SimulateMain.simulator.get_engine().get_t();
            double dt = 0.01;
            double steps = totalTime/dt;
            long time = endT - startT;
            totalTimeValues += (long) (time/steps);
        }

        System.out.println("Sorted the list in " + (totalTimeValues)/1000 + " nanoseconds ");






        /*

        long startTT = System.nanoTime();
        for(int i = 0; i<100;i++) {
            SimulateMain.simulator.take_shot(Tools.velFromAngle(90, 10), true);
            SimulateMain.simulator.get_engine().resetPosition(new Vector2d(8.75,10));
        }
        long endTT = System.nanoTime();
        System.out.println("Sorted the list in " + (endTT - startTT) + " nanoseconds ");

        long startTTT = System.nanoTime();
        for(int i = 0; i<100;i++) {
            SimulateMain.simulator.take_shot(Tools.velFromAngle(90, 10), true);
            SimulateMain.simulator.get_engine().resetPosition(new Vector2d(8.75,10));
        }
        long endTTT = System.nanoTime();
        System.out.println("Sorted the list in " + (endTTT - startTTT) + " nanoseconds ");

        startTTT = System.nanoTime();
        for(int i = 0; i<100;i++) {
            SimulateMain.simulator.take_shot(Tools.velFromAngle(90, 10), true);
            SimulateMain.simulator.get_engine().resetPosition(new Vector2d(8.75,10));
        }
        endTTT = System.nanoTime();
        System.out.println("Sorted the list in " + (endTTT - startTTT) + " nanoseconds ");

        startTTT = System.nanoTime();
        for(int i = 0; i<100;i++) {
            SimulateMain.simulator.take_shot(Tools.velFromAngle(90, 10), true);
            SimulateMain.simulator.get_engine().resetPosition(new Vector2d(8.75,10));
        }
        endTTT = System.nanoTime();
        System.out.println("Sorted the list in " + (endTTT - startTTT) + " nanoseconds ");

// ========================================================================

        System.out.println("=====90, 30======");

        startT = System.nanoTime();
        for(int i = 0; i<100;i++) {
            SimulateMain.simulator.take_shot(Tools.velFromAngle(90, 30), true);
            SimulateMain.simulator.get_engine().resetPosition(new Vector2d(8.75,10));
        }
        endT = System.nanoTime();
        System.out.println("Sorted the list in " + (endT - startT) + " nanoseconds ");

        startTT = System.nanoTime();
        for(int i = 0; i<100;i++) {
            SimulateMain.simulator.take_shot(Tools.velFromAngle(90, 30), true);
            SimulateMain.simulator.get_engine().resetPosition(new Vector2d(8.75,10));
        }
        endTT = System.nanoTime();
        System.out.println("Sorted the list in " + (endTT - startTT) + " nanoseconds ");

        startTTT = System.nanoTime();
        for(int i = 0; i<100;i++) {
            SimulateMain.simulator.take_shot(Tools.velFromAngle(90, 30), true);
            SimulateMain.simulator.get_engine().resetPosition(new Vector2d(8.75,10));
        }
        endTTT = System.nanoTime();
        System.out.println("Sorted the list in " + (endTTT - startTTT) + " nanoseconds ");

        startTTT = System.nanoTime();
        for(int i = 0; i<100;i++) {
            SimulateMain.simulator.take_shot(Tools.velFromAngle(90, 30), true);
            SimulateMain.simulator.get_engine().resetPosition(new Vector2d(8.75,10));
        }
        endTTT = System.nanoTime();
        System.out.println("Sorted the list in " + (endTTT - startTTT) + " nanoseconds ");

        startTTT = System.nanoTime();
        for(int i = 0; i<100;i++) {
            SimulateMain.simulator.take_shot(Tools.velFromAngle(90, 30), true);
            SimulateMain.simulator.get_engine().resetPosition(new Vector2d(8.75,10));
        }
        endTTT = System.nanoTime();
        System.out.println("Sorted the list in " + (endTTT - startTTT) + " nanoseconds ");


        // ========================================================================

        System.out.println("=====90, 50=====");

        startT = System.nanoTime();
        for(int i = 0; i<100;i++) {
            SimulateMain.simulator.take_shot(Tools.velFromAngle(90, 50), true);
            SimulateMain.simulator.get_engine().resetPosition(new Vector2d(8.75,10));
        }
        endT = System.nanoTime();
        System.out.println("Sorted the list in " + (endT - startT) + " nanoseconds ");

        startTT = System.nanoTime();
        for(int i = 0; i<100;i++) {
            SimulateMain.simulator.take_shot(Tools.velFromAngle(90, 50), true);
            SimulateMain.simulator.get_engine().resetPosition(new Vector2d(8.75,10));
        }
        endTT = System.nanoTime();
        System.out.println("Sorted the list in " + (endTT - startTT) + " nanoseconds ");

        startTTT = System.nanoTime();
        for(int i = 0; i<100;i++) {
            SimulateMain.simulator.take_shot(Tools.velFromAngle(90, 50), true);
            SimulateMain.simulator.get_engine().resetPosition(new Vector2d(8.75,10));
        }
        endTTT = System.nanoTime();
        System.out.println("Sorted the list in " + (endTTT - startTTT) + " nanoseconds ");

        startTTT = System.nanoTime();
        for(int i = 0; i<100;i++) {
            SimulateMain.simulator.take_shot(Tools.velFromAngle(90, 50), true);
            SimulateMain.simulator.get_engine().resetPosition(new Vector2d(8.75,10));
        }
        endTTT = System.nanoTime();
        System.out.println("Sorted the list in " + (endTTT - startTTT) + " nanoseconds ");

        startTTT = System.nanoTime();
        for(int i = 0; i<100;i++) {
            SimulateMain.simulator.take_shot(Tools.velFromAngle(90, 50), true);
            SimulateMain.simulator.get_engine().resetPosition(new Vector2d(8.75,10));
        }
        endTTT = System.nanoTime();
        System.out.println("Sorted the list in " + (endTTT - startTTT) + " nanoseconds ");

        // ========================================================================

        System.out.println("======90, 500======");

        startT = System.nanoTime();
        for(int i = 0; i<100;i++) {
            SimulateMain.simulator.take_shot(Tools.velFromAngle(90, 500), true);
            SimulateMain.simulator.get_engine().resetPosition(new Vector2d(8.75,10));
        }
        endT = System.nanoTime();
        System.out.println("Sorted the list in " + (endT - startT) + " nanoseconds ");

        startTT = System.nanoTime();
        for(int i = 0; i<100;i++) {
            SimulateMain.simulator.take_shot(Tools.velFromAngle(90, 500), true);
            SimulateMain.simulator.get_engine().resetPosition(new Vector2d(8.75,10));
        }
        endTT = System.nanoTime();
        System.out.println("Sorted the list in " + (endTT - startTT) + " nanoseconds ");

        startTTT = System.nanoTime();
        for(int i = 0; i<100;i++) {
            SimulateMain.simulator.take_shot(Tools.velFromAngle(90, 500), true);
            SimulateMain.simulator.get_engine().resetPosition(new Vector2d(8.75,10));
        }
        endTTT = System.nanoTime();
        System.out.println("Sorted the list in " + (endTTT - startTTT) + " nanoseconds ");

        startTTT = System.nanoTime();
        for(int i = 0; i<100;i++) {
            SimulateMain.simulator.take_shot(Tools.velFromAngle(90, 500), true);
            SimulateMain.simulator.get_engine().resetPosition(new Vector2d(8.75,10));
        }
        endTTT = System.nanoTime();
        System.out.println("Sorted the list in " + (endTTT - startTTT) + " nanoseconds ");

        startTTT = System.nanoTime();
        for(int i = 0; i<100;i++) {
            SimulateMain.simulator.take_shot(Tools.velFromAngle(90, 500), true);
            SimulateMain.simulator.get_engine().resetPosition(new Vector2d(8.75,10));
        }
        endTTT = System.nanoTime();
        System.out.println("Sorted the list in " + (endTTT - startTTT) + " nanoseconds ");

         */
    }
}
