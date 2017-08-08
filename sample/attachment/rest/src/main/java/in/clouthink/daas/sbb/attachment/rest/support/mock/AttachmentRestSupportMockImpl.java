package in.clouthink.daas.sbb.attachment.rest.support.mock;

import in.clouthink.daas.fss.core.FileObject;
import in.clouthink.daas.fss.rest.UploadFileRequest;
import in.clouthink.daas.sbb.account.domain.model.User;
import in.clouthink.daas.sbb.attachment.rest.dto.*;
import in.clouthink.daas.sbb.attachment.rest.support.AttachmentRestSupport;
import in.clouthink.daas.sbb.shared.domain.request.impl.PageQueryParameter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AttachmentRestSupport mocker
 *
 * @author dz
 */
@Component
public class AttachmentRestSupportMockImpl implements AttachmentRestSupport {
	@Override
	public Page<AttachmentSummary> listAttachment(AttachmentQueryParameter queryRequest) {
		return null;
	}

	@Override
	public AttachmentDetail getAttachmentDetail(String id) {
		return null;
	}

	@Override
	public String createAttachment(SaveAttachmentParameter request, User user) {
		return null;
	}

	@Override
	public void updateAttachment(String id, SaveAttachmentParameter request, User user) {

	}

	@Override
	public void deleteAttachment(String id, User user) {

	}

	@Override
	public void publishAttachment(String id, User user) {

	}

	@Override
	public void unpublishAttachment(String id, User user) {

	}

	@Override
	public Page<DownloadSummary> listDownloadHistory(String id, PageQueryParameter queryParameter) {
		return null;
	}

	@Override
	public void downloadAttachment(String id, User user, HttpServletResponse response) throws IOException {

	}

	@Override
	public FileObject uploadAvatar(UploadFileRequest uploadFileRequest,
								   HttpServletRequest request,
								   HttpServletResponse response) throws IOException {
		return null;
	}
}
