/**
 * Added By Nikhil On 19-Nov-2018 for Basic CURD operations
 *
 */
package com.api.RESTful.dao;

import com.api.RESTful.dto.issues.Story;

public interface DAORepository<T, I> {
	/**
	 * create the <code>T</code>
	 *
	 * @param i
	 */
	public void create(T i);

	/**
	 * get the <code>T</code>
	 *
	 * @param i
	 * @return
	 */
	public T get(I i);

	/**
	 * update the <code>T</code>
	 *
	 * @param i
	 */
	public void update(T i);

	/**
	 * update the <code>T</code>
	 *
	 * @param i
	 */
	public void delete(I i);

	/**
	 * Added By Nikhil On 20-Nov-2018 for
	 */
	Story get(int i);

}
