<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin" %>

<script src="<%=request.getContextPath()%>/commform/basejs/json2.js"></script>

<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script>
    var contexts1 = " <%=request.getContextPath()%>";
</script>
<style>
    .containslast {
        height: 50px;
        background-color: #cedff5;
        width: 100%;
    }
    #first{

    }
    #serond{

    }
    .bluebutton {
        font-size: 11.5px;
        font-weight: bolder;
        color: white;
        width: 58px;
        height: 25px;
        /* line-height: 20px; */
        border-top: 0;
        border-left: 0;
        background-color: dodgerblue;
    }
    body { 
        overflow-y: hidden;
        overflow-x: hidden; 
    }
</style>
<table style="width: 100%;" border="0" cellspacing='0' id="table_id">
    <tr>
        <td valign="top" style="position: relative;" id="first">
            <table>
                <tr><odin:select2 property="GbmcType" label="�ɲ�����" value="gbmc1" data="['gbmc1','�ɲ�����']"></odin:select2></tr>
                <%-- <tr><odin:select2 property="GbmcType" label="�ɲ�����" value="gbmc1" data="['gbmc1','�ɲ�����'],['gbmc2','��������']"></odin:select2></tr> --%>
                <tr><odin:textEdit property="TYPA2"   label="��ѯ��Ա"  emptyText="��������" ></odin:textEdit>
                   <td rowspan="2"><button class="bluebutton" onclick="selectPerson()">����</button></td></tr>
            </table>

            <odin:tab id="tab" width="300"  tabchange="grantTabChange">
                <odin:tabModel>
                	<odin:tabItem title="�ճ�����" id="tab1"></odin:tabItem>
                    <odin:tabItem title="������" id="tab2" isLast="true"></odin:tabItem>
                </odin:tabModel>
                <odin:tabCont itemIndex="tab1">
                    <div id="tree" style="border: 2px solid #c3daf9;overflow: auto;"></div>
                    <div class="containslast" align="center">
                        <!-- <div style="float: left">
                            <input type="checkbox" id="continueCheckbox" style="display:none" onclick="continueChoose()">
                            <input type="checkbox" id="existsCheckbox" checked="checked" onclick="existsChoose()"><font
                                style="font-size: 13px">�����¼�</font>
                        </div> -->
                        <div style="float: right;    padding-right: 2%;">
                            <input type="button" onclick="rolster()" class="bluebutton" style="float: left;width: 80px"
                                                         value="���ɸɲ�����"/>
                        </div>
                            <%--<odin:button text="���ɸɲ�����" handler="displayRolster"></odin:button>--%>
                    </div>
                </odin:tabCont>
                <odin:tabCont itemIndex="tab2">

                    <div id="tree-div" style="border: 2px solid #c3daf9"></div>
                    <div class="containslast" align="center">
                        <div style="float: left">
                            <input type="checkbox" id="continueCheckbox" style="display:none" onclick="continueChoose()">
                            <input type="checkbox" id="existsCheckbox" checked="checked" onclick="existsChoose()"><font
                                style="font-size: 13px">�����¼�</font>
                        </div>
                        <div style="float: right;    padding-right: 2%;">
                            <input type="button" onclick="displayRolster()" class="bluebutton" style="float: left;width: 80px"
                                                         value="���ɸɲ�����"/>
                        </div>
                            <%--<odin:button text="���ɸɲ�����" handler="displayRolster"></odin:button>--%>
                    </div>
                </odin:tabCont>

            </odin:tab>
        </td>
        <td valign="top" class='bigDiv'  width="100%;" id="serond">
            <div id="girdDiv" style="width: 100%;height: 100%;margin:0px 0px 0px 0px;">
                <iframe width="100%" height="100%" id="openrunit" src="" frameborder=0 scrolling=auto
                        style="float: right; margin-left: 0px; clear: both; margin-right: 0px"></iframe>
            </div>
        </td>
    </tr>
