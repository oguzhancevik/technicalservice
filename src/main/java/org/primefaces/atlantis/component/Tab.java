package org.primefaces.atlantis.component;

import javax.faces.component.UIComponentBase;

public class Tab extends UIComponentBase {

	public static final String COMPONENT_TYPE = "org.primefaces.component.AtlantisTab";
	public static final String COMPONENT_FAMILY = "org.primefaces.component";

	public enum PropertyKeys {
		icon,
        title;

		String toString;

		PropertyKeys(String toString) {
			this.toString = toString;
		}

		PropertyKeys() {}

		public String toString() {
			return ((this.toString != null) ? this.toString : super.toString());
        }
	}

	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	public java.lang.String getIcon() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.icon, null);
	}
	public void setIcon(java.lang.String _icon) {
		getStateHelper().put(PropertyKeys.icon, _icon);
	}
    
    public java.lang.String getTitle() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.title, null);
	}
	public void setTitle(java.lang.String _title) {
		getStateHelper().put(PropertyKeys.title, _title);
	}
    
}