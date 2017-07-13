package com.zkzy.framework.bo;

import java.io.IOException;
import java.io.Writer;

import org.beetl.ext.web.WebErrorHandler;

public class MyError extends WebErrorHandler {
	@Override
	protected void render(Writer w, String title, String msg) {
		try {
			w.write("<div>" + title + "<br/>" + msg + "</div>");
		} catch (IOException localIOException) {
		}
	}
}
