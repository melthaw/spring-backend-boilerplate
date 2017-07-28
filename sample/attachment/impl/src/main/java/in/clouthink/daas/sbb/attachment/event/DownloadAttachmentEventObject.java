package in.clouthink.daas.sbb.attachment.event;


import in.clouthink.daas.sbb.account.domain.model.SysUser;
import in.clouthink.daas.sbb.attachment.domain.model.Attachment;
import in.clouthink.daas.sbb.attachment.domain.request.DownloadAttachmentEvent;

/**
 * The download attachment event object
 */
public class DownloadAttachmentEventObject implements DownloadAttachmentEvent {

	private Attachment attachment;

	private SysUser user;

	public DownloadAttachmentEventObject(Attachment attachment, SysUser user) {
		this.attachment = attachment;
		this.user = user;
	}

	@Override
	public Attachment getAttachment() {
		return attachment;
	}

	@Override
	public SysUser getUser() {
		return user;
	}

}
