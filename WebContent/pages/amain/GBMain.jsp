<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>


<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/main/css/gbmain.css">



<script src="<%=request.getContextPath()%>/pages/DataAnalysis/js/jquery1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/echarts/echartsn.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/amain/js/ctsOptions.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<script type="text/javascript">
function openMate(p){
	$h.openWin('gbmainListWin','pages.amain.GBMainList','��Ա�б�',1410,900,'','<%=request.getContextPath()%>',null,{query_type:p},true);
}
</script>
<!-- <a style="float: left;position: absolute;" href="javascript:location.reload();">���ˢ��ҳ��</a>
 -->
<div class="maindiv">
	<div class="section1">
		<div class="gbgl">
			<div class="gbgl-content">
				<div class="gb-title" >
					<img class="gb-title-img" alt="" src="<%=request.getContextPath()%>/main/gbmainimg/jx-bt.png">
					<h5>�ش�����</h5>
				</div>
				<div style="width: 100%;clear: both;"></div>
				<div>
					<div class="gbgl1-btn gbgl2-btn-select" onclick="openPicPage()"><div class="gbgl-btn-img4">����ȫ��ͼ</div></div>
					<div class="gbgl1-btn gbgl2-btn-select" onclick="openXBDJ()"><div class="gbgl-btn-img1">Ѳ������</div></div>
					<div class="gbgl1-btn gbgl2-btn-select" onclick="openQHTZ()"><div class="gbgl-btn-img2">��������</div></div>
<!-- 					<div class="gbgl-btn" onclick="openXBDJ2()"><div class="gbgl-btn-img3">��Ҫ��ʵ</div></div>
					<div class="gbgl-btn"><div class="gbgl-btn-img4">����</div></div> -->
					
					<!-- <div class="gbgl-btn"><div class="gbgl-btn-img4">��λ����</div></div>
					<div class="gbgl-btn"><div class="gbgl-btn-img5">�������</div></div>
					<div class="gbgl-btn"><div class="gbgl-btn-img6">�ϻ����</div></div> -->
				</div>
			</div>
		</div>
		<div class="zgbd">
			<div class="zgbd-content">
				<div class="gb-title" >
					<img class="gb-title-img" alt="" src="<%=request.getContextPath()%>/main/gbmainimg/jx-bt.png">
					<h5>�鹤��������</h5>
				</div>
				<div style="width: 100%;clear: both;"></div>
				<div style="width: 100%;">
					<div class="zgbd-width zgbd-search">
						<input class="zgbd-search-input" type="text" id="searchdata" name="searchdata" placeholder="��������������">
						<div onclick="openMate('zgss')" class="zgbd-search-icon"></div>
					</div>
					<%-- <div class="zgbd-width " style="height: 5px;">
						<div class="zgbd-zgrd-left">�鹤�ȵ�</div>
						<div class="zgbd-zgrd-right">
							<img alt="" src="<%=request.getContextPath()%>/main/gbmainimg/hyp.png">
							<span>��һ��</span>
						</div>
					</div> --%>
					<!-- <div class="zgbd-width ">
						<div  class="zgbd-zgrd-ul">
							<ul>
								<li><a><span style="color: rgb(255,71,94);">1  </span><span class="zgbd-zgrd-icon-r">�鹤�ȵ��鹤�ȵ��鹤�ȵ�</span></a></li>
								<li><a><span style="color: rgb(255,122,33);">2  </span><span>�鹤�ȵ��鹤�ȵ��鹤�ȵ��鹤�ȵ�</span></a></li>
								<li><a><span style="color: rgb(251,183,52);">3  </span><span>�鹤�ȵ��鹤�ȵ�</span></a></li>
								
							</ul>
						</div>
						<div  class="zgbd-zgrd-ul">
							<ul >
								<li><a><span>4  </span><span>�鹤�ȵ��鹤�ȵ��鹤�ȵ�</span></a></li>
								<li><a><span>5  </span><span class="zgbd-zgrd-icon-x">�鹤�ȵ��鹤�ȵ��鹤�ȵ��鹤�ȵ�</span></a></li>
								<li><a><span>6  </span><span>�鹤�ȵ��鹤�ȵ�</span></a></li>
							</ul>
						</div>
					</div> -->
					<div class="zgbd-width zgbd-btn">
						<button type='button' class="btn  btn-primary" onclick="openCYMD()"
						style='margin-top: 10px;font-size: 16px;padding: 7px 20px;'>��������</button>
						<button type='button' class="btn  btn-primary " onclick="openDTZB()"
						style='margin-top: 10px;margin-left: 10px;font-size: 16px;padding: 7px 20px;'>���ָ��</button>
					</div>
				</div>
				
			</div>
		</div>
		<div class="hxyw">
			<div class="hxyw-content">
				<div class="gb-title" >
					<img class="gb-title-img" alt="" src="<%=request.getContextPath()%>/main/gbmainimg/jx-bt.png">
					<h5>����ҵ��</h5>
				</div>
				<div style="width: 100%;clear: both;"></div>
				<div>
					<div class="gbgl1-btn gbgl2-btn-select"  onclick="openLTHX()"><div class="gbgl-btn-img4">���廭��</div>
						<!-- <span class="layui-nav-more2"></span>
						<dl class="layui-nav-child2 ">
							�����˵�
							<dd>
								<a  onclick="openPicPage2()">�ɲ�����</a>
							</dd>
							<dd>
								<a  >��λ����</a>
							</dd>
							<dd>
								<a  onclick="openBZHX()">���ӻ���</a>
							</dd>
							
						</dl> -->
					</div>
					<div class="gbgl1-btn gbgl-btn-select" onclick="openXBDJTab()"><div class="gbgl-btn-img1">ѡ���㽫</div></div>
						<!-- <span class="layui-nav-more"></span>
						<dl class="layui-nav-child ">
							�����˵�
							<dd>
								<a  onclick="addTab3()">�䱸��״</a>
							</dd>
							<dd>
								<a  >�������</a>
							</dd>
							<dd>
								<a  onclick="addTab5()">���ӵ���</a>
							</dd>
							
							<dd>
								<a href="">�������</a>
							</dd>
							<dd>
								<a href="">�������</a>
							</dd>
						</dl> -->
