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
                <li class="gwy action">公务员</li>
                <li class="csqt">参公群团</li>
                <li class="cssy">参公事业</li>
            </ul>
        </div>
    </div> -->
    <div class="content-title">
            <div style="display: table-cell;vertical-align: middle">
                <p style="margin-left: 20px;float: left">公务员整体情况宏观分析</p>
                <!-- <ul style="margin-right: 20px;float: right">
                    <li class="action">全国</li>
                    <li>中央单位</li>
                    <li>地方</li>
                </ul> -->
            </div>
        </div>
	<div class="content-total">
          <div class="content-total-number">
              <div style="HEIGHT: 100%; WIDTH: 35%; BACKGROUND: #fff; FLOAT: left; DISPLAY: block; align-items: center">
              <div class="content-total-number-bc" style=""></div>
              <p style="MARGIN-TOP: 15%; MARGIN-LEFT: 55%; LINE-HEIGHT: 1.2">人员总数</p>
          </div>
          <div style="HEIGHT: 100%; WIDTH: 65%; BACKGROUND: #fff; FLOAT: right; align-items: center">
              <ul></ul><!--comm.js（setPeopleNum）-->
          </div>
      </div>
      <div class="content-total-detail">
          <ul>
              <li class="nx">女性<span>28</span>万人，占<span>28%</span></li>
              <li class="ssmz">少数名族<span>28</span>万人，占<span>28%</span></li>
              <li class="fdy">非党员<span>28</span>万人，占<span>28%</span></li>
              <li class="bk">本科以上学历<span>28</span>万人，占<span>28%</span></li>
              <li class="age30">30岁以下<span>28</span>万人，占<span>28%</span></li>
              <li class="age35">35岁以下<span>28</span>万人，占<span>28%</span></li>
              <li class="cj">处级以上<span>28</span>万人，占<span>28%</span></li>
          </ul>
      </div>
  </div>
  <div class="content-chart">
      <!-- <div class="chart-box" style="height: 52%;width: 35%;margin: 0 1% 1% 0;">
          <div class="chart-title">全国公务员分布</div>
          <div class="chart" id="chart1-1"></div>
      </div> -->
      <div class="chart-box" style="height: 52%;width: 100%;margin: 0 0 1% 0;">
          <div class="chart-title">各职务层次年龄总数及占比</div>
          <!-- <ul class="chart-box-tab" style="">
              <li class="action">综合</li>
              <li>警察</li>
              <li>法检</li>
          </ul> -->
          <div class="chart" id="chart1-2"></div>
      </div>
      <div class="chart-box" style="height: 43%;width: 49%;margin: 0 1% 0 0;">
          <div class="chart-title">历年公务员人数变化情况</div>
          <div class="chart" id="chart2-1"></div>
      </div>
      <div class="chart-box" style="height: 43%;width: 50%;margin: 0;">
          <div class="chart-title">年龄情况分析</div>
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