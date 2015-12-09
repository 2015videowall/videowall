package com.millionasia.asuscloud.entity;

public class GetAccountInfoResponse 
{
	/* all fields */
	private String _status = null;
	private String _account = null;
	private String _email = null;
	
	/* default constructor */
	public GetAccountInfoResponse()
	{		
	}

	/* all fields constructor */
	public GetAccountInfoResponse(String status, String account, String email)
	{
		this._status = status;
		this._account = account;
		this._email = email;
	}

	/* setters */
	public void setStatus(String _status) {
		this._status = _status;
	}

	public void setAccount(String _account) {
		this._account = _account;
	}

	public void setEmail(String _email) {
		this._email = _email;
	}
	

	/* getters */
	public String getStatus() {
		return _status;
	}

	public String getAccount() {
		return _account;
	}

	public String getEmail() {
		return _email;
	}
	
	/* toString */
	public String toString() 
	{
		StringBuilder msg = new StringBuilder("GetAccountInfo=>\n");
		msg.append(" status:").append(_status).append("\n");
		msg.append(" account:").append(_account).append("\n");
		msg.append(" email:").append(_email).append("\n");
		return msg.toString();
	}
}
