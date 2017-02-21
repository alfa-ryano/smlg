package uk.ac.york.cs.es.smlg.model;

import java.util.ArrayList;
import java.util.HashMap;

public class SMLGResult {

	public ArrayList<GSMUnsatifiedConstraint> unsatisfiedConstraints = new ArrayList<>();
	public HashMap<String, Object> data = new HashMap<>();
	public boolean completed = false; 

	public class GSMUnsatifiedConstraint {
		public String name = null;
		public String message = null;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		
	}

	public void addUnsatisfiedConstraint(String name, String message) {
		GSMUnsatifiedConstraint item = new GSMUnsatifiedConstraint();
		item.setName(name);
		item.setMessage(message);
		unsatisfiedConstraints.add(item);
		if (unsatisfiedConstraints.size() > 0) {
			completed = false;
		}else{
			completed = true;
		}
	}
	public ArrayList<GSMUnsatifiedConstraint> getUnsatisfiedConstraints() {
		return unsatisfiedConstraints;
	}

	public boolean getCompleted() {
		return completed;
	}
}
