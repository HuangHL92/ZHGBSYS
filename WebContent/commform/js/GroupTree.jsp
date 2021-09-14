<%@page contentType="text/javascript; charset=GBK"language="java"%>function CommonTree(){var bShowTreeImage=true;this.init=function(treeName,selectNodeId){var xmlData=document.all(treeName).documentElement;var divTree=document.all("div_tree_"+treeName);divTree.onNodeSelecting=function(){showContent(event.treenode);}
divTree.onNodeToggleClick=function(){ToggleNode(divTree,xmlData,event.treenode);}
divTree.onNodeLabelDblClick=function(){ToggleNode(divTree,xmlData,event.treenode);}
loadSubMenu(divTree,xmlData,divTree.getRootNode());var oNode=divTree.findNodeByID("01");if(selectNodeId==null){if(oNode!=null){ToggleNode(divTree,xmlData,oNode);}}else if(selectNodeId!=null){var totalLevel=selectNodeId.length/2;for(i=1;i<=totalLevel;i++){var parentId=selectNodeId.substring(0,i*2);oNode=divTree.findNodeByID(parentId);if(oNode!=null){ToggleNode(divTree,xmlData,oNode);if(oNode.expanded==false){divTree.toggleNode(oNode,true);}}}}}
function ToggleNode(divTree,xmlData,oParentNode){if(oParentNode.isLeaf){return;}
if(oParentNode.childNodes.length>0){return;}
var treelist=xmlData.getElementsByTagName("Tree");var toggleelement;var xmlElement;for(i=0;i<treelist.length;i++){toggleelement=treelist.item(i);if(toggleelement.selectSingleNode("@id").text==oParentNode.ID){xmlElement=toggleelement;}}
var oXMLNodes=xmlElement.childNodes;var strMenuID,strMenuLabel,strMenuTitle,strLinkName;for(var i=0;i<oXMLNodes.length;i++){strMenuID=oXMLNodes[i].selectSingleNode("@id").text;strMenuLabel=oXMLNodes[i].selectSingleNode("@label").text;strMenuTitle=oXMLNodes[i].selectSingleNode("@title").text;if(oXMLNodes[i].selectSingleNode("@link")!=null)
strLinkName=oXMLNodes[i].selectSingleNode("@link").text;else
strLinkName=null;var oTreeNodeInitInfo=divTree.newNodeInitInfo(strMenuID,strMenuLabel,strMenuTitle,null,strLinkName);if(oXMLNodes[i].nodeName=="TreeNode"){oTreeNodeInitInfo.isLeaf=true;if(bShowTreeImage){oTreeNodeInitInfo.imgSelect="<%=request.getContextPath()%>/images/tree/tree_leaf_select.gif";oTreeNodeInitInfo.imgUnselect="<%=request.getContextPath()%>/images/tree/tree_leaf_unselect.gif";}}else{if(bShowTreeImage){oTreeNodeInitInfo.imgSelect="<%=request.getContextPath()%>/images/tree/tree_branch_select.gif";oTreeNodeInitInfo.imgUnselect="<%=request.getContextPath()%>/images/tree/tree_branch_unselect.gif";}}
var oTreeNode=divTree.createNode(oTreeNodeInitInfo);if(oTreeNodeInitInfo.data==null)
oTreeNode.htmlContainer.all.tags("SPAN")[1].style.borderBottom="none";divTree.insertNode(oParentNode,oTreeNode);if(oXMLNodes[i].nodeName=="Tree"){oTreeNode.htmlContainer.all.tags("SPAN")[1].style.borderBottom="none";}}}
function getSubMenuTitles(treeName,id){var SubTitles=new Array();var divTree=document.all("div_tree_"+treeName);var oNode;if(id==""){oNode=divTree.getRootNode();}else{oNode=divTree.findNodeByID(id);}
if(oNode==null)
{return SubTitles;}
var oNodes=oNode.childNodes;for(var i=0;i<oNodes.length;i++){SubTitles[i]=oNodes[i].label;}
return SubTitles;}
function getBrotherMenuTitles(treeName,id){var Titles=new Array();var divTree=document.all("div_tree_"+treeName);var oNode=divTree.findNodeByID(id);var parentNo=oNode.parentNode;if(parentNo!=null){Titles=getSubMenuTitles(treeName,parentNo.ID);}
return Titles;}
function loadSubMenu(divTree,xmlData,oParentNode){var oXMLNodes=xmlData.childNodes;if(oParentNode.childNodes.length>0){return;}
var strMenuID,strMenuLabel,strMenuTitle,strLinkName;for(var i=0;i<oXMLNodes.length;i++){strMenuID=oXMLNodes[i].selectSingleNode("@id").text;strMenuLabel=oXMLNodes[i].selectSingleNode("@label").text;strMenuTitle=oXMLNodes[i].selectSingleNode("@title").text;if(oXMLNodes[i].selectSingleNode("@link")!=null)
strLinkName=oXMLNodes[i].selectSingleNode("@link").text;else
strLinkName=null;var oTreeNodeInitInfo=divTree.newNodeInitInfo(strMenuID,strMenuLabel,strMenuTitle,null,strLinkName);if(oXMLNodes[i].nodeName=="TreeNode"){oTreeNodeInitInfo.isLeaf=true;if(bShowTreeImage){oTreeNodeInitInfo.imgSelect="<%=request.getContextPath()%>/images/tree/tree_leaf_select.gif";oTreeNodeInitInfo.imgUnselect="<%=request.getContextPath()%>/images/tree/tree_leaf_unselect.gif";}}else{if(bShowTreeImage){oTreeNodeInitInfo.imgSelect="<%=request.getContextPath()%>/images/tree/tree_branch_select.gif";oTreeNodeInitInfo.imgUnselect="<%=request.getContextPath()%>/images/tree/tree_branch_unselect.gif";}}
var oTreeNode=divTree.createNode(oTreeNodeInitInfo);if(oTreeNodeInitInfo.data==null)
oTreeNode.htmlContainer.all.tags("SPAN")[1].style.borderBottom="none";divTree.insertNode(oParentNode,oTreeNode);if(oXMLNodes[i].nodeName=="Tree"){oTreeNode.htmlContainer.all.tags("SPAN")[1].style.borderBottom="none";}}}
function showContent(oTreeNode){var form=parent.displayFrame.document.forms[0];var hasChildren="true";if(oTreeNode.isLeaf==true)
hasChildren="false"
form.action="<%=request.getContextPath()%>/sysmanager/groupAction.do?method=findByKey&groupid="
+oTreeNode.title+"&treeid="+oTreeNode.ID+"&hasChildren="+hasChildren;form.submit();}}
function selectDefaultNode(parentNodeId){new CommonTree().init('groupTree',parentNodeId);}
function getChildNodesLength(){var divTree=document.all("div_tree_groupTree");var parentNodeId="01";var args=getChildNodesLength.arguments;if(args.length>0){parentNodeId=args[0];}
var oNode=divTree.findNodeByID(parentNodeId);if(oNode!=null&&oNode.childNodes.length>0){return oNode.childNodes.length;}
return 0;}