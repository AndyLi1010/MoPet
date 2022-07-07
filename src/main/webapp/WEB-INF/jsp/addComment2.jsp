<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:choose>
	<c:when test="${empty loginOK}">
		<jsp:include page="layout/frontendBar3.jsp" />
	</c:when>
	<c:otherwise>
		<jsp:include page="layout/frontendBar.jsp" />
	</c:otherwise>
</c:choose>
<c:if test="${empty loginOK}">
	<c:redirect url="/login" />
</c:if>
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />
<main role="main" class="col-md-10 ml-sm-auto col-lg-10 px-md-4">
</main>
<br />
<br />
<div>

	<div class="row justify-content-center">
		<div class="text-white bg-secondary text-center" style="width: 1000px">
			<h1>新增貼文</h1>
		</div>
	</div>

	<div class="row justify-content-center">
		<div class="col-8">
			<div
				class="card text-center shadow-lg p-3 mb-5 bg-white rounded font-italic">
				<div class="card-body">

					<form:form class="form" method="post"
						action="${contextRoot}/comments/add2" modelAttribute="comments"
						enctype="multipart/form-data">
 
 
						<input type="hidden" name="name2" value="${member.memberName}"  />
						

<<<<<<< HEAD
=======

						<input type="hidden" name="name2" value="${member.memberName}"  />
						

>>>>>>> 202207071050
						<div class="form-group font-weight-bold ">
							<img src="${member.memberProfile}" width="5%" height="5%"/><c:out value="${member.memberName}"/>
						</div><br/>
						

						<div class="font-weight-bold">標題</div>
						<div class="form-group">
							<form:input path="title" class="form-control" />
						</div><br/>
						
						<div class="font-weight-bold">內容</div>
						<div class="form-group">
							<form:textarea path="content" class="form-control" />
						</div><br/>
						


						<div class="font-weight-bold">上傳圖片</div>
						<div class="form-group">
							<div id="upload"></div>
							<br> <input type="file" name="comimg" id="comimg" />
						</div>
						
						

						<input onclick="return confirm('確認發布?')" type="submit"
							name="submit" value="發文">
					</form:form>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(function() {

		function preView(preDIV) {
			var files = preDIV.files;
			for (var i = 0; i < files.length; i++) {
				var data = URL.createObjectURL(files[i]);
				$('<img class="img-item" src="'+data+'" />').appendTo(
						$("#upload"));
			}
		}

		$(':file').change(function() {
			console.log(this);
			preView(this);
		});
	});
</script>
<div style="height: 100px"></div>
<jsp:include page="layout/footer.jsp" />