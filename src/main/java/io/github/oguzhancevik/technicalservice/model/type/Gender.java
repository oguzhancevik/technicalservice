package io.github.oguzhancevik.technicalservice.model.type;

/**
 * @author oguzhan
 */
public enum Gender {

	MAN("ERKEK"), WOMAN("KADIN");

	private String displayName;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	private Gender(String displayName) {
		this.displayName = displayName;
	}
}
