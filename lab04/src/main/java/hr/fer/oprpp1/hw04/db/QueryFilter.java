package hr.fer.oprpp1.hw04.db;

import java.util.List;
import java.util.Objects;

/**
 * Razred kroz metodu accepts filtrira zapise o studentima koristeći predane uvjetne izraze.
 * 
 * @author Ana Bagić
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * Lista uvjetnih izraza.
	 */
	private List<ConditionalExpression> expressions;
	
	/**
	 * Konstruktor prima listu uvjetnih izraza po kojoj filtrira zapise.
	 * 
	 * @param expressions lista uvjetnih izraza
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = Objects.requireNonNull(expressions);
	}
	
	@Override
	public boolean accepts(StudentRecord record) {
		for(ConditionalExpression exp : expressions) {
			String value1 = exp.getFieldGetter().get(record);
			String value2 = exp.getStringLiteral();
			
			if(!exp.getComparisonOperator().satisfied(value1, value2))
				return false;
		}
		
		return true;
	}

}
