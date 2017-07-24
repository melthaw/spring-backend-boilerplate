package in.clouthink.daas.sbb.shared.repository;


import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface AbstractRepository<T> extends PagingAndSortingRepository<T, String> {

	/**
	 * Finds the individual record by the external facing id. This is used
	 * instead of findOne, which finds based on the database id.
	 *
	 * @param id The Externally facing ID
	 * @return the matching record
	 */
	T findById(String id);

}