</table> 
<script type="text/javascript">
    document.onkeydown = function () {

        if (event.keyCode == 13) {
            if (document.activeElement.type == "textarea") {
                toDOQuery();
                return false;
            }
        } else if (event.keyCode == 27) {	//����ESC
            return false;
        }
    }
    var continueCount = 0;//����ѡ�����
    var changeNode = {};//ÿ�β����ļ�¼
    var childNodes = "";
    var continueOne;//����ѡ�����һ������
    var top = "";//
    var tag = 0;
    var nocheck = 1;

    var nodeSelectedSet = {};

    function existsChoose() {
        var existsCheckbox = document.getElementById('existsCheckbox');
        var continueCheckbox = document.getElementById('continueCheckbox');
        if (existsCheckbox.checked == false) {
            existsCheckbox.checked = false;
        } else {
            existsCheckbox.checked = true;
            continueCheckbox.checked = false;
        }
    }

    function continueChoose() {
        var existsCheckbox = document.getElementById('existsCheckbox');
        var continueCheckbox = document.getElementById('continueCheckbox');
        if (continueCheckbox.checked == false) {
            continueCheckbox.checked = false;
        } else {
            continueCount = 0;
            tag = 0;
            continueCheckbox.checked = true;
            existsCheckbox.checked = false;
        }
    }

    Ext.onReady(function() {
    	//var man = document.getElementById('manager').value;
        var Tree = Ext.tree;
        
    	var tree = new Tree.TreePanel( {
    	      id:'group',
              el : 'tree',//Ŀ��div����
              split:false,
              /* width: 164, */
              minSize: 164,
              maxSize: 164,
              rootVisible: false,
              autoScroll : true,
              animate : true,
              border:false,
              enableDD : true,
              containerScroll : true ,
              loader : new Tree.TreeLoader( {
                    dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.gbmc.YNTPGL&eventNames=orgTreeJsonData'
                        //,
                    //baseParams : {userid: ''}
                    //baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI }
              }),
              listeners : {
      			'click' : function(node) {
      				var id  = node.id;
      				document.getElementById('gbmcid').value = id;
      				//radow.doEvent("chooseUser",userid);
      				/* var loader = Ext.getCmp("treegrid").getLoader();
      				Ext.apply(loader.baseParams,{userid: node.id});
      				Ext.getCmp("treegrid").root.reload();
      				Ext.getCmp("treegrid").expandAll(); */
      			}
      		  }
          });
       
        var root = new Tree.AsyncTreeNode( {
              text :  '',
              draggable : false,
              id : '-1'/* ,//Ĭ�ϵ�nodeֵ��?node=-100
              href:"javascript:radow.doEvent('querybyid','"+document.getElementById('ereaid').value+"')" */
        });
        tree.setRootNode(root);
        tree.render();
        root.expand(false,true, callback2);//Ĭ��չ��
        //tree.expandAll();
    });

    var callback2 = function (node){//��չ���¼�
    	if(node.hasChildNodes()) {
    		node.eachChild(function(child){
    			child.expand();
    		})
    	}
    }

    Ext.onReady(function () {
        var viewSize = Ext.getBody().getViewSize();
 		var LEFT_HEIGHT = viewSize.height - 145;
 		$("#tree-div").height(LEFT_HEIGHT ); 
 		$("#tree").height(LEFT_HEIGHT ); 
 		$("#openrunit").height(viewSize.height);
        var Tree = Ext.tree;
        var tree = new Tree.TreePanel(
            {
                id: 'group',
                el: 'tree-div',//Ŀ��div����
                split: false,
                width: 300,
                rootVisible: false,
                autoScroll: true,
                animate: true,
                border: false,
                height: LEFT_HEIGHT,
                enableDD: false,
                containerScroll: true,
                checkModel: 'multiple',
                loader: new Tree.TreeLoader(
                    {
                        baseAttrs: {
                            uiProvider: Ext.tree.TreeCheckNodeUI
                        },
                        //dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.SysOrgTree&eventNames=orgTreeJsonData&tag=1',
                        dataUrl: 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataPeople&unsort=1',
                        baseParams: {sign: 'look'}
                        //dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.SysOrg&eventNames=orgTreeJsonData&tag=1'
                    }),
                listeners: {
                    'expandnode': function (node) {
                        //��ȡչ���ڵ����Ϣ
                        if (node.attributes.tag == "1") {
                            node.attributes.tag = "2";
                            for (var i = 0; i < node.childNodes.length; i++) {
                                node.childNodes[i].ui.checkbox.checked = true;
                                node.childNodes[i].attributes.tag = "1";
                            }
                        } else if (node.attributes.tag == "0") {
                            node.attributes.tag = "2";
                            for (var i = 0; i < node.childNodes.length; i++) {
                                node.childNodes[i].ui.checkbox.checked = false;
                                node.childNodes[i].attributes.tag = "0";
                            }
                        }
                    }
                }
            });
        var root = new Tree.AsyncTreeNode({
            checked: false,
            text: '',
            iconCls: '',
            draggable: false,
            expanded: true,
            id: '-1'//,//Ĭ�ϵ�nodeֵ��?node=-100
            //href:"javascript:radow.doEvent('querybyid','"+document.getElementById('ereaid').value+"')"
        });
        tree.setRootNode(root);
        tree.on(
            "check",
            function (node, checked) {
                //������ѡ��ťΪѡ�У������node.idΪ��ѡ�в����� �����node.id Ϊѡ������Ϊ��һnode.id ���� ����ڶ���node.id Ϊѡ��  ѡ�����пɼ�node.id
                //node.attributes.tag    1��������ѡ�¼� 0������ȡ���¼� 2��ʲô������
                var existsCheckbox = document
                    .getElementById('existsCheckbox');
                var continueCheckbox = document
                    .getElementById('continueCheckbox');
                if (checked && existsCheckbox.checked
                    && !continueCheckbox.checked) {
                    node.attributes.tag = "1";
                    loop(node)
                } else if (!checked
                    && existsCheckbox.checked) {
                    node.attributes.tag = "0";
                }
                if (continueCheckbox.checked) {
                    if (checked) {
                        if (continueCount == 1) {
                            continueCheckbox.checked = false;
                            continueCheck(continueOne, node);
                        }
                        if (continueCount == 0) {

                            continueOne = node;
                            continueCount = 1;
                        }
                    }
                }
                //��������¼���ѡ��,�˴��¼���ѡ��
                if (existsCheckbox.checked) {
                    loop(node);
                }
                if (existsCheckbox.checked) {
                    if (checked) {
                        //changeNode += node.id+":"+checked+":1,";
                        changeNode[node.id] = changeNode[node.id] ? (changeNode[node.id]
                            + checked + ":1,")
                            : (checked + ":1,");
                    } else {
                        //changeNode += node.id+":"+checked+":2,";
                        changeNode[node.id] = changeNode[node.id] ? (changeNode[node.id]
                            + checked + ":2,")
                            : (checked + ":2,");
                    }
                } else {
                    //changeNode += node.id+":"+checked+":0,";
                    changeNode[node.id] = changeNode[node.id] ? (changeNode[node.id]
                        + checked + ":0,")
                        : (checked + ":0,");
                }
                //alert(changeNode);
            }); //ע��"check"�¼�
        tree.render();
        var callback = function (node){
        	if(node.hasChildNodes()) {
        		node.eachChild(function(child){
        			child.expand();
        		})
        	}
        }
        root.expand(false,true, callback);  
        
/***
        var LEFT_HEIGHT = viewSize.height - 145;
        //$("#tab").height(viewSize.height);
        $("#tree-div ").height(LEFT_HEIGHT );
        $("#tree-div  div div").height(LEFT_HEIGHT );
        $("#serond").height($("#first").height()-50);
    
        //Ext.get('GbmcType').setValue( 'gbmc1'  ); 
 ***/       

        //delayDisplayRoster(""); 
    });

    
    function findNode(node, one, two) {
        if (tag == 1) {
            return one;
        }
        if (tag == 2) {
            return two;
        }
        if (node.id == one.id) {
            tag = 1;
            return one;
        }
        if (node.id == two.id) {
            tag = 2;
            return two;
        }
        if (node.childNodes.length > 0) {
            for (var i = 0; i < node.childNodes.length; i++) {
                if (tag != 0) {
                    return;
                }
                findNode(node.childNodes[i], one, two);
            }
        }
    }

    function continueCheck(one, two) {
        //�ж�ѡ��ڶ��εķ���������ϣ�ִ�г��Ϸ�����������ִ��
        //�ж��Ƿ����ϼ��ڵ㣬�ϼ��ڵ������ң����û���ϼ��ڵ㷵��false
        upOrDown(one, two);
        if (tag == 1) {
            if (continueCheckDownLoop(one, two.id) == 1) {
                two.attributes.tag = "2";
                return 1;
            }
        } else {
            if (continueCheckDownLoop(two, one.id) == 1) {
                one.attributes.tag = "2";
                return 1;
            }
        }
    }

    function continueCheckUpLoop(one, two) {
        one.attributes.tag = "1";
        if (one.id == two) {
            return 1;
        } else {
            if (!one.ui.checkbox.checked && nocheck == 1) {
                one.ui.checkbox.checked = true;
                //changeNode += one.id+":"+one.ui.checkbox.checked+":1,";
                changeNode[one.id] = changeNode[one.id] ? (changeNode[one.id]
                    + one.ui.checkbox.checked + ":1,")
                    : (one.ui.checkbox.checked + ":1,");
            }
        }
        try {
            if (one.parentNode.parentNode.childNodes.length > 0) {
                for (var i = 0; i < one.parentNode.parentNode.childNodes.length; i++) {
                    if (one.parentNode.parentNode.childNodes[i].id == one.parentNode.id) {
                        if (i + 1 < one.parentNode.parentNode.childNodes.length) {
                            //���¼�����Ҳ�����ƽ��
                            if (continueCheckDownLoop(
                                one.parentNode.parentNode.childNodes[i + 1],
                                two) == 1) {
                                return 1;
                            } else {
                                if (continueCheckSameLoop(
                                    one.parentNode.childNodes[i + 1], two) == 1) {
                                    return 1;
                                }
                            }
                        } else {
                            if (continueCheckUpLoop(
                                one.parentNode.parentNode.childNodes[0],
                                two) == 1) {
                                return 1;
                            }
                        }
                    }
                }
            }
        } catch (e) {

        }
    }

    function continueCheckDownLoop(one, two) {
        one.attributes.tag = "1";
        if (one.id == two) {
            return 1;
        } else {
            if (!one.ui.checkbox.checked) {
                one.ui.checkbox.checked = true;
                //changeNode += one.id+":"+one.ui.checkbox.checked+":1,";
                changeNode[one.id] = changeNode[one.id] ? (changeNode[one.id]
                    + one.ui.checkbox.checked + ":1,")
                    : (one.ui.checkbox.checked + ":1,");
            }
        }
        var node = one;
        //һֱ������
        if (node.childNodes.length > 0) {
            for (var i = 0; i < node.childNodes.length; i++) {
                if (continueCheckDownLoop(node.childNodes[i], two) == 1) {
                    return 1;
                }
            }
        } else {
            //ƽ������
            nocheck = 0;
            if (continueCheckSameLoop(one, two) == 1) {
                return 1;
            }
        }
    }

    function continueCheckSameLoop(one, two) {
        one.attributes.tag = "1";
        if (one.id == two) {
            return 1;
        } else {
            if (!one.ui.checkbox.checked && nocheck == 1) {
                one.ui.checkbox.checked = true;
                //changeNode += one.id+":"+one.ui.checkbox.checked+":1,";
                changeNode[one.id] = changeNode[one.id] ? (changeNode[one.id]
                    + one.ui.checkbox.checked + ":1,")
                    : (one.ui.checkbox.checked + ":1,");
            }
        }
        var node = one;
        if (node.parentNode.childNodes.length > 0) {
            for (var i = 0; i < node.parentNode.childNodes.length; i++) {
                if (node.parentNode.childNodes[i].id == one.id) {
                    nocheck = 1;
                    //���û��ƽ������һ��node����ô�������ϼ�
                    if (i + 1 < node.parentNode.childNodes.length) {
                        //���¼�����Ҳ�����ƽ��
                        if (continueCheckDownLoop(
                            node.parentNode.childNodes[i + 1], two) == 1) {
                            return 1;
                        } else {
                            if (continueCheckSameLoop(
                                node.parentNode.childNodes[i + 1], two) == 1) {
                                return 1;
                            }
                        }
                    } else {
                        //���ϼ�
                        nocheck = 0;
                        if (continueCheckUpLoop(one, two)) {
                            return 1;
                        }
                    }
                }
            }
        }
    }

    function loop(node) {
        if (node.ui.checkbox.checked == true) {
            //node.expand();
            if (node.childNodes.length > 0) {
                for (var i = 0; i < node.childNodes.length; i++) {
                    node.childNodes[i].attributes.tag = "1";
                    node.childNodes[i].ui.checkbox.checked = true;
                    //changeNode += node.childNodes[i].id+":"+node.childNodes[i].ui.checkbox.checked+":1,";
                    changeNode[node.childNodes[i].id] = changeNode[node.childNodes[i].id] ? (changeNode[node.childNodes[i].id]
                        + node.childNodes[i].ui.checkbox.checked + ":1,")
                        : (node.childNodes[i].ui.checkbox.checked + ":1,");
                    loop(node.childNodes[i]);
                }
            }
        } else {
            node.ui.checkbox.checked = false;
            for (var i = 0; i < node.childNodes.length; i++) {
                node.childNodes[i].attributes.tag = "0";
                node.childNodes[i].ui.checkbox.checked = false;
                changeNode[node.childNodes[i].id] = changeNode[node.childNodes[i].id] ? (changeNode[node.childNodes[i].id]
                    + node.childNodes[i].ui.checkbox.checked + ":0,")
                    : (node.childNodes[i].ui.checkbox.checked + ":0,");
                loop(node.childNodes[i]);
            }
        }
    }

    function getValue() {
        var URLParams = new Array();
        var aParams = document.location.search.substr(1).split('&');
        for (i = 0; i < aParams.length; i++) {
            var aParam = aParams[i].split('=');
            URLParams[aParam[0]] = aParam[1];
        }
        return URLParams["roleid"];
    }

    Ext.tree.TreeCheckNodeUI = function () {
        //'multiple':��ѡ; 'single':��ѡ; 'cascade':������ѡ
        this.checkModel = 'multiple';

        //only leaf can checked
        this.onlyLeafCheckable = false;

        Ext.tree.TreeCheckNodeUI.superclass.constructor.apply(this, arguments);
    };

    Ext.extend(
        Ext.tree.TreeCheckNodeUI,
        Ext.tree.TreeNodeUI,
        {

            renderElements: function (n, a, targetNode, bulkRender) {
                var tree = n.getOwnerTree();
                this.checkModel = tree.checkModel
                    || this.checkModel;
                this.onlyLeafCheckable = tree.onlyLeafCheckable || false;

                // add some indent caching, this helps performance when rendering a large tree
                this.indentMarkup = n.parentNode ? n.parentNode.ui
                    .getChildIndent() : '';

                var cb = (!this.onlyLeafCheckable || a.leaf);
                var href = a.href ? a.href : Ext.isGecko ? "" : "#";
                var buf = [
                    '<li class="x-tree-node"><div ext:tree-node-id="', n.id, '" class="x-tree-node-el x-tree-node-leaf x-unselectable ', a.cls, '" unselectable="on">',
                    '<span class="x-tree-node-indent">',
                    this.indentMarkup,
                    "</span>",
                    '<img src="', this.emptyIcon, '" class="x-tree-ec-icon x-tree-elbow" />',
                    '<img src="',
                    a.icon || this.emptyIcon,
                    '" class="x-tree-node-icon',
                    (a.icon ? " x-tree-node-inline-icon" : ""),
                    (a.iconCls ? " " + a.iconCls : ""),
                    '" unselectable="on" />',
                    cb ? ('<input class="x-tree-node-cb" type="checkbox" ' + (a.checked ? 'checked="checked" />'
                        : '/>'))
                        : '',
                    '<a hidefocus="on" class="x-tree-node-anchor" href="',
                    href,
                    '" tabIndex="1" ',
                    a.hrefTarget ? ' target="' + a.hrefTarget
                        + '"' : "",
                    '><span unselectable="on">',
                    n.text,
                    "</span></a></div>",
                    '<ul class="x-tree-node-ct" style="display:none;"></ul>',
                    "</li>"].join('');

                var nel;
                if (bulkRender !== true && n.nextSibling
                    && (nel = n.nextSibling.ui.getEl())) {
                    this.wrap = Ext.DomHelper.insertHtml(
                        "beforeBegin", nel, buf);
                } else {
                    this.wrap = Ext.DomHelper.insertHtml(
                        "beforeEnd", targetNode, buf);
                }

                this.elNode = this.wrap.childNodes[0];
                this.ctNode = this.wrap.childNodes[1];
                var cs = this.elNode.childNodes;
                this.indentNode = cs[0];
                this.ecNode = cs[1];
                this.iconNode = cs[2];
                var index = 3;
                if (cb) {
                    this.checkbox = cs[3];
                    Ext.fly(this.checkbox).on(
                        'click',
                        this.check.createDelegate(this,
                            [null]));
                    index++;
                }
                this.anchor = cs[index];
                this.textNode = cs[index].firstChild;
            },

            // private
            check: function (checked) {
                var n = this.node;
                var tree = n.getOwnerTree();
                this.checkModel = tree.checkModel
                    || this.checkModel;

                if (checked === null) {
                    checked = this.checkbox.checked;
                } else {
                    this.checkbox.checked = checked;
                }

                n.attributes.checked = checked;
                tree.fireEvent('check', n, checked);

                if (!this.onlyLeafCheckable
                    && this.checkModel == 'cascade') {
                    var parentNode = n.parentNode;
                    if (parentNode !== null) {
                        this.parentCheck(parentNode, checked);
                    }
                    if (!n.expanded && !n.childrenRendered) {
                        n.expand(false, false, this.childCheck);
                    } else {
                        this.childCheck(n);
                    }
                } else if (this.checkModel == 'single') {
                    var checkedNodes = tree.getChecked();
                    for (var i = 0; i < checkedNodes.length; i++) {
                        var node = checkedNodes[i];
                        if (node.id != n.id) {
                            node.getUI().checkbox.checked = false;
                            node.attributes.checked = false;
                            tree.fireEvent('check', node, false);
                        }
                    }
                }

            },

            // private
            childCheck: function (node) {
                var a = node.attributes;
                if (!a.leaf) {
                    var cs = node.childNodes;
                    var csui;
                    for (var i = 0; i < cs.length; i++) {
                        csui = cs[i].getUI();
                        if (csui.checkbox.checked ^ a.checked)
                            csui.check(a.checked);
                    }
                }
            },

            // private
            parentCheck: function (node, checked) {
                var checkbox = node.getUI().checkbox;
                if (typeof checkbox == 'undefined')
                    return;
                if (!(checked ^ checkbox.checked))
                    return;
                if (!checked && this.childHasChecked(node))
                    return;
                checkbox.checked = checked;
                node.attributes.checked = checked;
                node.getOwnerTree().fireEvent('check', node,
                    checked);

                var parentNode = node.parentNode;
                if (parentNode !== null) {
                    this.parentCheck(parentNode, checked);
                }
            },

            // private
            childHasChecked: function (node) {
                var childNodes = node.childNodes;
                if (childNodes || childNodes.length > 0) {
                    for (var i = 0; i < childNodes.length; i++) {
                        if (childNodes[i].getUI().checkbox.checked)
                            return true;
                    }
                }
                return false;
            },

            toggleCheck: function (value) {
                var cb = this.checkbox;
                if (cb) {
                    var checked = (value === undefined ? !cb.checked
                        : value);
                    this.check(checked);
                }
            }
        });

    function loopRoot(rootnode) {//[�ڵ�����Ƿ�����¼����Ƿ�ѡ�б���]
        for (var i = 0; i < rootnode.childNodes.length; i++) {
            var cNode = rootnode.childNodes[i];
            if (cNode.ui.checkbox.checked) {
                nodeSelectedSet[cNode.id] = [cNode, true, true];
            } else {
                loopParent(cNode);//��������һ��δ��ѡ�У��ϼ����ĳɲ������¼�
            }
            if (cNode.childNodes.length > 0) {
                loopRoot(cNode);
            } else {
                if (cNode.attributes.tag == 1 && !cNode.ui.checkbox.checked) {//û���ֶ�չ���¼������� �������¼�     ����û��ѡ��
                    if (cNode.isLeaf()) {//��Ҷ�ӽڵ�
                        //nodeSelectedSet[cNode.id]=[cNode,false,false];//[�ڵ���󣬲������¼�������û��ѡ��]
                    } else {//����Ҷ�ӽڵ�
                        nodeSelectedSet[cNode.id] = [cNode, true, false];//[�ڵ���󣬰����¼�������û��ѡ��]
                    }
                } else if (cNode.attributes.tag != 1
                    && cNode.ui.checkbox.checked) {//�������¼���û��չ���¼�������������ѡ��
                    if (cNode.isLeaf()) {//��Ҷ�ӽڵ�

                    } else {//����Ҷ�ӽڵ�
                        loopParent(cNode);
                    }
                    nodeSelectedSet[cNode.id] = [cNode, false, true];

                } else if (cNode.attributes.tag == 1
                    && cNode.ui.checkbox.checked && cNode.isLeaf()) {//Ҷ�ӽڵ�  �����¼��������������¼���
                    nodeSelectedSet[cNode.id] = [cNode, false, true];
                }
            }
        }

    }

    //�����ڵ����ò������¼���
    function loopParent(cNode) {
        if (cNode.parentNode) {
            if (nodeSelectedSet[cNode.parentNode.id]) {
                nodeSelectedSet[cNode.parentNode.id][1] = false;
            }
            loopParent(cNode.parentNode);
        }
    }

    var oldSelectIdArrayCount = 0;
    var oldSelectIdArray = new Array();
    var count = 0;//������
    function doQueryNext() {
        var nextProperty = document.getElementById('nextProperty').value;
        if (nextProperty == "") {
            return;
        }
        var tree = Ext.getCmp("group");
        var node = tree.getRootNode();
        oldSelectIdArray.length = 0;//���
        loopNext(node, nextProperty);
        oldSelectIdArray[count % oldSelectIdArray.length].select();
        count += 1;
    }

    function loopNext(node, nextProperty) {
        if (node.childNodes.length > 0) {
            for (var i = 0; i < node.childNodes.length; i++) {
                if (node.childNodes[i].text.indexOf(nextProperty) >= 0) {
                    oldSelectIdArray.push(node.childNodes[i]);
                    loopNext(node.childNodes[i], nextProperty);
                } else {
                    loopNext(node.childNodes[i], nextProperty);
                }
            }
        }
    }

    var allCheckedString = "";

    function findchildnode(node) {
        var childnodes = node.childNodes;

        var length = 0;
        if (typeof(childnodes.length) != undefined && childnodes.length) length = childnodes.length;

        for (var i = 0; i < length; i++) {  //�ӽڵ���ȡ���ӽڵ����α���
            var tNode = childnodes[i];
            allCheckedString += "," + tNode.text;
            if (typeof(tNode.length) == undefined || !tNode.length || tNode.length <= 0) {

            } else if (tNode.childNodes.length > 0) {  //�ж��ӽڵ����Ƿ�����ӽڵ㣬���˾����ж��Ƿ�leaf��̫������Ϊ��ʱ����leaf�Ľڵ�Ҳ����û���ӽڵ�
                findchildnode(tNode);    //��������ӽڵ�  �ݹ�
            }
        }
    }


    function isObjectEmpty(obj) {
        for (var key in obj) {
            if (key) {
                return false
            }
        }
        return true
    }

    function doQuery() {
        //alert( JSON.stringify(changeNode));
        var treenode = Ext.getCmp('group');
        var rootNode = treenode.getRootNode();
        loopRoot(rootNode);

        var nodeRealSelectedSet = {};//��Ҫ����Ľڵ㡣
        for (var nodeid in nodeSelectedSet) {
            var selectedNode = nodeSelectedSet[nodeid][0];//��ѡ�еĽڵ�
            //����¼�û��չ���ڵ㣬�ұ���û�б�ѡ�У����а����¼�ѡ�б�־��
            if (!nodeSelectedSet[nodeid][2]) {
                nodeRealSelectedSet[nodeid] = selectedNode.text + "(�¼�����):true:false";//�ڵ�����+ �����¼�+ �����Ƿ�ѡ��
                continue;
            }
            if (nodeSelectedSet[nodeid][2] && !nodeSelectedSet[nodeid][1]) {
                var pNode = selectedNode.parentNode;
                if (!nodeSelectedSet[pNode.id] || !nodeSelectedSet[pNode.id][1]) {//���ڵ㲻����  �� ���ڵ㲻�����¼�
                    nodeRealSelectedSet[nodeid] = selectedNode.text + ":false:true";//�ڵ�����+ �������¼�+ ������ѡ��

                }
                continue;
            }
            //�����¼��ڵ�δ��չ��
            //��������ظ��İ����¼�
            if (nodeSelectedSet[nodeid][1]) {//�����¼�
                if (selectedNode.parentNode) {//���ϼ��ڵ�
                    var pNode = selectedNode.parentNode;
                    if (!nodeSelectedSet[pNode.id] || !nodeSelectedSet[pNode.id][1]) {//�ϼ�û�б�ѡ�� �� �ϼ��ڵ㲻�����¼�
                        nodeRealSelectedSet[nodeid] = selectedNode.text + "(ȫ������):true:true";//�ڵ�����+ �����¼�+ �����Ƿ�ѡ��
                    }
                }
            } else {
                nodeRealSelectedSet[nodeid] = selectedNode.text + ":false:true";//�ڵ�����+ �������¼�+ �����Ƿ�ѡ��
            }
        }

        //alert( JSON.stringify(nodeRealSelectedSet));
        nodeSelectedSet = {};

        return nodeRealSelectedSet;
        //radow.doEvent('dogrant',JSON.stringify(nodeRealSelectedSet));

    }

	//����UUID
	function GUID() {
	  return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
	    var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
	    return v.toString(16);
	  });
	}
	
    function rolster() {
        var gbmcid = document.getElementById('gbmcid').value;
		if (isObjectEmpty(gbmcid)){
			Ext.Msg.alert('ϵͳ��ʾ:','��ѡ��һ���ɲ����ᣡ');
			return;
		}
		
        setTimeout("delayDisplayRoster('"+gbmcid+"')", 500);  
    }

    function displayRolster() {
        var o = doQuery();
		if (isObjectEmpty(o)){
			Ext.Msg.alert('ϵͳ��ʾ:','������߻�����ѡ��λ��');
			return;
		}        
		
        var treenode = Ext.getCmp('group'); 
		for (var t in o){
			if (t.indexOf("P")>=0){
				var node = treenode.getNodeById(t);
				if (node && node.parentNode){ 
					 delete o[node.parentNode.id]; 
				}
			}
		}
        var paraKey = GUID();
        var paraValue = JSON.stringify(o); 
        $("#ParaKey").val(paraKey);
        $("#ParaValue").val(paraValue);   
        radow.doEvent('setParaToSession');
		
        setTimeout("delayDisplayRoster('"+paraKey+"')", 500);  
    }
    
    function delayDisplayRoster(paraKey){
    	var gbmcType = $("#GbmcType").val();
    	if (gbmcType==""){
    		gbmcType = "gbmc1";
            $("#GbmcType_combo").val("�ɲ�����");
    	}
        var openiframe = "/radowAction.do?method=doEvent&pageModel=pages.gbmc.TPB&subWinId=qcjs&subWinIdBussessId=";
        var params = paraKey + ","+gbmcType;
        var opencontext = contexts1 + openiframe + params;

        $("#openrunit", document.body).attr("src", opencontext);
        
    }
</script>
 <odin:hidden property="gbmcid"/>
 <odin:hidden property="ParaKey"/>
 <odin:hidden property="ParaValue"/>
</html>




