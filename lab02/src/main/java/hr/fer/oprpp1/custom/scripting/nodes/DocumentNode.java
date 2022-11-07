package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Razred predstavlja dokument.
 * 
 * @author Ana Bagić
 *
 */
public class DocumentNode extends Node {
	
	@Override
		public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i<numberOfChildren(); i++) {
			sb.append(getChild(i));
		}
		
		return sb.toString();
		}
	
	@Override
	public String toStringFancy() {
		StringBuilder sb = new StringBuilder();
		sb.append("DocumentNode :\n");
		
		for(int i=0; i<numberOfChildren(); i++) {
			sb.append("    ");
			sb.append(getChild(i).toStringFancy());
		}
		
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof DocumentNode))
			return false;
		DocumentNode other = (DocumentNode) obj;
		
		if(this.numberOfChildren() != other.numberOfChildren())
			return false;
		
		for(int i=0; i<this.numberOfChildren(); i++) {
			if(!(this.getChild(i).equals(other.getChild(i))))
				return false;
		}
		
		return true;
	}
}
