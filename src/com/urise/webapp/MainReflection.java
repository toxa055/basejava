package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume("resume_with_first_uuid");

        Class<Resume> clazz = Resume.class;
        Field field = clazz.getDeclaredFields()[0];
        System.out.println(field.getName());
        field.setAccessible(true);
        System.out.println(field.get(resume));
        field.set(resume, "resume_with_modified_uuid");
        System.out.println(field.get(resume));
        field.setAccessible(false);
        System.out.println();

        Method method = clazz.getDeclaredMethod("toString");
        System.out.println(method.invoke(resume));
    }
}
