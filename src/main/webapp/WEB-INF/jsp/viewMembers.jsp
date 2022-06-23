<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />
<jsp:include page="layout/navbar2.jsp" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
</style>
<title>All Members</title>
</head>
<body>
	<main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4">
		<div
			class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
			<h1 class="h2">會員管理</h1>
			<div class="btn-toolbar mb-2 mb-md-0">
				<a href="${contextRoot}/member"><button type="button"
						class="btn btn-sm btn-outline-secondary">
						<span data-feather="calendar"></span> 新增會員
					</button></a>
			</div>
		</div>
	</main>
	<div
		class="col-md-9 ml-sm-auto col-lg-10 px-md-4 pt-3 pb-2 mb-3 border-bottom">
		<div class="container">
			<div>
				<form class="d-flex" role="search">
					<input class="form-control me-2" type="search" placeholder="輸入姓名尋找"
						aria-label="Search">
					<button class="btn btn-dark" type="submit">Search</button>
				</form>


				<br />
				<table class="table">
					<thead class="thead-dark">
						<tr>
							<th scope="col">Avatar</th>
							<th scope="col">Name</th>
							<th scope="col">Account</th>
							<th scope="col">Phone</th>
							<th scope="col">Address</th>
							<th scope="col">編輯</th>
							<th scope="col">刪除</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="member" items="${page.content}">
							<tr>
								<td style="width: 100px"><img alt="avatar"
									src="${member.memberProfile}" width="50%" height="50%"></td>
								<td><c:out value="${member.memberName}" /></td>
								<td scope="row" style="width: 50px"><c:out
										value="${member.memberEmail}" />
								</th>
								<td><c:out value="${member.memberTel}" /></td>
								<td style="width: 200px"><c:out
										value="${member.memberAddress}" /></td>
								<td><a href="${contextRoot}/member/${member.id}"><button
											class="btn btn-info">編輯</button></a></td>
								<td><a onclick="return confirm('真的要刪除嗎')"
									href="${contextRoot}/member/delete/${member.id}"><button
											class="btn btn-danger">刪除</button></a></td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
				<nav aria-label="...">
					<div class="row justify-content-center">
						<div class="col-9">
							<ul class="pagination">
								<!-- 			<li class="page-item"><a class="page-link disabled">Previous</a></li> -->
								<c:forEach var="pageNumber" begin="1" end="${page.totalPages}">

									<c:choose>
										<c:when test="${page.number != pageNumber-1}">
											<li class="page-item"><a class="page-link"
												href="${contextRoot}/members/all?p=${pageNumber}"><c:out
														value="${pageNumber}" /></a></li>
										</c:when>

										<c:otherwise>
											<li class="page-item disabled"><a class="page-link"><c:out
														value="${pageNumber}" /></a></li>
										</c:otherwise>
									</c:choose>

									<%-- 			<c:if test="${pageNumber != page.totalPages}"> --%>
									<!--    | -->
									<%--    </c:if> --%>

								</c:forEach>
								<!-- 				<li class="page-item"><a class="page-link disabled">Next</a></li> -->
							</ul>
						</div>
					</div>
				</nav>
			</div>
		</div>
	</div>

</body>
</html>