<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="com.insigma.siis.local.pagemodel.fxyp.DongYiPreviewPageModel"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%

	DongYiPreviewPageModel mpp=new DongYiPreviewPageModel();
	try{
		String publishid=request.getParameter("publishid");
		if(publishid==null||"".equals(publishid)){
		}else{
		List<HashMap<String, Object>> list_publish=mpp.queryPublish(publishid);
		String publishname="";
		String type="";
		if(list_publish!=null&&list_publish.size()>0) {
			publishname=list_publish.get(0).get("mntp04").toString();
		}
	
%>
<style>
table{
	font-family: 'FangSong_GB2312';
	font-size:21px;
	line-height:1.8em;
}
.class1{
	font-size:29px;
	font-family:'����С���μ���';
}
.class2{
	font-family: 'KaiTi_GB2312';
	font-size:21px;
}
.class3{
	font-family: ����;
	font-size:21px;
}
.class4{
	font-family: 'FangSong_GB2312';
	font-size:21px;
}
.class5{
	font-family: 'KaiTi_GB2312';
	font-size:21px;
	font-weight:bold;
}
.class6{
	font-family: 'FangSong_GB2312';
	font-size:21px;
	font-weight:bold;
}
.class7{
	font-family: 'KaiTi_GB2312';
	font-size:12pt;
	line-height:1.2em;
	text-align:center;
	text-align:justify;
	text-justify:distribute-all-lines;
	text-align-last:justify
}
.class8{
	font-family: 'KaiTi_GB2312';
	font-size:14pt;
	line-height:1.5em;
}
.rmfont{
	font-family: 'Times New Roman';
}
</style>
<script type="text/javascript" src="js/html2canvas.js"></script>
<script type="text/javascript" src="js/jsPdf.debug.js"></script>
<script type="text/javascript">
/*function pdfdown(){
	var pdfbtn = document.getElementById("pdfbtn");
	pdfbtn.style.display = "none";
	pdfdown2();
}

var copyDom = document.body;//Ҫ�����dom
var width = copyDom[0].offsetWidth; //dom��
var height = copyDom[0].offsetHeight; //dom��
var scale = 4; //�Ŵ���
var opts = {
  dpi: window.devicePixelRatio * 2,
  scale: scale,
  width: width,
  height: height,
  useCORS: true // �����ͼ����������ͼƬ,���ܻ��п�������,�����������,����ļ���������
};

function pdfdown2() {
	html2canvas(copyDom[0], opts).then((canvas) => {
	  debugger
	  var contentWidth = canvas.width;
	  var contentHeight = canvas.height;
	  var pdfWidth = (contentWidth + 10) / 2 * 0.75;
	  var pdfHeight = (contentHeight + 200) / 2 * 0.75; // 500Ϊ�ײ�����
	  var imgWidth = pdfWidth;
	  var imgHeight = (contentHeight / 2 * 0.75); //����ͼƬ���ﲻ��Ҫ���׵ľ���
	  var pageData = canvas.toDataURL('image/jpeg', 1.0);
	  var pdf = new jsPDF('', 'pt', [pdfWidth, pdfHeight]);
	  pdf.addImage(pageData, 'jpeg', 0, 0, imgWidth, imgHeight);
	  pdf.save('content.pdf');
	  });
}*/
/*function pdfdown2() {

    html2canvas(document.body, {
    	scale: '4',
        onrendered:function(canvas) {

            var contentWidth = canvas.width;
            var contentHeight = canvas.height;
            //һҳpdf��ʾhtmlҳ�����ɵ�canvas�߶�;
            var pageHeight = contentWidth / 592.28 * 841.89;
            //δ����pdf��htmlҳ��߶�
            var leftHeight = contentHeight;
            //pdfҳ��ƫ��
            var position = 0;
            //a4ֽ�ĳߴ�[595.28,841.89]��htmlҳ�����ɵ�canvas��pdf��ͼƬ�Ŀ��
            var imgWidth = 595.28;
            var imgHeight = 592.28/contentWidth * contentHeight;

            var pageData = canvas.toDataURL('image/jpeg', 1.0);

            var pdf = new jsPDF('', 'pt', 'a4');

            //�������߶���Ҫ���֣�һ����htmlҳ���ʵ�ʸ߶ȣ�������pdf��ҳ��߶�(841.89)
            //������δ����pdfһҳ��ʾ�ķ�Χ�������ҳ
            if (leftHeight < pageHeight) {
                pdf.addImage(pageData, 'JPEG', 0, 0, imgWidth, imgHeight );
            } else {
                while(leftHeight > 0) {
                    pdf.addImage(pageData, 'JPEG', 0, position, imgWidth, imgHeight)
                    leftHeight -= pageHeight;
                    position -= 841.89;
                    //������ӿհ�ҳ
                    if(leftHeight > 0) {
                        pdf.addPage();
                    }
                }
            }
            pdf.save('content.pdf');
        }
    })
}*/

</script>
<div style="overflow:auto;height:700; ">
<table style="width: 100%">
<tr>
<td  width="2%">
</td>
<td  width="96%">

<table style="width: 100%">
	<tr height="20px" >
		<td width="100%"></td>
	</tr>
	<tr>
		<td>
			<table style="width: 100%;">
				<%--<tr>
					 <td colspan="3" align="right">
						<odin:button text="����PDF" property="pdfbtn" handler="pdfdown"></odin:button>
					</td>
				</tr> --%>
				<tr>
					<td style="border: 1px solid black;" width="15%"  align="center">
						<table>
							<tr>
								<td class="class7" align="left" width="10%">
									&nbsp;&nbsp;��
								</td>
								<td width="80%"></td>
								<td class="class7" align="right" width="10%">
									��&nbsp;&nbsp;
								</td>
							</tr>
							
						</table>
					</td>
					<td width="70%">
					</td>
					<td  width="15%"  align="center">
						<table>
							<tr>
								<td class="class8">
									�������
								</td>
							</tr>
							<tr>
								<td class="class8">
									ע�Ᵽ��
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr height="40px">
		<td></td>
	</tr>
	<tr>
		<td align="center" class="class1">
			<%=mpp.repNum(publishname) %>
		</td>
	</tr>
	<tr height="40px">
		<td></td>
	</tr>
	<%
	List<HashMap<String, Object>> list1=mpp.queryTitle(publishid, "-1");
	if(list1!=null&&list1.size()>0){
		for(int i=1;i<=list1.size();i++){
			HashMap<String, Object> map1=list1.get(i-1);
	%>	
	<tr>
		<td class="class3"> 
			&nbsp;&nbsp;&nbsp;&nbsp;<%= mpp.queryNum(i)%>����λ��<%=mpp.repNum(map1.get("a0192a"))%>
		</td>
	</tr>
	
	<%
			List<HashMap<String, Object>> list2=mpp.queryDYPenson(publishid, map1.get("zwqc00").toString());
			if(list2!=null&&list2.size()>0){
				if(list2.size()>1){
				%>
				<tr>
					<td>
						<table>
				<%
							for(int d1=1;d1<=list2.size();d1++){
								HashMap<String, Object> map2=list2.get(d1-1);
				%>
						<tr>
							<td>
								&nbsp;&nbsp;&nbsp;&nbsp;<span class="class3">������Ա<%=mpp.repNum(d1+"")+"��"+map2.get("a0101")%>��</span><%=mpp.repNum(map2.get("a0102"))%>
							</td>
						</tr>
							
				<%
							}
				%>
						</table>
					</td>
				</tr>
				<%
				}else{
				%>
				<tr>
					<td>
						<table>
				<%
								HashMap<String, Object> map2=list2.get(0);
				%>
						<tr>
							<td>
								&nbsp;&nbsp;&nbsp;&nbsp;<span class="class3">������Ա��<%=map2.get("a0101")%>��</span><%=mpp.repNum(map2.get("a0102"))%>
							</td>
						</tr>
						
						</table>
					</td>
				</tr>
				<%
				}
			}
		}
	}
	%>
	<tr height="40px">
		<td></td>
	</tr>
</table>
</td>
<td width="2%">
</td>
</tr>
</table>
<% 		}
	}catch(Exception e){
		e.printStackTrace();
	}
%>
</div>
<odin:hidden property="publishid"/>
<script type="text/javascript">

</script>
