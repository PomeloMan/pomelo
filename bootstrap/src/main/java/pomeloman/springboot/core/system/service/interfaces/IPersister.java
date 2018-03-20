package pomeloman.springboot.core.system.service.interfaces;

import java.util.Collection;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface IPersister {

	/**
	 * @param col
	 */
	@Transactional
	public void merge(Collection<?> col);

	/**
	 * @param col
	 */
	@Transactional
	public void persist(Collection<?> col);
}
