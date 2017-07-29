package in.clouthink.daas.sbb.attachment.rest.support;

import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.attachment.rest.dto.*;
import in.clouthink.daas.sbb.shared.domain.request.impl.PageQueryParameter;
import in.clouthink.daas.fss.core.FileObject;
import in.clouthink.daas.fss.rest.UploadFileRequest;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public interface AttachmentRestSupport {

	Page<AttachmentSummary> listAttachment(AttachmentQueryParameter queryRequest);

	AttachmentDetail getAttachmentDetail(String id);

	String createAttachment(SaveAttachmentParameter request, SysUser user);

	void updateAttachment(String id, SaveAttachmentParameter request, SysUser user);

	void deleteAttachment(String id, SysUser user);

	void publishAttachment(String id, SysUser user);

	void unpublishAttachment(String id, SysUser user);

	Page<DownloadSummary> listDownloadHistory(String id, PageQueryParameter queryParameter);

	void downloadAttachment(String id, SysUser user, HttpServletResponse response) throws IOException;

	FileObject uploadAvatar(UploadFileRequest uploadFileRequest,
							HttpServletRequest request,
							HttpServletResponse response) throws IOException;
}
