package controllers;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.validator.routines.checkdigit.CheckDigitException;
import org.aspectj.asm.internal.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Brand;
import domain.Customer;
import domain.Manager;
import forms.CustomerForm;
import forms.ManagerForm;
import services.ManagerService;

@Controller
@RequestMapping("/manager")
public class ManagerController extends AbstractController {

	@Autowired
	private ManagerService managerService;
	
	// Constructors -----------------------------------------------------------

	public ManagerController() {
		super();
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {

		ModelAndView result;

		ManagerForm managerForm = managerService.createForm();
		result = this.createModelAndView(managerForm);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid ManagerForm managerForm, BindingResult binding) throws CheckDigitException {

		ModelAndView result = new ModelAndView();

		Manager manager;

		manager = managerService.reconstruct(managerForm, binding);
		if (binding.hasErrors()) {
			result = createModelAndView(managerForm);
		} else {
			try {
				managerService.save(manager);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = createModelAndView(managerForm, "manager.commit.error");
			}
		}

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = false) final String successMessage) {
		ModelAndView result;
		Manager manager;
		ManagerForm managerForm;

		manager = this.managerService.findByPrincipal();
		managerForm = this.managerService.toFormObject(manager);
		managerForm.setAcceptCondition(true);

		result = this.editModelAndView(managerForm);
		result.addObject("successMessage", successMessage);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editSave(@Valid final ManagerForm managerForm, final BindingResult binding) throws CheckDigitException {
		ModelAndView result = new ModelAndView();
		final Manager manager;

		try {
			manager = this.managerService.reconstruct(managerForm, binding);

			if (binding.hasErrors()) {
				result = this.editModelAndView(managerForm, "chorbi.creditCard.error");
			} else {
				try {
					this.managerService.save(manager);
					result = new ModelAndView("redirect:/manager/edit.do");
					result.addObject("successMessage", "manager.edit.success");
				} catch (final Throwable oops) {
					result = this.editModelAndView(managerForm, "manager.commit.error");
				}
			}

		} catch (final Throwable oops) {
			result = this.editModelAndView(managerForm, "manager.commit.error");
		}

		return result;
	}
	
	protected ModelAndView createModelAndView(final ManagerForm managerForm) {
		return this.createModelAndView(managerForm, null);
	}

	protected ModelAndView createModelAndView(final ManagerForm managerForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("manager/register");
		result.addObject("managerForm", managerForm);
		result.addObject("message", message);
		return result;
	}
	
	protected ModelAndView editModelAndView(final ManagerForm managerForm) {
		final ModelAndView result = this.editModelAndView(managerForm, null);
		return result;
	}

	protected ModelAndView editModelAndView(final ManagerForm managerForm, final String message) {
		ModelAndView result;
		result = new ModelAndView("manager/edit");
		result.addObject("managerForm", managerForm);
		result.addObject("message", message);
		result.addObject("requestURI", "manager/edit.do");

		return result;
	}
}
