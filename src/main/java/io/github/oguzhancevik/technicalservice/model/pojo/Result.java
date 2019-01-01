package io.github.oguzhancevik.technicalservice.model.pojo;

/**
 * @author oguzhan
 */
public class Result {

	private Boolean result;

	private String message;

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Result [result=" + result + ", message=" + message + "]";
	}

}
