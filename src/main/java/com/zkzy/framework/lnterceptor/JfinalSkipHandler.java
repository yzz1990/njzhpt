package com.zkzy.framework.lnterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;
import com.jfinal.kit.StrKit;

public class JfinalSkipHandler extends Handler {

	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
		if (target.contains(".html")) {
			String pjax = request.getHeader("X-PJAX");
			request.setAttribute("ispjax", "true".equals(pjax));
			String Referer = request.getHeader("Referer");
			request.setAttribute("Referer", StrKit.isBlank(Referer) ? ""
					: Referer);
			if (target.contains("/framework/")) {
				request.setAttribute("_url_", target.replace("/framework/", ""));
				target = "/framework/toHTML";
			} else if (target.contains("/update/")) {
				request.setAttribute("_url_", target.replace("/update/", ""));
				target = "/update/toHTML";
			} else if (target.contains("/ui/")) {
				request.setAttribute("_url_", target.replace("/ui/", ""));
				target = "/ui/toHTML";
			} else if (target.contains("/courseapp/")) {
				request.setAttribute("_url_", target.replace("/courseapp/", ""));
				target = "/courseapp/toHTML";
			}
		} else if (target.contains(".do")) {
			String[] urls = target.split("/");
			request.setAttribute("_url_",
					urls[urls.length - 1].replace(".do", ""));
			if (target.contains("/framework/")) {
				target = "/framework/index";
			} else if (target.contains("/update/")) {
				target = "/update/index";
			} else if (target.contains("/courseapp/")) {
				target = "/courseapp/index";
			}
		}
		next.handle(target, request, response, isHandled);
	}
}
