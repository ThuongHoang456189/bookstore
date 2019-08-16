package pq.jdev.b001.bookstore.listbooks.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pq.jdev.b001.bookstore.users.service.UserService;

@Controller

public class UploadController {

	@Autowired
	private UserService userService;

	// Save the uploaded file to this folder
	private static String UPLOADED_FOLDER = "C:/Users/duyhi/Desktop/booktest/bookstore/src/main/resources/static/images/";

	@GetMapping("/upload")
	public String viewUpLoad(Authentication authentication, ModelMap map) {
		if (authentication != null) {
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			List<String> roles = new ArrayList<String>();
			for (GrantedAuthority a : authorities) {
				roles.add(a.getAuthority());
			}

			if (isUser(roles)) {
				map.addAttribute("header", "header_user");
				map.addAttribute("footer", "footer_user");

			} else if (isAdmin(roles)) {
				map.addAttribute("header", "header_admin");
				map.addAttribute("footer", "footer_admin");
			}
		} else {
			map.addAttribute("header", "header_login");
			map.addAttribute("footer", "footer_login");
		}
		return "upload";
	}

	@PostMapping("/upload") // //new annotation since 4.3
	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		try {

			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/";
	}
	
	
	
	//Down images main
	
//	@PostMapping("/upload") // //new annotation since 4.3
//	public String singleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, HttpServletResponse response) {
//
//		try {
//
//			
//			String fullPath = UPLOADED_FOLDER + "/" + file.getOriginalFilename();
//			File file2 = new File(fullPath);	
//
//			if (!file2.exists()) {
//				String errorMessage = "Sorry. The file you are looking for does not exist";
//				OutputStream outputStream = response.getOutputStream();
//				outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
//				outputStream.close();
//				return "redirect:/upload";
//			}
//
//			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
//			if (mimeType == null) {
//				System.out.println("mimetype is not detectable, will take default");
//				mimeType = "application/octet-stream";
//			}
//
//			response.setContentType(mimeType);
//
//			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file2.getName() + "\""));
//
//			response.setContentLength((int) file2.length());
//
//			InputStream inputStream = new BufferedInputStream(new FileInputStream(file2));
//
//			FileCopyUtils.copy(inputStream, response.getOutputStream());
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return "redirect:/";
//	}


	private boolean isUser(List<String> roles) {
		if (roles.contains("ROLE_EMPLOYEE")) {
			return true;
		}
		return false;
	}

	private boolean isAdmin(List<String> roles) {
		if (roles.contains("ROLE_ADMIN")) {
			return true;
		}
		return false;
	}

//    @GetMapping("/uploadStatus")
//    public String uploadStatus() {
//        return "uploadStatus";
//    }

}