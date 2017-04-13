var PageRPC = jsonrpc.PageRPC;
var PageBean = new Bean("com.deya.wcm.bean.page.PageBean",true);
var val=new Validator();

function openAddPage(parent_id)
{	
	parent.addTab(true,"/sys/page/page_add.jsp?parent_id="+parent_id+"&app_id="+app_id+"&site_id="+site_id+"&cur_indexid="+parent.curTabIndex,'修改页面');
}

function openUpdatePage(id)
{
    parent.addTab(true,"/sys/page/page_add.jsp?id="+id+"&cur_indexid="+parent.curTabIndex,'修改页面');
}

function addPage()
{
	var bean = BeanUtil.getCopy(PageBean);
	$("#page_table").autoBind(bean);
	if(!standard_checkInputInfo("page_table"))
	{
		return;
	}

	if(PageRPC.pageIsExist(bean.page_path,bean.page_ename,site_id))
	{
        parent.msgWargin("该文件名已存在此目录下");
		return;
	}

	bean.app_id = app_id;
	bean.site_id = site_id;
	bean.parent_id = parent_id;
	bean.id = PageRPC.getNewPageID();
	bean.page_id = bean.id;

	if(PageRPC.insertPage(bean))
	{
        parent.msgAlert("页面信息"+WCMLang.Add_success);
        parent.getCurrentFrameObj(top_index_id).initPageTree();
        parent.tab_colseOnclick(parent.curTabIndex)
	}else
	{
        parent.msgWargin("页面信息"+WCMLang.Add_fail);
	}
}

function updatePage()
{
	var bean = BeanUtil.getCopy(PageBean);
	$("#page_table").autoBind(bean);
	if(!standard_checkInputInfo("page_table"))
	{
		return;
	}

	//判断英文名或路径是否有变更，如果有，进行目录判断
	if(defaultBean.page_ename != bean.page_ename || defaultBean.page_path != bean.page_path)
	{
		//if(PageRPC.pageIsExist("",bean.page_path,bean.page_ename,app_id,site_id))
		if(PageRPC.pageIsExist(bean.page_path,bean.page_ename,site_id))
		{
            parent.msgWargin("该文件名已存在此目录下");
			return;
		}
	}

	bean.id = id;
	if(PageRPC.updatePage(bean))
	{
        parent.msgAlert("页面信息"+WCMLang.Add_success);
        parent.getCurrentFrameObj(top_index_id).initPageTree();
        parent.tab_colseOnclick(parent.curTabIndex)
	}else
	{
        parent.msgWargin("页面信息"+WCMLang.Add_fail);
	}
}



function deletePage(id)
{
    parent.msgConfirm(WCMLang.Delete_confirm,"deletePageHandl("+id+")");
}

function deletePageHandl(id)
{
	if(PageRPC.deletePage(id))
	{
        parent.msgAlert("页面信息"+WCMLang.Delete_success);
		initPageTree();
	}else
	{
        parent.msgWargin("页面信息"+WCMLang.Delete_fail);
	}
}

function createHtmlPage(id)
{
	if(PageRPC.createHtmlPage(id))
	{
        parent.msgAlert("静态页面生成成功");
		initPageTree();
	}else
	{
        parent.msgWargin("静态页面生成失败");
	}
}