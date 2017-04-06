package ua.lazareva.userregister.web.utility;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class PageGenerator {

    private Configuration configuration = new Configuration();
    private static PageGenerator pageGeneratorInstance;

    public static PageGenerator instance() {
        if (pageGeneratorInstance == null) {
            pageGeneratorInstance = new PageGenerator();
        }
        return pageGeneratorInstance;
    }

    public String getPage(String pageName, Map<String, Object> pageVariables) {
        final String templatePath = "src"+File.separator+"main"+File.separator+"webapp"+File.separator +"templates" +File.separator;
        Writer streamOut = new StringWriter();
        try {
            Template pageTemplate = configuration.getTemplate(templatePath + pageName);
            pageTemplate.process(pageVariables, streamOut);
            return streamOut.toString();
        } catch (IOException e) {
            throw new RuntimeException("Template " + pageName + " is not found at " + templatePath + "\n" + e);
        } catch (TemplateException e) {
            throw new RuntimeException("Impossible to process template " + pageName + "\n" + e);
        }
    }

    public Map<String, Object> parseRequestParam(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();

        map.put("method", request.getMethod());
        map.put("URL", request.getRequestURL().toString());
        map.put("pathInfo", request.getPathInfo());
        map.put("parameters", request.getParameterMap().toString());
        map.put("sessionId", request.getSession().getId());
        return map;
    }

    public String getPostedValue(Map<String, String[]> parameters, String key) {
        String[] array = parameters.get(key);
        return array[0];
    }
}


