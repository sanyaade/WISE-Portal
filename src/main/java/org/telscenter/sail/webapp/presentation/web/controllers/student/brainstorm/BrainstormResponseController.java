/**
 * Copyright (c) 2008 Regents of the University of California (Regents). Created
 * by TELS, Graduate School of Education, University of California at Berkeley.
 *
 * This software is distributed under the GNU Lesser General Public License, v2.
 *
 * Permission is hereby granted, without written agreement and without license
 * or royalty fees, to use, copy, modify, and distribute this software and its
 * documentation for any purpose, provided that the above copyright notice and
 * the following two paragraphs appear in all copies of this software.
 *
 * REGENTS SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE. THE SOFTWAREAND ACCOMPANYING DOCUMENTATION, IF ANY, PROVIDED
 * HEREUNDER IS PROVIDED "AS IS". REGENTS HAS NO OBLIGATION TO PROVIDE
 * MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS.
 *
 * IN NO EVENT SHALL REGENTS BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
 * SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
 * ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * REGENTS HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.telscenter.sail.webapp.presentation.web.controllers.student.brainstorm;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBElement;

import org.imsglobal.xsd.imsqti_v2p0.ImgType;
import org.imsglobal.xsd.imsqti_v2p0.SimpleChoiceType;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.telscenter.sail.webapp.domain.brainstorm.Brainstorm;
import org.telscenter.sail.webapp.domain.brainstorm.Questiontype;
import org.telscenter.sail.webapp.domain.workgroup.WISEWorkgroup;
import org.telscenter.sail.webapp.service.brainstorm.BrainstormService;
import org.telscenter.sail.webapp.service.workgroup.WISEWorkgroupService;

/**
 * @author patrick lawler
 *
 */
public class BrainstormResponseController extends AbstractController{
	
	private final static String BRAINSTORMID = "brainstormId";
	
	private final static String WORKGROUPID = "workgroupId";
	
	private final static String BRAINSTORM = "brainstorm";
	
	private final static String WORKGROUP = "workgroup";
	
	private final static String CHOICES = "choices";
	
	private final static String KEYS = "keys";
	
	private BrainstormService brainstormService;
	
	private WISEWorkgroupService workgroupService;

	/**
	 * @see org.springframework.web.servlet.mvc.AbstractController#handleRequestInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Brainstorm brainstorm = brainstormService.getBrainstormById(Long.parseLong(request.getParameter(BRAINSTORMID)));
		WISEWorkgroup workgroup = (WISEWorkgroup) workgroupService.retrieveById(Long.parseLong(request.getParameter(WORKGROUPID)));

		Map<String, Serializable> choiceMap = new LinkedHashMap<String, Serializable>();
		if(brainstorm.getQuestiontype()==Questiontype.SINGLE_CHOICE){
			choiceMap = BrainstormUtils.getChoiceMap(brainstorm.getQuestion().getChoices());
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject(BRAINSTORM, brainstorm);
		modelAndView.addObject(WORKGROUP, workgroup);
		modelAndView.addObject(CHOICES, choiceMap);
		modelAndView.addObject(KEYS, choiceMap.keySet());
		
		return modelAndView;
	}

	/**
	 * @param brainstormService the brainstormService to set
	 */
	public void setBrainstormService(BrainstormService brainstormService) {
		this.brainstormService = brainstormService;
	}

	/**
	 * @param workgroupService the workgroupService to set
	 */
	public void setWorkgroupService(WISEWorkgroupService workgroupService) {
		this.workgroupService = workgroupService;
	}

}
