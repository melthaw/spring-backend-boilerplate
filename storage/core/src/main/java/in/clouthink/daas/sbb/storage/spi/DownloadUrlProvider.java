package in.clouthink.daas.sbb.storage.spi;

/**
 * The download url provider because different storage service stored the file in different way.
 */
public interface DownloadUrlProvider {

	/**
	 * @param id
	 * @return the download url of specified attachment by id
	 */
	String getDownloadUrl(String id);

}
