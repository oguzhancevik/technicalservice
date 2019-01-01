package io.github.oguzhancevik.technicalservice.model.type;

/**
 * @author oguzhan
 */
public enum MemberStatu {
	WAITING("ONAY BEKLİYOR"), APPROVED("ONAYLANDI"), DENIED("REDDEDİLDİ"), BLOCKED("BLOKELİ");

	private String displayName;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	private MemberStatu(String displayName) {
		this.displayName = displayName;
	}

}
