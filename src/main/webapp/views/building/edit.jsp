<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<c:url var="buildingAPI" value="/api-admin-building"></c:url>
<c:url var="buildingURL" value="/admin-building"></c:url>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cập Nhật Tòa Nhà</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
	<div class="main-content">
		<div class="main-content-inner">
			<div class="breadcrumbs ace-save-state" id="breadcrumbs">
				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">Trang
							chủ</a></li>
					<li class="active">Thêm sản phẩm</li>
				</ul>
				<!-- /.breadcrumb -->
			</div>
			<div class="page-content">
				<form id="formEdit">
					<div class="row">
						<div class="col-xs-12">
							<div class="form-group">

								<label class="col-sm-3">Tên sản phẩm</label>
								<div class="col-sn-3">
									<input type="text" class="form-control input-sm" name="name"
										value="${model.name}" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 ">Diện tích sàn</label>
								<div class="col-sn-3">
									<input type="text" class="form-control input-sm"
										name="buildingArea" value="${model.buildingArea}" />
								</div>
							</div>
							<div class="form-group">
								<label>Quận hiện có</label>
								<div class="fg-line">
									<select class="form-control" id="sel1" name="district">
										<option value="">---Chọn quận---</option>
										<c:forEach var="item" items="${districts}">
											<option value="${item.key}"
												${item.key==model.district ? 'selected' : ''}>${item.value}</option>
										</c:forEach>
									</select>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 ">Phường</label>
								<div class="col-sn-3">
									<input type="text" class="form-control input-sm" name="ward"
										value="${model.ward}" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 ">Đường</label>
								<div class="col-sn-3">
									<input type="text" class="form-control input-sm" name="street"
										value="${model.street}" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 ">Hướng</label>
								<div class="col-sn-3">
									<input type="text" class="form-control input-sm"
										name="direction" value="${model.direction}" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 ">Hạng</label>
								<div class="col-sn-3">
									<input type="text" class="form-control input-sm" name="level"
										value="${model.level}" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 ">Số tầng hầm</label>
								<div class="col-sn-3">
									<input type="number" class="form-control input-sm"
										name="numberOfBasement" value="${model.numberOfBasement}" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 ">Diện tích thuê</label>
								<div class="col-sn-3">
									<input type="text" class="form-control input-sm"
										name="rentArea" value="${model.rentArea}" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 ">Giá thuê</label>
								<div class="col-sn-3">
									<input type="number" class="form-control input-sm"
										name="costrent" value="${model.costrent}" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-3 ">Tên quản lý</label>
								<div class="col-sn-3">
									<input type="text" class="form-control input-sm"
										name="managerName" value="${model.managerName}" />
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 ">Số điện thoại quản lý</label>
								<div class="col-sn-3">
									<input type="text" class="form-control input-sm"
										name="managerPhone" value="${model.managerPhone}" />
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-6">
									<label>Loại tòa nhà</label>
									<div class="fg-line">
										<c:forEach var="item" items="${buildingTypes}">
											<label class="checkbox-inline"> <input
												type="checkbox" value="${item.key}" name="buildingTypes"
												${fn:contains(fn:join(model.buildingTypes, ','),item.key) ? 'checked' : ''}>${item.value}</label>
										</c:forEach>

									</div>
								</div>

							</div>
						</div>
					</div>
					<input type="hidden" name="id" value="${model.id}" id="buildingId" />
				
					</form>
				<div class="row text-center btn-addsp">
					<c:if test = "${empty model.id}">
					<button class="btn btn-success" id="btnAddOrUpdateBuilding">Thêm sản phẩm</button>
					</c:if>
					<c:if test="${not  empty model.id}">
					<button class="btn btn-success"  id="btnAddOrUpdateBuilding">Cập nhật sản phẩm</button></c:if>
					
				</div>
				
				
			</div>
		</div>
	</div>
	<!-- /.main-content -->
	<script type="text/javascript">
		$("#btnAddOrUpdateBuilding").click(function(){
			btnAddOrUpdateBuilding();
		});
		
		
		function btnAddOrUpdateBuilding(){
			var buildingId = $('#buildingId').val();
			var formData=$('#formEdit').serializeArray();
			var data={};
			var buildingTypes = [];
			$.each(formData, function(index,v){
				if(v.name=='buildingTypes'){
					buildingTypes.push(v.value);
				}else{
					data[""+v.name+""]	 = v.value;
				}
			});
			
			data['buildingTypes']=buildingTypes;
			if(buildingId== ''){
				addBuilding(data);
			}
			else{
				editBuilding(data,buildingId);
			}
		}
		function addBuilding(data){
			$.ajax({
				url: '${buildingAPI}',
				data: JSON.stringify(data),
				type: 'POST',
				contentType:'application/json',
				dataType: 'json',
				success: function(data) {
					
					window.location.href = "${buildingURL}?action=EDIT&id="+data.id+"&message=insert_success";
				},
				
				error: function() {
					window.location.href = "${buildingURL}?action=LIST&message=error_system";
				}
				});
		}
		
		function editBuilding(data,id){
			$.ajax({
				url: '${buildingAPI}',
				data: JSON.stringify(data),
				type: 'PUT',
				contentType:'application/json',
			/* 	dataType: 'json', */
				success: function(data) {
					
					window.location.href = "${buildingURL}?action=EDIT&id="+id+"&message=update_success";
				},
				
				error: function() {
					window.location.href = "${buildingURL}?action=LIST&message=error_system";
				}
				});
		}
		
		
	</script>
</body>