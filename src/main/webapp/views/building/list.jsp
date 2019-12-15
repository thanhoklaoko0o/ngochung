<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/common/taglib.jsp"%>
<c:url var="buildingURL" value="/admin-building"></c:url>
<c:url var="buildingAPI" value="/api-admin-building"></c:url>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Danh sách tòa nhà</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>
	<div class="main-content">
		<div class="main-content-inner">
			<div class="breadcrumbs ace-save-state" id="breadcrumbs">
				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">Trang
							chủ</a></li>
					<li>Danh Sách Sản Phẩm</li>
				</ul>
				<!-- /.breadcrumb -->
			</div>
			<div class="page-content">
				<div class="row">
					<div class="col-xs-12">

						<div class="widget-box table-filter">
							<div class="widget-header">
								<h4 class="widget-title">Tìm kiếm</h4>
								<div class="widget-toolbar">
									<a href="#" data-action="collapse"> <i
										class="ace-icon fa fa-chevron-up"></i>
									</a>
								</div>
							</div>
							<div class="widget-body">
								<div class="widget-main">

									<!-- start form -->
									<form action="${buildingURL}" method="get" id="formSubmit" >


										<div class="form-horizontal">

											<div class="form-group">

												<div class="col-sm-6">
													<label>Tên sản phẩm</label>
													<div class="fg-line">
														<input type="text" class="form-control input-sm"
															name="name" value="${model.name}" />
													</div>
												</div>
											</div>
											<div class="form-group">
												<div class="col-sm-2">
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
												<div class="col-sm-4">
													<label>Phường</label>
													<div class="fg-line">
														<input type="text" class="form-control input-sm"
															name="ward" value="${model.ward}" />
													</div>
												</div>
												<div class="col-sm-4">
													<label>Đường</label>
													<div class="fg-line">
														<input type="text" class="form-control input-sm"
															name="street" value="${model.street}" />
													</div>
												</div>
											</div>
											<div class="form-group">
												<div class="col-sm-3">
													<label>Diện tích sàn</label>
													<div class="fg-line">
														<input type="text" class="form-control input-sm"
															name="buildingArea" value="${model.buildingArea}" />
													</div>
												</div>
												<div class="col-sm-3">
													<label>Số tầng hầm</label>
													<div class="fg-line">
														<input type="text" class="form-control input-sm"
															name="numberOfBasement" value="${model.numberOfBasement}" />
													</div>
												</div>
												<div class="col-sm-3">
													<label>Hướng</label>
													<div class="fg-line">
														<input type="text" class="form-control input-sm"
															name="direction" value="${model.direction}" />
													</div>
												</div>
												<div class="col-sm-3">
													<label>Hạng</label>
													<div class="fg-line">
														<input type="text" class="form-control input-sm"
															name="level" value="${model.level}" />
													</div>
												</div>
											</div>
											<div class="form-group">
												<div class="col-sm-3">
													<label>Diện tích từ</label>
													<div class="fg-line">
														<input type="text" class="form-control input-sm"
															name="areaRentFrom" value="${model.areaRentFrom}" />
													</div>
												</div>
												<div class="col-sm-3">
													<label>Diện tích đến</label>
													<div class="fg-line">
														<input type="text" class="form-control input-sm"
															name="areaRentTo" value="${model.areaRentTo}" />
													</div>
												</div>
												<div class="col-sm-3">
													<label>Giá thuê từ</label>
													<div class="fg-line">
														<input type="text" class="form-control input-sm"
															name="costRentFrom" value="${model.costRentFrom}" />
													</div>
												</div>
												<div class="col-sm-3">
													<label>Giá thuê đến</label>
													<div class="fg-line">
														<input type="text" class="form-control input-sm"
															name="costRentTo" value="${model.costRentTo}" />
													</div>
												</div>
											</div>
											<div class="form-group">
												<div class="col-sm-4">
													<label>Tên quản lý</label>
													<div class="fg-line">
														<input type="text" class="form-control input-sm"
															name="managerName" value="${model.managerName}" />
													</div>
												</div>
												<div class="col-sm-4">
													<label>Điện thoại quản lý</label>
													<div class="fg-line">
														<input type="text" class="form-control input-sm"
															name="managerPhone" value="${model.managerPhone}" />
													</div>
												</div>
												<!-- <div class="col-sm-4">
												<label>Nhân viên phụ trách</label>
												<div class="fg-line">
													<select class="form-control" id="sel1">
														<option>---Chọn nhân viên phụ trách---</option>
														<option>Quận 1</option>

													</select>
												</div>
											</div>  -->

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

											<input type="hidden" name="action" value="LIST">
											<div class="form-group">
												<div class="col-sm-6">
													<button type="submit" class="btn btn-sm btn-success">
														Tìm kiếm <i class="fa fa-arrow-right"></i>
													</button>
												</div>

											</div>
										</div>
									<input type="hidden" value="" id="page" name="page" />
									<input type="hidden" value="" id="maxPageItem" name="maxPageItem" />
									<input type="hidden" value="" id="sortName" name="sortName" />
									<input type="hidden" value="" id="sortBy" name="sortBy" />
									</form>
									<!-- end form  -->

									<!-- button add, delete -->


									<!--  admin-building?action=EDIT -->



								</div>
							</div>
						</div>

					</div>
				</div>
				<div class="table-btn-controls">
					<div class="pull-right tableTools-container">
						<div class="dt-buttons btn-overlap btn-group">
							<c:url var="searchURL" value="${urlMapping}" />
							<a flag="info"
								class="dt-button buttons-colvis btn btn-white btn-primary btn-bold"
								data-toggle="tooltip" title='Thêm tòa nhà'
								href='<c:url value="/admin-building?action=EDIT"/>'> <span><i
									class="fa fa-plus-circle bigger-110 purple"></i></span>
							</a>
							<button type="button" id="btnDelete"
								class="dt-button buttons-html5 btn btn-white btn-primary btn-bold"
								data-toggle="tooltip" title='Xóa tòa nhà'>
								<span><i class="fa fa-trash-o bigger-110 pink"></i></span>
							</button>
						</div>
					</div>
				</div>
				<!-- table -->
				<div class="row">
					<div class="col-xs-12">
						<table class="table">
							<thead>
								<tr>
								    <th><input type="checkbox" value="" id="checkAll" ></th>
									<th>Tên sản phẩm</th>
									<th>Địa chỉ</th>
									<th>Diện tích sàn</th>
									<th>Số tầng hầm</th>
									<th>Đơn vị</th>
									<th>Giá thuê</th>
									<th>Diện tích thuê</th>
									<th>Loại tòa nhà</th>
									<th>Tên quản lý</th>
									<th>SĐT quản lý</th>
									<th>Thao tác</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="item" items="${model.listResults}">
									<tr>
									    <th> 
											<input type="checkbox" value="${item.id}" id="checkbox_${item.id}"  >				
								    	</th>
										<td>${item.name}</td>
										<td>${item.address}</td>
										<td>${item.buildingArea}</td>
										<td>${item.numberOfBasement}</td>
										<td>${item.costrent}</td>
										<td>${item.costDescription}</td>
										<td>${item.rentArea}</td>
										<td>${item.type}</td>
										<td>${item.managerName}</td>
										<td>${item.managerPhone}</td>
										<td><a class="btn btn-xs btn-primary btn-edit"
											data-toggle="tooltip" 
											title='Cập nhật tòa nhà'
											 href='<c:url value="/admin-building?action=EDIT&id=${item.id}"/>'> <i
												class="fa fa-pencil-square-o" aria-hidden="true"></i>

										</a></td>
									</tr>


								</c:forEach>


							</tbody>
						</table>
					</div>
				</div>
				<div class="container">
    <nav aria-label="Page navigation">
        <ul class="pagination" id="pagination"></ul>
    </nav>
