<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CLIMBER</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link type="image/x-icon" rel="shortcut icon" href="images/favicon.png" />
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/navbar-fixed-top.css" rel="stylesheet">

<script type="text/javascript" src="js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrap-paginator.min.js"></script>
</head>
<body>

	<!-- TITLE START -->
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="./">ClimberSearch</a>
		</div>

		<div class="navbar-header">
			<form action="news-getNewsInPages" class="navbar-form" role="form">
				<div class="form-group">
					<input id="keyWords" type="text" placeholder="Search..."
						value="${requestScope.key}" class="form-control"
						style="width: 400px">
				</div>
				<button id="search" type="button" class="btn btn-default"
					style="background: #3385ff; border-bottom: 1px solid #2d78f4; color: #fff">搜索一下</button>
			</form>
		</div>

		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="./">搜索首页</a></li>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
	</nav>
	<!-- TITLE END -->

	<!-- CONTENT START -->
	<div class="container">
		<div class="row">
			<div class="col-sm-9">
				<s:if
					test="#request.pageBean.list == null || #request.pageBean.list.size() == 0">
					没有任何新闻数据
			    </s:if>
				<s:else>
					<s:iterator value="#request.pageBean.list" id="new">
						<h3>
							<a href="${url}" target="_blank">${title } </a><small>[${rank}]</small>
						</h3>
						<p>${html }</p>
						<p>
							<a href="${url}" target="_blank" style="color: green">${url }</a>
						</p>
						<br>
					</s:iterator>
					<!-- 
					<s:iterator value="#request.pageBean.list" id="new">
						<h3>
							<a href="${url}">标题${url }</a>
						</h3>
						<p>
							<xmp>${content }</xmp>
						</p>
						<p>
							<a href="${url}" style="color: green">${url }</a>
						</p>
						<br>
					</s:iterator>
					 -->
				</s:else>

			</div>
			<div class="col-sm-3">
				<p>
					<s:if
						test="#request.recommendations == null || #request.recommendations.size() == 0">
					</s:if>
					<s:else>
						<h4 class="title">你可能还喜欢</h4>
						<ul class="list-group">
							<s:iterator value="#request.recommendations" id="keyWord">
								<li class="list-group-item"><a
									href="news-getNewsInPages?key=${keyWord }"><p
											class="list-group-item-text">${keyWord }</p></a></li>
							</s:iterator>
						</ul>
						<br>
					</s:else>
				</p>
				<p>
					<s:if
						test="#request.topTenWeekly == null || #request.topTenWeekly.size() == 0">
					</s:if>
					<s:else>
						<h4 class="title">本周热点</h4>
						<ul class="list-group">
							<s:iterator value="#request.topTenWeekly" id="keyWord">
								<li class="list-group-item"><a
									href="news-getNewsInPages?key=${keyWord }"><p
											class="list-group-item-text">${keyWord }</p></a></li>
							</s:iterator>
						</ul>
						<br>
					</s:else>
				</p>
				<p>
					<s:if test="#request.topTen == null || #request.topTen.size() == 0">
					</s:if>
					<s:else>
						<h4 class="title">大家还在搜</h4>
						<ul class="list-group">
							<s:iterator value="#request.topTen" id="keyWord">
								<li class="list-group-item"><a
									href="news-getNewsInPages?key=${keyWord }"><p
											class="list-group-item-text">${keyWord }</p></a></li>
							</s:iterator>
						</ul>
						<br>
					</s:else>
				</p>
			</div>

		</div>
	</div>
	<!-- CONTENT END -->
	<!-- PAGE START -->
	<div class="container">
		<div>
			<br> <br>
			<p>
				ClimberSearch为您找到相关结果约
				<s:property value="#request.pageBean.allRows" />
				个，共
				<s:property value="#request.pageBean.totalPage" />
				页
			</p>
		</div>
		<div class="row">
			<div id="paginator" style="text-align: left">
				<ul id="pageLimit" style="cursor: pointer;"></ul>
			</div>
		</div>
		<div class="row">
			<br> <br>
		</div>
	</div>
	<script type="text/javascript">
		
			var options = {
				currentPage : ${requestScope.pageBean.currentPage},
				totalPages : ${requestScope.pageBean.totalPage},
				size : "large",
				bootstrapMajorVersion : 3,
				alignment : "left",
				numberOfPages : 10,
				pageUrl: function(type, page, current){
	                return "news-getNewsInPages?key=${requestScope.key}&page="+page;
				},
				useBootstrapTooltip:true,
	            tooltipTitles: function (type, page, current) {
	                switch (type) {
	                case "first":
	                    return "Go To First Page <i class='icon-fast-backward icon-white'></i>";
	                case "prev":
	                    return "Go To Previous Page <i class='icon-backward icon-white'></i>";
	                case "next":
	                    return "Go To Next Page <i class='icon-forward icon-white'></i>";
	                case "last":
	                    return "Go To Last Page <i class='icon-fast-forward icon-white'></i>";
	                case "page":
	                    return "Go to page " + page + " <i class='icon-file icon-white'></i>";
	                }
	            },
	            bootstrapTooltipOptions: {
	                html: true,
	                placement: 'bottom'
	            },
				itemTexts : function(type, page, current) {
					switch (type) {
					case "first":
						return "首页";
					case "prev":
						return "上一页";
					case "next":
						return "下一页";
					case "last":
						return "末页";
					case "page":
						return page;
					}
				}
			}
			$('#pageLimit').bootstrapPaginator(options);
	</script>
	<!-- PAGE END -->

	<!-- FOOTER START -->
	<div class="footer" style="background: #f5f6f5">
		<div class="container">
			<div class="footer-grids">
				<div class="col-md-4 footer-grid">
					<h3 class="title">友情链接</h3>
					<ul class="list-unstyled list-inline">
						<li><a href="http://news.sina.com.cn">新浪新闻</a></li>
						<li><a href="http://news.sohu.com">搜狐新闻</a></li>
						<li><a href="http://news.163.com">网易新闻</a></li>
						<li><a href="http://www.ifeng.com">凤凰网</a></li>

						<li><a href="http://news.qq.com">腾讯新闻</a></li>
						<li><a href="http://www.xinhuanet.com">新华网</a></li>
						<li><a href="http://www.people.com.cn">人民网</a></li>
						<li><a href="http://www.chinanews.com">中国新闻网</a></li>

						<li><a href="http://www.huanqiu.com">环球网</a></li>
						<li><a href="http://news.cctv.com">央视网新闻</a></li>
					</ul>
				</div>
				<div class="col-md-4 footer-grid">
					<h3 class="title">我们用到的技术</h3>
					<ul class="list-unstyled list-inline">
						<li><a href="https://en.wikipedia.org/wiki/Web_crawler">WebCrawler</a></li>
						<li><a
							href="https://en.wikipedia.org/wiki/Naive_Bayes_classifier">NaiveBayesClassifier</a></li>
						<li><a href="https://en.wikipedia.org/wiki/PageRank">PageRank</a></li>
						<li><a href="https://en.wikipedia.org/wiki/Inverted_index">InvertedIndex</a></li>
						<li><a href="http://spring.io/">Spring4</a></li>
						<li><a href="http://hibernate.org/orm/">Hibernate4</a></li>
						<li><a href="http://struts.apache.org/">Struts2</a></li>
						<li><a href="http://www.bootcss.com/">Bootstrap3</a></li>
					</ul>
				</div>
				<div class="col-md-4 footer-grid contact-grid">
					<h3 class="title">联系我们</h3>
					<ul class="list-unstyled">
						<li><span class="glyphicon glyphicon-map-marker"
							aria-hidden="true"></span><a
							href="http://labs.xjtudlc.com/labs/cloud.html">陕西省天地网技术重点实验室</a></li>
						<li><span class="glyphicon glyphicon-earphone"
							aria-hidden="true"></span>电话：+86 187 xxxx xxxx</li>
						<li><span class="glyphicon glyphicon-envelope"
							aria-hidden="true"></span>邮件：climber@mail.xjtu.edu.cn</li>
						<li><span class="glyphicon glyphicon-qrcode"
							aria-hidden="true"></span>微信：ClimberSearch2017</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="container">
			<div class="copy-left">
				<br>
				<p>Copyright © 2017.Cloud Computing Team All rights reserved.
					MOE KLINNS Lab and SKLMS Lab, Xi'an Jiaotong University</p>
				<br>
			</div>
		</div>
	</div>
	<!-- FOOTER END -->
	<script type="text/javascript">
		$(function(){
		  $("#search").click(function(){
			  if ($.trim($('#keyWords').val()) == '') {
					alert("请输入查询关键词");
					return false;
				}
			  window.location.href = "news-getNewsInPages?key=" +  $("#keyWords").val();
		  });
			//所需单据绑定回车键  
			$("#keyWords").keyup(function(event) {
				if (event.keyCode == 13) {
					if ($.trim($('#keyWords').val()) == '') {
						alert("请输入查询关键词");
						return false;
					}
					$("#search").click();
				}
			});
		});
	</script>
</body>
</html>