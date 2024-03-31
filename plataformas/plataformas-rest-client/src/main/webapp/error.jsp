<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${empty header['X-Requested-With']}">
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Mensaje de Error</title>

	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"></script>

	<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body class="container">
</c:if>
	<div id="errorModal" class="modal" tabindex="-1">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title">Mensaje de Error</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
	      </div>
	      <div class="modal-body">
	        <p>${requestScope.error}</p>
	      </div>
	    </div>
	  </div>
	</div>
    <script type="text/javascript">
    $(function(){
        $('#errorModal').modal('show');
    });
	</script>
<c:if test="${empty header['X-Requested-With']}">
</body>
</html>
</c:if>