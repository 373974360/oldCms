jsonrpc.SiteRPC.addSiteHits();

$.fn.getSiteCount = function(hit_type){
	if(hit_type == null) hit_type = "";
	$(this).text(jsonrpc.SiteRPC.getSiteHits(hit_type));
}