<script type="text/javascript">
function openLTHX(){
	//parent.addTab1('3e2081e47288909a017288daf4b90003','ģ�����','/radowAction.do?method=doEvent&pageModel=pages.fxyp.Mntp')
	parent.addTab1('c788313d78f225f50178f3a1720200d5','���廭��','/radowAction.do?method=doEvent&pageModel=pages.amain.LTHX')
}
function openXBDJTab(){
	//parent.addTab1('3e2081e47288909a017288daf4b90003','ģ�����','/radowAction.do?method=doEvent&pageModel=pages.fxyp.Mntp')
	parent.addTab1('c788313d78f225f50178f3a1d73800d6','ѡ���㽫','/radowAction.do?method=doEvent&pageModel=pages.amain.XBDJ')
}
/* $(function(){
	$('.gbgl2-btn-select').mouseenter(function() {
		$('.layui-nav-child2').stop(true).slideDown("fast");
		$('.layui-nav-more2').addClass('layui-nav-mored2');
	}).mouseleave(function() {
		$('.layui-nav-child2').stop(true,false).slideUp("fast");
		$('.layui-nav-more2').removeClass('layui-nav-mored2');
	});
}); */
function addTab3(){
	$h.openPageModeWin('TPQXXB','pages.fxyp.MNTPQXXB&type=1','ģ�����ǰ��Ϣ��',1300,800,'',g_contextpath);
}
function addTab5(){
	//parent.addTab1('3e2081e47288909a017288daf4b90003','ģ�����','/radowAction.do?method=doEvent&pageModel=pages.fxyp.Mntp')
	parent.addTab1('8ab66e277801431e0178014c519f0003','��λ����','/radowAction.do?method=doEvent&pageModel=pages.mntpsj.MntpSJ')
}
/* $(function(){
	$('.gbgl-btn-select').mouseenter(function() {
		$('.layui-nav-child').stop(true).slideDown("fast");
		$('.layui-nav-more').addClass('layui-nav-mored');
	}).mouseleave(function() {
		$('.layui-nav-child').stop(true,false).slideUp("fast");
		$('.layui-nav-more').removeClass('layui-nav-mored');
	});
}); */
</script>
					
					<div class="gbgl1-btn" onclick="openYJTX()" ><div class="gbgl-btn-img2" id="yjtx">Ԥ������</div></div>
