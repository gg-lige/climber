<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>搜索引擎管理端&nbsp;&nbsp;采集</title>
	<link rel="stylesheet" type="text/css" href="/manager-1.0/stylesheet/base.css" />
	<style type="text/css">
		body>header>nav>a:nth-of-type(1) { color: #e15782; }
		body>div>form { display: inline-block; vertical-align: middle; }
		body>div>div { display: inline-block; margin: 0 0 0 100px; vertical-align: middle; }
		body>div>form>div:nth-of-type(1) { display: inline-block; font-size: 18px; }
		body>div>form>div:nth-of-type(2) { display: inline-block; color: #e15782; font-size: 16px; }
		body>div>form>textarea { background: #F0F0EE; border: 0; display: block; font-size: 16px; height: 361px; margin: 10px auto; padding: 0; width: 400px; }
		body>div>form>input { background: #F0F0EE; display: inline-block; font-size: 16px; margin: 10px 10px 10px 0; }
		body>div>form>input:nth-of-type(4) { display: none; }
	</style>
	<script src="http://d3js.org/d3.v3.min.js"></script>
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
		<form action="" method="post">
			<div>最新采集地址</div>
			<textarea name="sources">${urls}</textarea>
			<input id="start" type="submit" value="启动" />
			<input id="stop" type="submit" value="停止" />
			<input id="refresh" type="submit" value="刷新" />
			<input id="command" type="text" name="command" />
			<div>已经完成${total}条...</div>
		</form>
		<div id="source"></div>
	</div>
	<footer>&copy;2017&nbsp;&nbsp;XJTU</footer>
	<script>
	var pie = new d3pie("source", {"header":{"title":{"text":"全部数据来源","color":"#000000","fontSize":18,"font":"Microsoft Yahei"},"subtitle":{"color":"#999999","fontSize":12,"font":"open sans"},"location":"top-left","titleSubtitlePadding":14},"footer":{"color":"#999999","fontSize":10,"font":"open sans","location":"bottom-left"},"size":{"canvasHeight":450,"canvasWidth":450,"pieInnerRadius":"20%","pieOuterRadius":"70%"},"data":{"sortOrder":"value-desc","content":[{"label":"${sources[0].name}","value":${sources[0].count},"color":"#7e3838"},{"label":"${sources[1].name}","value":${sources[1].count},"color":"#7e6538"},{"label":"${sources[2].name}","value":${sources[2].count},"color":"#7c7e38"},{"label":"${sources[3].name}","value":${sources[3].count},"color":"#587e38"},{"label":"${sources[4].name}","value":${sources[4].count},"color":"#387e45"},{"label":"${sources[5].name}","value":${sources[5].count},"color":"#387e6a"},{"label":"${sources[6].name}","value":${sources[6].count},"color":"#386a7e"},{"label":"${sources[7].name}","value":${sources[7].count},"color":"#38507e"},{"label":"${sources[8].name}","value":${sources[8].count},"color":"#4d387e"},{"label":"${sources[9].name}","value":${sources[9].count},"color":"#7a387e"}]},"labels":{"outer":{"format":"label-value1","pieDistance":10},"inner":{"hideWhenLessThanPercentage":3},"mainLabel":{"fontSize":11},"percentage":{"color":"#ffffff","decimalPlaces":0},"value":{"color":"#adadad","fontSize":11},"lines":{"enabled":true},"truncation":{"enabled":true}},"effects":{"pullOutSegmentOnClick":{"effect":"linear","speed":400,"size":8}},"misc":{"gradient":{"enabled":true,"percentage":100}}});
	$("#start").click(function() {
		$("#command").val("start");
	});
	$("#stop").click(function() {
		$("#command").val("stop");
	});
	$("#refresh").click(function() {
		$("#command").val("refresh");
	});
	</script>
</body>
</html>