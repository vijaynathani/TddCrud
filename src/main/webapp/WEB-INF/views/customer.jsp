<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Maintain Customers</title>
</head>
<body>
	<form method="post" action="customer">
		<p><label>${customer.numberDescription}: 
			<input type="text" name="${customer.numberField}" 
				size="${customer.numberScreenLength}" value="${customer.number}" />
		</label></p>
		<p><label>${customer.nameDescription}:
			<input type="text" name="${customer.nameField}"  
				size="${customer.nameScreenLength}" value="${customer.name}" />
		</label></p>
		<p><label>${customer.addressDescription}:
			<textarea name="${customer.addressField}" 
				rows="${customer.addressScreenRows}" cols="${customer.addressScreenCols}"
			>${customer.address}</textarea>
		</label></p>
		<p><label>${customer.mobileDescription}:
			<input type="text" name="${customer.mobileField}"  
				size="${customer.mobileScreenLength}" value="${customer.mobile}" />
		</label></p>
		<p> 
			<input type="submit" value="Add" name="Add" /> &nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" value="Chg" name="Chg" /> &nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" value="View" name="View" /> &nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" value="Del" name="Del" />
		</p>
	</form>
	<c:if test="${message != ''}">
	Message: <c:out value='${message}' />
	</c:if>
</body>
</html>
