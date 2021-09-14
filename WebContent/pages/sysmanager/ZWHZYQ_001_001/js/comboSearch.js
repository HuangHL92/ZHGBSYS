var search;
var ds;
var root1;
Ext.onReady(function() {	
	 ds = new Ext.data.Store({
		autoLoad : false, 
		proxy: new Ext.data.HttpProxy({
			url:'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_001.GroupManage&eventNames=fuzzy'			
		}),
//		baseParams :{b0101:0},
		reader: new Ext.data.JsonReader({
			root: 'rows',
			totalProperty: 'results',
			id: 'b0101'
		}, [
			{name: 'b0101', mapping: 'b0101'},
			{name: 'b0114', mapping: 'b0114'},
			{name: 'b0194', mapping: 'b0194'},
			{name: 'b0111', mapping: 'b0111'},
			{name: 'b0121', mapping: 'b0121'}
		])
	});	
//	ds.load();
	var resultTpl = new Ext.XTemplate(
			'<tpl for="."><div class="search-item" style="width=400px">',
			'<p><span class="listname">{b0101}</span>,<span class="listsex">{b0194}</span><br>',
			'<span class="listnormal">{b0114}</span></p>',
			'</div></tpl>'
		);
 search = new Ext.form.ComboBox({
				store: ds,
				id:'searchBox',
				displayField:'b0101',
				typeAhead: true,
				queryDelay : 900,
				loadingText: '正在加载...',
				width: 200,
				listWidth :400,
				minChars : 1,
				emptyText  : '输入要查询的机构名称',
				autoSelect:false, 
				mode: 'remote',
				autoLoadStore: true,
				pageSize:10,
				hideTrigger:true,
				queryParam: 'b0101',
				triggerAction : 'all', 
				tpl: resultTpl,
				itemSelector: 'div.search-item',
 				onSelect: function(record){ // override default onSelect to do redirect
 					 search.setValue(record.data.b0101);
 					 
 					 search.collapse();
					 Ext.apply(tree,{ rootVisible: true});
					 root1 = new Ext.tree.AsyncTreeNode({
						 	hidden : false,
							text : record.data.b0101,
							draggable : false,
							id : record.data.b0111,//默认的node值：?node=-100
							href : "javascript:radow.doEvent('querybyid','"+record.data.b0111+"')"
						});
						tree.setRootNode(root1);
						tree.expandPath(root1.getPath(),null,function(){});
						radow.doEvent('querybyid',record.data.b0111);

				}, 
		        listeners : { 

		            'beforequery':function(e){  

		            	
		                var combo = e.combo; 
//		                combo.collapse();
		                ds.load({params:{b0101:e.query.replace(/[&\|\\\*^%$#@\-]/g,""),start:0,limit:10}});//{params:{b0101:e.query}}
		                combo.expand();
// 					一次性加载爆内存，改为后台模糊查询
/*		                 combo.collapse();//
		                
			                combo.onLoad();
			                if(!e.forceAll){   
			                	combo.store.clearFilter();
			                    var input = e.query;    
			                    // 检索的正则  
			                    var regExp = new RegExp(".*" + input + ".*");  
			                    // 执行检索  
			                    combo.store.filterBy(function(record,id){    
			                        // 得到每个record的项目名称值  
			                        var text = record.get(combo.displayField);    
			                        return regExp.test(text);   
			                    });  
			                    
			                    combo.expand();    
			                    return false;  
			                }  
			                if(!combo.getValue()) {
			                    //如果文本框没值，清除过滤器
			                    combo.store.clearFilter();
			                }*/
		               

		            }
		        } 
			});
			


			
}); 