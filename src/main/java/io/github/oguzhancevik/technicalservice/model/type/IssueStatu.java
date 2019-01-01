package io.github.oguzhancevik.technicalservice.model.type;

/**
 * @author oguzhan
 */
public enum IssueStatu {

	MAINTENANCE("BAKIM"), REPAIR("ONARIM");

	private String displayName;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	private IssueStatu(String displayName) {
		this.displayName = displayName;
	}
}
