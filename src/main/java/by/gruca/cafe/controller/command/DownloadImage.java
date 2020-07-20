package by.gruca.cafe.controller.command;

import by.gruca.cafe.configuration.UrlManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

public class DownloadImage implements ActionCommand {
    Logger logger = LogManager.getLogger(DownloadImage.class);
    @Override
    public String execute(HttpServletRequest req) {
        String uploadPath = req.getServletContext().getContextPath();
        logger.info(uploadPath);
        File uploadDir = new File(uploadPath);
        String fileName;
        try {
            for (Part part : req.getParts()
            ) {
                fileName = part.getSubmittedFileName();
                logger.info(fileName);
                part.write(uploadPath + File.separator + fileName);
            }
        } catch (IOException | ServletException e) {
  logger.error(e);
        }
        return UrlManager.getProperty("path.page.main");
    }

}
