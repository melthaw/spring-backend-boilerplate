package in.clouthink.daas.sbb.storage.service;

/**
 */
public interface StorageService {

	/**
	 * @param id
	 * @return the download url of specified attachment by id
	 */
	String resolveImageUrl(String id);

}
