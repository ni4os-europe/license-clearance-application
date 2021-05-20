package gr.uoa.di.madgik.lcapp.pdf;


import com.lowagie.text.DocumentException;
import gr.uoa.di.madgik.lcapp.security.UserPrincipal;
import gr.uoa.di.madgik.lcapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static com.itextpdf.text.pdf.BaseFont.EMBEDDED;
import static com.itextpdf.text.pdf.BaseFont.IDENTITY_H;
import static org.thymeleaf.templatemode.TemplateMode.HTML;

@Service
public class PdfService {

    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    AuthService authService;

    @PostConstruct
    public void templateInit(){
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(HTML);
        templateResolver.setCharacterEncoding("UTF-8");

        templateEngine.setTemplateResolver(templateResolver);
    }

    public byte[] generatePdf(Map<String, Object> data, Date date) throws DocumentException, IOException {


        // Parse and fill in html template
        Context context = new Context();

        UserPrincipal userPrincipal =authService.getPrincipal();

        context.setVariable("date",date);
        context.setVariable("data",data);
        context.setVariable("user",userPrincipal);
        String renderedHtmlContent = templateEngine.process("report",context);

        // Render pdf from html template
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.getFontResolver().addFont("./fonts/ARIALUNI.TTF", IDENTITY_H, EMBEDDED);
        renderer.setDocumentFromString(renderedHtmlContent);
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();

        return outputStream.toByteArray();
    }
}
