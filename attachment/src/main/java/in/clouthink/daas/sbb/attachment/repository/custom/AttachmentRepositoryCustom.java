package in.clouthink.daas.sbb.attachment.repository.custom;

import in.clouthink.daas.sbb.attachment.domain.model.Attachment;
import in.clouthink.daas.sbb.attachment.domain.request.AttachmentQueryRequest;
import org.springframework.data.domain.Page;

public interface AttachmentRepositoryCustom {

	Page<Attachment> queryPage(AttachmentQueryRequest queryRequest);

	void updateDownloadCounter(String id, int downloadCounter);

}
