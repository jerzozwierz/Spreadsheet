package utilities;

public enum OperationType {
	ADD {
		@Override
		public double apply(double x, double y) {
			return x + y;
		}
	}, 
	SUBTRACT {
		@Override
		public double apply(double x, double y) {
			return x - y;
		}
	},
	MULTIPLY {
		@Override
		public double apply(double x, double y) {
			return x * y;
		}
	}, 
	DIVIDE {
		@Override
		public double apply(double x, double y) {
			return x / y;
		}
	};
	
	public abstract double apply(double x, double y);
}
