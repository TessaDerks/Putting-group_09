package physics;

import org.jetbrains.annotations.NotNull;

/**
 * @author Stijn Hennissen
 */
public class PuttingSimulator{

	//<editor-fold desc="Global Variables">
	private Vector2d ball_position;
	public Vector2d last_ball_position;
	private double last_angle;

	private PuttingCourse course;
	private PhysicsEngine engine;
	//</editor-fold>

	/**
	 * Constructor
	 * @param _course PuttingCourse, the course to play on
	 * @param _engine PhysicsEngine, the physics engine used for calculations
	 */
	public PuttingSimulator(PuttingCourse _course, PhysicsEngine _engine){
		course = _course;
		engine = _engine;
		engine.setMu(course.get_friction_coefficient());
		engine.set_v(new Vector2d(0,0));
		engine.recalculate();
	}

	/**
	 * Take a shot
	 * @param initial_ball_velocity Vector2d, the velocity of the ball when the shot is taken
	 * @param useWhileLoop boolean, set to true if shot should be calculated completely,
	 * or false when only one time step should be calculated.
	 */
	public void take_shot(Vector2d initial_ball_velocity, boolean useWhileLoop){

		engine.set_t(0);
		engine.set_v(initial_ball_velocity);
		last_ball_position = ball_position;

		if(useWhileLoop) {

			// Act timesteps.
			while(Tools.advRound(engine.get_v().get_x(),2) != 0 || Tools.advRound(engine.get_v().get_y(),2) != 0) {
				engine.actTimestep();

				Vector2d cp = engine.get_p();
				Vector2d cv = engine.get_v();

				// If ball hits water.
				if(engine.get_h().evaluate(new Vector2d(cp.get_x(),cp.get_y())) < 0){
					System.out.println("Ball fell in the water!");
					ball_position = last_ball_position;
					break;
				}

				// Update ball position.
				ball_position = cp;
			}
			//System.out.println(ball_position.toString());
		}
	}

	/**
	 * Calculate if ball is in the hole
	 * @param position Vector2d, position of ball
	 * @param flagPos Vector2d, position of flag
	 * @param clearance double, clearance > 0.1, number that radius around hole will be divide by, to have ai aim better
	 * @return boolean, true when ball is in hole, false otherwise
	 */
	public boolean calcWin(@NotNull Vector2d position, @NotNull Vector2d flagPos, double clearance){
		boolean r = false;
		double absDis = Math.sqrt(Math.pow(position.get_x() - flagPos.get_x(),2) + Math.pow(position.get_y() - flagPos.get_y(),2));
		if(absDis < course.get_hole_tolerance()/clearance){
			r = true;
		}
		return r;
	}

	/**
	 * Allows graphics engine to execute a time step
	 * @return Vector2d, ball position after time step was executed
	 */
	public Vector2d act_timestep_from_distance() {
		engine.actTimestep();

		Vector2d cp = engine.get_p();
		Vector2d cv = engine.get_v();

		// If ball hits water.
		if (engine.get_h().evaluate(new Vector2d(cp.get_x(), cp.get_y())) < 0) {
			System.out.println("Ball fell in the water!");
			ball_position = last_ball_position;
			engine.set_v(new Vector2d(0,0));
			return	ball_position;
		}

		// Calculate tree hits.
		for (Tree t : course.getTreeList()) {
			if (t.treeHit(cp)) {
				double rc;
				double newAngle;
				if (last_angle == 0 && ball_position.get_x() == t.getP().get_x()) {
					newAngle = 180;
				} else if (last_angle == 180 && ball_position.get_x() == t.getP().get_x()) {
					newAngle = 0;
				} else {
					rc = (t.getP().get_y() - cp.get_y()) / (t.getP().get_x() - cp.get_x());
					newAngle = 2 * Math.tan(rc) - last_angle - 90;
				}

				double ntv = Math.sqrt(Math.pow(cv.get_x(), 2) + Math.pow(cv.get_y(), 2));
				Vector2d nv = Tools.velFromAngle(newAngle, ntv);
				engine.set_v(nv);
				break;
			}
		}

		ball_position = cp;

		return ball_position;
	}

	// Setters.
	public void set_ball_position(Vector2d _ball_position){
		ball_position = _ball_position;
	}

	// Getters.
	public Vector2d get_ball_position(){
		return ball_position;
	}

	public PuttingCourse get_course(){
		return course;
	}

	public PhysicsEngine get_engine(){
		return engine;
	}
}