package com.shopme.admin.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shopme.admin.paging.PagingAndShortingParam;
import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.admin.setting.SettingService;
import com.shopme.common.entity.Setting;


import jakarta.servlet.http.HttpServletRequest;

@Controller
public class OrderController {

	private String defaultRedirectURL = "redirect:/orders/page/1?sortField=orderTime&sortDir=desc";
	
	@Autowired private OrderService orderService;
	@Autowired private SettingService settingService;
	
	@GetMapping("/orders")
	public String listFirstPage() {
		return defaultRedirectURL;
	}
	
	@GetMapping("/orders/page/{pageNum}")
	public String listByPage(
			@PagingAndShortingParam(listName = "listOrders",
			moduleURL = "/orders") PagingAndSortingHelper helper,
			@PathVariable(name = "pageNum") int pageNum,HttpServletRequest request) {
		
		orderService.listByPage(pageNum,helper);
		loadCurrencySetting(request);

		return "orders/orders";
	}
	
	private void loadCurrencySetting(HttpServletRequest request) {
		List<Setting> currencySettings = settingService.getCurrencySettings();
		
		for(Setting setting : currencySettings) {
			request.setAttribute(setting.getKey(), setting.getValue());
		}
	}

	/*
	 * @GetMapping("/shipping_rates/new") public String newRate(Model model) {
	 * List<Country> listCountries = service.listAllCountries();
	 * 
	 * model.addAttribute("rate", new ShippingRate());
	 * model.addAttribute("listCountries", listCountries);
	 * model.addAttribute("pageTitle", "New Rate");
	 * 
	 * return "shipping_rates/shipping_rate_form"; }
	 * 
	 * @PostMapping("/shipping_rates/save") public String saveRate(ShippingRate
	 * rate, RedirectAttributes ra) {
	 * 
	 * try { service.save(rate);
	 * ra.addFlashAttribute("message","The shipping has been saved successfully");
	 * }catch(ShippingRateAlreadyExistsException ex) {
	 * ra.addFlashAttribute("message",ex.getMessage());
	 * 
	 * } return defaultRedirectURL; }
	 * 
	 * @GetMapping("/shipping_rates/edit/{id}") public String
	 * editRate(@PathVariable("id") Integer id, Model model, RedirectAttributes ra)
	 * { try { ShippingRate rate = service.get(id); List<Country> listCountries =
	 * service.listAllCountries();
	 * 
	 * model.addAttribute("listCountries", listCountries);
	 * model.addAttribute("rate", rate); model.addAttribute("pageTitle",
	 * "Edit Rate (ID: " + id + ")");
	 * 
	 * return "shipping_rates/shipping_rate_form";
	 * 
	 * } catch (ShippingRateNotFoundException e) { ra.addFlashAttribute("message",
	 * e.getMessage());
	 * 
	 * return defaultRedirectURL; }
	 * 
	 * }
	 * 
	 * @Transactional
	 * 
	 * @GetMapping("/shipping_rates/cod/{id}/enabled/{supported}") public String
	 * updateCODSurport(@PathVariable("id") Integer id,
	 * 
	 * @PathVariable(name = "supported") Boolean supported, Model model,
	 * RedirectAttributes ra) {
	 * 
	 * try { service.updateSODSurpport(id, supported);
	 * ra.addFlashAttribute("message", "COD surpport for shipping rate ID " + id +
	 * " has been updated."); }catch (ShippingRateNotFoundException e) {
	 * ra.addFlashAttribute("message", e.getMessage()); } return defaultRedirectURL;
	 * }
	 * 
	 * @GetMapping("/shipping_rates/delete/{id}") public String
	 * deleteRate(@PathVariable(name="id") Integer id,Model model,
	 * RedirectAttributes ra){ try { service.delete(id);
	 * ra.addFlashAttribute("message", "The shipping rate ID " + id +
	 * " has been deleted successfully"); }catch(ShippingRateNotFoundException ex) {
	 * ra.addFlashAttribute("message", ex.getMessage()); } return
	 * defaultRedirectURL; }
	 */
	
}
