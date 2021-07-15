package com.command;

import org.springframework.web.servlet.ModelAndView;

import com.dao.UserDetailDAO;

public class PlaceNameInViewCommand implements PermanentCommand {
	
	private String userName;
	private ModelAndView modelAndView;
	private UserDetailDAO userDetailDAO;
	
	public PlaceNameInViewCommand(ModelAndView modelAndView, String userName, UserDetailDAO userDetailDAO) {
		this.modelAndView = modelAndView;
		this.userName = userName;
		this.userDetailDAO = userDetailDAO;
	}
	
	public PlaceNameInViewCommand(ModelAndView modelAndView, String userName, UserDetailDAO userDetailDAO, boolean autoExecute) {
		this.modelAndView = modelAndView;
		this.userName = userName;
		this.userDetailDAO = userDetailDAO;
		if(autoExecute) {
			this.execute();
		}
	}

	@Override
	public void execute() {
		String fullName = this.userDetailDAO.getFirstName(this.userName);
		this.modelAndView.addObject("userFirstName", fullName);
	}
}
