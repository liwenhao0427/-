<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
 
<script>
</script>
 
<title>回收员管理</title>
 
<div class="workingArea">
    <h1 class="label label-info" >回收员管理</h1>
 
    <br>
    <br>
     
    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover  table-condensed">
            <thead>
                <tr class="success">
                    <th>ID</th>
                    <th>回收员名称</th>
                    <th>手机号名称</th>
                    <th>真实姓名</th>
                    <th>所在小区</th>
                    
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${us}" var="u">
                 
                <tr>
                    <td>${u.id}</td>
                    <td>${u.name}</td>
                    <td>${u.mobile}</td>
                    <td>${u.realName}</td>
                    <td>${u.area.name}</td>
                    
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
     
    <div class="pageDiv">
        <%@include file="../include/admin/adminPage.jsp" %>
    </div>
     
</div>
 
<%@include file="../include/admin/adminFooter.jsp"%>