<!-- 					<div class="gbgl1-btn"  ><div class="gbgl-btn-img2">�ʸ�����</div></div> -->
<!-- 					<div class="gbgl-btn gbgl1-btn-select"><div class="gbgl-btn-img3">��Ҫ����</div>
					<span class="layui-nav-more1"></span>
						<dl class="layui-nav-child1">
							�����˵�
							<dd>
								<a  onclick="openMate('sgzzmc')">���ڿ������й���ְ</a>
							</dd>
							<dd>
								<a  onclick="openMate('sgfzmc')">���ڿ������йܸ�ְ</a>
							</dd>
							<dd>
								<a  onclick="openMate('szbmzz')">��ֱ������ְ</a>
							</dd>
							<dd>
								<a  onclick="openMate('szbmsf')">��ֱ����˫��</a>
							</dd>
							<dd>
								<a  onclick="openMate('szbmfsj')">��ֱ���Ÿ���Ǹ�ְ</a>
							</dd>
							<dd>
								<a href="">�������</a>
							</dd>
							<dd>
								<a href="">�������</a>
							</dd>
						</dl> -->
<!-- <script type="text/javascript">

$(function(){
	$('.gbgl1-btn-select').mouseenter(function() {
		$('.layui-nav-child1').stop(true).slideDown("fast");
		$('.layui-nav-more1').addClass('layui-nav-mored1');
	}).mouseleave(function() {
		$('.layui-nav-child1').stop(true,false).slideUp("fast");
		$('.layui-nav-more1').removeClass('layui-nav-mored1');
	});
});
</script> -->
				<!-- </div> -->
					
					
					<!-- <div class="gbgl-btn"><div class="gbgl-btn-img4">��λ����</div></div>
					<div class="gbgl-btn"><div class="gbgl-btn-img5">�������</div></div>
					<div class="gbgl-btn"><div class="gbgl-btn-img6">�ϻ����</div></div> -->
				</div>
			</div>
		</div>
	
	
	</div>
	
		
	
	<div style="width: 100%;clear: both;"></div>

	<div class="section2">
		<div class="gbtj">
			<div class="gbtj-content">
				<div class="gb-title" style="visibility: hidden;">
					<img class="gb-title-img" alt="" src="<%=request.getContextPath()%>/main/gbmainimg/jx-bt.png">
					<h5>�ɲ���Ϣͳ��</h5>
				</div>
				<div style="width: 100%;clear: both;"></div>
				<div class="gbtj-sggb gbtj-border">
					<div class="zgbd-content">
						<div class="gb-title" >
							<img class="gb-title-img" alt="" src="<%=request.getContextPath()%>/main/gbmainimg/jx-bt.png">
							<h5>�йܸɲ�</h5>
						</div>
						<div style="width: 100%;clear: both;"></div>
						<div class="gbtj-sggb-img">
							<div style="height: 20px;"></div>
							<div class="gbtj-sggb-img-sub">
								
							</div>
							<hr style="color: white;border:2px solid white;width: 80%; margin: auto;"/>
							<div style="text-align: center;padding-top: 7px;"><span queryType="SGGB" class="gbtj-sggb-rs"></span></div>
						</div>
						<div style="position: relative;">
							<div class="gbtj-sggb-szdw-img">
								<div class="gbtj-sggb-szdw">
									<span class="gbtj-sggb-t-color">��ֱ��λ</span><br/><span queryType="SZDW" class="gbtj-sggb-f-color1 gbtj-szdw"></span><span queryType="SZDW" class="gbtj-sggb-f-color1" style="font-size: 12px;">��</span>
								</div>
								<div class="gbtj-sggb-qxs">
									<span class="gbtj-sggb-t-color">�����أ��У�</span><br/><span queryType="QXS" class="gbtj-sggb-f-color1 gbtj-qxs"></span><span queryType="QXS" class="gbtj-sggb-f-color1" style="font-size: 12px;">��</span>
								</div>
								<div class="gbtj-sggb-gqgx">
									<span class="gbtj-sggb-t-color">�����У</span><br/><span queryType="GQGX" class="gbtj-sggb-f-color1 gbtj-gqgx"></span><span queryType="GQGX" class="gbtj-sggb-f-color1" style="font-size: 12px;">��</span>
								</div>
							</div>
							<div class="gbtj-sggb-sg-img">
								<div class="gbtj-sggb-szdw">
									<span class="gbtj-sggb-t-color">�й���ְ</span><br/><span queryType="SGZZ" class="gbtj-sggb-f-color2 gbtj-sgzz"></span><span queryType="SGZZ" class="gbtj-sggb-f-color2" style="font-size: 12px;">��</span>
								</div>
								<div class="gbtj-sggb-qxs">
									<span class="gbtj-sggb-t-color">�йܸ�ְ</span><br/><span queryType="SGFZ" class="gbtj-sggb-f-color2 gbtj-sgfz"></span><span  queryType="SGFZ" class="gbtj-sggb-f-color2" style="font-size: 12px;">��</span>
								</div>
								<div class="gbtj-sggb-gqgx">
									<span class="gbtj-sggb-t-color">�й�����</span><br/><span queryType="SGQT" class="gbtj-sggb-f-color2 gbtj-sgqt"></span><span queryType="SGQT" class="gbtj-sggb-f-color2" style="font-size: 12px;">��</span>
								</div>
							</div>
							
						</div>
						<div class="chart-zzmm">
							<ul>
								<li onclick="openMate('zgdy')" style="cursor: pointer;"><table ><tr><td>�й���Ա</td></tr><tr><td class="zgdyrs" style="vertical-align: top;">1</td></tr></table></li>
								<li onclick="openMate('feizgdy')" style="cursor: pointer;"><table ><tr><td>���й���Ա</td></tr><tr><td class="fzgdyrs" style="vertical-align: top;">1</td></tr></table></li>
							</ul>
						</div>
					</div>
				</div>
				
				<div class="gbtj-nljg gbtj-border">
					<div class="zgbd-content">
						<div class="gb-title" >
							<img class="gb-title-img" alt="" src="<%=request.getContextPath()%>/main/gbmainimg/jx-bt.png">
							<h5>����ṹ</h5>
						</div>
						<div style="width: 100%;clear: both;"></div>
						<div class="chart" id="chart1-1"></div>
					</div>
				</div>
				
				
				
				<div class="gbtj-xlxw gbtj-border">
					<div class="zgbd-content">
						<div class="gb-title" >
							<img class="gb-title-img" alt="" src="<%=request.getContextPath()%>/main/gbmainimg/jx-bt.png">
							<h5>ѧ��ѧλ</h5>
						</div>
						<div style="width: 100%;clear: both;"></div>
						<div class="chart" id="chart1-2"></div>
						<div class="chart-xlxw">
							<div class="chart-qrz chart-qrz-bg">ȫ����</div>
							<div class="chart-zz chart-zz-bg">��ְ</div>
						</div>
					</div>
				</div>
				
				<div class="gbtj-xbfb gbtj-border">
					<div class="zgbd-content">
						<div class="gb-title" >
							<img class="gb-title-img" alt="" src="<%=request.getContextPath()%>/main/gbmainimg/jx-bt.png">
							<h5>�Ա�ֲ�</h5>
						</div>
						<div style="width: 100%;clear: both;"></div>
						<div class="chart" id="chart1-3"></div>
					</div>
				</div>
				
				<div style="width: 100%;clear: both;"></div>
				<%-- <div class="gbtj-zzmm gbtj-border">
					 <div class="zgbd-content">
						<div class="gb-title" >
							<img class="gb-title-img" alt="" src="<%=request.getContextPath()%>/main/gbmainimg/jx-bt.png">
							<h5>������ò</h5>
						</div>
						<div style="width: 100%;clear: both;"></div>
						<div class="chart2" id="chart1-4"></div>
						<div class="chart-zzmm">
							<ul>
								<li onclick="openMate('zgdy')" style="cursor: pointer;"><table ><tr><td class="zgdyrs" style="vertical-align: bottom;">1</td></tr><tr><td>�й���Ա</td></tr></table></li>
								<li onclick="openMate('feizgdy')" style="cursor: pointer;"><table ><tr><td class="fzgdyrs" style="vertical-align: bottom;">1</td></tr><tr><td>���й���Ա</td></tr></table></li>
							</ul>
						</div>
					</div> 
				</div> --%>
				
				<div class="gbtj-jlyj gbtj-border">
					<div class="zgbd-content">
						<div class="gb-title" >
							<img class="gb-title-img" alt="" src="<%=request.getContextPath()%>/main/gbmainimg/jx-bt.png">
							<h5>����ɲ���ְ�����ֲ�</h5>
						</div>
						<div style="width: 100%;clear: both;"></div>
						<div class="chart" id="chart1-6"></div>
					</div>
				</div>
				
				
				<div class="gbtj-zyfb gbtj-border">
					<div class="zgbd-content">
						<div class="gb-title" >
							<img class="gb-title-img" alt="" src="<%=request.getContextPath()%>/main/gbmainimg/jx-bt.png">
							<h5>רҵ�ֲ�</h5>
						</div>
						<div style="width: 100%;clear: both;"></div>
						<div class="chart" id="chart1-5"></div>
					</div>
				</div>
			</div>
		</div>
	</div>


