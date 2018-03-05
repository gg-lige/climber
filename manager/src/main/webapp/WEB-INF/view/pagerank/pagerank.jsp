<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>搜索引擎管理端&nbsp;&nbsp;采集</title>
	<link rel="stylesheet" type="text/css" href="/manager-1.0/stylesheet/base.css" />
	<style type="text/css">
		body>header>nav>a:nth-of-type(3) { color: #e15782; }
		.links line { stroke: #999; stroke-opacity: 0.6; }
		.nodes circle { stroke: #fff; stroke-width: 1.5px; }
	</style>
	<script src="https://d3js.org/d3.v4.min.js"></script>
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
	<svg width="960" height="600"></svg>
	<footer>&copy;2017&nbsp;&nbsp;XJTU</footer>
<script>
var svg = d3.select("svg"),
    width = +svg.attr("width"),
    height = +svg.attr("height");

var color = d3.scaleOrdinal(d3.schemeCategory20);

var simulation = d3.forceSimulation()
    .force("link", d3.forceLink().id(function(d) { return d.id; }))
    .force("charge", d3.forceManyBody())
    .force("center", d3.forceCenter(width / 2, height / 2));

//d3.json("miserables.json", function(error, graph) {
//  if (error) throw error;
$.ajax({
		url: "http://202.117.16.32:8080/manager-1.0/pagerank/json",
		success: function(graph) {

  var link = svg.append("g")
      .attr("class", "links")
    .selectAll("line")
    .data(graph.links)
    .enter().append("line")
      .attr("stroke-width", function(d) { return Math.sqrt(d.value); });

  var node = svg.append("g")
      .attr("class", "nodes")
    .selectAll("circle")
    .data(graph.nodes)
    .enter().append("circle")
      .attr("r", 5)
      .attr("fill", function(d) { return color(d.group); })
      .call(d3.drag()
          .on("start", dragstarted)
          .on("drag", dragged)
          .on("end", dragended));

  node.append("title")
      .text(function(d) { return d.id; });

  simulation
      .nodes(graph.nodes)
      .on("tick", ticked);

  simulation.force("link")
      .links(graph.links);

  function ticked() {
    link
        .attr("x1", function(d) { return d.source.x; })
        .attr("y1", function(d) { return d.source.y; })
        .attr("x2", function(d) { return d.target.x; })
        .attr("y2", function(d) { return d.target.y; });

    node
        .attr("cx", function(d) { return d.x; })
        .attr("cy", function(d) { return d.y; });
  }
}});

function dragstarted(d) {
  if (!d3.event.active) simulation.alphaTarget(0.3).restart();
  d.fx = d.x;
  d.fy = d.y;
}

function dragged(d) {
  d.fx = d3.event.x;
  d.fy = d3.event.y;
}

function dragended(d) {
  if (!d3.event.active) simulation.alphaTarget(0);
  d.fx = null;
  d.fy = null;
}

</script>
</body>
</html>