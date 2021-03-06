package environment;

public class Environment {

	private static final double winPosition = 1.75 * Math.PI;
	private static final double winReward = 1;
	private static final double constReward = -0.01;
	private static final CarState winstat = new CarState(winPosition, 0);

	private static final double gravity = 0.1;
	private static final double deltaTime = 1;
	private static final double accelerationRatio = 0.04;

	public Result getResult(CarState state_t, Activity a) {
		CarState state_tpp = calculateNextState(state_t, a);
		double reward = constReward;
		if (Math.abs(state_tpp.getPosition() - winstat.getPosition()) < 0.1) {
			reward = winReward;
		}

		return new Result(state_tpp, reward);
	}

	private CarState calculateNextState(CarState state_t, Activity a) {
		double m = -Math.sin(state_t.getPosition() + Math.PI / 4);
		double acc = -Math.sin(Math.atan(m)) * gravity + (a.ordinal() - 1)
				* accelerationRatio;
		double vel = state_t.getVelocity() + acc * deltaTime / 2;
		double pos = state_t.getPosition() + vel * deltaTime;
		return saturate(new CarState(pos, vel));
	}

	private CarState saturate(CarState stat) {
		double vel = stat.getVelocity();
		vel = vel < CarState.minVelocity ? CarState.minVelocity
				: (vel > CarState.maxVelocity ? CarState.maxVelocity : vel);

		double pos = stat.getPosition();
		if (pos < CarState.minPosition) {
			pos = CarState.minPosition;
			vel *= -0.2;
		} else if (pos > CarState.maxPosition) {
			pos = CarState.maxPosition;
			vel *= -0.2;
		}

		return new CarState(pos, vel);
	}
}
