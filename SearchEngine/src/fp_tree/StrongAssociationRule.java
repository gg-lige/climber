package fp_tree;

import java.util.List;

import org.apache.struts2.components.Else;

public class StrongAssociationRule implements Comparable<StrongAssociationRule> {
	public List<String> condition;
	public String result;
	public int support;
	public double confidence;

	@Override
	public String toString() {
		return "StrongAssociationRule [condition=" + condition + ", result=" + result + ", support=" + support + ", confidence=" + confidence + "]";
	}

	@Override
	public int compareTo(StrongAssociationRule o) {
		// TODO Auto-generated method stub
		if (confidence < o.confidence) {
			return -1;
		} else if (confidence > o.confidence) {
			return 1;
		} else {
			return 0;
		}

	}

}
