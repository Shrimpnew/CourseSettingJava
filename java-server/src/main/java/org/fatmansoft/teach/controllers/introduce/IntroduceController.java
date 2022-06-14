package org.fatmansoft.teach.controllers.introduce;

import com.itextpdf.text.pdf.BaseFont;
import org.fatmansoft.teach.payload.request.DataRequest;
import org.fatmansoft.teach.util.Base64ImgReplacedElementFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/introduce")
public class IntroduceController {
    @PostMapping("/previewInit")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void previewInit(HttpServletResponse httpServletResponse, @Valid @RequestBody DataRequest dataRequest){
        httpServletResponse.setContentType("application/pdf;charset=utf-8");
        httpServletResponse.setHeader("Access-Control-Expose-Headers","Content-Disposition");
        httpServletResponse.setHeader("Content-Disposition", "inline;filename=introduce.pdf");
        TemplateEngine templateEngine = new TemplateEngine();
        Context context = new Context();
        context.setVariable("tableList",dataRequest.getList("tableList"));
//        System.out.println("tableList:" + dataRequest.getList("tableList"));
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        templateEngine.setTemplateResolver(resolver);
        StringWriter stringWriter = new StringWriter();
        try(BufferedWriter writer = new BufferedWriter(stringWriter)) {
            templateEngine.process("static/introTemp.html",context, writer);
            writer.flush();
//            System.out.println("writer:" + stringWriter);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(stringWriter.toString().getBytes());
//            System.out.println("byteInputStream:" + byteArrayInputStream.readAllBytes());
            Document document = builder.parse(new ByteArrayInputStream(stringWriter.toString().getBytes()));
//            System.out.println("document:" + document);
            ITextRenderer renderer = new ITextRenderer();
            renderer.getSharedContext().setReplacedElementFactory(new Base64ImgReplacedElementFactory());
            renderer.getSharedContext().getTextRenderer().setSmoothingThreshold(0);
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont("font/simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.setDocument(document,null);
            renderer.layout();
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            renderer.createPDF(servletOutputStream);
//            renderer.createPDF(new FileOutputStream(new File("E:\\DATA\\Project\\Javawork1\\bin\\introduce.pdf")));
            servletOutputStream.flush();
            servletOutputStream.close();
        }catch (Exception e){
            //ResponseEnum.TEMPLATE_PARSE_ERROR.assertFail(e);
        }
    }
}
