<%@ page contentType="text/css; charset=GBK" language="java" %>
.TopGroup
{
  z-index:99;
  position:relative;
}

.DefaultTab
{
  color:black;
  background-image:url(<%=request.getContextPath()%>/images/tab_bg.gif);
  font-family:MS Sans Serif, Verdana;
  font-size:10px;
  cursor:default;
}

.DefaultTabHover
{
  color:black;
  background-image: url(<%=request.getContextPath()%>/images/hover_tab_bg.gif);
  font-family:MS Sans Serif, Verdana;
  font-size:10px;
  cursor:default;
}

.SelectedTab
{
  color:black;
  background-image: url(<%=request.getContextPath()%>/images/selected_tab_bg.gif);
  font-family:MS Sans Serif, Verdana;
  font-size:10px;
  cursor:default;
}

.MultiPage
{
  background-color:White;
  border: 1px solid #919B9C;
  width:487px;
  height:250px;
  position:relative;
  top:-3px;
  left:1px;
  z-index:98;
}