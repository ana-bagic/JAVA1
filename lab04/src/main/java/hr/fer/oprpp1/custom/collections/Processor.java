package hr.fer.oprpp1.custom.collections;

/**
 * Sučelje predstavlja procesor - model objekta koji može izvesti operaciju nad poslanim objektom.
 * 
 * @author Ana Bagić
 *
 */
public interface Processor<T> {

	/**
	 * Provodi neku operaciju nad poslanim objektom.
	 * 
	 * @param value objekt nad kojim se provodi operacija
	 */
	void process(T value);
}
