<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<!-- <meta http-equiv="X-UA-Compatible" content="edge" /> -->
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/DataAnalysis/css/css.css">
<script src="<%=request.getContextPath()%>/pages/DataAnalysis/js/jquery1.7.2.min.js"></script>
<script src="<%=request.getContextPath()%>/pages/DataAnalysis/js/echarts.min.js"></script>
<script src="<%=request.getContextPath()%>/pages/DataAnalysis/js/comm.js"></script>
<script src="<%=request.getContextPath()%>/pages/DataAnalysis/js/option.js"></script>

<script  type="text/javascript">

var g_contextpath = '<%= request.getContextPath() %>';


</script>
<body>
<div class="container">
	<!-- <div class="nav-bar-hover">
        <div class="arrow"></div>
    </div> -->
    <!-- <div class="nav-bar">
        <div class="nav-bar-top">
            <div style="background: url(images/Group10.png) center center / 35% auto no-repeat;height: 50%;"></div>
        </div>
        <div class="nav-bar-center">
            <ul>
                <li class="gwy action">����Ա</li>
                <li class="csqt">�ι�Ⱥ��</li>
                <li class="cssy">�ι���ҵ</li>
            </ul>
        </div>
    </div> -->
    <div class="content-title">
            <div style="display: table-cell;vertical-align: middle">
                <p style="margin-left: 20px;float: left">����Ա���������۷���</p>
                <!-- <ul style="margin-right: 20px;float: right">
                    <li class="action">ȫ��</li>
                    <li>���뵥λ</li>
                    <li>�ط�</li>
                </ul> -->
            </div>
        </div>
	<div class="content-total">
          <div class="content-total-number">
              <div style="HEIGHT: 100%; WIDTH: 35%; BACKGROUND: #fff; FLOAT: left; DISPLAY: block; align-items: center">
              <div class="content-total-number-bc" style=""></div>
              <p style="MARGIN-TOP: 15%; MARGIN-LEFT: 55%; LINE-HEIGHT: 1.2">��Ա����</p>
          </div>
          <div style="HEIGHT: 100%; WIDTH: 65%; BACKGROUND: #fff; FLOAT: right; align-items: center">
              <ul></ul><!--comm.js��setPeopleNum��-->
          </div>
      </div>
      <div class="content-total-detail">
          <ul>
              <li class="nx">Ů��<span>28</span>���ˣ�ռ<span>28%</span></li>
              <li class="ssmz">��������<span>28</span>���ˣ�ռ<span>28%</span></li>
              <li class="fdy">�ǵ�Ա<span>28</span>���ˣ�ռ<span>28%</span></li>
              <li class="bk">��������ѧ��<span>28</span>���ˣ�ռ<span>28%</span></li>
              <li class="age30">30������<span>28</span>���ˣ�ռ<span>28%</span></li>
              <li class="age35">35������<span>28</span>���ˣ�ռ<span>28%</span></li>
              <li class="cj">��������<span>28</span>���ˣ�ռ<span>28%</span></li>
          </ul>
      </div>
  </div>
  <div class="content-chart">
      <!-- <div class="chart-box" style="height: 52%;width: 35%;margin: 0 1% 1% 0;">
          <div class="chart-title">ȫ������Ա�ֲ�</div>
          <div class="chart" id="chart1-1"></div>
      </div> -->
      <div class="chart-box" style="height: 52%;width: 100%;margin: 0 0 1% 0;">
          <div class="chart-title">��ְ��������������ռ��</div>
          <!-- <ul class="chart-box-tab" style="">
              <li class="action">�ۺ�</li>
              <li>����</li>
              <li>����</li>
          </ul> -->
          <div class="chart" id="chart1-2"></div>
      </div>
      <div class="chart-box" style="height: 43%;width: 49%;margin: 0 1% 0 0;">
          <div class="chart-title">���깫��Ա�����仯���</div>
          <div class="chart" id="chart2-1"></div>
      </div>
      <div class="chart-box" style="height: 43%;width: 50%;margin: 0;">
          <div class="chart-title">�����������</div>
          <div class="chart" id="chart2-2"></div>
      </div>
  </div>
</div>
</body>
<script type="text/javascript">

</script>


       </div>
        </div>
    </div>
</div>
</body>
<script>
</script>
</html>