package model;

public class TableInfo {
	private int tableId, sessionId;
	private String sessionStatus;

	private TableInfo(int tableId, int sessionId, String sessionStatus) {
		super();
		this.tableId = tableId;
		this.sessionId = sessionId;
		this.sessionStatus = sessionStatus;
	}

	public int getTableId() {
		return tableId;
	}
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	public int getSessionId() {
		return sessionId;
	}
	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionStatus() {
		return sessionStatus;
	}
	public void setSessionStatus(String sessionStatus) {
		this.sessionStatus = sessionStatus;
	}
	public static void main(String[] args) {
		
	}
}
