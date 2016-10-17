function setClickCount(app_id,id)
{
	if(app_id == "appeal")
	{
		jsonrpc.SQRPC.setSQClickCount(id);
	}
}