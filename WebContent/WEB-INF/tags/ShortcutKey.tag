
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ attribute name="label" required="false" %>
<%@ attribute name="inputWidth" required="false" %>
<%@ attribute name="dispWidth" required="false" %>
<%@ attribute name="selectSuccess" required="false" %>
<%@ attribute name="selectFailure" required="false" %>
<%@ attribute name="required" required="false" %>

<odin:keyMap property="testAddKey">
    <odin:keyMapItem key="W" alt="true"  fn="odin.openOpLogList"></odin:keyMapItem>
	<odin:keyMapItem key="Q" alt="true"  fn="query"></odin:keyMapItem>
	<odin:keyMapItem key="C" alt="true"  ctrl="true" fn="clear"></odin:keyMapItem>
	<odin:keyMapItem key="J" alt="true"  fn="compute"></odin:keyMapItem>
	<odin:keyMapItem key="S" alt="true"  fn="save" isLast="true"></odin:keyMapItem>
</odin:keyMap>

 