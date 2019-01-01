package io.github.oguzhancevik.technicalservice.model.type;

/**
 * @author oguzhan
 */
public enum ProcessType {

	WAITING("BEKLİYOR"), END("BİTTİ");

	private String displayName;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	private ProcessType(String displayName) {
		this.displayName = displayName;
	}
}
