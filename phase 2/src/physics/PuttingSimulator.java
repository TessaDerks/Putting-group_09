package physics;

import java.util.ArrayList;

public class PuttingSimulator{
	private Vector2d ball_position;
	public Vector2d last_ball_position;
	private Vector2d ball_velocity;
	private double last_angle;
	private int round_count = 0;

	private PuttingCourse course;
	private PhysicsEngine engine;

	private ArrayList<Tree> trees = new ArrayList<>();

	public PuttingSimulator(PuttingCourse _course, PhysicsEngine _engine){
		course = _course;
		engine = _engine;
		engine.setMu(course.get_friction_coefficient());
		engine.set_v(new Vector2d(0,0));
		engine.recalculate();
	}

	public void take_shot(Vector2d initial_ball_velocity){
		round_count++;
		engine.set_t(0);
		engine.set_v(initial_ball_velocity);
		last_ball_position = ball_position;

		while(Tools.advRound(engine.get_v().get_x(),2) == 0 || Tools.advRound(engine.get_v().get_y(),2) == 0) {
			engine.actTimestep();

			Vector2d cp = engine.get_p();
			Vector2d cv = engine.get_v();

			// If ball hits water.
			if(engine.get_h().evaluate(new Vector2d(cp.get_x(),cp.get_y())) < 0){
				System.out.println("Ball fell in the water!");
				ball_position = last_ball_position;
				break;
			}

			// Calculate tree hits.
			for(int i = 0; i < trees.size(); i++){
				if(trees.get(i).treeHit(cp)){
					double rc;
					double newAngle;
					if(last_angle == 0 && ball_position.get_x() == trees.get(i).getP().get_x()){
						newAngle = 180;
					}
					else if(last_angle == 180 && ball_position.get_x() == trees.get(i).getP().get_x()){
						newAngle = 0;
					}
					else{
						rc = (trees.get(i).getP().get_y() - cp.get_y()) / (trees.get(i).getP().get_x() - cp.get_x());
						newAngle = 2 * Math.tan(rc) - last_angle - 90;
					}

					double ntv = Math.sqrt(Math.pow(cv.get_x(),2) + Math.pow(cv.get_y(),2));
					Vector2d nv = Tools.velFromAngle(newAngle,ntv);
					engine.set_v(nv);
					break;
				}
			}

			// Update ball position.
			ball_position = cp;
		}

		System.out.println(">> Shot landed at ( " + ball_position.get_x() + " , " + ball_position.get_y() + " ). t=" + engine.get_t());
	}

	public boolean calcWin(){
		boolean r = false;
		double absDis = Math.sqrt(Math.pow(ball_position.get_x() - course.get_flag_position().get_x(),2) + Math.pow(ball_position.get_y() - course.get_flag_position().get_y(),2));
		if(absDis < course.get_hole_tolerance()){
			r = true;
		}
		return r;
	}

	public Vector2d act_timestep_from_distance() {
		engine.actTimestep();

		Vector2d cp = engine.get_p();
		Vector2d cv = engine.get_v();

		// If ball hits water.
		if (engine.get_h().evaluate(new Vector2d(cp.get_x(), cp.get_y())) < 0) {
			System.out.println("Ball fell in the water!");
			ball_position = last_ball_position;
			return	ball_position;
		}

		// Calculate tree hits.
		for (int i = 0; i < trees.size(); i++) {
			if (trees.get(i).treeHit(cp)) {
				double rc;
				double newAngle;
				if (last_angle == 0 && ball_position.get_x() == trees.get(i).getP().get_x()) {
					newAngle = 180;
				} else if (last_angle == 180 && ball_position.get_x() == trees.get(i).getP().get_x()) {
					newAngle = 0;
				} else {
					rc = (trees.get(i).getP().get_y() - cp.get_y()) / (trees.get(i).getP().get_x() - cp.get_x());
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