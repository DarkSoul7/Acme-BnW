
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BalanceSearchEngineService;
import forms.BalanceSearchEngineForm;
import forms.CustomerForm;

@Controller
@RequestMapping("/balanceSearchEngine")
public class BalanceSearchEngineController extends AbstractController {

	//Related services

	@Autowired
	private BalanceSearchEngineService	balanceSearchEngineService;


	//Constructor

	public BalanceSearchEngineController() {
		super();
	}

	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String errorMessage, @RequestParam(required = false) final String name, @RequestParam(required = false) final String surname,
		@RequestParam(required = false) final String nid) {
		ModelAndView result;

		final Collection<CustomerForm> customers = this.balanceSearchEngineService.getGlobalBalance(name, surname, nid);
		final BalanceSearchEngineForm balanceSearchEngineForm = new BalanceSearchEngineForm();

		result = new ModelAndView("balanceSearchEngine/list");
		result.addObject("customers", customers);
		result.addObject("formRequestURI", "balanceSearchEngine/search.do");
		result.addObject("tableRequestURI", "balanceSearchEngine/list.do");
		result.addObject("errorMessage", errorMessage);
		result.addObject("balanceSearchEngineForm", balanceSearchEngineForm);

		return result;
	}

	//Search

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "save")
	public ModelAndView search(@Valid final BalanceSearchEngineForm balanceSearchEngineForm, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("redirect:/balanceSearchEngine/list.do");
			result.addObject("errorMessage", "balanceSearchEngine.search.error");
		} else {
			try {
				result = new ModelAndView("redirect:/balanceSearchEngine/list.do");
				result.addObject("name", balanceSearchEngineForm.getName());
				result.addObject("surname", balanceSearchEngineForm.getSurname());
				result.addObject("nid", balanceSearchEngineForm.getNid());
			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/balanceSearchEngine/list.do");
				result.addObject("errorMessage", "balanceSearchEngine.search.error");
			}
		}
		return result;
	}
}
