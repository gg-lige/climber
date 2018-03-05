<%@ page pageEncoding="utf-8" session="false"%>
<!DOCTYPE html>
<html>
<head>
	<title>搜索引擎管理端&nbsp;&nbsp;分类</title>
	<link rel="stylesheet" type="text/css" href="/manager-1.0/stylesheet/base.css" />
	<style type="text/css">
		body>header>nav>a:nth-of-type(2) { color: #e15782; }
		body>div>div { display: inline-block; vertical-align: middle; }
		body>div>div:nth-of-type(2) { margin: 0 0 0 100px; }
	</style>
	<script src="//cdnjs.cloudflare.com/ajax/libs/d3/3.4.4/d3.min.js"></script>
	<script src="/manager-1.0/javascript/d3pie.min.js"></script>
	<script src="/manager-1.0/javascript/liquidFillGauge.js"></script>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
</head>
<body>
	<header>
		<img src="/manager-1.0/image/logo.png" alt="J&nbsp;&amp;&nbsp;H" />
		<nav>
			<a href="/manager-1.0/crawler">采集</a>
			<a href="/manager-1.0/classification">分类</a>
			<a href="/manager-1.0/pagerank">PageRank</a>
		</nav>
	</header>
	<div>
		<div id="class"></div>
		<div id="indicator">
		</div>
	</div>
	<footer>&copy;2017&nbsp;&nbsp;XJTU</footer>
	<script>
		var recall = new Array();
		recall[0] = ${recall[0]};
		recall[1] = ${recall[1]};
		recall[2] = ${recall[2]};
		recall[3] = ${recall[3]};
		recall[4] = ${recall[4]};
		recall[5] = ${recall[5]};
		recall[6] = ${recall[6]};
		var precision = new Array();
		precision[0] = ${precision[0]};
		precision[1] = ${precision[1]};
		precision[2] = ${precision[2]};
		precision[3] = ${precision[3]};
		precision[4] = ${precision[4]};
		precision[5] = ${precision[5]};
		precision[6] = ${precision[6]};
	var pie = new d3pie("class", {"header":{"title":{"text":"数据类别","color":"#000000","fontSize":24,"font":"open sans"},"subtitle":{"color":"#999999","fontSize":12,"font":"open sans"},"location":"top-left","titleSubtitlePadding":14},"footer":{"color":"#999999","fontSize":10,"font":"open sans","location":"bottom-left"},"size":{"canvasHeight":450,"canvasWidth":450,"pieInnerRadius":"20%","pieOuterRadius":"70%"},"data":{"sortOrder":"value-desc","content":[{"label":"${names[0]}","value":${counts[0]},"color":"#7e3838"},{"label":"${names[1]}","value":${counts[1]},"color":"#7e6538"},{"label":"${names[2]}","value":${counts[2]},"color":"#7c7e38"},{"label":"${names[3]}","value":${counts[3]},"color":"#587e38"},{"label":"${names[4]}","value":${counts[4]},"color":"#387e45"},{"label":"${names[5]}","value":${counts[5]},"color":"#387e6a"},{"label":"${names[6]}","value":${counts[6]},"color":"#386a7e"},{"label":"${names[7]}","value":${counts[7]},"color":"#38507e"},{"label":"${names[8]}","value":${counts[8]},"color":"#4d387e"},{"label":"${names[9]}","value":${counts[9]},"color":"#7a387e"}]},"labels":{"outer":{"format":"label-value1","pieDistance":10},"inner":{"hideWhenLessThanPercentage":3},"mainLabel":{"fontSize":11},"percentage":{"color":"#ffffff","decimalPlaces":0},"value":{"color":"#adadad","fontSize":11},"lines":{"enabled":true},"truncation":{"enabled":true}},"effects":{"pullOutSegmentOnClick":{"effect":"linear","speed":400,"size":8}},"misc":{"gradient":{"enabled":true,"percentage":100}}, "callbacks":{onClickSegment:function(e){
		$("#indicator").empty();
		$("#indicator").append('<p style="font-size: 18px;">查全率&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查准率</p><svg id="recall" width="200px"></svg><svg id="precision" width="200px"></svg>');
		var recallConfig = liquidFillGaugeDefaultSettings();
		//recallConfig.circleColor = "#FF7777";
		//recallConfig.textColor = "#FF4444";
		//recallConfig.waveTextColor = "#FFAAAA";
		recallConfig.circleThickness = 0.2;
		recallConfig.textVertPosition = 0.2;
		recallConfig.waveAnimateTime = 1000;
		loadLiquidFillGauge("recall", Math.round(recall[e.index] * 100), recallConfig);
		var precisionConfig = liquidFillGaugeDefaultSettings();
		//precisionConfig.circleColor = "#FF7777";
		//precisionConfig.textColor = "#FF4444";
		//precisionConfig.waveTextColor = "#FFAAAA";
		precisionConfig.circleThickness = 0.2;
		precisionConfig.textVertPosition = 0.2;
		precisionConfig.waveAnimateTime = 1000;
		loadLiquidFillGauge("precision", Math.round(precision[e.index] * 100), precisionConfig);
	}}});
	</script>
</body>
</html>