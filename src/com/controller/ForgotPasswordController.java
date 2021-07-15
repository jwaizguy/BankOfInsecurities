package com.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.constants.ControllerConstants;
import com.controller.types.AbstractController;
import com.dao.UserCredentialDAO;
import com.orm.UserCredential;

@Controller
@RequestMapping(value={ControllerConstants.FORGOT_PASSWORD_PAGE_DIRECTORY})
public class ForgotPasswordController extends AbstractController 
{
	private static Logger logger = Logger.getLogger(ForgotPasswordController.class);
	
	@Autowired
	private UserCredentialDAO userCredentialDAO; // DAO object will act as an interface to the Database

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView home(
			HttpServletResponse response, 
			HttpServletRequest request)
	{	
		List<UserCredential> userCredentialList=userCredentialDAO.getUserCredentialList();
		ModelAndView modelAndView = new ModelAndView("forgot_password");
		request.setAttribute("userCredentialList",userCredentialList);
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.POST)
    public String submit(
    		HttpServletResponse response, 
    		HttpServletRequest request, 
    		ModelMap modelMap, 
    		HttpServletRequest req,
    		@RequestParam(value="id", required=false) String id,
    		@RequestParam(value="question", required=false) String question,
    		@RequestParam(value="answer", required=false) String answer) {
		List<UserCredential> userCredentialList=userCredentialDAO.getUserCredentialList();
		request.setAttribute("userCredentialList",userCredentialList);
        if (!StringUtils.isEmpty(id) && !StringUtils.isEmpty(question) && !StringUtils.isEmpty(answer)) {
        	String password="";
        	for(UserCredential user:userCredentialList){
        		if(user.getId().equals(id) && user.getSecurity_question().equals(question) && user.getSecurity_answer().equals(answer)){
        			password=user.getPassword();
        			break;
        		}
        	}
        	if(!StringUtils.isEmpty(password)){
        		modelMap.put("error", "Your Password is:"+password);
        	}else{
        		modelMap.put("error", "Sorry,the answer is wrong!");
        	}
        	return "forgot_password";
        } else {
            modelMap.put("error", "The information must not be empty!");
            return "forgot_password";
        }
     }
}
