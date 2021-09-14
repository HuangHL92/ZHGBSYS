<?xml version="1.0" encoding="UTF-8"?>
<Form xmlVersion="20141222" releaseVersion="7.1.1">
<TableDataMap>
<TableData name="ds2" class="com.fr.data.impl.DBTableData">
<Parameters>
<Parameter>
<Attributes name="saleman"/>
<O>
<![CDATA[1]]></O>
</Parameter>
</Parameters>
<Attributes maxMemRowCount="-1"/>
<Connection class="com.fr.data.impl.NameDatabaseConnection">
<DatabaseName>
<![CDATA[FRDemo]]></DatabaseName>
</Connection>
<Query>
<![CDATA[select a.month1,实际-预算 as 差额 from
(SELECT month1,num as 预算 FROM sale_month
where saleman='${saleman}' and type='预算') a
inner join
(SELECT month1,num as 实际 FROM sale_month
where saleman='${saleman}' and type='实际') b
on
a.month1=b.month1]]></Query>
</TableData>
<TableData name="ds1" class="com.fr.data.impl.DBTableData">
<Parameters>
<Parameter>
<Attributes name="saleman"/>
<O>
<![CDATA[1]]></O>
</Parameter>
</Parameters>
<Attributes maxMemRowCount="-1"/>
<Connection class="com.fr.data.impl.NameDatabaseConnection">
<DatabaseName>
<![CDATA[FRDemo]]></DatabaseName>
</Connection>
<Query>
<![CDATA[SELECT * FROM sale_month
where saleman='${saleman}' ]]></Query>
</TableData>
<TableData name="ds3" class="com.fr.data.impl.DBTableData">
<Parameters>
<Parameter>
<Attributes name="saleman"/>
<O>
<![CDATA[1]]></O>
</Parameter>
</Parameters>
<Attributes maxMemRowCount="-1"/>
<Connection class="com.fr.data.impl.NameDatabaseConnection">
<DatabaseName>
<![CDATA[FRDemo]]></DatabaseName>
</Connection>
<Query>
<![CDATA[SELECT distinct'销售贡献率' as a,contri FROM sale_month
where saleman='${saleman}' ]]></Query>
</TableData>
</TableDataMap>
<Layout class="com.fr.form.ui.container.WBorderLayout">
<WidgetName name="form"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Margin top="0" left="0" bottom="0" right="0"/>
<Border>
<border style="0" color="-723724" type="0" borderStyle="0"/>
<WidgetTitle>
<O>
<![CDATA[新建标题]]></O>
<FRFont name="SimSun" style="0" size="72"/>
<Position pos="0"/>
</WidgetTitle>
<Alpha alpha="1.0"/>
</Border>
<LCAttr vgap="0" hgap="0" compInterval="0"/>
<Center class="com.fr.form.ui.container.WFitLayout">
<WidgetName name="body"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Margin top="10" left="10" bottom="5" right="10"/>
<Border>
<border style="0" color="-723724" type="0" borderStyle="0"/>
<WidgetTitle>
<O>
<![CDATA[新建标题]]></O>
<FRFont name="SimSun" style="0" size="72"/>
<Position pos="0"/>
</WidgetTitle>
<Alpha alpha="1.0"/>
</Border>
<LCAttr vgap="0" hgap="0" compInterval="5"/>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.container.WTitleLayout">
<WidgetName name="chart0"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Margin top="0" left="0" bottom="0" right="0"/>
<Border>
<border style="0" color="-723724" type="0" borderStyle="0"/>
<WidgetTitle>
<O>
<![CDATA[新建标题]]></O>
<FRFont name="SimSun" style="0" size="72"/>
<Position pos="0"/>
</WidgetTitle>
<Alpha alpha="1.0"/>
</Border>
<LCAttr vgap="0" hgap="0" compInterval="0"/>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.ChartEditor">
<WidgetName name="chart0"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Margin top="0" left="0" bottom="0" right="0"/>
<Border>
<border style="1" color="-2434342" type="1" borderStyle="0"/>
<WidgetTitle>
<O>
<![CDATA[=\"   与预算的差额\"]]></O>
<FRFont name="微软雅黑" style="0" size="80"/>
<Position pos="0"/>
</WidgetTitle>
<Background name="GradientBackground" color1="-1" color2="-52" direction="1" useCell="true" begin="0.0" finish="0.0" cyclic="false"/>
<Alpha alpha="1.0"/>
</Border>
<Background name="GradientBackground" color1="-1" color2="-52" direction="1" useCell="true" begin="0.0" finish="0.0" cyclic="false"/>
<LayoutAttr selectedIndex="0"/>
<Chart name="默认">
<Chart class="com.fr.chart.chartattr.Chart">
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="0" isRoundBorder="false"/>
<newColor borderColor="-6908266"/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<ChartAttr isJSDraw="true"/>
<Title>
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="0" isRoundBorder="false"/>
<newColor borderColor="-6908266"/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<O>
<![CDATA[新建图表标题]]></O>
<TextAttr>
<Attr alignText="0">
<FRFont name="微软雅黑" style="0" size="88"/>
</Attr>
</TextAttr>
<TitleVisible value="false" position="0"/>
</Title>
<Plot class="com.fr.chart.chartattr.Bar2DPlot">
<CategoryPlot>
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="0" isRoundBorder="false"/>
<newColor/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<Attr isNullValueBreak="true" autoRefreshPerSecond="-1" seriesDragEnable="false" plotStyle="0"/>
<newHotTooltipStyle>
<AttrContents>
<Attr showLine="false" position="1" seriesLabel="${SERIES}${BR}${CATEGORY}${BR}${VALUE}"/>
<Format class="com.fr.base.CoreDecimalFormat">
<![CDATA[#.##]]></Format>
<PercentFormat>
<Format class="com.fr.base.CoreDecimalFormat">
<![CDATA[#.##%]]></Format>
</PercentFormat>
</AttrContents>
</newHotTooltipStyle>
<ConditionCollection>
<DefaultAttr class="com.fr.chart.chartglyph.ConditionAttr">
<ConditionAttr name=""/>
</DefaultAttr>
</ConditionCollection>
<Legend>
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="0" isRoundBorder="false"/>
<newColor borderColor="-6908266"/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<Attr position="4" visible="false"/>
<FRFont name="微软雅黑" style="0" size="72"/>
</Legend>
<DataSheet>
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="1" isRoundBorder="false"/>
<newColor borderColor="-16777216"/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<Attr isVisible="false"/>
</DataSheet>
<newPlotFillStyle>
<AttrFillStyle>
<AFStyle colorStyle="0"/>
<FillStyleName fillStyleName=""/>
</AttrFillStyle>
</newPlotFillStyle>
<RectanglePlotAttr interactiveAxisTooltip="false"/>
<xAxis>
<CategoryAxis class="com.fr.chart.chartattr.CategoryAxis">
<newAxisAttr isShowAxisLabel="true"/>
<AxisLineStyle AxisStyle="1" MainGridStyle="0"/>
<newLineColor mainGridColor="-4144960" lineColor="-5197648"/>
<AxisPosition value="2"/>
<TickLine201106 type="2" secType="0"/>
<ArrowShow arrowShow="false"/>
<TextAttr>
<Attr alignText="0">
<FRFont name="Microsoft YaHei" style="0" size="72"/>
</Attr>
</TextAttr>
<AxisLabelCount value="=0"/>
<AxisRange/>
<AxisUnit201106 isCustomMainUnit="false" isCustomSecUnit="false" mainUnit="=0" secUnit="=0"/>
<ZoomAxisAttr isZoom="false"/>
<axisReversed axisReversed="false"/>
</CategoryAxis>
</xAxis>
<yAxis>
<ValueAxis class="com.fr.chart.chartattr.ValueAxis">
<ValueAxisAttr201108 alignZeroValue="false"/>
<newAxisAttr isShowAxisLabel="true"/>
<AxisLineStyle AxisStyle="1" MainGridStyle="1"/>
<newLineColor mainGridColor="-4144960" lineColor="-5197648"/>
<AxisPosition value="3"/>
<TickLine201106 type="2" secType="0"/>
<ArrowShow arrowShow="false"/>
<TextAttr>
<Attr alignText="0">
<FRFont name="Century Gothic" style="0" size="72"/>
</Attr>
</TextAttr>
<AxisLabelCount value="=0"/>
<AxisRange/>
<AxisUnit201106 isCustomMainUnit="false" isCustomSecUnit="false" mainUnit="=0" secUnit="=0"/>
<ZoomAxisAttr isZoom="false"/>
<axisReversed axisReversed="false"/>
</ValueAxis>
</yAxis>
<secondAxis>
<ValueAxis class="com.fr.chart.chartattr.ValueAxis">
<ValueAxisAttr201108 alignZeroValue="false"/>
<newAxisAttr isShowAxisLabel="true"/>
<AxisLineStyle AxisStyle="1" MainGridStyle="1"/>
<newLineColor mainGridColor="-4144960" lineColor="-5197648"/>
<AxisPosition value="4"/>
<TickLine201106 type="2" secType="0"/>
<ArrowShow arrowShow="false"/>
<TextAttr>
<Attr alignText="0">
<FRFont name="Century Gothic" style="0" size="72"/>
</Attr>
</TextAttr>
<AxisLabelCount value="=0"/>
<AxisRange/>
<AxisUnit201106 isCustomMainUnit="false" isCustomSecUnit="false" mainUnit="=0" secUnit="=0"/>
<ZoomAxisAttr isZoom="false"/>
<axisReversed axisReversed="false"/>
</ValueAxis>
</secondAxis>
<CateAttr isStacked="false"/>
<BarAttr isHorizontal="true" overlap="-0.25" interval="0.0"/>
<Bar2DAttr isSimulation3D="false"/>
</CategoryPlot>
</Plot>
<ChartDefinition>
<MoreNameCDDefinition>
<Top topCate="-1" topValue="-1" isDiscardOtherCate="false" isDiscardOtherSeries="false" isDiscardNullCate="false" isDiscardNullSeries="false"/>
<TableData class="com.fr.data.impl.NameTableData">
<Name>
<![CDATA[ds2]]></Name>
</TableData>
<CategoryName value="month1"/>
<ChartSummaryColumn name="差额" function="com.fr.data.util.function.SumFunction" customName="差额"/>
</MoreNameCDDefinition>
</ChartDefinition>
</Chart>
</Chart>
</InnerWidget>
<BoundsAttr x="0" y="38" width="291" height="229"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.Label">
<WidgetName name="title"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<widgetValue>
<O t="Formula" class="Formula">
<Attributes>
<![CDATA[="   与预算的差额"]]></Attributes>
</O>
</widgetValue>
<LabelAttr verticalcenter="true" textalign="2" autoline="true"/>
<FRFont name="微软雅黑" style="0" size="80"/>
<border style="1" color="-2434342"/>
</InnerWidget>
<BoundsAttr x="0" y="0" width="291" height="38"/>
</Widget>
<title class="com.fr.form.ui.Label">
<WidgetName name="title"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<widgetValue>
<O t="Formula" class="Formula">
<Attributes>
<![CDATA[="   与预算的差额"]]></Attributes>
</O>
</widgetValue>
<LabelAttr verticalcenter="true" textalign="2" autoline="true"/>
<FRFont name="微软雅黑" style="0" size="80"/>
<border style="1" color="-2434342"/>
</title>
<body class="com.fr.form.ui.ChartEditor">
<WidgetName name="chart0"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Margin top="0" left="0" bottom="0" right="0"/>
<Border>
<border style="0" color="-723724" type="0" borderStyle="0"/>
<WidgetTitle>
<O>
<![CDATA[新建标题]]></O>
<FRFont name="SimSun" style="0" size="72"/>
<Position pos="0"/>
</WidgetTitle>
<Alpha alpha="1.0"/>
</Border>
<LayoutAttr selectedIndex="0"/>
<Chart name="默认">
<Chart class="com.fr.chart.chartattr.Chart">
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="0" isRoundBorder="false"/>
<newColor borderColor="-6908266"/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<ChartAttr isJSDraw="true"/>
<Title>
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="0" isRoundBorder="false"/>
<newColor borderColor="-6908266"/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<O>
<![CDATA[新建图表标题]]></O>
<TextAttr>
<Attr alignText="0">
<FRFont name="Microsoft YaHei" style="0" size="88"/>
</Attr>
</TextAttr>
<TitleVisible value="true" position="0"/>
</Title>
<Plot class="com.fr.chart.chartattr.Bar2DPlot">
<CategoryPlot>
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="0" isRoundBorder="false"/>
<newColor/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<Attr isNullValueBreak="true" autoRefreshPerSecond="0" seriesDragEnable="true" plotStyle="0"/>
<newHotTooltipStyle>
<AttrContents>
<Attr showLine="false" position="1" seriesLabel="${VALUE}"/>
<Format class="com.fr.base.CoreDecimalFormat">
<![CDATA[#.##]]></Format>
<PercentFormat>
<Format class="com.fr.base.CoreDecimalFormat">
<![CDATA[#.##%]]></Format>
</PercentFormat>
</AttrContents>
</newHotTooltipStyle>
<ConditionCollection>
<DefaultAttr class="com.fr.chart.chartglyph.ConditionAttr">
<ConditionAttr name=""/>
</DefaultAttr>
</ConditionCollection>
<Legend>
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="0" isRoundBorder="false"/>
<newColor borderColor="-6908266"/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<Attr position="4" visible="true"/>
<FRFont name="Microsoft YaHei" style="0" size="72"/>
</Legend>
<DataSheet>
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="1" isRoundBorder="false"/>
<newColor borderColor="-16777216"/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<Attr isVisible="false"/>
</DataSheet>
<newPlotFillStyle>
<AttrFillStyle>
<AFStyle colorStyle="0"/>
<FillStyleName fillStyleName=""/>
</AttrFillStyle>
</newPlotFillStyle>
<RectanglePlotAttr interactiveAxisTooltip="false"/>
<xAxis>
<CategoryAxis class="com.fr.chart.chartattr.CategoryAxis">
<newAxisAttr isShowAxisLabel="true"/>
<AxisLineStyle AxisStyle="1" MainGridStyle="0"/>
<newLineColor mainGridColor="-4144960" lineColor="-5197648"/>
<AxisPosition value="2"/>
<TickLine201106 type="2" secType="0"/>
<ArrowShow arrowShow="false"/>
<TextAttr>
<Attr alignText="0">
<FRFont name="Microsoft YaHei" style="0" size="72"/>
</Attr>
</TextAttr>
<AxisLabelCount value="=0"/>
<AxisRange/>
<AxisUnit201106 isCustomMainUnit="false" isCustomSecUnit="false" mainUnit="=0" secUnit="=0"/>
<ZoomAxisAttr isZoom="false"/>
<axisReversed axisReversed="false"/>
</CategoryAxis>
</xAxis>
<yAxis>
<ValueAxis class="com.fr.chart.chartattr.ValueAxis">
<ValueAxisAttr201108 alignZeroValue="false"/>
<newAxisAttr isShowAxisLabel="true"/>
<AxisLineStyle AxisStyle="1" MainGridStyle="1"/>
<newLineColor mainGridColor="-4144960" lineColor="-5197648"/>
<AxisPosition value="3"/>
<TickLine201106 type="2" secType="0"/>
<ArrowShow arrowShow="false"/>
<TextAttr>
<Attr alignText="0">
<FRFont name="Century Gothic" style="0" size="72"/>
</Attr>
</TextAttr>
<AxisLabelCount value="=0"/>
<AxisRange/>
<AxisUnit201106 isCustomMainUnit="false" isCustomSecUnit="false" mainUnit="=0" secUnit="=0"/>
<ZoomAxisAttr isZoom="false"/>
<axisReversed axisReversed="false"/>
</ValueAxis>
</yAxis>
<secondAxis>
<ValueAxis class="com.fr.chart.chartattr.ValueAxis">
<ValueAxisAttr201108 alignZeroValue="false"/>
<newAxisAttr isShowAxisLabel="true"/>
<AxisLineStyle AxisStyle="1" MainGridStyle="1"/>
<newLineColor mainGridColor="-4144960" lineColor="-5197648"/>
<AxisPosition value="4"/>
<TickLine201106 type="2" secType="0"/>
<ArrowShow arrowShow="false"/>
<TextAttr>
<Attr alignText="0">
<FRFont name="Century Gothic" style="0" size="72"/>
</Attr>
</TextAttr>
<AxisLabelCount value="=0"/>
<AxisRange/>
<AxisUnit201106 isCustomMainUnit="false" isCustomSecUnit="false" mainUnit="=0" secUnit="=0"/>
<ZoomAxisAttr isZoom="false"/>
<axisReversed axisReversed="false"/>
</ValueAxis>
</secondAxis>
<CateAttr isStacked="false"/>
<BarAttr isHorizontal="true" overlap="-0.25" interval="1.0"/>
<Bar2DAttr isSimulation3D="false"/>
</CategoryPlot>
</Plot>
</Chart>
</Chart>
</body>
</InnerWidget>
<BoundsAttr x="0" y="0" width="291" height="267"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.container.WTitleLayout">
<WidgetName name="chart1"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Margin top="0" left="0" bottom="0" right="0"/>
<Border>
<border style="0" color="-723724" type="0" borderStyle="0"/>
<WidgetTitle>
<O>
<![CDATA[新建标题]]></O>
<FRFont name="SimSun" style="0" size="72"/>
<Position pos="0"/>
</WidgetTitle>
<Alpha alpha="1.0"/>
</Border>
<LCAttr vgap="0" hgap="0" compInterval="0"/>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.ChartEditor">
<WidgetName name="chart1"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Margin top="0" left="0" bottom="0" right="0"/>
<Border>
<border style="1" color="-2368549" type="1" borderStyle="0"/>
<WidgetTitle>
<O>
<![CDATA[=\"   销售贡献率\"]]></O>
<FRFont name="微软雅黑" style="0" size="80"/>
<Position pos="0"/>
<Background name="ColorBackground" color="-52"/>
</WidgetTitle>
<Background name="GradientBackground" color1="-51" color2="-1" direction="1" useCell="true" begin="0.0" finish="0.0" cyclic="false"/>
<Alpha alpha="1.0"/>
</Border>
<Background name="GradientBackground" color1="-51" color2="-1" direction="1" useCell="true" begin="0.0" finish="0.0" cyclic="false"/>
<LayoutAttr selectedIndex="0"/>
<Chart name="默认">
<Chart class="com.fr.chart.chartattr.Chart">
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="0" isRoundBorder="false"/>
<newColor borderColor="-6908266"/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<ChartAttr isJSDraw="true"/>
<Title>
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="0" isRoundBorder="false"/>
<newColor borderColor="-6908266"/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<O>
<![CDATA[新建图表标题]]></O>
<TextAttr>
<Attr alignText="0">
<FRFont name="微软雅黑" style="0" size="88"/>
</Attr>
</TextAttr>
<TitleVisible value="false" position="0"/>
</Title>
<Plot class="com.fr.chart.chartattr.MeterBluePlot">
<MeterPlot>
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="0" isRoundBorder="false"/>
<newColor/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<Attr isNullValueBreak="true" autoRefreshPerSecond="0" seriesDragEnable="true" plotStyle="0"/>
<newHotTooltipStyle>
<AttrContents>
<Attr showLine="false" position="1" seriesLabel="${VALUE}"/>
<Format class="com.fr.base.CoreDecimalFormat">
<![CDATA[#.##]]></Format>
<PercentFormat>
<Format class="com.fr.base.CoreDecimalFormat">
<![CDATA[#.##%]]></Format>
</PercentFormat>
</AttrContents>
</newHotTooltipStyle>
<ConditionCollection>
<DefaultAttr class="com.fr.chart.chartglyph.ConditionAttr">
<ConditionAttr name=""/>
</DefaultAttr>
</ConditionCollection>
<DataSheet>
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="1" isRoundBorder="false"/>
<newColor borderColor="-16777216"/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<Attr isVisible="false"/>
</DataSheet>
<newPlotFillStyle>
<AttrFillStyle>
<AFStyle colorStyle="0"/>
<FillStyleName fillStyleName=""/>
</AttrFillStyle>
</newPlotFillStyle>
<MeterStyle>
<Attr meterAngle="360" maxArrowAngle="300" units="%" order="0" designType="0" tickLabelsVisible="true" dialShape="1" isShowTitle="false" meterType="1" startValue="=0.0" endValue="=180.0" tickSize="=20.0"/>
<TextAttr>
<Attr alignText="0">
<FRFont name="微软雅黑" style="0" size="80"/>
</Attr>
</TextAttr>
<valueTextAttr>
<TextAttr>
<Attr alignText="0">
<FRFont name="Century Gothic" style="1" size="144" foreground="-1"/>
</Attr>
</TextAttr>
</valueTextAttr>
<unitTextAttr>
<TextAttr>
<Attr alignText="0">
<FRFont name="微软雅黑" style="0" size="96" foreground="-1"/>
</Attr>
</TextAttr>
</unitTextAttr>
<IntervalList>
<MeterInterval label="分段区域" backgroudColor="-1620162" beginValue="=0.0" endValue="=60.0"/>
<MeterInterval label="分段区域" backgroudColor="-208375" beginValue="=60.0" endValue="=120.0"/>
<MeterInterval label="分段区域" backgroudColor="-11683767" beginValue="=120.0" endValue="=180.0"/>
</IntervalList>
<MapHotAreaColor>
<MC_Attr minValue="0.0" maxValue="100.0" useType="0" areaNumber="5" mainColor="-16776961"/>
</MapHotAreaColor>
</MeterStyle>
</MeterPlot>
</Plot>
<ChartDefinition>
<MeterTableDefinition>
<Top topCate="-1" topValue="-1" isDiscardOtherCate="false" isDiscardOtherSeries="false" isDiscardNullCate="false" isDiscardNullSeries="false"/>
<TableData class="com.fr.data.impl.NameTableData">
<Name>
<![CDATA[ds3]]></Name>
</TableData>
<MeterTable201109 meterType="1" name="a" value="contri"/>
</MeterTableDefinition>
</ChartDefinition>
</Chart>
</Chart>
</InnerWidget>
<BoundsAttr x="0" y="38" width="291" height="205"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.Label">
<WidgetName name="title"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<widgetValue>
<O t="Formula" class="Formula">
<Attributes>
<![CDATA[="   销售贡献率"]]></Attributes>
</O>
</widgetValue>
<LabelAttr verticalcenter="true" textalign="2" autoline="true"/>
<FRFont name="微软雅黑" style="0" size="80"/>
<Background name="ColorBackground" color="-52"/>
<border style="1" color="-2368549"/>
</InnerWidget>
<BoundsAttr x="0" y="0" width="291" height="38"/>
</Widget>
<title class="com.fr.form.ui.Label">
<WidgetName name="title"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<widgetValue>
<O t="Formula" class="Formula">
<Attributes>
<![CDATA[="   销售贡献率"]]></Attributes>
</O>
</widgetValue>
<LabelAttr verticalcenter="true" textalign="2" autoline="true"/>
<FRFont name="微软雅黑" style="0" size="80"/>
<border style="1" color="-2368549"/>
</title>
<body class="com.fr.form.ui.ChartEditor">
<WidgetName name="chart1"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Margin top="0" left="0" bottom="0" right="0"/>
<Border>
<border style="0" color="-723724" type="0" borderStyle="0"/>
<WidgetTitle>
<O>
<![CDATA[新建标题]]></O>
<FRFont name="SimSun" style="0" size="72"/>
<Position pos="0"/>
</WidgetTitle>
<Alpha alpha="1.0"/>
</Border>
<LayoutAttr selectedIndex="0"/>
<Chart name="默认">
<Chart class="com.fr.chart.chartattr.Chart">
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="0" isRoundBorder="false"/>
<newColor borderColor="-6908266"/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<ChartAttr isJSDraw="true"/>
<Title>
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="0" isRoundBorder="false"/>
<newColor borderColor="-6908266"/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<O>
<![CDATA[新建图表标题]]></O>
<TextAttr>
<Attr alignText="0">
<FRFont name="Microsoft YaHei" style="0" size="88"/>
</Attr>
</TextAttr>
<TitleVisible value="true" position="0"/>
</Title>
<Plot class="com.fr.chart.chartattr.MeterPlot">
<MeterPlot>
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="0" isRoundBorder="false"/>
<newColor/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<Attr isNullValueBreak="true" autoRefreshPerSecond="0" seriesDragEnable="true" plotStyle="0"/>
<newHotTooltipStyle>
<AttrContents>
<Attr showLine="false" position="1" seriesLabel="${VALUE}"/>
<Format class="com.fr.base.CoreDecimalFormat">
<![CDATA[#.##]]></Format>
<PercentFormat>
<Format class="com.fr.base.CoreDecimalFormat">
<![CDATA[#.##%]]></Format>
</PercentFormat>
</AttrContents>
</newHotTooltipStyle>
<ConditionCollection>
<DefaultAttr class="com.fr.chart.chartglyph.ConditionAttr">
<ConditionAttr name=""/>
</DefaultAttr>
</ConditionCollection>
<DataSheet>
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="1" isRoundBorder="false"/>
<newColor borderColor="-16777216"/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<Attr isVisible="false"/>
</DataSheet>
<newPlotFillStyle>
<AttrFillStyle>
<AFStyle colorStyle="0"/>
<FillStyleName fillStyleName=""/>
</AttrFillStyle>
</newPlotFillStyle>
<MeterStyle>
<Attr meterAngle="180" maxArrowAngle="180" units="元" order="0" designType="0" tickLabelsVisible="true" dialShape="180" isShowTitle="true" meterType="0" startValue="=0.0" endValue="=180.0" tickSize="=20.0"/>
<TextAttr>
<Attr alignText="0">
<FRFont name="Microsoft Yahei" style="0" size="80"/>
</Attr>
</TextAttr>
<valueTextAttr>
<TextAttr>
<Attr alignText="0">
<FRFont name="Century Gothic" style="1" size="144" foreground="-11683767"/>
</Attr>
</TextAttr>
</valueTextAttr>
<unitTextAttr>
<TextAttr>
<Attr alignText="0">
<FRFont name="Microsoft Yahei" style="0" size="96"/>
</Attr>
</TextAttr>
</unitTextAttr>
<IntervalList>
<MeterInterval label="分段区域" backgroudColor="-1620162" beginValue="=0.0" endValue="=60.0"/>
<MeterInterval label="分段区域" backgroudColor="-208375" beginValue="=60.0" endValue="=120.0"/>
<MeterInterval label="分段区域" backgroudColor="-11683767" beginValue="=120.0" endValue="=180.0"/>
</IntervalList>
<MapHotAreaColor>
<MC_Attr minValue="0.0" maxValue="100.0" useType="0" areaNumber="5" mainColor="-16776961"/>
</MapHotAreaColor>
</MeterStyle>
</MeterPlot>
</Plot>
</Chart>
</Chart>
</body>
</InnerWidget>
<BoundsAttr x="0" y="267" width="291" height="243"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.container.WTitleLayout">
<WidgetName name="report0"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Margin top="0" left="0" bottom="0" right="0"/>
<Border>
<border style="0" color="-723724" type="0" borderStyle="0"/>
<WidgetTitle>
<O>
<![CDATA[新建标题]]></O>
<FRFont name="SimSun" style="0" size="72"/>
<Position pos="0"/>
</WidgetTitle>
<Alpha alpha="1.0"/>
</Border>
<LCAttr vgap="0" hgap="0" compInterval="0"/>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.ElementCaseEditor">
<WidgetName name="report0"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Margin top="1" left="1" bottom="1" right="1"/>
<Border>
<border style="1" color="-2434342" type="1" borderStyle="0"/>
<WidgetTitle>
<O>
<![CDATA[=\"   销售代表每月销售\"]]></O>
<FRFont name="微软雅黑" style="0" size="80"/>
<Position pos="0"/>
</WidgetTitle>
<Background name="GradientBackground" color1="-1" color2="-52" direction="1" useCell="true" begin="0.0" finish="0.0" cyclic="false"/>
<Alpha alpha="1.0"/>
</Border>
<Background name="GradientBackground" color1="-1" color2="-52" direction="1" useCell="true" begin="0.0" finish="0.0" cyclic="false"/>
<FormElementCase>
<ReportPageAttr>
<HR/>
<FR/>
<HC/>
<FC/>
</ReportPageAttr>
<ColumnPrivilegeControl/>
<RowPrivilegeControl/>
<RowHeight defaultValue="723900">
<![CDATA[723900,723900,723900,723900,723900,723900,723900,723900,7886700,723900,723900]]></RowHeight>
<ColumnWidth defaultValue="2743200">
<![CDATA[4572000,1219200,4114800,5486400,7277100,2743200,2743200,2743200,2743200,2743200,2743200]]></ColumnWidth>
<CellElementList>
<C c="0" r="0" cs="5" rs="13">
<O t="CC">
<LayoutAttr selectedIndex="0"/>
<Chart name="默认">
<Chart class="com.fr.chart.chartattr.Chart">
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="0" isRoundBorder="false"/>
<newColor borderColor="-6908266"/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<ChartAttr isJSDraw="true"/>
<Title>
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="0" isRoundBorder="false"/>
<newColor borderColor="-6908266"/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<O>
<![CDATA[新建图表标题]]></O>
<TextAttr>
<Attr alignText="0">
<FRFont name="微软雅黑" style="0" size="88"/>
</Attr>
</TextAttr>
<TitleVisible value="false" position="0"/>
</Title>
<Plot class="com.fr.chart.chartattr.CustomPlot">
<CategoryPlot>
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="0" isRoundBorder="false"/>
<newColor/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<Attr isNullValueBreak="true" autoRefreshPerSecond="-1" seriesDragEnable="true" plotStyle="0"/>
<newHotTooltipStyle>
<AttrContents>
<Attr showLine="false" position="1" seriesLabel="${SERIES}${BR}${CATEGORY}${BR}${VALUE}"/>
<Format class="com.fr.base.CoreDecimalFormat">
<![CDATA[#.##]]></Format>
<PercentFormat>
<Format class="com.fr.base.CoreDecimalFormat">
<![CDATA[#.##%]]></Format>
</PercentFormat>
</AttrContents>
</newHotTooltipStyle>
<ConditionCollection>
<DefaultAttr class="com.fr.chart.chartglyph.CustomAttr">
<CustomAttr>
<ConditionAttr name=""/>
<ctattr renderer="2"/>
</CustomAttr>
</DefaultAttr>
<ConditionAttrList>
<List index="0">
<CustomAttr>
<ConditionAttr name="条件属性01">
<AttrList>
<Attr class="com.fr.chart.base.AttrAxisPosition">
<AttrAxisPosition>
<Attr axisPosition="LEFT"/>
</AttrAxisPosition>
</Attr>
</AttrList>
<Condition class="com.fr.data.condition.ListCondition">
<JoinCondition join="0">
<Condition class="com.fr.data.condition.CommonCondition">
<CNUMBER>
<![CDATA[0]]></CNUMBER>
<CNAME>
<![CDATA[系列序号]]></CNAME>
<Compare op="0">
<O t="I">
<![CDATA[1]]></O>
</Compare>
</Condition>
</JoinCondition>
</Condition>
</ConditionAttr>
<ctattr renderer="1"/>
</CustomAttr>
</List>
<List index="1">
<CustomAttr>
<ConditionAttr name="条件属性11">
<AttrList>
<Attr class="com.fr.chart.base.AttrLineStyle">
<AttrLineStyle>
<newAttr lineStyle="5"/>
</AttrLineStyle>
</Attr>
<Attr class="com.fr.chart.base.AttrMarkerType">
<AttrMarkerType>
<Attr markerType="NullMarker"/>
</AttrMarkerType>
</Attr>
<Attr class="com.fr.chart.base.AttrAxisPosition">
<AttrAxisPosition>
<Attr axisPosition="RIGHT"/>
</AttrAxisPosition>
</Attr>
</AttrList>
<Condition class="com.fr.data.condition.ListCondition">
<JoinCondition join="0">
<Condition class="com.fr.data.condition.CommonCondition">
<CNUMBER>
<![CDATA[0]]></CNUMBER>
<CNAME>
<![CDATA[系列序号]]></CNAME>
<Compare op="0">
<O t="I">
<![CDATA[2]]></O>
</Compare>
</Condition>
</JoinCondition>
</Condition>
</ConditionAttr>
<ctattr renderer="2"/>
</CustomAttr>
</List>
<List index="2">
<CustomAttr>
<ConditionAttr name="条件属性3">
<Condition class="com.fr.data.condition.CommonCondition">
<CNUMBER>
<![CDATA[0]]></CNUMBER>
<CNAME>
<![CDATA[系列序号]]></CNAME>
<Compare op="0">
<O>
<![CDATA[3]]></O>
</Compare>
</Condition>
</ConditionAttr>
<ctattr renderer="2"/>
</CustomAttr>
</List>
</ConditionAttrList>
</ConditionCollection>
<Legend>
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="0" isRoundBorder="false"/>
<newColor borderColor="-6908266"/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<Attr position="3" visible="true"/>
<FRFont name="微软雅黑" style="0" size="72"/>
</Legend>
<DataSheet>
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="1" isRoundBorder="false"/>
<newColor borderColor="-16777216"/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<Attr isVisible="false"/>
</DataSheet>
<newPlotFillStyle>
<AttrFillStyle>
<AFStyle colorStyle="0"/>
<FillStyleName fillStyleName=""/>
</AttrFillStyle>
</newPlotFillStyle>
<RectanglePlotAttr interactiveAxisTooltip="false"/>
<xAxis>
<CategoryAxis class="com.fr.chart.chartattr.CategoryAxis">
<newAxisAttr isShowAxisLabel="true"/>
<AxisLineStyle AxisStyle="1" MainGridStyle="0"/>
<newLineColor mainGridColor="-4144960" lineColor="-5197648"/>
<AxisPosition value="3"/>
<TickLine201106 type="2" secType="0"/>
<ArrowShow arrowShow="false"/>
<TextAttr>
<Attr alignText="0">
<FRFont name="Microsoft YaHei" style="0" size="72"/>
</Attr>
</TextAttr>
<AxisLabelCount value="=0"/>
<AxisRange/>
<AxisUnit201106 isCustomMainUnit="false" isCustomSecUnit="false" mainUnit="=0" secUnit="=0"/>
<ZoomAxisAttr isZoom="false"/>
<axisReversed axisReversed="false"/>
</CategoryAxis>
</xAxis>
<yAxis>
<ValueAxis class="com.fr.chart.chartattr.ValueAxis">
<ValueAxisAttr201108 alignZeroValue="false"/>
<newAxisAttr isShowAxisLabel="true"/>
<AxisLineStyle AxisStyle="1" MainGridStyle="1"/>
<newLineColor mainGridColor="-4144960" lineColor="-5197648"/>
<AxisPosition value="2"/>
<TickLine201106 type="2" secType="0"/>
<ArrowShow arrowShow="false"/>
<TextAttr>
<Attr alignText="0">
<FRFont name="Century Gothic" style="0" size="72"/>
</Attr>
</TextAttr>
<AxisLabelCount value="=0"/>
<AxisRange/>
<AxisUnit201106 isCustomMainUnit="false" isCustomSecUnit="false" mainUnit="=0" secUnit="=0"/>
<ZoomAxisAttr isZoom="false"/>
<axisReversed axisReversed="false"/>
</ValueAxis>
</yAxis>
<secondAxis>
<ValueAxis class="com.fr.chart.chartattr.ValueAxis">
<ValueAxisAttr201108 alignZeroValue="false"/>
<newAxisAttr isShowAxisLabel="true"/>
<AxisLineStyle AxisStyle="1" MainGridStyle="0"/>
<newLineColor mainGridColor="-4144960" lineColor="-5197648"/>
<AxisPosition value="4"/>
<TickLine201106 type="2" secType="0"/>
<ArrowShow arrowShow="false"/>
<TextAttr>
<Attr alignText="0">
<FRFont name="Century Gothic" style="0" size="72"/>
</Attr>
</TextAttr>
<AxisLabelCount value="=0"/>
<AxisRange/>
<AxisUnit201106 isCustomMainUnit="false" isCustomSecUnit="false" mainUnit="=0" secUnit="=0"/>
<ZoomAxisAttr isZoom="false"/>
<axisReversed axisReversed="false"/>
</ValueAxis>
</secondAxis>
<CateAttr isStacked="false"/>
</CategoryPlot>
</Plot>
<ChartDefinition>
<OneValueCDDefinition seriesName="type" valueName="num" function="com.fr.data.util.function.SumFunction">
<Top topCate="-1" topValue="-1" isDiscardOtherCate="false" isDiscardOtherSeries="false" isDiscardNullCate="false" isDiscardNullSeries="false"/>
<TableData class="com.fr.data.impl.NameTableData">
<Name>
<![CDATA[ds1]]></Name>
</TableData>
<CategoryName value="month1"/>
</OneValueCDDefinition>
</ChartDefinition>
</Chart>
</Chart>
</O>
<PrivilegeControl/>
<Expand/>
</C>
</CellElementList>
<ReportAttrSet>
<ReportSettings headerHeight="0" footerHeight="0">
<PaperSetting/>
</ReportSettings>
</ReportAttrSet>
</FormElementCase>
<StyleList/>
<showToolbar showtoolbar="false"/>
<IM>
<![CDATA[m<X%;eEd"H/.&#OQ*#b9d7bo2`c=ZtKFTb+A$&`(D<rTA%Z2GUiDfN!a>t/2.bTu%(h4%e)o
L<Pk4bGbCS/rIo=`HDqY^0THEJmhB:e:8me_IhgTq#gdo#:=5K(j_^qcphje<du"4IthL0Yn
q0EOC5n&=[+?iZ+">Wcr_hE?VfE5"1K2f+M2=?\`9?=pRLN&$`93$!:/:$^lNLm*T9:EHpi]A
5ocCh\Brd9WM!a55\/]AStT2LgAWJfDjX8d0)U!*n#`cNqp_i_0H,T^iGPDg$.jG+]AP_B)M3]A
g:R$;b9dFG+VEU?b;cGe1.;\45i$]A5_+<L]A%u\]AWa`W)r["F?WlT1%oD$0l3DN$^-LfnE#t(
A?N7@h5WjqdpZn(\@C^A_C*(E^2fX7cm)?jgpM-XT*R>QRY4e2c(!uT@n#Hui_=^&0cVHsc=
H<E9m=\i;GL+EEBUK-)$?D5V"#ENr()tu7ZmppE;ib^Y1/lUi)'AR/]A,?%"L4:q)t&3rp4ft
G<G(,"_MHN6R_bT*H0-!-hH`2dJW6W>L7@s1Yb8t9\bJ]A[n>644X_D1.>gP!.p;4K.l%'UM:
!SKfjtKNf0.p.`kp;$Nf(pJ4+Tdm%fFUC;1HcVkLgUgr?ef[MKK(lq,oD)ao6,!E7i7(qC&%
n_NtGTJ;<n0B/WA85URX[Pa_!on8S.>?5AF7)FBG4FB;3pQ=r<a(NZj58EUgI4kT]A4CW4a,p
U/&76r,*6X4Ro3*d;jV2IfZa:CA3p3Lm0%gOgK4p#uFu?PE5KdF=#PV"r7@$!lF3*_pg=P[H
SC>?.NJ<mMHXPKcN_%:mNOC)L@;t(lDsbPG;*\6s,H]A[q\-JhCVc5j(*g-;9lUs-9<-k$slN
bK1IE+HHk7M^7#,</QOb1phB5*G<IUYe-Qqh@jnJ\?o5ZQ^83<fO$rsAI5(F5WFM[&)&UOf'
p'N;&Pcso>hi,.!J"5rS/D@OU(,2/&Ysh2gs1@MG#D'J#EYaLb&;55DF4>Un&cnLhS/`<iuQ
mZJRu,:?T+V-'aEuGD<;4%oIl":iPifIoU$#8F^KF<g8mnd-9!b`oo2`Ec,P1-q+Q%mKu-.@
&.[,+0i`*+GB_@Z_^3bUS$!@&bM`XAZ$.mf<&NW/m7\mMjNgNmr<U:\.Sn?^cC14Y9c4sXco
*23`APA1jHBN_f;t"IUC9:\AbjX<6;6b:FG+Pj?HuQk!*(2ikJ$aF`<Y,FQU>?+Y5hRr*l=&
NC@ObK,QWIRmYZ&KMFg)ICPoWV&0om5EE*>b;#`:=CD,uY$ai)7k%/0T`!Y&A6^^A%L=QOKP
m@W4q^8<=6e&KN0g3j^o0ri@;<s.@ps,!8p2jVO!bUdXO'W9,,6@(8oPr4d2otZ#V2>X'6rU
+8%JWKr56le#f>(^7l;1e!'`pEAlQF%'Ro.HY%&pa=Aa;L4l_FWYBh7tOpTrra7fpa4H;89C
dJEh$9#<U9_^mX[&VHN&+d/6MP,'[mK%LMXksi"?rXFemhi1s#J"d(u6_`$SGk'=I@Qq(d/X
9[1;C5FqO3T;,b.k/087.$Y&*]AXPi5RI-",K11n(:!K,H0`>"6olV<JN3Ka-rm72sC0ODR%*
jXpgB5a#&E+)]AI2!$"=+n]Af?n!]A:f756+U>XdLCL#)dsM1!]ArJnp88V/E24&EqTd^H,^2NcQ
$C#s<@tF1NMC>uCnjR+"B\iHp#F%Gct"bHVNYLqO-)?QUarO]AC4`&aS#!^E)d+0i?Xd:?n@1
*a0h)Fq'1F::_.P[E)(\&UM/bmKgXq@UC1IihSK!7%,FZ^UEpAohJSujl\eXRdZCn3="@+1O
bd*9-2N;P$UoB#u)%g*3ZYOt+cX&#5RfFh`a[:7A#:Zgc^MAnH:SC.pYap+:?&$E2kjn)7AS
bQL.434t[="Si'<'5\@p`&p\g2idTF.NkNU<U3nc^`t#=_tD]A,jr)<]ANF_`f,nj?>%cDjbu=
C(`W":rHYM&mD\OZ]Am[:%nd0<TU3k07/@RG]AkmHKrgHKodY\D?mU=:P!e^/F2R&iNB`K9[F(
U)*;q6ZrJIkAn5@:6Q@%Ag_38H*&Silo*qhc>M+F<Sl?=a&O__2U8P#"Y"<;:Yf]AOtsj6AsL
%+;g-Wl-1S=Io8X3>X<b,T?/lDGH7T?tfD'h>HD7N7]A,=i8$JP8Kr01tV#*OJ6k.,&S_+G`]A
K;+YU_W\k#K6'I%:[Ife"J1+Q/)#6JLRj#dV$Zf\fR=H5k<0V@N*4!@_<'PB+=jM)XB:HO&L
+7$3sR62CX)eAT]AoPN?A^$m_-?o/ls!$sIiu,XMfeN\6fZ*2)j30/ZJN/#9bXQ.VU3#@f"/"
<`a)8lhq>3)@d]AVU9BJ<DB5YT2S"dg'KO+K/1-X<%A7g"Gpub$k[bUuY.>8>aIc3!2Bg!M?B
UkYqrHoBB-9.ZO'5"ZmPNFN@H6mCF1Xl%h2N6=%_$))JF-#'=^pTb"jc%#X'ihP2$]A1-g8sC
Jgqj=NTh,a3hXIldOL[F>r7>QrVWFFI9ZR&at?h@^78\P'LX4Ook$!>^['k8KEaRYRE4;u]AW
Z@L0McJ''(m<T<%*'%[fI,O[G79LZl1[LIo,+ko#osh1&:7aGViMj6p^NBsn^<a##T<l`e5c
;]A2fAR2$6aaSm0(#TF%\?B1#JE.B9$9NJiYtX/nBs(5[_+`?!k^';Ym[")c\CXF1XN=,('8$
N4"f&HLO.3reCkTV`t,#$$,,1LPir5HL=sTnZLiN69ke>!,T?qW`r)n$M&>0,^]A/iWdc1tUS
u634<U8ZF+*OsURh>k*`bfX(g\fj_7jYLuiZP<E[@1b=T"TsfQRbf]A#m4.-aD$f/4BGY0MLu
YPZ]A=SEFf&).g5Gqa<2oIpdC="-G%#!.KQkf4dk,!nE.6Nhfsf5$a_Ica3."kWWV,nVaP2PE
5i0_Ed/7lk'\i=4oPI:/mS)3UMp0Lg+qO&7^)@/KcLEh9S$SgGbakYccYS>$Mtc"`':0JB\I
gs[/(I!fT9+:XG>raJf)Y8I\mf?hhPY@DX,/_/_*+g7.6dWZ5h;39>gn:iXTYh\o<!2-1.M&
Z]Adt3=Y`kX<c?=F^]A9:S_hZU$0(_]A_aqU&M,(F4_UpqR@"iQ*ZjZ";JD-(H$<F[\G+O'B=p;
EF_>JGb!P_l,DIA?4o#*Lo2AP4mg_#5K7AA(l_Y<5a-l`A1=TQj\n'L4Lm!mbL5?V%H`'MGh
;lbZ>nB-E1(e9@Qo9iZL6EJNk!&\iSB+cPq&7T/&`%n@0IWU\([!"Ghi!=dPW$<E*#NqBL'+
'r1TP#0Ot!"FZq+q*X7Tg$9UZaX."EN`]AM,Mt\f?bcjW14KGuj1F$rDnFJRgPe0]AFoln"pqm
lY_-&';M2[o,]AZ4@0hnY1,0!N4%<E#[`bp4M6K-"IKqJ`MsF_b,oh@.\iOdE5jYjQkqe*e\+
+`[&*mQ>fp:NAD7Y663/Mc8:Gc<S9aaZ8\ueiOFV2+_Q#NlE(=)]A:6!1,(*.e-uq[S0UnB5+
muS-8b;,E7^(?m%b*6`!TFG:c?(cA<[J(3IL8=49SQ[&oG/shSf2i7#&5;:9rZ^]A:m#@8J_a
/<=Vso6D`ENLrS-im0WiqZbJr,7T<fc!(B?P9GFJnjotfQioF4d<bHZ]A&/HM\ob3uf$aiOR&
KD.R%OjWqi@d=Rk3am'X0bX0I9n)IlB[mmO9I3/HE<_;?$/S*h"GrD5j*5I=Nf'2p#L95&;7
XgQWFKe3k#b02hc8NGVuLuVTLeH]A6L-dcIhFT%N4aROq)aPljeN#Y'7+JV[d.gO2`ITA@L!M
\F!JP7^Vd/,r&0klW3c(8^MBm>Z;-G6Uq11464D.G:V&1Rf]Aqi.6^9^$2^7h3/iBQdn1M89S
l#ed=q8e'Ig>u))hGA12u;1f5GXF$`+MosO<d2XhR6H]A-Fq0#J1`(KT\sdcq_Sb??oWGkh)Z
L1Bl("Q[a38;d03o;H#+K9:du>aAe4d&r+jg9`I4MU1aIM%,o_',$OD\Tp>h?Mqkd:TH09g.
0B;@EQY9XZ7-3S@>a*0"%2nR4o_ln'r?a+J`4<4Y*9:<ca%=?/#D'(SSj3s>)Oh1n]A6?Up?S
>$A(_fpuHHUd)q93_qRk/4@aX*,cBI&b/`YMCAV)bKD[6EF<"KJ\JR8;`Oo6hst9U61);_pl
a(_7X/h2HboY[hl4;e6+K+:Yg)b!`:h!24&&p=6uo+^oKBg0NGdonMqVJ&#4r>V1C7NN0_M:
qc!W7-kh$(GnB3nF$0qj7*"FII/WjVkhI!XduXR7[a]Aj(*nXG'\n#BBP&,n\Sc08#3LND\)n
"uF1;DZ1m3;?2BLZX/\BEupiJhT(J'NaW,%m(Yp/TQN)g.Y&d?(l(D3""R`YfZjc(nZWlA%.
=*1qd^u$\(4OX1dH/1jg,L?)O+<F_7aZ4&&bYdF=-n(5]A`rWmS94^<PQ;IQ9ba)h2YT^g*99
+cdVN(*3lM#'f7o.c,`;r87VFGq\#6RPoV7koZT0%S%%b)A$Kbu30"FuOMe.ps!5NX5!nk%$
S9gnZDSGR4b@`N\0bqqG7l30:&[g.$?X._W5>TtC'fXRB9A,6&S*R#C/iktHQY_#c%PPZrHS
HUJse#)E_Or96T(Z<8/p??^(#!i$R42&8W+IZP33hWF_BEr3>+ooE3s0940?WK-nDdA;!4(*
aIp;4)2J)jM#fM)JJ""q`YH#m5im,^X!c7MT)"2g#Kf?;.d7F3,J3@pnpUeU=/e9<K@N^F]AS
TMJ?b3E>*#oif\37Z@)r7tAb[:`1i?7'7%@cZCZ/(Ljk1.?Re+Ce?;d0V0hR"'dQ'eha]ARFn
W(&B#6op0J^jb?>B#id'c@_ZG;1Ykg^>ViBr#+QeW*<HoR*FMb<t"@U1(l_m[9,dE$ENs)%!
3QIA4*Ipl-5leg5++*!=R-@\&Qf\=^t"1_N[$"ZoOhKW8L^XU(LPc.b9)jF[DcX_?tKdk3QL
r19JD7cJ<gO?/oWlK]AZ(V&P&\V0ZRQcfM:d.r=p8b4-+qB/.Ml0h%mF`..UAKrM+/\4-95[(
91\o_t^a0;Is9P6n<*%PMI[S1DHgh[:uFJS^ri%TjF8]AQ7HW((f/a[EO^CQ?!,,C-:R)RLk>
0<8Qi_8(`>lAet^*VO4UGcgT(UOs*7QASSVk1/$.Yo^Q9K0nHO>mpo@=D12QQjW<2]A'[54mO
E4`X9rMJOEJ8r=5CYQb%H;X"ADNJ61iiPd*o+Zd=9'@)OcaWK0m'9oSM3C-2,tQc+THLB`tF
WFWfTB[BHu,L$U,sQ/EJC5c=s&Q[HYW^)54%1RCQPKBp(Wa(aS9+@&bO1LM+]AC!P1pU<\ZX_
nK[lTp8IZk4P-tbCG!E:p_UgUJ%Vp9]A+UfkN*uF*Nh<OL_8='Pil4&97,68G=cqoSn]AkSdX/
9=g[jhu&.5G)R0@aW/`cdSZ)\mFG+31g2oMbOf02VW+k=k=rViPuTXM<gXl1,h>.eO[c\,-C
$$a;=rG5=RFqndlD+QOTSoHF^>q)OfdPGjs-.ipFT=tIDqGn5\dH:E']A/BLbN;51oN09&c7^
6b,C?E^dn%7*q*OjH+Y[^G(RQF8gNZJ)bSa)b2]AN(MAP"u_d-T`TEJ^3=bl5n.B+V?-E:3eN
$OQ>P)-T'*#\&eW"+$iZ7%dPmgoUOmVZ>EJ-cRtcfQ[rEUg"siWnpi<YGKZa-aK%^Y+uQ`$b
s.*?#2(sR->3tHR.As\8$BL,r^s'NFpbW2=(&KaBr1ZKML3CrbKmQcJ#:tG0<LBcdQ8<C*Y?
6fNbmb:3*>camG.E-/5f**)r"$6\OaqKB&I>&f24K-db1ok_.[eOGHl(uhmW3`^M#RdQ+e,)
lbP7=[f\%oVbL;*N#R(P6<f9Bi<`!;[0";fljo>G"t-=\mTd%uPnAusq&>,%UD2qcQ!qo(f*
g>3_C@p\n_!"AJaJY'C]AQKi5a;/VB8="5q1d;2"Wt(#Yl=J,4!*%n^9_tdr5P<ri82a1@ob\
AZW8.N4]ABSXadN80PGfQ2FAi6*pjNl'itd8<adr9I8-eDs#FDF"PuUN>Q3`EJE9HKZV58iY)
:Dfp&!&Ij\h>FgJOU1=3Cg,%lO,]AUf7)4@)@m<dUlDX=&8r50f1QiJ.$,Ra.:RCQ7(:T;euh
=]AYPEm+`]ARfrW3Y1M2(qVKXRVRJBU4>T\inEL%ihB-ijZBATqMVR`<8UXJXu\]A4WbaS#Z^/X
1M32lN'm&dagj_4"t=6XjSU=b=5V6$bJN<=<hX;WFi:&Zc6]A$I,.3!DI``D%%hJbASj(9oiR
jnt(IBT>64<T;lM^7ho,Xi455C)"DJg0F<`mY\4mit`2/\(fHXO$f>Vo2$QC8YB&1tqCd@k%
4A5%l)ARUdgFU#cN.\pD>[KgOq$`i"J#nTF3lk/o([)c0giOh@IZF8&mrVWCo!@IGGV*I:Tn
L@pQ7j,uIN#=&6d-\?U4'"/72<:f^(t#>$$]Aci#ftEe3r*`k=L60h$\-`KB'G]AMHU9LdNPs?
C!7gf4<Yh@pe1#bc!p0Z+A'Sr5KUC?6[,772e1T^cGe4gU]A8Q%M=?0SuFp5M[K2kCpKd'JE9
M]Ao9%[m_7/Fn>aI!E57!6/M;eo:]A1^@?j(eP]AXSP.%b*\[ojZsl;ST\G'"!M1D`+0P<1D3G:
2qIdeLP,Kc[e,hupF`^-*a'O(<&&k^h>Om>]AkM@RAgk&jm7"T+,)8P;eCH9bXuLXont:jESQ
K32*#I`LXrr@Cf\Df8:&.$B'.QE+>if(d%O9W"rq?BL>JY;$,B4QfCY.4KR$+m`@akaGtD$Q
M+hg.Qs-iMf,E)53a3t3g[bW.t''K>Ls*(:ho`7$s@df8?;-IRpoF_iNgC/!/ONb`-H^$4eK
Ge%nYBJ1Og"]AfZ9=PnWu*j+(Xd*=@3/s>N2iW1RCTVn.X>;\DbR+jTc,hE(/R:.3<kI-e_da
G-J:*BICQ!'<1kaXbjgmG:)`ooRk[7FLGX+oo+b?\jO-T-M&kuDU[:aM22*m'E8pPO\h4rl5
8Q)NFaotJ;GQtgL%l(ofS\_Ni86_OcJ><Cg5[4Lt%[YM6h]AUUngerY\g@\n78rD%ODUVHi^;
@hZe<6BG0s\LbpuR`#M*aHfonq:rR<,h/qt4!G8sR43VJQUEAW82L<KP7@+n^+3\/4#Mc\tk
^TD?B8;g1Y\Y]AZqgTlu&,SSts)/<T#Mic2dW%iYT=M*1\)%auc"t;*=Z[U*5Afu/'9spu:A<
<"7mi@^'jP1H'?-G7JbEu7G48>%:"L`%Ya9)Q&=JiIU=D4_9ZQYL[0@Ln$OF>399$(9%J[jQ
lGgQ*[IYDEj.?U/5#<OmO8$Q%,;\4,KJ!l3,!UiZNpX;S"&`hfUjqCVIbuqHhQ&j6!>qQ,cS
(%2-YFTp5@(sSc+dONkI''WaC@9\(3f??AX*eU)\D9X&G>19^!%\3O^8,<\1CBt?GA*[TWC.
keM&l=f_&cEY%$LIIbq/^)!ND^i*>=5:S1<nB$/8S(S5Kt.GW6sUUI[8i&*_Mi^V?F&,[2,o
O641#=blLbUjp6HJ7@S[e1B5lZiHEC&WJ.AGeXsX.=qC=e+AEW,cP5g<omkGhLd0rZc*k&Gc
o0pd_"Udf'9%^U/Ilg4)^Ss,6/ddRF)#0`5kNKR\Y`/kC0aOE;_gCqFH=@Vu(2qZ#bmf4&L1
.o5W:=;,o@B^mQkNC1AikDmj.=oSV&c(cOlldK/)$;8V\<SX[lNgOaTs(;Up?GR@_IuI(@0`
4R5i)rXlmVO!fO6KV22#<)mAsfAq5at0cf6:bfD>`T*($?qL)khuoSPMD^./*)cRc,pmL%=F
.I<UY\b;hj>,$2pA*V\,hNeIs1M@H^<)<bS,h<a.I%*9m^)T$8*3TF%S=G:EQ"jXie/;g@Go
I->)H<*DQI3CBbR8YbUTiaLhJ?e9JD,_29Z:H*^P^NLh^9s:E\dbBBmD]A#^n(C'4f%B9K>S4
O$qO(An0E'Lbj2n"/-/j-o%s:q2"9$u@.(%0GlLT\BBUl)Q>V%i4d[L#oJH`08/C3tN`1Dhm
COC"R(l<#k9R?j%/L9Zl;3H1teGFu/o=[>u5TlOKL/A_bQVaKV.,7664aZl-%0BbipVN7sgr
A5U`K,*WB(^pG&ZUbulb@i]A><U?[\EZ@_$ORSHiUQ$/IPgOKLsUQT9B@]AF>K;jY.pY5'YKu\
sQ]A`ifMR2>RCiZh\b076pT6@-biMhBuq/I#-dR[ek_YhPc(`?%+KB"Nn.'/f,PrJC3gFp7D%
ga+GnC*8tE*o#deSnUhr0buG>19b'a,O"]A)s)M:+jCBHr`F-)=m8'dT0jJf0EJ4_PAK#<MkJ
*G%'^Vn=UhoNLB@['F,S"%1R\6F"cGmR\L:;J1)H.cA-C&9YFKdN-b]A;7J``6*mj9ekIn#sn
$R$`DmS!:jgn,.uf2TYPoHFt[]A2&&EZTW_m+Yb^rL%]A%0b&!!I[;MM4r-f2$1,=G:"gNAL8^
"Q3*(S<[KSgpa<Z;ts@8s4($YI#CG[(!iR7pDGp3I.d8!<`qj=^N<_Q@VsP(!"5fmL!.K!Gp
Na,-F-[kYDK)enjm<U.]A[\%0+c9O2:c0B@s^F/sWE[cibhn5[Ws3io/e.6\_nF?/j]Adb!!*G
H`HS4MI^^h>]ANM'/:*(cWBa4]AEB5Z+bquh8gS%)h]AjLR[2ZfY6?..7H^uIj=-MN/h*NcLa\'
2@GW*93)B_ue1,6n/H=a+X=k,[<Sp't;(pH-+.kU-?HZnQC`/!!Q_-BLDcaG^aG%qaI;I2?n
q+TX&q!okYg!=1[b4=?1LP@/5pmqaXOWD0/kM4>=Ab/saq4=sMPj8"`='jZ_^&aUha0OFh'[
F@`17.t":ZN\fA[8(f`0-N5CZSlpCH?<L0q.,DLT*5;ALHS7QJceZI`&U<QuM<4V\-X"-W&+
aS<^]A$U[g?>`Ll2#.(%.`V$2=E<K\2*ihK!q\6.&JJ0O7J+X,oWIR;iLA=1t1h626')2.>>8
lO`=,GO5#6OBb:QZ)MI:$hMk"'j9S+UTt[$$S/0GVArFn/o-%j?S=d465XqgekSU3i@8/PL/
\XX/F1aJ)$oK]A>9OF8U)S5e=g_ZUA<23?B=s57FO(mbEFfii0P'_<BJNfI6If'V::ru0_i?W
etd#m#20!-lGEAJ[8gNF&^3P'<?t+85A(nW>O#Sb'4+.%^2hV+'!<mOi-9)J0\'iFa^m-U'p
hkIhN=G;!T6sZ7k.jcMiN)df+AX&HMZVSa^C:aTb1,%+ijJ]AQ2DH91=Oa3,Dbd-+]AkXSYB@K
j\ub4#3MfI`*\*&Wcr[LQpu5j'd_q6#Q(JktqjX%:#CYp#j!FOf2c(ApjXU^'EftJLKipp$j
_fq"=b0PKBIqFJ9n?#Dou.0YFiMhi^[!&1.*q3;Q9QLdh@gP1$]APdkldgA2/RaO6hMrtf@%.
[O[!KZl)4MrXmcSO^J-3._'<a+'[U##`]A+BCBK0Uu4!oXcMB;)jWJ+eQB>\3$'@G)?cnlWjE
#NMos*AYG>`NStAN[Cij)NqI$',)Ea!O$?U94/I462D#),o.8b9(`"BWY'_]ABG_DN-Vj]A/r*
c52P$$:'^,k%b?&*TAf7*7q.=UsYOVD'.bM+`kRV=YeGGpG(/-oAti^RBGID:^:/mNsMcH\E
6k_Vp$++(*&^IQ)b_qd7/421^n,O%Xc`i3&::#;,b_!t<<qc=uK>V&E:aTAH/?hdegj,![-j
\$_ckNpa,G`A>*3qQCRGLVP<f$T3fAo78cH[VJ1-W&,%).K[!brQ05cVl[p,3m@*ZK.o-S[)
.J`mhu535a_UHW"g,8C+q1s7DAO>`NJH_<?>-orZnalLmOR7=/6.H[`?l5&AusVUPhAkXeaV
"Q^tfqXb=<H(8tgDc'<nqSP[&[3:Bm5%J%G_SM4XXrEG\(sSeI&N:cX0pfFPB37]A<:nK,B.3
Rg^Jin(*[Ctp1M]Ah?C)B_H:?T3oNQKQFG%t[1scidPXL_d[=MS/!S.k>pb&:#5IlD56LB".q
hGk$C_LQ#!uFYcU8>%T!>4bOJ9!WmBB1KY)SVM6r!FH>NQ0[hoh]A\"bfoXFgKH++h/<+V(%8
jD7\0e3mP2E(A3=FQj/DGJP]A*GoJo_A0Vd%Oj'e)b\X--Y"iU:/cVCks2>,;0`eDIQCQRPdf
YD?%jZBLK,oVX4+;IOd7'A3S,q"(st69-k9Y6=Ac`(9jA;f3FHoXZ"TjIYfG(a)7o;MX(H18
Dju?9Y;9h4K/g8dl8u)1m'g7GF#P5R'c1P!A%WWSNe)NEVC@pO]AakB$YpdbcBFfZ5q$]AR>LP
@KaiCG(I[Jil`$RL6jO*<s&VRsE,U[69g^@0W"PY'Nj"!E)Q.M3/Nr&Q9c:._\5aF&(%fR:C
%jq27sG?FD9bQWq!*=+=0\ISVS*ct\Y/f@%P[Q#iSR"Lodi.[Dd5&`b.T^:GRVs.-TMcAJ5&
c[/Khb=&rqphlr5CU\sI"8nN-J;gtl>Y2D;Duqe0q=]A1A=?d*+TB()JlT6EEl*%EA[EW@@[F
!^if6kpm^U,A+BU^=03BgE098J-8Rh.QWEA#nT(7\CJ)G,CV?.#?dEtSU%iP!:Q+q35Y8#u0
II8%SilPp(.I[0>Klh[ZAmoD]AN)fRq5JY'KeP,+#]ADchDBFbdiruCgG+d)u/^$FON5VIJ/8h
=WdrYTjlSdZF%3!G[l$\ZB+5:^.'XX/.T!ViR48`sRV"Z-2Y#6~
]]></IM>
</InnerWidget>
<BoundsAttr x="425" y="38" width="540" height="472"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.Label">
<WidgetName name="title"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<widgetValue>
<O t="Formula" class="Formula">
<Attributes>
<![CDATA[="   销售代表每月销售"]]></Attributes>
</O>
</widgetValue>
<LabelAttr verticalcenter="true" textalign="2" autoline="true"/>
<FRFont name="微软雅黑" style="0" size="80"/>
<border style="1" color="-2434342"/>
</InnerWidget>
<BoundsAttr x="0" y="0" width="540" height="38"/>
</Widget>
<title class="com.fr.form.ui.Label">
<WidgetName name="title"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<widgetValue>
<O t="Formula" class="Formula">
<Attributes>
<![CDATA[="   销售代表每月销售"]]></Attributes>
</O>
</widgetValue>
<LabelAttr verticalcenter="true" textalign="2" autoline="true"/>
<FRFont name="微软雅黑" style="0" size="80"/>
<border style="1" color="-2434342"/>
</title>
<body class="com.fr.form.ui.ElementCaseEditor">
<WidgetName name="report0"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Margin top="1" left="1" bottom="1" right="1"/>
<Border>
<border style="0" color="-723724" type="0" borderStyle="0"/>
<WidgetTitle>
<O>
<![CDATA[新建标题]]></O>
<FRFont name="SimSun" style="0" size="72"/>
<Position pos="0"/>
</WidgetTitle>
<Alpha alpha="1.0"/>
</Border>
<FormElementCase>
<ReportPageAttr>
<HR/>
<FR/>
<HC/>
<FC/>
</ReportPageAttr>
<ColumnPrivilegeControl/>
<RowPrivilegeControl/>
<RowHeight defaultValue="723900">
<![CDATA[723900,723900,723900,723900,723900,723900,723900,723900,723900,723900,723900]]></RowHeight>
<ColumnWidth defaultValue="2743200">
<![CDATA[266700,2590800,2133600,266700,2400300,2209800,190500,2209800,2171700,457200,2743200]]></ColumnWidth>
<CellElementList>
<C c="0" r="0" s="0">
<PrivilegeControl/>
<CellGUIAttr/>
<CellPageAttr/>
<Expand/>
</C>
<C c="1" r="0" cs="2" s="1">
<O>
<![CDATA[本年迄今销售]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="3" r="0" s="2">
<PrivilegeControl/>
<Expand/>
</C>
<C c="4" r="0" cs="2" s="1">
<O>
<![CDATA[本年迄今预算]]></O>
<PrivilegeControl/>
<CellGUIAttr/>
<CellPageAttr/>
<Expand/>
</C>
<C c="6" r="0" s="2">
<PrivilegeControl/>
<Expand/>
</C>
<C c="7" r="0" cs="2" s="1">
<O>
<![CDATA[差额]]></O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="9" r="0" s="0">
<PrivilegeControl/>
<Expand/>
</C>
<C c="0" r="1" s="0">
<PrivilegeControl/>
<Expand/>
</C>
<C c="1" r="1" cs="2" s="3">
<O t="DSColumn">
<Attributes dsName="ds1" columnName="factyear"/>
<Complex/>
<RG class="com.fr.report.cell.cellattr.core.group.FunctionGrouper"/>
<Parameters/>
</O>
<PrivilegeControl/>
<Expand dir="0"/>
</C>
<C c="3" r="1" s="4">
<PrivilegeControl/>
<Expand/>
</C>
<C c="4" r="1" cs="2" s="3">
<O t="DSColumn">
<Attributes dsName="ds1" columnName="preyear"/>
<Complex/>
<RG class="com.fr.report.cell.cellattr.core.group.FunctionGrouper"/>
<Parameters/>
</O>
<PrivilegeControl/>
<Expand dir="0"/>
</C>
<C c="6" r="1" s="4">
<PrivilegeControl/>
<Expand/>
</C>
<C c="7" r="1" cs="2" s="5">
<O t="Formula" class="Formula">
<Attributes>
<![CDATA[=B2 - E2]]></Attributes>
</O>
<PrivilegeControl/>
<Expand/>
</C>
<C c="9" r="1" s="0">
<PrivilegeControl/>
<Expand/>
</C>
<C c="0" r="2" s="0">
<PrivilegeControl/>
<Expand/>
</C>
<C c="1" r="2" s="0">
<PrivilegeControl/>
<Expand/>
</C>
<C c="2" r="2" s="0">
<PrivilegeControl/>
<Expand/>
</C>
<C c="3" r="2" s="0">
<PrivilegeControl/>
<Expand/>
</C>
<C c="4" r="2" s="0">
<PrivilegeControl/>
<Expand/>
</C>
<C c="5" r="2" s="0">
<PrivilegeControl/>
<Expand/>
</C>
<C c="6" r="2" s="0">
<PrivilegeControl/>
<Expand/>
</C>
<C c="7" r="2" s="0">
<PrivilegeControl/>
<Expand/>
</C>
<C c="8" r="2" s="0">
<PrivilegeControl/>
<Expand/>
</C>
<C c="9" r="2" s="0">
<PrivilegeControl/>
<Expand/>
</C>
<C c="0" r="3" cs="10" rs="22">
<O t="CC">
<LayoutAttr selectedIndex="0"/>
<Chart name="Default">
<Chart class="com.fr.chart.chartattr.Chart">
<GI>
<AttrBackground>
<Background name="GradientBackground" color1="-52" color2="-1" direction="1" useCell="true" begin="0.0" finish="0.0" cyclic="false"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="0" isRoundBorder="true"/>
<newColor borderColor="-71254"/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<ChartAttr isJSDraw="true"/>
<Title>
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="0" isRoundBorder="false"/>
<newColor borderColor="-16777216"/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<O>
<![CDATA[销售代表每月销售]]></O>
<TextAttr>
<Attr alignText="0">
<FRFont name="微软雅黑" style="1" size="88"/>
</Attr>
</TextAttr>
<TitleVisible value="true" position="0"/>
</Title>
<Plot class="com.fr.chart.chartattr.CustomPlot">
<CategoryPlot>
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="0" isRoundBorder="true"/>
<newColor borderColor="-203360"/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<Attr isNullValueBreak="true" autoRefreshPerSecond="0" seriesDragEnable="false" plotStyle="0"/>
<newHotTooltipStyle>
<AttrContents>
<Attr showLine="false" position="1" seriesLabel="${SERIES}${BR}${CATEGORY}${BR}${VALUE}"/>
<Format class="com.fr.base.CoreDecimalFormat">
<![CDATA[#0.00]]></Format>
<PercentFormat>
<Format class="com.fr.base.CoreDecimalFormat">
<![CDATA[#0.00%]]></Format>
</PercentFormat>
</AttrContents>
</newHotTooltipStyle>
<ConditionCollection>
<DefaultAttr class="com.fr.chart.chartglyph.CustomAttr">
<CustomAttr>
<ConditionAttr name=""/>
<ctattr renderer="1"/>
</CustomAttr>
</DefaultAttr>
<ConditionAttrList>
<List index="0">
<CustomAttr>
<ConditionAttr name="条件属性1">
<AttrList>
<Attr class="com.fr.chart.base.AttrAxisPosition">
<AttrAxisPosition>
<Attr axisPosition="LEFT"/>
</AttrAxisPosition>
</Attr>
</AttrList>
<Condition class="com.fr.data.condition.CommonCondition">
<CNUMBER>
<![CDATA[4]]></CNUMBER>
<CNAME>
<![CDATA[系列名称]]></CNAME>
<Compare op="0">
<O>
<![CDATA[实际]]></O>
</Compare>
</Condition>
</ConditionAttr>
<ctattr renderer="1"/>
</CustomAttr>
</List>
<List index="1">
<CustomAttr>
<ConditionAttr name="条件属性2">
<AttrList>
<Attr class="com.fr.chart.base.AttrAxisPosition">
<AttrAxisPosition>
<Attr axisPosition="LEFT"/>
</AttrAxisPosition>
</Attr>
</AttrList>
<Condition class="com.fr.data.condition.CommonCondition">
<CNUMBER>
<![CDATA[4]]></CNUMBER>
<CNAME>
<![CDATA[系列名称]]></CNAME>
<Compare op="0">
<O>
<![CDATA[预算]]></O>
</Compare>
</Condition>
</ConditionAttr>
<ctattr renderer="2"/>
</CustomAttr>
</List>
<List index="2">
<CustomAttr>
<ConditionAttr name="条件属性3">
<AttrList>
<Attr class="com.fr.chart.base.AttrAxisPosition">
<AttrAxisPosition>
<Attr axisPosition="LEFT"/>
</AttrAxisPosition>
</Attr>
</AttrList>
<Condition class="com.fr.data.condition.CommonCondition">
<CNUMBER>
<![CDATA[4]]></CNUMBER>
<CNAME>
<![CDATA[系列名称]]></CNAME>
<Compare op="0">
<O>
<![CDATA[计划]]></O>
</Compare>
</Condition>
</ConditionAttr>
<ctattr renderer="2"/>
</CustomAttr>
</List>
</ConditionAttrList>
</ConditionCollection>
<Legend>
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="0" isRoundBorder="false"/>
<newColor borderColor="-8355712"/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<Attr position="3" visible="true"/>
<FRFont name="微软雅黑" style="0" size="72"/>
</Legend>
<DataSheet>
<GI>
<AttrBackground>
<Background name="NullBackground"/>
</AttrBackground>
<AttrBorder>
<Attr lineStyle="1" isRoundBorder="false"/>
<newColor borderColor="-16777216"/>
</AttrBorder>
<AttrAlpha>
<Attr alpha="1.0"/>
</AttrAlpha>
</GI>
<Attr isVisible="false"/>
<FRFont name="SimSun" style="0" size="72"/>
</DataSheet>
<newPlotFillStyle>
<AttrFillStyle>
<AFStyle colorStyle="0"/>
<FillStyleName fillStyleName=""/>
</AttrFillStyle>
</newPlotFillStyle>
<RectanglePlotAttr interactiveAxisTooltip="false"/>
<xAxis>
<CategoryAxis class="com.fr.chart.chartattr.CategoryAxis">
<newAxisAttr isShowAxisLabel="true"/>
<AxisLineStyle AxisStyle="1" MainGridStyle="0"/>
<newLineColor lineColor="-5197648"/>
<AxisPosition value="3"/>
<TickLine201106 type="2" secType="0"/>
<ArrowShow arrowShow="false"/>
<TextAttr>
<Attr alignText="0">
<FRFont name="微软雅黑" style="0" size="72"/>
</Attr>
</TextAttr>
<AxisLabelCount value="=0"/>
<AxisRange/>
<AxisUnit201106 isCustomMainUnit="false" isCustomSecUnit="false" mainUnit="=0" secUnit="=0"/>
<ZoomAxisAttr isZoom="false"/>
<axisReversed axisReversed="false"/>
</CategoryAxis>
</xAxis>
<yAxis>
<ValueAxis class="com.fr.chart.chartattr.ValueAxis">
<ValueAxisAttr201108 alignZeroValue="false"/>
<newAxisAttr isShowAxisLabel="true"/>
<AxisLineStyle AxisStyle="1" MainGridStyle="1"/>
<newLineColor mainGridColor="-1184275" lineColor="-5197648"/>
<AxisPosition value="2"/>
<TickLine201106 type="2" secType="0"/>
<ArrowShow arrowShow="false"/>
<TextAttr>
<Attr alignText="0">
<FRFont name="微软雅黑" style="0" size="72"/>
</Attr>
</TextAttr>
<AxisLabelCount value="=0"/>
<AxisRange/>
<AxisUnit201106 isCustomMainUnit="false" isCustomSecUnit="false" mainUnit="=0" secUnit="=0"/>
<ZoomAxisAttr isZoom="false"/>
<axisReversed axisReversed="false"/>
</ValueAxis>
</yAxis>
<secondAxis>
<ValueAxis class="com.fr.chart.chartattr.ValueAxis">
<ValueAxisAttr201108 alignZeroValue="false"/>
<newAxisAttr isShowAxisLabel="true"/>
<AxisLineStyle AxisStyle="1" MainGridStyle="1"/>
<newLineColor mainGridColor="-1184275" lineColor="-5197648"/>
<AxisPosition value="4"/>
<TickLine201106 type="2" secType="0"/>
<ArrowShow arrowShow="false"/>
<TextAttr>
<Attr alignText="0">
<FRFont name="宋体" style="0" size="72"/>
</Attr>
</TextAttr>
<AxisLabelCount value="=0"/>
<AxisRange/>
<AxisUnit201106 isCustomMainUnit="false" isCustomSecUnit="false" mainUnit="=0" secUnit="=0"/>
<ZoomAxisAttr isZoom="false"/>
<axisReversed axisReversed="false"/>
</ValueAxis>
</secondAxis>
<CateAttr isStacked="false"/>
</CategoryPlot>
</Plot>
<ChartDefinition>
<OneValueCDDefinition seriesName="type" valueName="num" function="com.fr.data.util.function.NoneFunction">
<Top topCate="-1" topValue="-1" isDiscardOtherCate="false" isDiscardOtherSeries="false" isDiscardNullCate="false" isDiscardNullSeries="false"/>
<TableData class="com.fr.data.impl.NameTableData">
<Name>
<![CDATA[ds1]]></Name>
</TableData>
<CategoryName value="month1"/>
</OneValueCDDefinition>
</ChartDefinition>
</Chart>
</Chart>
</O>
<PrivilegeControl/>
<CellGUIAttr/>
<CellPageAttr/>
<Expand/>
</C>
</CellElementList>
<ReportAttrSet>
<ReportSettings headerHeight="0" footerHeight="0">
<PaperSetting/>
</ReportSettings>
</ReportAttrSet>
</FormElementCase>
<StyleList>
<Style horizontal_alignment="2" imageLayout="1">
<FRFont name="SimSun" style="0" size="72"/>
<Background name="ColorBackground" color="-13312"/>
<Border/>
</Style>
<Style horizontal_alignment="0" imageLayout="1">
<FRFont name="微软雅黑" style="1" size="96" foreground="-1"/>
<Background name="ColorBackground" color="-13312"/>
<Border/>
</Style>
<Style horizontal_alignment="2" imageLayout="1">
<FRFont name="微软雅黑" style="1" size="96" foreground="-1"/>
<Background name="ColorBackground" color="-13312"/>
<Border/>
</Style>
<Style horizontal_alignment="0" imageLayout="1">
<Format class="com.fr.base.CoreDecimalFormat">
<![CDATA[$#,##0;($#,##0)]]></Format>
<FRFont name="微软雅黑" style="1" size="96"/>
<Background name="NullBackground"/>
<Border/>
</Style>
<Style horizontal_alignment="0" imageLayout="1">
<FRFont name="微软雅黑" style="1" size="96"/>
<Background name="ColorBackground" color="-13312"/>
<Border/>
</Style>
<Style horizontal_alignment="0" imageLayout="1">
<Format class="com.fr.base.CoreDecimalFormat">
<![CDATA[#,##0.00]]></Format>
<FRFont name="微软雅黑" style="1" size="96"/>
<Background name="ColorBackground" color="-3342388"/>
<Border/>
</Style>
</StyleList>
<showToolbar showtoolbar="false"/>
<IM>
<![CDATA[[<i3>P\^;KS^N64Q'B-);43LQg!=pWKi6$K@;HB-K?CG/k#=[#,9[Ak;`GY)`\uD9YpT?6`n
6V/1%sE?1]AQX+6(IP\^[(Xo?e<%cb<4*6ci(PlbNJKmhTt$LRl>HbpPJ7k[VZu`[B&#j[r'l
>Ene%i6[O7^YI&IQCtkg9FA;Yq4.bj">lq36gu@E;,_S"DMrEJQ?G*3Hnu]A&HEpdV'V7m<-/
^f2_Kq^0[@j>aHXfU0,fCT.T)<OO-cHANlXqlXK!hMqNPgr@9)E^7V_p7;OIM12ERJcu-9V-
RnAF8I!`VRO+ai2eT$GL0(cZ?jL4`dQ8=P6\QSkLS3Z&R%kXd9D@$H9]A#2V3)=Ja^XFN=r0k
n.4M0Q2?bTi/16""8=DuIJLYoCU^q,2,dJ&H.pK,PDi]A!IL,4P\+^.=9T4?>Z#Q8"#Rm:@Ka
%krq-6I31>?6D]A84ZJlcg5EKi"I50-gc1\D$@kia8(Y=E_oD02m=F-@d)3/%ag\akdc-!gGE
+nsEmukb#i/ebD%G%XpIer=lAY5YhSO88Z5tBE\`WK\j+%nWS1d.n,.Cn$cq'Bsa@Sgnj2RP
aTMa[&)')9YZ<c%1MTT8b,[B;'4g8X"%h)e^XKTkJbO7_8h7Q.??+)h1]A<4=Rr5t6Gt4bI(c
DJ6n8NiB3.*X<HY@GfdZQ&PVonQ@DB*!;.O_6*(mJ_7#CU?f<-$<=-Al;M*TQi6`4WB&ScbK
CZ(g$XKXL3Br,!ar.UQY?1tbNTOh=&.46m0UYs=PHbKNE=9/oK-6#R3/c>7KYN+Pk..tj"5E
tPfKhdsFm'FZ#Y[S0P[TE]A#ahu?l:,q:)?Xi3M,]ASsMYJV-E"H:eVPJf9-eBGU&MG/H#4%i.
,XWZ.'eiP_UCMNQhcg,YhJ>GQaPRtU4TK'H*bLMSk+gVs1(X4o/'%MEiW]A>&ONUF[jUIq9>U
]A69b$SQM&$/7kK5)jY^MQ=),C4"ub/97Oj!&&53:Tko$NlDeg0I`_'/u>]A43g=!<-TQVe7Qp
crG2l.P1*L3a"GA.n#Gr>q]A,0Im2%Vq7gY^a?]A_q&JGb'PUO)MnXbm3T3Kdcd#6"f8$>D1H-
<9Q[Hn`dR96+d3mQkJUK>O9cq(%ZoJBj;A9%UM=A$(&9re/Fgh+*e<]Aclh=Y64aL?%>paNJW
#0I,CXrY[.u7I`c'aCB"8@RYG?eH)6.)?;U]AeFL6fudQ?=&SpRKDVq8LnhHUgNGr#@F)YV=0
eLSi[O$gUJ??9%HCl)-s=Z-#aU%JHh`=-8GJ&VI8HP[^o*X:U=<7`?:?qU`!/:'oE@;G7cW=
PY@E]A06,9m_Q1oIBb9*?*5a9A<lCK/#.L_IX2Q/0!Dq05;6`7<m;[YpJq[W!s;gRjL$8R3e$
\F.Ec/.%$-8-N'Wq-]AtIlT23]A@]AD:D5t44RU</QZbq8N::EW,GF%MLcG"oo8N,lOKj0<bk.g
(J(m9f$C';UeQ@6=BK09$fjGr3F*gU7n;qod,dj9-m?@`LXYUb\>"WR%kS[f4Ak%sG9!Np,T
mY#)8+<h?&SB</#.GIOrnt';j(7h"^bNR:",`iEKsE#A=\ouSAdZ=aK=16`(s&klnKVLG@U#
ZIss-J;:`^+8>YlPP"A=3]A8u*=5dtdBJKk5cI,eS]AN1G:MLbPj&)PfSfm^)B'Y*^+N&r4tan
cfRF*fS]A:"Y@5FHmgd45YON_o>'5+C3VrllGJ0GrK?ho\,<@0"]Arib-:GdQ30\nBL6`jXp*]A
]A%?_"BU'FGN>>harmAi[_*=&f$62]A[<.o,r'5qOsUYdcc7_nR>Ie#>h\CerWnQYD:U:IIla_
XbVFkjs?&aaJ=(.l6s8YNkK"99f5mYobnMGqgtAaEu/iGeWcC^b$@JANhK"H@3m+]A&fM_lRY
1CkmtP3OgA:MWf]A(4&A/S0kNqP(bb([AK*5X/c6F@]A/;Gb<m*_-G<dOJ#FV:uo#_-AFqG3U_
2?qkorN4I]AoUbA>aGTB)n/;Z"A<1rbV=I$H71Bk6N3>6pp+(,,[J.F8"@'b<HUZg(kbX%n$\
O0R8LlWl/mn!./Rh]A\_LZ"tpDlhhOSg"L/-5u*n]A`&fBECG9Qi,23::TR]Ak`p.%@Osj0fqG*
OGU2:&SeV1@$@5nE^Ve)4YH'9%;3E?,r@nSQkBm6DdY^@k;`L"4rW&C*#V,Lg#EN0ep+<r&W
_70Qkbdt!fJ9'q9h[]A%u4A?2iiHu7,e.1,=kgYI?E2?_gCbVu;mW0hab8S5uo[8-)K'ZjY?]A
O:M=2Z28S,!UA9&(^he+Y]Ab[Hlq3n,\A2dfdT^Ru,iJ<A2mrjTAT<D3jIL4(4#<pNX<TNT<J
\Z8o\P?UH-dkrX4M\3*Djd+rVhJ0jGG,Z01t]AC-.JHl*im1KaCZJ'u]Ai,W%0:q-LJfVYgj-8
1Pja)!WBt<d*T,qq2i]AiE+l&!r":JYY.IbQd`#/F3DM?!#9VXGEreO?[G>_UAA!Bqi?7#-Kq
J/(s@a!4Y^ZW,R)Xp6R6)fiA:%j/S!P+cCoo'O+0::3&=KC]AVmRf<p'<iE+*C8q[t*53dnS\
"Em5ZmR&9WU1+loDcR:S38X@>GWW+]A)`+nZq;"IRW\IT$?Fpr[g^js!d*bQ#.;Ck=ggB;[B;
gmt@obs"Z^M/lq0m\BO-ZU*$tCMf,n6U/pod:[LbnU7KK^`Seq_*[,leV=c$Wd=k9TYWkd-+
<H5Bo%:I>G6V9:^nm^*GRdN0QY!%-52lR;V]A0"''<-]A+8)(>%Srns:ZI4i-N"BWK.K-@N/N(
1PG`Rp$u5'FDdr]A#;q@UhHaZ;&)ZW$?S9*kAG5OeM:ms,Be]ABJr/47Oc)03ki*-+]AJ'NdpG8
1^O`Jb<H:Dc:RFIqt:5%aP.?16omtDKD=_m9\39Wi]A'=l"j.?LqoHq@R\<TnRE;/Sg>rU#?D
,^(@e$58I6;9;LpZ\ciO2Mp/8?;-QN4?CNB:6mJ4LY.X?>rLgTIr+Zp5$g"I$d!(ahsSF<g5
Gh'3F"<658514gTf=6Di/!(MGm5jCTPp1%ej#*F3k]Ad!U:'*`[)qJ?sgqppUd.rg-9fomTgG
GW++6\YUT>?'ANn\n3-*upe8+)-;Tf/71qB$qa`bcH1'TZ$'a&);UgHTWSRrn+mBG@RD1?!\
WE5i4u"bNGAVl'ce3pRN<O&=XSfoJ1Z1Bk07fX-A:XMP&dOEfr[7aVn2B_7q]A&CB^Y8a/`Lq
APq?4TN/+2-B^YU/#]AV2_oV_G)a%P[]AGdA/8!$95Yc0T"b/_<qI4_5>dSduODD1QC@(;f-AA
XV:dMTEARo*Nq,?*4C>S`To<YA\5"bH_q.RZ92NA6Z[NZ6K7\n't`<.'m7djN(h$E[WE3-'^
%qm^r'?P,U@D>,7QO)JZ9nWULj)&JbB=X`k4748KV=/M2G:4m<K6Z`t3e]Ag[Cbfihed)8eBs
F^__07(5'<d:_FeULEeRKp(@ngD1(>o8P&aZ]AD75=Kf-gJ/F@uG@u^XIc.]A1g#Z;9T>kPi%.
6:(%:ll?3liBAhXkuB+__kSG3CMBJ[T_.bg^+#E$49g*J4"jTK'q`r7D[[tM^i752e#Jf;Ra
t0Vbh2"RVFYqo.Q=;ShZDrH;HN>B(`B6o6`#V9tG`hL!-&b(g*QNZAa?s'T,lhl>E`#0QC^n
,^H@54Q0/FIoU#T[T,n9LND8;4+[0Jm48T[hR$(X3LQ[V^->K9fr'KN`NT="j@]A0@k%mLHaW
nE+?S7tI##h@h^XU,Pba^5*W`PfuH6R+]Ai.oEA,Rd@OU\=1IQigN^,id3&O^a[!6LB1gDii9
A[)-('cL-VT=X9J7QKqd0MhOsG=]A<4LcQ;lDrQ&H,AMm:ba+mIQZJ-;9Wn<H7/o:93jj&16?
$5^m=t/:oC6Jc4Q?VqC%tBJoR&p[/_Q?rI72(m&N$ECNGiHigFlNDZ!F!'g*Bbq/nN<12c/g
F#.HUNFV3[<j?b?29g'VmsI5H@7)=Y32\0gg`UV=8[(GNUIFhAc:kD.kOJ56Ch-(lWW^#f,W
\pL88SGc0+Og:'YF:_ccdP\\[*2[u,`bE-SAFWjcBpsB#FtF1Vr4)?+HE>OS-7k30"CWb5a8
C,)V+?n4IkYla-BC0%P"<$^Hg64N#V6p2/[Ei`@-d18[P+!G=]A^VJ`e?7QH\He>!6c.PXK>D
*U^0f,%hK;Il=W9S,+,XXP:i\uREXb\&_=Sg@B,B7)qb:pB.a)%?V:s,oS95`/INAX#MuPb9
H'c]A;fgF`0Vm)q?Jb+/&:'R7?Z]AXBT6S0tH1JqZ<jO7<2"UXoMeDnISg!E"L0)P6h<,^0$9k
BVe1_5*S6+kfYZ,;1CP.?nJZlccn;Des73pQg7b6"k!I]ASR-H-O+kq)>q\LQFN0pQUsrpm`P
.+([dIn>AN'D:Hgmk3#^SmkGJ%qAB3oUe]A(]A'qAbOeu\)$_\Rqk_]A5O01P;ZY";>^o.9?UjY
B:%K]AQV^%Y$G7gWY1sU)VXVmrG&VAUk<a#uOtLQVSN?/&5Qn2#?q[9E2RKR;7!8jM`FAVq\,
;ZYZt3cfL%]AhKCApFF//l4iEW$;rSTkqXBWqUqWN^r*E#WH%eB8+!U3S+>`:nKR'Gc4+"A*<
/VJDD;aYh@Y2&):VFq;]A#_B)+GH![HN7%tN4LntSS,W+?3aM02>tS]AS@?0R[f-1_*)k>@*41
(-qB;1!QWq&uo,hkNHehK1G:i4R0uLL)q^NAQZnP]Am[Jh#,laFm\n't:eb'0H>@uV0R%j@G3
KDsn]A4^!dsb$mP56cJRA[!aD31ttnsoE+VjG2Yk8[I&78R9A1=nSG>nl()?lYGLfS7+T8Dee
TguW3beVRo/FCmg:p;+TC=I["'"bb"SQ:BiN8?Hl$X=+nf`<P^i@)g_fjS+4*R\:e/bq+S6]A
1&J.Wd/\Pi+T!,QNDoG,RBJi6%f/[nI896I"/&rG^8b/6D^7H8ncIREt'r`8;3)hC>:t,0Q.
]A_*bf;-Pe5h&J#q^PBN<n+e&jddBB\s'X"?2ok@^hX?0BCqQ-+j/t.YIs]A\F%st0@I%?(DRZ
8YAZk93rc!b)mi9>4;q5[G)An\S]AH8>9#0;CCQ!mNi2os4ZO>975Mi3bAoQUnV0MBrm]AB:HZ
aiV#dQ`ZG6daLC1OdE[A'moq7U^kik5\5I28fYtaABY9a/a7@o#jp*;>>0@HHqY^4(HNrQ1n
<7.X/[WS86JeMnOnOl7CKA&^P1/>e!s9)V]AqrMW]ATb5EhM=\-Y9Sgk=ed4R.jQOgqgEU9?Ne
)6.Vmi7ot!bW[1K*ei-.t.eD@CP&+dNK0$3pGa0R]AU#k<Z,*AeldfoUfL+EpWp&2kkH8Q$L7
$8Yr&CD@P)0Aj&jk#834.K`_hr_h6Q;Rs'-qaKU,gt[$q`uOk,,]AMQ1[0MoA^IKm?-f"^9%9
4:3(Eo7m;6o2mfd.0B75BOU3#'6FCo'</_a_`.qp]AYGK%mTGu.`G_r&.'>kY?!6t]A`]A_,H)W
B9qf(f<l$]AF*?AA=P`CDodjDYo?-7PG_aD3hSKpjE*BrP(T."h)S^Yn)=&WK>2M`r+a/!e#C
/aUoU,q6-`mqI[3J\F460a8F?<_)pYu"fbt*eD:;n]A='n6P>k0;M^\0DCHpSqd'A_6Q?)1]AZ
#-*$T<q7;W:B$I3^A[*<XjhKH]AdSNDQ#!=4MIQRi(ed7tgP-_eOH[0eA5AKj%QWDsm'I^K`_
MQKO+(]A;N'LmFL?lP>4@G*R1p,aY!#[2/D^;mPFIfj4RdgO+a"&/_pJKpSi\`6-i_eRm9gE-
JFpS0DLOC;Wk&:fZFLa6h_dT`80gdaSkh4S;/><-;7rW_,<Ns+JL[&!::_$H0B:k,iO6L4)X
(l/I72&T#4+$-oDKZnm1;?_+U![q'Q72MPRQ*0_eSm(>!cK(XK'X^91//4tF)?#[V<$ql!;a
#J3Za5]AgVgFMQ^O?gihKaqK8:5G4oW\8Y"6O^d:i3:qqk^.SOli7d[euQ.hW[M%NE%U?QJgI
0q?$DIXk\u0Z/5c'/C@+^L(DAeAfncFeU0g_UbAVhPs>dj]A$]AP^5[\%_YH/Ua^6j5rhLI^*N
B-E*QYPl<4-JF]AenQ@(6Rfu7=jcKt!urU8ZQ0e`B2#oqXNE23.<sEWl!2V2"*H64pcNPpY42
GS(F-HaM"%ug6@\*9]AA?Cg_*WL.Zlpr?F[c5pdW=@G!C<)NS89@o&H]A:mP6]AQ6%OuthbR7\Y
>[C_W*H_@<Mh.?g``OsoT"l4saN1m_e_>mPc\&AUNc=(/qD8>H%>T8W6tYktDZg^"^n5m154
p;gpoiim"G2Mofb/:B8u:79SE`o=TXCDWO;P7alp%QZ\<=b.kA*A++;e$EhN\$2`eGr"XlIJ
X&=8?"rB1UHBdQ7-]AiWrc:4e'0EuDIeLk326M?s2/NONdSmE%gDr.58]A?JW#ooQr!8-0Vc)p
%t6,H$'$ps8/RID6WNf2;]A:\6N2W3^+\$;rqhkACpBPJMU2:t==`5;6NaEO8=cNp<2cJJ2=W
9mYg++HBhf1]A*m4m[!1p-qX)ZGEBNh@k>Z6W7p-70J/R+<(esHDK+)I%?geW<;r`>VRKZ$MX
:7!+6HQ$Ys13\/n`f0cOTB-nD]AWn7E+`S?@I0^m*o%gk,b_pthq#._2Wq5MLJ=n:DK2E3bU;
B?^s"qoM#a*j/eO;^3q2]A/n.iFTIG^hu#F2mKM2/*mb>ckj!*eH!#=QJ8Q*o?D)A)&D4!<1m
F`FE&dG0h0PHXYk=^]AY*\M&*c7L9,X9FMt6XiC;Wo;FMJQaZ%4X`J(Vil&EK6=sl@(Q+l7^(
;Hnm#j[ad'0e_=C*.VUChP6"S%Q@IOYJn3[(cRR'AV^2C^UQe/p@\6Hr.N]APQ``q\!&A6eC'
F,$D%GWT_L517'Opm,%LaXncN\[9qLf>!BCZk-#aUo]Aohde*c;@0SuAYrSY.-F4\dFJ+K1>j
4bYLO%/&E9`R'jB3tU6YA_g64]A=Yl5n+^3kcFh-[pkmZp'EYDU&!ikF0nAiHjlc382r!G^bJ
T1>/]AJY"\%`[sr"d#dR*Y-Tn.YebAXhQ9+3@*TV7B!7IrC1mCA:M$\A=XpktSkp3o%OL:+d#
+2H,0'S6:=@?jEkJCt>RoI!jFV[-q#LlI`u/ideb``78h^0u(p?D&q,N_4*6Skk]A'X8Gh!+5
]AM`&,/MaR,r=/i!nP"t7X1_1;V9O2G`do4GNpi0opm,oPr=,o`GXBV&!4urVRG$QF(Hjh@C'
[HctFk*=OU`q`2P^9-P_Wue(XD!`rY_'E=P"VBmBUDd]AQ:K#=$7s;fRN*6a^(J)poQ=j.OGn
o7WF4e_-q\BN]A/tI(BQ_1[b<@)]A0dj<&Um3\gr*@Reg"k;<gA_>8a<S>1/#c3*p+2p05od7b
Ht[002N5U)"BK9<%B\!;B8OIuG%3RM6BbXeJ/aE@=M,g8f-`-5MG![A33!'01,<n`k,`B>_A
0)p-O83LrPJ_TI1JAUIP0q4:;TfKYZ$W5nCGUf%U@b-BNTZ[]A_Q,=h;fT+s-NC6m?M,"n8Rc
6W2MT1$q"iijjM#Dc(^AkgbKq+si<(8r73<dZ<Y4?X:^N\B=#rgqPR[QOXZ60\af+/=T[^7.
ltk2m%;$:na"FmrU.?r$N:&fZLGlAm?QLg.IsoDVgtP-S*ifA/p0k@1-9Pd#m%iIRXo>%99>
,[Z*QV,3nP#m]Ah3Q]A4S'%;2$e#L4"`Ee<hd6ZA"gr>a9+[)J4(f=O2k(1`KPT?4C.2=IMZS>
do92I%_s2]AC,(b:QpO`Y,4=o)%,6\[('a$KK7h_#8YMMBO+odOe*ML)`\M?!GF,nLe99&\lT
^aj?l2+@\l8e.dnO%!deapn#pa<sP:eKf2)N8fH#!X>V,n[7?;:Yf#g'`Dk:uoG(,qGcsq(.
nnq&0,WkbhA?,L:D2cLi8l9nE=H:b,nJ1ODZB/%&njZHQq/1,nb1JqQl1dSp%gU-gF$^Qi%q
o.S0oD5?&!j8Y,tR`#'TBJ:$JT%oW@8#OOhpWI/C$]AP[+NEpt/ViWtr:"5K:[&TE1j!GP?/G
eKMa<'d^pC896qC/u``7,&OZ#]A^)QCje5BQMX[^03+lg%)fcQb+u#(fP1"jYd9O]AHj9:qf3"
+*q_NV^6Wd#WnPf<FXZ6j:m"$9o\<Es=3NPfS7OQapDoRoX";J8-SJrlb["p5G/.]An"?ji\R
_I@nC(*6%@m:u:)N6kCc$N>VT@*TX?i.C?&FA^*8RU)@$6Y</+UP&3*7%csI/'4'Q'bB9LkZ
=]A`"&ig.(f?.[Q(CdOf-9B&QXaSgU[*>kK2K-NE,d8QJhW(=C;/)!Jb^el^qe\6"_YVUIg3B
/$k_S294n1cdjV4^7g^>o##pN)*6OnoI48Jr54S1A6,ZsELJN+FeD8,Vo?C1guG(_3N"4m"*
1r$8@IM_ND%:cXE>a1umEf!lXeP_UHQ:478!IAQ\hNVh^il9V.WnR*qbsOFH1p1mu^C.H&C-
WP\VlF"sRXACl0Rk`32ln'-=\L]Au=g2Qed*]AY:NrY.B$\\\Zo+as:(uT:Ce"R_>/A$fgVS>B
5RDf9'/-,rhR_Hsr\_P:j0W'aCi$NEd4J2`SQBE1Ar`^FID(W6O"NCA#IiVk_%b_idDkVZ8j
<*#0F:SLTA\#A6k#-J$)IS*XIrU`;5]Ai>^e%_:mip\_-Y*F.j$bPR7hZ_s]Ahc8C/!O/"D,u2
tfET800LcktnbEDJ4'<"4-Z7T)3;@Rbt\p6;to>F=I)pfD^J,Qac@N4GU]A.%,7?YtqY=41D?
M:?VT-+D:?aW?=X%p=)IiiZp)7SR-uY(*YQKn*,!gqf4t+Q7K#-.IlGEZ]ApoGB!,TR'*6pdA
\k7N)X;C8WaP[SS>5lcOjqg/]A)be4dY\Kc3@I"d9:T_F!U<KZUH&NI24!g=/=EuYmQCaSog_
dBZ?-.nk;Ni>FTd`kUe@8<;O9P[pEEps$gmcRHTSP\+4=B`Jut+lP!2DkIJIgek5!/g(uILp
oUcNQopdH2>HHn7j/q'N8%"I4>o(%oJOl<=Hu8l,o<si6]AH&`3jP5="0R>imY(XU2%B&qX)B
S6B\piQJ@=t+\%dmurk3EI(t'P=RkSd=.P4,#/0<XU@fp>%_8J_>JbfBY4/I5-^nu\j7&^E7
&f%$0DCo-2O!(f+E]An,_*QW$-p>"GE\I</SPs9-$mbsfr*TuF_mkRI^d+;K2!Q5,S.2D@VQ.
/K:`R,qpP_Jj&O85P#b<TCc@JG-"+j79nK-CSWXL?=_7jJ/XAlU/sZ@C<+&N!*g/LP4.et3'
GV=#Q?"_hGM^]Ar#tqR3dS\(#/Un.n&]APU>gmm-]A?Dj)W*G$N[U:((1mgSH9r>dK\/@<aNsFn
+1B:!>]ALZ[0/YcP2tc^EtlP-^cu0ll/O.s"(n/\.0Kon.<LA1XuX\o%VL^^%8GnTWm._ON%e
ChCrHh.4-%Pc'9Q^.49+2HV.IJc9DQltL#DeGrgJ,EV'LeqT`ni)[>@"bHl;N(OLDL\@^"56
j2Y[@&;QE7Zdg6k:,Fn2OLTj;L=.3'69(Ej`o;RXG*mZB'Ca/JSEP0\6\/\fIinT<LPU`b'-
dF.o$XtAgub#7mmMM?`"%-(p2>ecj9OSqQ4`YFP8u3nc&2t(NIoFKB_oDcAP7On*%%k[6to&
KiSMm932S@e5UnV4be6VY(bjkJ:BKbj1'[[?%[ab7&$TI9PA'gVZK[f"i^#!`"9-]AF8`ma/f
t=W;&DRSYVg&$cP$>EtqG-hsZ2O5t"@bTs-00#gbGFLHkiSP_=TPA@N#6BV"/&T`!rDZ9E1:
T8cNbE@@2U2:"`gX^Yn*>!O3kO>3U(9o71WN7.KG[PJ89mY+;j%XCslad#J_Vj3D(<R1]Af+i
3?N'[f8A/c/D_[7cjn#'7HQB\4eIkm>Nhi.lH488s2l3`l"-n4g,'Lk7IubMNO*P?Te3oFPS
21\7YSY)i/Z$uSb(l]APiFi5QA-5D$#=kR4dTkL'[bteCAc'QdCN<ni4QKY;Yo2XNEnqr.54Q
J2UVIV*/UGoRLo*.<uD=k]Ai,b5,iUuQSV[=G`_(+jYdqP[J?0tGfL18Y75lUjZ^r<.91?[GP
)`ao-0'u8YbQ4?E2O<rpO0Oiq_U"cl)'kZJoMDpMGk92E<&cQpF;$Wbs&K1F=<'s`RSd_Sfl
\N%eD1^']Ajc5D7RrBZqGZN2XBc"Bs@Bt-]AAcGDEfr8]AH,us/5J]AP]A%9X\I&&/j=P7feDrnGT
k._SoVL/TDrFMG=['A8LFU&R?[.qCr4736r>8%.f@OE:Aq#DAR;#i0ddc;#.O%\@9V+1BM;c
u.XN-*Y`ZpdH6`Xrc@E*4^&&`<Y30&A]As_ja0sO!R*2qEBKbmusjB&,EN9-2ToZMZ<]A36as-
.H]A=]A>$l'2H^pZT%TG93fX&Gt"8q_Zu5Y's&)uuq"A$TR^-MPH6c0p3?7=h3$(pHR0NM86C=
^qo9%<\gc@th;QW,k5^WN;*O?6STu;L)b:JOM5$f!\#Z@?YVG7@`S?/[pcREpW'Z,GhT=*#r
G<QXB!7RIY7;>(mBoM1"j%/Gk]AVQdIO#9b2[*%,]Atl%2L&2+>M&VWn4ZD=YGe-Kl3dV[=+3(
B"Ri7WNdX#=1Nq[>E`m-P"NG/Gr@;3ZX9i-0NWekf90=YO\1,61NEU)3Gtr*pKSij/&g#c5[
i)ZhCDNuJqWr/U4":9a!KqNgn92d)..bN7em95<.k!>B'r9oFtj`0[:7F3-R(p,`?bhh7u`M
KKEG"P(JZW4rP%/U=I2bGSZ$\]A,[lBXWBS3Ul#,1lMbrj`i[>OCCWJd2isDpBTsrmRka>1-.
g3<5:BH:i^V2rZHhk@2_RY'`qT7ZG!s5M-Jo!&Y054[2KfZ@:,pll$3:)0g^BF)*nL-'o"/o
D)D&UPu_A@\:!\4D<_S#^W#6^r?<!.#O7#V.[$i:KhoAp0X\s3'ZEl5+dDK^3n()AVi`Hu^m
/rk4JrJa!uV*&F8Ltl6iRs&bL$h<]A'rN7B-o_`.4H_37(!CVq%DOC7L6PUYl(JVXGU.Ojh@p
LPm2%ORL$ftY!.Q%gf#jI(,)@)')q?m:1RW)et728?J=qM_H$l!V+p^'cPV`<M;Ka,&L!Fi>
2Pu;Kll]AsjHs$QU>J3/Ou1<LEB<[dCn0%t)fLKc_MgcbS]AP4%Mrn9O`^Xd.^\>>r53G8RN<S
tRmo!#5Wbgu2bn35e\&.mo[?rj_;Iqp'#s?,Qn3"ajgkTB&JQd`..S#lA-"3EN`ho=R@9JA>
ikE1m]AESH]A_EUnQ]APNH#^2#W?aSB#M37-XGb:gOW$t3'3ZV^89qCPl4=HrU8EQdnnumHpmI3
oBA-!(sCA`gJD`^VKgV'h0Kc,^Q%3"B:f/>g+ap6,QXhKBjX!D1Oud]A!m%YjA^[\)\C)-(_J
LuLmf#oqa#0@t+)F+8:u_c<c5A#8m6i'pi5U8(i;]APt#lXrIrHl0rP8"76VNChq;Yu2GKBC1
R7))8FE<<k@k?9CE(P[qq\c!]AWd![eY83f'#FWp1=J9*^E1ArH-is'DUA<Zl#HT:O\`8V3@Q
STsc)2]AVk^C0?@Ir#-m8EA[:P_?t`:NJ>NN9sj/Fl);IdJm_N?#>S>E3g5)15;4-$[$:,/u%
r;:'hgk:`G`?!-/sI,MDas"1km+$Je"@'9CKI0)FQNcnQZ/!/e@uj.AQU)$5dA_:P9>;kR6>
?d?C@LT_d#\clVamc64?=c!WYf,"BN.J,#SZ!Zn:iT3j>p(P)m4=*Mp@R;ccUQ6eW>qQ-:Dd
V\SNY#ZF0bkc:[[s0;Kai;;0Kq1?6"1"e0+AE\=K`&'/oYHVm;d4ld\Ftk51D'6!D:-]AY_/K
R(^E1oYtZHHRa[?1$-)qo>7j<F7j3<#n8BNM9[hRsM\(#[&_bs7-"_"2l[2P,h(qJiG&A1Od
'SRMrG=J")F3mp=BA'rjYd*eSq5ON#"5UX&7@.95*W5b[lf55gEN*A.Ki<K!ggfeH+Y.='ue
Z1TTU*qbBKaP-6W3djJ>opf`%YnrNH*"8MP1X$;ND&ZPKcB*mUh9\d]AaLSnS>RnG`/Ca7$@R
b&:rWjf34V:Y843/gkq^lD6JA0EqW%.L?NTg#4dtj9c)%"3<jgj>BO1f(0kd:\6=*#<A@.3;
WOR,3+(75C1P>hZ8j[,=m]An]AX;5VQ,B/K$+/LX?Pm5tab4$E*gqf^T#r%9Yem+S[T'Z/[$jA
3A)*+#IN#YeFNn3t'EaWjjl+..bo<M,_r-)L+F3XV&1f0Z^7L1p*!h%pYn@pal$"]A[[NFuFi
!grd;oFIb_W6nq?c3O:Dl\E3IN5fcairj">fS4f+bs2G\^I^Do0r]Asdl=gk>pNc78S`Vb(Tr
9Xc1BT[RloCDojs+mb3?RU8krucro]AK7X437lbh:`go'<=b4m);%Y7P[%a1U9:An>F_A06&\
PTT#7jX.Y6p?C%aUQL8$9hd;([>#[I<M\#3P2=!f/&i1VI3!-!:<r#NfgqKX$b2VLfm>ae48
!bH9GSt`?aqpIjm>a9[6F=;LZYChklmiuD-:%4jii3g**]A#R#KW(r1^!@M=I4n7aWb/3DcIT
P=f^bNlZ&rmA$i!I1`oHbZD>[CWQ$!$E-gQW<jT<IkX"]Ad']AjVee^2J3*R8Vns71o7qnG;Fh
UWTP,R.*?D5LTFHp94Q)!KBiGqCIo5:fr,]AeAeaL>jkDIBVrbpi02U#FHI0m'jJ<l&Ms:KBT
F@56dmo$8VOtiK:PUJ>EKhGZM99+Q(uOn-kYJ1mm*HUPlK7>u8W1aE:@VVi?*&'T.!QMEa=m
*I8.CkZN2'bo4S<4fU/bm]Ao(H%.ddaH;^MUlLH"Ar))F_7W[V8C?f<8HEZ:PoKo%\L$uZn9m
9`[),_&ed\HQ?G;7KcUh-!3c^h7&;aF7^@Qk=ghps&[O#+-7n>ct=qH?Hn@*g?3':;FQ:/8>
(YGW5C0Vs\%gV'L8&r^_,W#UkjQq/[6]ANf]ATIOW3QksQHMrQfCAn*mNU"pKuX[`HDod6@)2l
N;'TBu6ZlTVT/M#<@X7Uu&"Fa>CPL.q('>nr\\/NZF/#Y\J;[Iju%kZ",GQ[/*G(n7h]A>?/%
Q%&t1tQc7"C\hJJMTp9:&Q!j`gN_aL3iMM;e',Gdt0.M^2[3S<3aWb9$r"V$nNBiC!u_pBDc
"IRju&=Dc;C\<SSC0e#n"_P0m;N]A?!W+OFWfbmqK?nShkFS`*Fr6^oMloL7CiuP9do9n:L(<
#4VgkIU7a_/glQdmd2NOi;0T@R?0_Bc#IchCilj('=:qf&/9qh6c]A1(VL3d/lA^X@p4gq8"-
7>dIk+qcN?U9Dt&d3IV^Zlk?cB.X*J9iqZ!cDT/14Rt#'nae6t;IsI=.s%$3fHZ<aSIXcNH&
FGCC!.==<VA3E^c9kcDY$g1!KE&52X+W3:a<'e5\F[C&+tOsWS=%f->2U`R.Uf;_:\3<iQtL
\3KL/\'8\N7K/,#l;HJ&esM,3R[*n;bXPb65qUV>j_OR,,[4G:$r$_M,k?XRKKLPo!e>]A4e/
p'^Vd@.fG+/*C/m]A_\p+)sintNO%u,M5n*>2:u9rd:HKhWVF*S>5gW%3E@FQO+tQ,C\,l@_o
^+f`CC;!+DAr3EHm=dRWqr#@HKSIcWVXJ<Y@K(b9%iR?KpUCo@t/Emec2No;S1aaY&.S?lY'
edXFo1&\Aehb"@:?r"At1CV:SVJ[i&/4c/5DK^k-aX]A1_1S@&kUHo8R2AX8O+%W^rD/@'3GA
LK9,POV@d&iLaYI1nbd$G'@8aKr17]A_l+"pp::ON+,`%rpaqfFJ:bEo1/5j!YKkuO=,k1"W!
M=0.u'ADt'jse/k/I:%bb70iqG-pHn@*<M<"!)&]AU&6L\p"*Y$EEfB^^?r;%m%/Y_6q+GJ'm
2:f-8Z:'b?T!1`>f+hDQY<\NEp'.g[+0V^BIV5p+jp>rh3sB,;:5#PnF(o@EN1omtjYNG:bh
_OO#pW^O\5EjE@X_;*k1u=>gdYW[(ta*,`a'G,4I9CNSdCX]Ag="90qlQcLm:qu&cCMH:ri"&
"o,F^#Q(_aNgi%#BTe\US3=fKuY7FuU?q(R::NK>JH*9DRBiY-bDPiE[41d>/[jYDe(r/]A=/
JbX[T9RIK_tE[BLY4EE0%`+EcZqj)G0AtX>\I9bi-Vh,'ONhNm@sn_1hSugCj%)@$GqHVO#4
h@4Th@CHOAlZ+]Ar)I1S*e8<:8f&IW+(,jV@:4O7l^j_>Cbpg!;epXp5!%F%8I)MM3c<eST=j
EraEpq^`+?HrMD[qPpaQ8a+=S:'Cd#q:*LZ1pA&Ic9s1S+4al+K[ZSkW:md$C^23;DIQY!-=
dIOm8tP2-9^2%rp@(3?_pbP\M>4$3hMme)^es5;s"R.oY/;.9KUq>;c,4BKe4$+EH7`.XqS!
Ui>u?Dkrr=hBHG$<+euVnLZRCDO>$XjIsf@P@YTbuq\1Z#8[2[#)bY/\b_<-8-pGBjD3RB,e
3o'UcgIB!cmkQbL03^+<NI4$REsqEd^8.!M4/&Ag\K3M="=U$Z0ig[T6**.ehg4he2W]A]AWr2
`;qb:#k?F!^Dhf_97Y'(&,<NMftdYX[SNsYVMrE2tdj"q!A@5L-Xif-;mj!q:=QLF"[NhZeQ
HT$m66%k%T5*!ka%;u;,:1_rW$=ec6W"H]A4?Ne$R!rM2*^Rm[#eA0ar&Z[nUUe1@4puE[MG@
bkqi/g5rI*kP&B(Ro5=)tas(`8)cZdfM0q:?u,?c'ZJQ(:"OI%9OPE-'Q4*&h?QUcpg(f+VP
@iE+^gAuS;2kLcl?pJ=c)qi?5bdpgp.MIV%:=_;o]AC4f"q\?bZDaTaNif=RUo+u+C=-tMj<e
D<&A21;0XNd#bB=('CVAT<-!JP/d_<W`_:!j<=2khI?.R96uQREn'0?0\%j>If7:n3g4^FQ1
PtG;uL_V8qU(*LPGpc&gr&YJ(l)e'+c/9CIe6n#WP)XF\N`gb6gSGdNoORKt#dnUYga_/;X5
l1h8,5^0W\NJMf@]AB\NDM7[TgUY+O<ke^[rA.P9C;<sZqjU^)a]A.?@QC"pf2M`0)j^Ul%U;>
fX^*_)>Wja+LrP>g9De/0d4.S<dlm0hS`NV+!2G`MPiY-s66311Kq,n'Oj>:"A%lo(Z5m>]AQ
h%Y1N2n!@6]AA?.>OVftV0KGoccb4*HL\X%lJ,j,JO@_t<&Aa$P#RM]At%II5HA_!XA=mC=j5[
BXPir":&Z+*I2CXPpU[@c]A>fUZKX5*YV$h#`dKWns-Q:oG]A'c>s4#M/u.!2,(>]Ak/CDLZjji
R/!mc,=$bli8QJAVW6]A]AJ#HCP]A<U/2Vp'3SZdFN%4L47d]AA=UO9r=a[bM[JYDE<hRKi!St:@
>.`GhMsaMLobM"eV<(s-/O_?/+j*3F#>^J403T'&3[>75jD&`G12qe4YDd4=6RjEn3tV24[#
X6-0\t*:-D0jVh6ZQgLDPqul`!1>Nn4L8$HS]A&Q&XiXJ[<=4G2sSTjc!ZX!PbdC\Y5SnDlY8
c#6~
]]></IM>
</body>
</InnerWidget>
<BoundsAttr x="425" y="0" width="540" height="510"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.container.WScaleLayout">
<Listener event="afterinit">
<JavaScript class="com.fr.js.JavaScriptImpl">
<Parameters/>
<Content>
<![CDATA[this.invisible();]]></Content>
</JavaScript>
</Listener>
<WidgetName name="textEditor0"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Margin top="0" left="0" bottom="0" right="0"/>
<Border>
<border style="0" color="-723724" type="0" borderStyle="0"/>
<WidgetTitle>
<O>
<![CDATA[新建标题]]></O>
<FRFont name="SimSun" style="0" size="72"/>
<Position pos="0"/>
</WidgetTitle>
<Alpha alpha="1.0"/>
</Border>
<LCAttr vgap="0" hgap="0" compInterval="0"/>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.TextEditor">
<WidgetName name="saleman"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<TextAttr/>
<widgetValue>
<O>
<![CDATA[1]]></O>
</widgetValue>
</InnerWidget>
<BoundsAttr x="385" y="0" width="40" height="21"/>
</Widget>
</InnerWidget>
<BoundsAttr x="385" y="0" width="40" height="33"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.FreeButton">
<Listener event="click">
<JavaScript class="com.fr.js.JavaScriptImpl">
<Parameters/>
<Content>
<![CDATA[var w=this.options.form.getWidgetByName("saleman");
w.setValue(6);
w.fireEvent('afteredit');
w.invisible();]]></Content>
</JavaScript>
</Listener>
<WidgetName name="button1"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Text>
<![CDATA[销售员6]]></Text>
</InnerWidget>
<BoundsAttr x="291" y="198" width="134" height="34"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.FreeButton">
<Listener event="click">
<JavaScript class="com.fr.js.JavaScriptImpl">
<Parameters/>
<Content>
<![CDATA[var w=this.options.form.getWidgetByName("saleman");
w.setValue(1);
w.fireEvent('afteredit');
w.invisible();]]></Content>
</JavaScript>
</Listener>
<WidgetName name="button2"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Text>
<![CDATA[销售员1]]></Text>
</InnerWidget>
<BoundsAttr x="291" y="33" width="134" height="31"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.FreeButton">
<Listener event="click">
<JavaScript class="com.fr.js.JavaScriptImpl">
<Parameters/>
<Content>
<![CDATA[var w=this.options.form.getWidgetByName("saleman");
w.setValue(2);
w.fireEvent('afteredit');
w.invisible();]]></Content>
</JavaScript>
</Listener>
<WidgetName name="button3"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Text>
<![CDATA[销售员2]]></Text>
</InnerWidget>
<BoundsAttr x="291" y="64" width="134" height="36"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.FreeButton">
<Listener event="click">
<JavaScript class="com.fr.js.JavaScriptImpl">
<Parameters/>
<Content>
<![CDATA[var w=this.options.form.getWidgetByName("saleman");
w.setValue(4);
w.fireEvent('afteredit');
w.invisible();]]></Content>
</JavaScript>
</Listener>
<WidgetName name="button4"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Text>
<![CDATA[销售员4]]></Text>
</InnerWidget>
<BoundsAttr x="291" y="132" width="134" height="33"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.FreeButton">
<Listener event="click">
<JavaScript class="com.fr.js.JavaScriptImpl">
<Parameters/>
<Content>
<![CDATA[var w=this.options.form.getWidgetByName("saleman");
w.setValue(3);
w.fireEvent('afteredit');
w.invisible();]]></Content>
</JavaScript>
</Listener>
<WidgetName name="button5"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Text>
<![CDATA[销售员3]]></Text>
</InnerWidget>
<BoundsAttr x="291" y="100" width="134" height="32"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.FreeButton">
<Listener event="click">
<JavaScript class="com.fr.js.JavaScriptImpl">
<Parameters/>
<Content>
<![CDATA[var w=this.options.form.getWidgetByName("saleman");
w.setValue(10);
w.fireEvent('afteredit');
w.invisible();]]></Content>
</JavaScript>
</Listener>
<WidgetName name="button7"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Text>
<![CDATA[销售员10]]></Text>
</InnerWidget>
<BoundsAttr x="291" y="321" width="134" height="37"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.FreeButton">
<Listener event="click">
<JavaScript class="com.fr.js.JavaScriptImpl">
<Parameters/>
<Content>
<![CDATA[var w=this.options.form.getWidgetByName("saleman");
w.setValue(8);
w.fireEvent('afteredit');
w.invisible();]]></Content>
</JavaScript>
</Listener>
<WidgetName name="button8"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Text>
<![CDATA[销售员8]]></Text>
</InnerWidget>
<BoundsAttr x="291" y="265" width="134" height="22"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.FreeButton">
<Listener event="click">
<JavaScript class="com.fr.js.JavaScriptImpl">
<Parameters/>
<Content>
<![CDATA[var w=this.options.form.getWidgetByName("saleman");
w.setValue(11);
w.fireEvent('afteredit');
w.invisible();]]></Content>
</JavaScript>
</Listener>
<WidgetName name="button9"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Text>
<![CDATA[销售员11]]></Text>
</InnerWidget>
<BoundsAttr x="291" y="358" width="134" height="36"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.FreeButton">
<Listener event="click">
<JavaScript class="com.fr.js.JavaScriptImpl">
<Parameters/>
<Content>
<![CDATA[var w=this.options.form.getWidgetByName("saleman");
w.setValue(7);
w.fireEvent('afteredit');
w.invisible();]]></Content>
</JavaScript>
</Listener>
<WidgetName name="button10"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Text>
<![CDATA[销售员7]]></Text>
</InnerWidget>
<BoundsAttr x="291" y="232" width="134" height="33"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.FreeButton">
<Listener event="click">
<JavaScript class="com.fr.js.JavaScriptImpl">
<Parameters/>
<Content>
<![CDATA[var w=this.options.form.getWidgetByName("saleman");
w.setValue(9);
w.fireEvent('afteredit');
w.invisible();]]></Content>
</JavaScript>
</Listener>
<WidgetName name="button11"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Text>
<![CDATA[销售员9]]></Text>
</InnerWidget>
<BoundsAttr x="291" y="287" width="134" height="34"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.FreeButton">
<Listener event="click">
<JavaScript class="com.fr.js.JavaScriptImpl">
<Parameters/>
<Content>
<![CDATA[var w=this.options.form.getWidgetByName("saleman");
w.setValue(13);
w.fireEvent('afteredit');
w.invisible();]]></Content>
</JavaScript>
</Listener>
<WidgetName name="button12"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Text>
<![CDATA[销售员13]]></Text>
</InnerWidget>
<BoundsAttr x="291" y="427" width="134" height="36"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.FreeButton">
<Listener event="click">
<JavaScript class="com.fr.js.JavaScriptImpl">
<Parameters/>
<Content>
<![CDATA[var w=this.options.form.getWidgetByName("saleman");
w.setValue(12);
w.fireEvent('afteredit');
w.invisible();]]></Content>
</JavaScript>
</Listener>
<WidgetName name="button0"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Text>
<![CDATA[销售员12]]></Text>
</InnerWidget>
<BoundsAttr x="291" y="394" width="134" height="33"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.FreeButton">
<Listener event="click">
<JavaScript class="com.fr.js.JavaScriptImpl">
<Parameters/>
<Content>
<![CDATA[var w=this.options.form.getWidgetByName("saleman");
w.setValue(14);
w.fireEvent('afteredit');
w.invisible();]]></Content>
</JavaScript>
</Listener>
<WidgetName name="button13"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Text>
<![CDATA[销售员14]]></Text>
</InnerWidget>
<BoundsAttr x="291" y="463" width="134" height="47"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.FreeButton">
<Listener event="click">
<JavaScript class="com.fr.js.JavaScriptImpl">
<Parameters/>
<Content>
<![CDATA[var w=this.options.form.getWidgetByName("saleman");
w.setValue(5);
w.fireEvent('afteredit');
w.invisible();]]></Content>
</JavaScript>
</Listener>
<WidgetName name="button6"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<Text>
<![CDATA[销售员5]]></Text>
</InnerWidget>
<BoundsAttr x="291" y="165" width="134" height="33"/>
</Widget>
<Widget class="com.fr.form.ui.container.WAbsoluteLayout$BoundsWidget">
<InnerWidget class="com.fr.form.ui.Label">
<WidgetName name="label0"/>
<WidgetAttr>
<PrivilegeControl/>
</WidgetAttr>
<widgetValue>
<O>
<![CDATA[选择销售员]]></O>
</widgetValue>
<LabelAttr verticalcenter="true" textalign="2" autoline="true"/>
<FRFont name="微软雅黑" style="0" size="80" foreground="-6697984"/>
<border style="0" color="-723724"/>
</InnerWidget>
<BoundsAttr x="291" y="0" width="94" height="33"/>
</Widget>
<Sorted sorted="false"/>
<WidgetZoomAttr compState="0"/>
<Size width="965" height="510"/>
<MobileWidgetList>
<Widget widgetName="chart0"/>
<Widget widgetName="label0"/>
<Widget widgetName="saleman"/>
<Widget widgetName="report0"/>
<Widget widgetName="button2"/>
<Widget widgetName="button3"/>
<Widget widgetName="button5"/>
<Widget widgetName="button4"/>
<Widget widgetName="button6"/>
<Widget widgetName="button1"/>
<Widget widgetName="button10"/>
<Widget widgetName="button8"/>
<Widget widgetName="chart1"/>
<Widget widgetName="button11"/>
<Widget widgetName="button7"/>
<Widget widgetName="button9"/>
<Widget widgetName="button0"/>
<Widget widgetName="button12"/>
<Widget widgetName="button13"/>
</MobileWidgetList>
</Center>
</Layout>
<DesignerVersion DesignerVersion="HBB"/>
<PreviewType PreviewType="0"/>
</Form>