</div>
				
			</div>
		</div>
	</div>
	<!-- /.main-content -->
	<script type="text/javascript">
		$('#btnDelete').click(function name(){
			var dataArray=$('tbody input[type=checkbox]:checked').map(function(){
				return $(this).val();
			}).get();
			var data={};
			data['ids']=dataArray;
			deleteBuilding(data);
		});
		
		function deleteBuilding(data){
			$.ajax({
				url: '${buildingAPI}',
				data: JSON.stringify(data),
				type: 'DELETE',
				contentType:'application/json',
				
				success: function(data) {
					
					window.location.href = "${buildingURL}?action=LIST&message=delete_success";
				},
				
				error: function() {
					window.location.href = "${buildingURL}?action=LIST&message=error_system";
				}
				});
		}
		
		var totalPage=${model.totalPage};
		var currentPage = ${model.page};
		  
		  
		 $(function () {
		        window.pagObj = $('#pagination').twbsPagination({
		            totalPages: totalPage,
		            visiblePages: 5,
		            startPage:currentPage,
		            onPageClick: function (event, page) {
		             //   console.info(page);
		           
		             if(currentPage != page){
		            	 $('#page').val(page);
		            	 $('#maxPageItem').val(5);
		            	 $('#sortName').val('name');
		            	 $('#sortBy').val('ASC');
		            	 $('#formSubmit').submit();
		             }
		                
		            }
		        });
		    });
	</script>
	
	
	
	
	
</body>