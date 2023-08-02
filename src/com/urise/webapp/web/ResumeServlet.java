package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResumeServlet extends HttpServlet {
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        /*String name = request.getParameter("name");
        response.getWriter().write(name == null ? "Hello Resume!" : "Hello " + name + "!");*/
        PrintWriter writer = response.getWriter();
        StringBuilder sb = new StringBuilder();
        sb.append("<html>\n" +
                  "<style>\n" +
                  "table, th, td {\n" +
                  "  border:1px solid black;\n" +
                  "}\n" +
                  "</style>\n" +
                  "<body>\n" +
                  "\n" +
                  "<h2>Resumes</h2>");
        sb.append("""
                <table style="width:100%">
                    <tr>
                        <th>UUID</th>
                        <th>FULL NAME</th>
                    </tr>""");

        for (Resume resume : storage.getAllSorted()) {
            sb.append("<tr>\n" +
                      "    <td>" + resume.getUuid() + "</td>\n" +
                      "    <td>" + resume.getFullName() + "</td>\n" +
                      "</tr>");
        }
        sb.append("</table\n> </body\n </html>");
        writer.println(sb);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}