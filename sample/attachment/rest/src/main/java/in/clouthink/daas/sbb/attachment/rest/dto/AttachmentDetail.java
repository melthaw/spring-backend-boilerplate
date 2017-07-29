package in.clouthink.daas.sbb.attachment.rest.dto;

import in.clouthink.daas.sbb.attachment.domain.model.Attachment;
import io.swagger.annotations.ApiModel;

/**
 *
 */
@ApiModel
public class AttachmentDetail extends AttachmentSummary {

	public static AttachmentDetail from(Attachment attachment, Object fileObject) {
		if (attachment == null) {
			return null;
		}
		AttachmentDetail result = new AttachmentDetail();
		convert(attachment, result);
		result.setFileObject(fileObject);
		return result;
	}

	//附件关联的存储对象,例如daas fss object
	private Object fileObject;

	public Object getFileObject() {
		return fileObject;
	}

	public void setFileObject(Object fileObject) {
		this.fileObject = fileObject;
	}

}
