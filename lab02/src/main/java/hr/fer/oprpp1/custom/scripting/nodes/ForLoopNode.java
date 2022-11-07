package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * Razred predstavlja for-loop strukturu.
 * 
 * @author Ana Bagić
 *
 */
public class ForLoopNode extends Node {
	
	/**
	 * Varijabla.
	 */
	private ElementVariable variable;
	/**
	 * Prvi element izraza.
	 */
	private Element startExpression;
	/**
	 * Zadnji element izraza.
	 */
	private Element endExpression;
	/**
	 * Opcionalni srednji element izraza.
	 */
	private Element stepExpression;
	
	/**
	 * Konstruktor stvara novi čvor na temelju poslanih parametara.
	 * 
	 * @param variable varijabla
	 * @param startExpression prvi element izraza
	 * @param endExpression zadnji element izraza
	 * @param stepExpression srednji element izraza
	 * @throws NullPointerException ako je bilo koji osim stepExpression <code>null</code>
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		if(variable == null || startExpression == null || endExpression == null)
			throw new NullPointerException("Svi parametri osim stepExpression moraju biti not null");
		
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Vraća varijablu.
	 * 
	 * @return varijablu
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Vraća prvi element izraza.
	 * 
	 * @return prvi element izraza
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Vraća zadnji element izraza.
	 * 
	 * @return zadnji element izraza
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Vraća srednji element izraza.
	 * 
	 * @return srednji element izraza
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{$ FOR ");
		sb.append(variable.asText()).append(" ").append(startExpression.asText());
		
		if(stepExpression != null)
			sb.append(" ").append(stepExpression.asText());
		
		sb.append(" ").append(endExpression.asText()).append(" $}");
		
		for(int i=0; i<numberOfChildren(); i++) {
			sb.append(getChild(i));
		}
		
		sb.append("{$ END $}");
		
		return sb.toString();
	}
	
	@Override
	public String toStringFancy() {
		StringBuilder sb = new StringBuilder();
		sb.append("ForLoopNode : ");
		sb.append(variable.asText()).append(" ").append(startExpression.asText());
		
		if(stepExpression != null)
			sb.append(" ").append(stepExpression.asText());
		
		sb.append(" ").append(endExpression.asText()).append("\n");
		
		for(int i=0; i<numberOfChildren(); i++) {
			sb.append("        ");
			sb.append(getChild(i).toStringFancy());
		}
		
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ForLoopNode))
			return false;
		ForLoopNode other = (ForLoopNode) obj;
		
		if(this.getVariable().equals(other.getVariable()) && this.getStartExpression().equals(other.getStartExpression()) &&
				this.getStepExpression().equals(other.getStepExpression()) && this.getEndExpression().equals(other.getEndExpression()))
			return true;
		
		return false;
	}
	
}