<div style="width: 100%;clear: both;"></div>

</div>



<script>
function initData(data){
	$('.gbtj-sggb-rs').text(data['sgbb_count']);
	$('.gbtj-szdw').text(data['szdw_count']);
	$('.gbtj-qxs').text(data['qxs_count']);
	$('.gbtj-gqgx').text(data['gqgx_count']);
	$('.gbtj-sgzz').text(data['sgzz_count']);
	$('.gbtj-sgfz').text(data['sgfz_count']);
	$('.gbtj-sgqt').text(data['sgqt_count']);
	$('.gbtj-sggb-f-color1,.gbtj-sggb-f-color2,.gbtj-sggb-rs').bind('click',function(){
		var p = $(this).attr('queryType');
		openMate(p)
	});
}

function initEchartsData(chartNum,data){
	if(chartNum=="chart1-1"){
		getChart(chartNum).setOption(OPTIONS.option1(data));
	}else if(chartNum=="chart1-3"){
		getChart(chartNum).setOption(OPTIONS.option3(data));
	}else if(chartNum=="chart1-2"){
		var dataArray = 
			[
                //{value: data['qrzmybos'], name: '������ʿ',id:'qrzmybos'},
                {value: data['qrzbos'], name: '�о���',id:'qrzbos'},
                {value: data['qrzshuos'], name: '��ѧ',id:'qrzshuos'},
                {value: data['qrzxues'], name: '��ר',id:'qrzxues'},
                {value: data['qrzqt'], name: '����',id:'qrzqt'}
            ];
		var dataArray2 = 
			[
                //{value: data['zzmybos'], name: '������ʿ',id:'zzmybos'},
                {value: data['zzbos'], name: '�о���',id:'zzbos'},
                {value: data['zzshuos'], name: '��ѧ',id:'zzshuos'},
                {value: data['zzxues'], name: '��ר',id:'zzxues'},
                {value: data['zzqt'], name: '����',id:'zzqt'}
            ];
		$('.chart-zz').removeClass('chart-zz-bg');
		$('.chart-zz,.chart-qrz').click(function(){
			if($('.chart-zz').hasClass("chart-zz-bg")){
				$('.chart-zz').removeClass('chart-zz-bg');
				$('.chart-qrz').addClass('chart-qrz-bg');
				
				getChart(chartNum).setOption(OPTIONS.option2(data,dataArray));
			}else{
				$('.chart-zz').addClass('chart-zz-bg');
				$('.chart-qrz').removeClass('chart-qrz-bg');
				
				getChart(chartNum).setOption(OPTIONS.option2(data,dataArray2));
			}
		});
		
		getChart(chartNum).setOption(OPTIONS.option2(data,dataArray));
	}else if(chartNum=="chart1-4"){
		//getChart(chartNum).setOption(OPTIONS.option4(data));
		$('.zgdyrs').text(data['zgdy']+"��");
		$('.fzgdyrs').text(data['feizgdy']+"��");
	}else if(chartNum=="chart1-5"){
		getChart(chartNum).setOption(OPTIONS.option5(data));
	}else if(chartNum=="chart1-6"){
		getChart(chartNum).setOption(OPTIONS.option6(data));
	}
}
$(function() {
	
	getChart("chart1-1").on('click', function (params) {
		//alert(params.data.id);
		var d = params.data.id;
		openMate(d)
	});
	getChart("chart1-2").on('click', function (params) {
		var d = params.data.id;
		openMate(d)
	});
	getChart("chart1-3").on('click', function (params) {
		var d = params.data.id;
		openMate(d)
	});
	
	/* getChart("chart1-4").on('click', function (params) {
		var d = params.data.id;
		openMate(d)
	}); */
	
	getChart("chart1-5").on('click', function (params) {
		var d = params.data.id;
		//alert(d)
		openMate(d)
	});
	
	getChart("chart1-6").on('click', function (params) {
		var d = params.data.id;
		//alert(d)
		openMate(d)
	});
	
	
});
function getChart(eleID) {
    if (!echarts.getInstanceByDom(document.getElementById(eleID))) {
        return echarts.init(document.getElementById(eleID));
    } else {
        return echarts.getInstanceByDom(document.getElementById(eleID));
    }
}

