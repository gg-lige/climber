<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>CLIMBER</title>
<link type="image/x-icon" rel="shortcut icon" href="images/favicon.png" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
<script src="js/jquery-2.1.1.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="css/component.css" />
<style type="text/css">
.fixed {
	width: 100%;
	height: 35px;
	line-height: 35px;
	position: fixed;
	bottom: 0px;
	left: 0px;
	font-size: 15px;
	color: #000;
	text-align: center;
}

.chinese {
	font-family: "Helvetica Neue", Helvetica, Arial, "Hiragino Sans GB",
		"Hiragino Sans GB W3", "Microsoft YaHei UI", "Microsoft YaHei",
		"WenQuanYi Micro Hei", sans-serif;
	font-size: 24px;
	color: #fff;
}

li {
	color: #ccc;
	display: inline;
}

a:link {
	color: #ccc;
} /* 未访问的链接 */
a:visited {
	color: #ccc;
} /* 已访问的链接 */
a:hover {
	color: #fff;
} /* 当有鼠标悬停在链接上 */
a:active {
	color: #ccc;
} /* 被选择的链接 */
a:hover {
	text-decoration: underline
}
</style>
<script src="js/modernizr.custom.js"></script>
</head>
<body>
	<div class="container1">
		<!-----start-wrap----->
		<div class="wrap">
			<!-----start-Content----->
			<div class="content">
				<div class="content-header">
					<h1>Search&nbsp;&nbsp;&nbsp;it,&nbsp;&nbsp;&nbsp;and&nbsp;&nbsp;&nbsp;you'll&nbsp;&nbsp;&nbsp;know.</h1>
					<p>Our website is dedicated to the search of news pages, but
						we'll do more in the future.</p>
				</div>

				<!---start-notifie----->
				<div class="notify">
					<form>
						<input id="keyWords" type="text" class="textbox"
							placeholder="KEY WORDS"> <input id="search"
							class="md-trigger md-setperspective button"
							data-modal="top-scorll" type="button" value="SEARCH"
							style="color: #fff; background: #3385ff;">
					</form>
				</div>
				<!---End-notifie----->
				<!---start-recommendation---->
				<!-- 
				<div class="chinese copy-right">
					<ul>
						<li>搜索热词：</li>
						<li><a href="news-getNewsInPages?key=热词1">热词1</a></li>
						<li><a href="news-getNewsInPages?key=热词2">热词2</a></li>
						<li><a href="news-getNewsInPages?key=热词3">热词3</a></li>
						<li><a href="news-getNewsInPages?key=热词4">热词4</a></li>
						<li><a href="news-getNewsInPages?key=热词5">热词5</a></li>
					</ul>
				</div>
				 -->
				<!---end-recommendation---->
				<!---start-copy-right---->
				<div class="fixed copy-right">
					<p>Copyright &copy; 2017.Cloud Computing Team All rights
						reserved.&nbsp;&nbsp;MOE KLINNS Lab and SKLMS Lab, Xi'an Jiaotong
						University</p>
				</div>
				<!---End-copy-right---->
			</div>
			<!-----End-Content----->
		</div>
		<!-----End-wrap----->
	</div>
	<script type="text/javascript">
		$(function() {
			$("#search").click(
					function() {
						if ($.trim($('#keyWords').val()) == '') {
							alert("请输入查询关键词");
							return false;
						}
						window.location.href = "news-getNewsInPages?key="
								+ $("#keyWords").val();
					});
			//所需单据绑定回车键  
			$("#keyWords").keydown(function(event) {
				if (event.keyCode == 13) {
					if ($.trim($('#keyWords').val()) == '') {
						alert("请输入查询关键词");
						return false;
					}
					//window.location.href = "news-getNewsInPages?key="+ $("#keyWords").val();
					//$("#search").click();
				}
			});

		});
	</script>

</body>
</html>