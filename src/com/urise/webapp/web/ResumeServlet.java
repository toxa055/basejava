package com.urise.webapp.web;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.SqlStorage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Writer writer;
        SqlStorage storage;
        List<Resume> resumes;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        writer = response.getWriter();
        storage = new SqlStorage("jdbc:postgresql://localhost:5432/resumes",
                "postgres", "password");
        resumes = storage.getAllSorted();

        writer.write("<table border=1>");
        writer.write("<tr><td>uuid</td>");
        writer.write("<td>full name</td></tr>");
        for (Resume resume : resumes) {
            writer.write("<tr><td>" + resume.getUuid() + "</td>");
            writer.write("<td>" + resume.getFullName() + "</td></tr>");
        }
        writer.write("</table>");
        writer.close();
    }
}
