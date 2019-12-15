package com.laptrinhweb.controller;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.laptrinhweb.builder.BuildingSearchBuilder;
import com.laptrinhweb.dto.BuildingDTO;
import com.laptrinhweb.paging.PageRequest;
import com.laptrinhweb.paging.Pageble;
import com.laptrinhweb.paging.Sorter;
import com.laptrinhweb.service.IBuildingService;
import com.laptrinhweb.service.impl.BuildingService;
import com.laptrinhweb.utils.DataUtils;
import com.laptrinhweb.utils.FormUtil;

@WebServlet(urlPatterns = { "/admin-building" })
public class BuildingController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	@Inject
	private IBuildingService buildingService;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		BuildingDTO model = FormUtil.toModel(BuildingDTO.class, req);
		String action = req.getParameter("action");
		String url = "";

		if (action.equals("LIST")) {
			url = "views/building/list.jsp";
			BuildingSearchBuilder builder = initBuildingBuilder(model);
			Sorter sorter=new Sorter(null, null);
			if(StringUtils.isNotBlank(model.getSortName())) {
				sorter=new Sorter(model.getSortName(), model.getSortBy());
			}
			Pageble pageble = new PageRequest(model.getPage(), model.getMaxPageItem(), sorter);
			model.setTotalItem(buildingService.getTotalItem(builder));
			
			model.setTotalPage((int)Math.ceil((double)model.getTotalItem() / model.getMaxPageItem()));

			model.setListResults(buildingService.findAll(builder, pageble));
		} else if (action.equals("EDIT")) {
			if(model.getId() != null) {
				model=buildingService.findById(model.getId());
			}
			url = "views/building/edit.jsp";
		}
		req.setAttribute("districts", DataUtils.getDistricts());
		req.setAttribute("buildingTypes", DataUtils.getBuildingType());
		req.setAttribute("model", model);
		RequestDispatcher dispatcher = req.getRequestDispatcher(url);
		dispatcher.forward(req, resp);
	}

	private BuildingSearchBuilder initBuildingBuilder(BuildingDTO model) {
		BuildingSearchBuilder builder = new BuildingSearchBuilder.Builder().setName(model.getName())
				// .setNumberOfBasement(model.getNumberOfBasement())
				.setDistrict(model.getDistrict()).setNumberOfBasement(model.getNumberOfBasement())
				.setBuildingArea(model.getBuildingArea()).setWard(model.getWard()).setStreet(model.getStreet())
				.setAreaRentFrom(model.getAreaRentFrom()).setAreaRentTo(model.getAreaRentTo())
				.setCostRentFrom(model.getCostRentFrom()).setCostRentTo(model.getCostRentTo())
				.setBuildingTypes(model.getBuildingTypes()).build();

		return builder;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		super.doPut(req, resp);
	}

}