function openQHTZ(){
	var ip='<%=GlobalNames.sysConfig.get("XBDJ_IP")%>';
	 var port='<%=GlobalNames.sysConfig.get("XBDJ_MAINPORT")%>';
	 //http://71.8.177.189:1902/ngbdp/yjgz/xszgdb
	 var url = "http://"+ip+":"+port+"/ngbdp/qhtz?zw=1&hasback=false"; 
	$h.showWindowWithSrc("qhtz",url,"��������", 1380, 740,null,{closeAction:'close'},true,true);
}
function openXBDJ(){
	var ip='<%=GlobalNames.sysConfig.get("XBDJ_IP")%>';
	 var port='<%=GlobalNames.sysConfig.get("XBDJ_MAINPORT")%>';
	 //http://71.8.177.189:1902/ngbdp/yjgz/xszgdb
	 var url = "http://"+ip+":"+port+"/ngbdp/yjgz/xszgdb?zw=1&hasback=false"; 
	$h.showWindowWithSrc("XSZG",url,"Ѳ������", 1380, 740,null,{closeAction:'close'},true,true);
}
function openXBDJ2(){
	var ip='<%=GlobalNames.sysConfig.get("XBDJ_IP")%>';
	 var port='<%=GlobalNames.sysConfig.get("XBDJ_MAINPORT")%>';
	 //http://71.8.177.189:1902/ngbdp/yjgz/newtable
	 var url = "http://"+ip+":"+port+"/ngbdp/yjgz/newtable?zw=1&hasback=false"; 
	$h.showWindowWithSrc("GYLS",url,"��Ҫ��ʵ", 1380, 740,null,{closeAction:'close'},true,true);
}
var g_contextpath = '<%= request.getContextPath() %>';
function openPicPage(){
	/* $h.showWindowWithSrc("HJQJT", contextPath+"/pages/amain/sxhj.jsp?a","���ػ���", 1420, 780,null,{closeAction:'close'},true,true); */
	var ip='<%=GlobalNames.sysConfig.get("XBDJ_IP")%>';
	 var port='<%=GlobalNames.sysConfig.get("XBDJ_MAINPORT")%>';
	 //http://71.8.177.189:1902/ngbdp/yjgz/newtable
	 var url = "http://"+ip+":"+port+"/ngbdp/hjqjt/?zw=1"; 
	$h.showWindowWithSrc("XXHJ",url,"", 1380, 740,null,{closeAction:'close'},true,true);
}
function openPicPage2(){
	var ip='<%=GlobalNames.sysConfig.get("XBDJ_IP")%>';
	 var port='<%=GlobalNames.sysConfig.get("XBDJ_MAINPORT")%>';
	 //http://71.8.177.189:1902/ngbdp/yjgz/newtable
	 var url = "http://"+ip+":"+port+"/ngbdp/gbhx/?zw=1&hasback=false"; 
	$h.showWindowWithSrc("GBHX",url,"�ɲ�����", 1380, 740,null,{closeAction:'close'},true,true);
}
function openYJTX(){
	<%-- var ip='<%=GlobalNames.sysConfig.get("XBDJ_IP")%>';
	 var port='<%=GlobalNames.sysConfig.get("XBDJ_MAINPORT")%>';
	 //http://71.8.177.189:1902/ngbdp/yjgz/newtable
	 var url = "http://"+ip+":"+port+"/ngbdp/yjgz/?zw=1&hasback=false"; 
	$h.showWindowWithSrc("YJGZ",url,"Ԥ����ע", 1350, 740,null,{closeAction:'close'},true,true); --%>
	$h.openWin('gbmainYJTXWin','pages.amain.GBMainYJTX','Ԥ������',1380, 740,'','<%=request.getContextPath()%>',null,{},true);
}
function openBZHX(){
	var ip='<%=GlobalNames.sysConfig.get("XBDJ_IP")%>';
	 var port='<%=GlobalNames.sysConfig.get("XBDJ_MAINPORT")%>';
	 //http://71.8.177.189:1902/ngbdp/yjgz/newtable
	 var url = "http://"+ip+":"+port+"/ngbdp/team/Analysis/?zw=1&hasback=false"; 
	$h.showWindowWithSrc("BZHX",url,"���ӻ���", 1380, 740,null,{closeAction:'close'},true,true);
}
/* function openGBTP(){
	parent.addTab1("3e2081e4728392a5017283bb71330003","�ɲ�����","/pages/fxyp/gbtp.jsp");
} */


document.onkeydown=function() {
	if (event.keyCode == 13) {
		if ((document.activeElement.type == "text")) {
			openMate('zgss');
			return false;
		}

	
	}
};


function openDTZB(){
	$h.openWin('DTZB','pages.customquery.DTJCZB','��ָ̬��ָ��',1400,600,null,contextPath,null,null,true);
}

function openCYMD(){
	$h.openWin('CYMD','pages.amain.CYMINGDAN','��������',900,700,null,contextPath,null,null,true);
}

</script> 
