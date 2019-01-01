package io.github.oguzhancevik.technicalservice.servlet;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.github.oguzhancevik.technicalservice.util.Const;
import io.github.oguzhancevik.technicalservice.util.UtilLog;

public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Urlden alınan name paremetresine göre resimi Const.filePathImage adresinden
	 * alır.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		if (request.getRequestURI().matches("/image")) {
			response.setContentType("image/jpeg");
		}

		String name = request.getParameter("name");
		if (name == null || name.equals("")) {
			return;
		}

		try (OutputStream out = response.getOutputStream()) {
			File file = new File(Const.filePathImage + name);
			Path path = file.toPath();
			Files.copy(path, out);

			out.flush();
		} catch (IOException e) {
			UtilLog.log(e);
		}

	}
}